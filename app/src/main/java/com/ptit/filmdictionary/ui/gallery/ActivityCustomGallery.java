package com.ptit.filmdictionary.ui.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.ImageAndVideoModel;
import com.ptit.filmdictionary.data.source.local.GalleryLoader;
import com.ptit.filmdictionary.databinding.ActivityCustomGalleryBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by vanh1200 on 09/11/2019
 */
public class ActivityCustomGallery extends AppCompatActivity implements GalleryLoader.GalleryLoaderCallback, GalleryAdapter.GalleryCallback, View.OnClickListener {
    public final static String EXTRAS_LIST_IMAGE_VIDEO = "intent_image_video";
    private static final int REQUEST_PERMISSION_CAMERA = 3;
    private static final int REQUEST_IMAGE_TAKEN_BY_CAM = 2;
    private ActivityCustomGalleryBinding mBinding;
    private GalleryLoader mLoader;
    private GalleryAdapter mGalleryAdapter;
    private ArrayList<ImageAndVideoModel> mImageAndVideoModels = new ArrayList<>();
    private int countColumn = 3;
    private String imageFilePath = "";
    private String[] permissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_custom_gallery);
        mLoader = new GalleryLoader(this);
        initComponents();
        loadGallery();
        initListeners();
    }

    private void initListeners() {
        mBinding.imageCamera.setOnClickListener(this);
        mBinding.textDone.setOnClickListener(this);
    }

    private void loadGallery() {
        mLoader.execute();
    }

    private void initComponents() {
        mGalleryAdapter = new GalleryAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, countColumn);
        int spanCount = countColumn; // 3 columns
        int spacing = 12; // 20px
        boolean includeEdge = false;
        mBinding.recyclerGallery.setLayoutManager(gridLayoutManager);
        mBinding.recyclerGallery.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mBinding.recyclerGallery.setAdapter(mGalleryAdapter);
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
                    ImageAndVideoModel imageAndVideoModel = new ImageAndVideoModel();
                    imageAndVideoModel.setUrl(imageFilePath);
                    imageAndVideoModel.setThumb(imageFilePath);
                    imageAndVideoModel.setMeDiaType(MediaType.TYPE_IMAGE);
                    mImageAndVideoModels.clear();
                    mImageAndVideoModels.add(imageAndVideoModel);
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(EXTRAS_LIST_IMAGE_VIDEO, mImageAndVideoModels);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onLoadedGallery(List<ImageAndVideoModel> data) {
        mGalleryAdapter.setData(data);
    }

    @Override
    public void onChosenList(List<ImageAndVideoModel> models) {
        mImageAndVideoModels = (ArrayList<ImageAndVideoModel>) models;
        if (models != null) {
            if (models.size() > 0) {
                mBinding.textDone.setVisibility(View.VISIBLE);
                mBinding.imageCamera.setVisibility(View.GONE);
            } else {
                mBinding.textDone.setVisibility(View.GONE);
                mBinding.imageCamera.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_done:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(EXTRAS_LIST_IMAGE_VIDEO, mImageAndVideoModels);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.image_camera:
                if (checkPermissionCamera()) {
                    openCameraIntent();
                } else {
                    requestPermissionCamera();
                }
                break;
        }
    }

    private void requestPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSION_CAMERA);
        }
    }

    private boolean checkPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile;
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
