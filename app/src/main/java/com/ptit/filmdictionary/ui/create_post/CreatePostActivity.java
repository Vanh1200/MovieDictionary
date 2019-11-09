package com.ptit.filmdictionary.ui.create_post;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.databinding.ActivityCreatePostBinding;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRAS_IMAGE_PATH = "image_path";
    private ActivityCreatePostBinding mBinding;
    private static final int REQUEST_PERMISSION_CODE_STORAGE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 3;
    private static final int REQUEST_IMAGE_TAKEN_BY_CAM = 2;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String imageFilePath = "";

    public static void start(Context context, String imagePath) {
        Intent intent = new Intent(context, CreatePostActivity.class);
        intent.putExtra(EXTRAS_IMAGE_PATH, imagePath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        getIncomingData();
        initListeners();
    }

    private void getIncomingData() {
        imageFilePath = getIntent().getStringExtra(EXTRAS_IMAGE_PATH);
        if (!imageFilePath.isEmpty()) {
            ImageHelper.loadImage(mBinding.imagePost, imageFilePath);
        }
    }

    private void initListeners() {
        mBinding.layoutBottomCreatePost.imageCamera.setOnClickListener(this);
        mBinding.textDone.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_done:
                Toast.makeText(this, "Click Done", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_camera:
                Toast.makeText(this, "Click Image", Toast.LENGTH_SHORT).show();
                if (checkPermissionCreatePost()) {
                    openCameraIntent();
                } else {
                    requestPermissionCamera();
                }
                break;
        }
    }

    private boolean checkPermissionCreatePost() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        requestPermissions(permissions, REQUEST_PERMISSION_CAMERA);
    }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_TAKEN_BY_CAM:
                if (resultCode == RESULT_OK) {
//                    Toast.makeText(this, imageFilePath, Toast.LENGTH_SHORT).show();
                    ImageHelper.loadImage(mBinding.imagePost, imageFilePath);
                }
                break;
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE_TAKEN_BY_CAM);
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

}
