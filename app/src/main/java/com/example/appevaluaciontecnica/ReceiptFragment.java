package com.example.appevaluaciontecnica;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.metrics.EditingSession;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appevaluaciontecnica.databinding.FragmentReceiptBinding;
import com.example.appevaluaciontecnica.ui.Global;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ReceiptFragment extends Fragment {


    private FragmentReceiptBinding binding;
    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {

            return;
        }
        Snackbar.make(Objects.requireNonNull(getView()), "Not enough permissions to access device storage", Snackbar.LENGTH_LONG).show();
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReceiptBinding.inflate(inflater, container, false);
        binding.amount.setText(
                Double.toString(
                        Global
                                .lastTransaction
                                .getDetails()
                                .stream()
                                .mapToDouble(
                                        detail -> Double.parseDouble(detail.getAmount())
                                ).sum()
                )
        );
        binding.targetAccount.setText(Global.lastTransaction.getDetails().get(0).getTarget_account());
        binding.transactionType.setText(Global.lastTransaction.getTransaction_type().getName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");
        binding.date.setText(
                LocalDateTime.parse(
                        Global.lastTransaction.getOperated_at(),
                        DateTimeFormatter.ISO_DATE_TIME
                ).format(dtf)
        );
        binding.transactionId.setText(Long.toString(Global.lastTransaction.getId()));
        binding.notes.setText(Global.lastTransaction.getNotes());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.downaloadReceipt.setOnClickListener(view1 -> {
            if (
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                saveImageInQ(view);
                return;
            }
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            saveImageInQ(view);

        });

    }

    private void saveImageInQ(View view) {

        View receipt = binding.receipt;
        receipt.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(receipt.getDrawingCache());
        receipt.setDrawingCacheEnabled(false);
        // save image to storage
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,
                String.format(
                        "Receipt-%s-%s.jpeg",
                        Global.lastTransaction.getTransaction_type().getName(),
                        Global.lastTransaction.getOperated_at()
                ));
        contentValues.put(MediaStore.Files.FileColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
        ContentResolver resolver = getContext().getApplicationContext().getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {

            OutputStream outputStream = resolver.openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            contentValues.clear();
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
            resolver.update(uri, contentValues, null, null);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"image/jpeg");
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(view, "Ups could not save the Receipt", Snackbar.LENGTH_LONG).show();
        }
    }

    private void saveImage(View view) {

        View receipt = binding.receipt;
        receipt.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(receipt.getDrawingCache());
        receipt.setDrawingCacheEnabled(false);
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Downloads";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("dir did not exist");
        }
        File file = new File(
                dirPath,
                String.format(
                        "Receipt-%s-%s.jpeg",
                        Global.lastTransaction.getTransaction_type().getName(),
                        Global.lastTransaction.getOperated_at()
                )
        );
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, "Ups could not save the Receipt", Snackbar.LENGTH_LONG).show();
        }
    }
}