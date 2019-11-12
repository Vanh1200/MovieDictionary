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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.model.ImageAndVideoModel;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.databinding.ActivityCreatePostBinding;
import com.ptit.filmdictionary.ui.feed.CardType;
import com.ptit.filmdictionary.ui.gallery.ActivityCustomGallery;
import com.ptit.filmdictionary.ui.gallery.MediaType;
import com.ptit.filmdictionary.utils.Constants;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRAS_IMAGE_PATH = "image_path";
    private ActivityCreatePostBinding mBinding;
    private static final int REQUEST_PERMISSION_GALLERY = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 3;
    private static final int REQUEST_IMAGE_TAKEN_BY_CAM = 2;
    private static final int REQUEST_CUSTOM_GALLERY = 4;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String imageFilePath = "";
    private List<ImageAndVideoModel> mImageAndVideoModels = new ArrayList<>();
    private BaseFeed mBaseFeed = new BaseFeed(); // base feed de upload
    private String image = "";

    @Inject
    CreatePostViewModel mViewModel;

    @Inject
    PreferenceUtil mPreferenceUtil;

    public static void start(Context context, String imagePath) {
        Intent intent = new Intent(context, CreatePostActivity.class);
        intent.putExtra(EXTRAS_IMAGE_PATH, imagePath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        getIncomingData();
        observeData();
        initListeners();
    }

    private void observeData() {
        mViewModel.getLiveFileRes().observe(this, data -> {
            image = Constants.BASE_VANH_URL + "file/" + data.getName();
            createPost();
        });
        mViewModel.getLiveCreatePost().observe(this, data -> {

        });
    }

    private void getIncomingData() {
        imageFilePath = getIntent().getStringExtra(EXTRAS_IMAGE_PATH);
        if (!imageFilePath.isEmpty()) {
            ImageHelper.loadImage(mBinding.imagePost, imageFilePath);
            ImageAndVideoModel imageAndVideoModel = new ImageAndVideoModel();
            imageAndVideoModel.setUrl(imageFilePath);
            imageAndVideoModel.setThumb(imageFilePath);
            imageAndVideoModel.setMeDiaType(MediaType.TYPE_IMAGE);
            mImageAndVideoModels.clear();
            mImageAndVideoModels.add(imageAndVideoModel);
        }
    }

    private void initListeners() {
        mBinding.layoutBottomCreatePost.layoutCamera.setOnClickListener(this);
        mBinding.layoutBottomCreatePost.layoutImageVideo.setOnClickListener(this);
        mBinding.textDone.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_done:
                if (mImageAndVideoModels.size() > 0) {
                    uploadData();
                } else {
                    createPost();
                }
                break;
            case R.id.layout_camera:
                if (checkPermissionCreatePost()) {
                    openCameraIntent();
                } else {
                    requestPermissionCamera();
                }
                break;
            case R.id.layout_image_video:
                if (checkPermissionCreatePost()) {
                    openCustomGallery();
                } else {
                    requestPermissionGallery();
                }
                break;
        }

    }

    private void createPost() {
        mBaseFeed.setCardType(CardType.CARD_TEXT_IMAGE); // todo tam thoi hard code the nay da
        if (image.isEmpty() && mBinding.textQuestion.getText().toString().isEmpty()) {
            return;
        }
        mBaseFeed.setText(mBinding.textQuestion.getText().toString());
        mBaseFeed.setImageUrl(image);
        mViewModel.createPost(mPreferenceUtil.getUserId(), mBaseFeed);
    }

    private void uploadData() {
        if (mImageAndVideoModels.size() > 0) {
            mViewModel.uploadFile(mImageAndVideoModels.get(0).getUrl());
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

    private void requestPermissionGallery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSION_GALLERY);
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
            case REQUEST_PERMISSION_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCustomGallery();
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
                    ImageHelper.loadImage(mBinding.imagePost, imageFilePath);
                    ImageAndVideoModel imageAndVideoModel = new ImageAndVideoModel();
                    imageAndVideoModel.setUrl(imageFilePath);
                    imageAndVideoModel.setThumb(imageFilePath);
                    imageAndVideoModel.setMeDiaType(MediaType.TYPE_IMAGE);
                    mImageAndVideoModels.clear();
                    mImageAndVideoModels.add(imageAndVideoModel);
                }
                break;
            case REQUEST_CUSTOM_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    mImageAndVideoModels = data.getParcelableArrayListExtra(ActivityCustomGallery.EXTRAS_LIST_IMAGE_VIDEO);
                    if (mImageAndVideoModels.get(0) != null) {
                        ImageHelper.loadImage(mBinding.imagePost, mImageAndVideoModels.get(0).getUrl());
                    }
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

    private void openCustomGallery() {
        Intent intent = new Intent(this, ActivityCustomGallery.class);
        startActivityForResult(intent, REQUEST_CUSTOM_GALLERY);
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
