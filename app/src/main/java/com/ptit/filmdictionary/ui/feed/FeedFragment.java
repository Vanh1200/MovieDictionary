package com.ptit.filmdictionary.ui.feed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.base.BaseFeed;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentFeedBinding;
import com.ptit.filmdictionary.ui.comment.CommentDialogFragment;
import com.ptit.filmdictionary.ui.create_post.CreatePostActivity;
import com.ptit.filmdictionary.ui.feed.card.card_text_image.CardTextImage;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends Fragment implements FeedCallback, View.OnClickListener {
    private static final int REQUEST_PERMISSION_CODE_STORAGE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 3;
    private static final int REQUEST_IMAGE_TAKEN_BY_CAM = 2;
    private FragmentFeedBinding mBinding;

    @Inject
    FeedViewModel mFeedViewModel;

    @Inject
    PreferenceUtil mPreferenceUtil;

    private FeedAdapter mFeedAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<BaseFeed> mData = new ArrayList<>();
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String imageFilePath = "";

    public static FeedFragment newInstance() {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        initComponents();
        initListeners();
//        fakeData();
        loadFeed();
        observeData();
        return mBinding.getRoot();
    }

    private void loadFeed() {
        mFeedViewModel.loadFeed(mPreferenceUtil.getUserId());
    }

    private void observeData() {
        mFeedViewModel.getLiveFeed().observe(this, data -> {
            mFeedAdapter.setData(data);
        });
    }

    private boolean checkPermissionFeed() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermissionCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            getActivity().requestPermissions(permissions, REQUEST_PERMISSION_CAMERA);
        }
    }

    private void fakeData() {
        CardTextImage CardTextImage = new CardTextImage();
        CardTextImage.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage1 = new CardTextImage();
        CardTextImage1.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage2 = new CardTextImage();
        CardTextImage2.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage3 = new CardTextImage();
        CardTextImage3.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage4 = new CardTextImage();
        CardTextImage4.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage5 = new CardTextImage();
        CardTextImage5.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage6 = new CardTextImage();
        CardTextImage6.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage7 = new CardTextImage();
        CardTextImage7.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage8 = new CardTextImage();
        CardTextImage8.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage9 = new CardTextImage();
        CardTextImage9.setCardType(CardType.CARD_TEXT_IMAGE);

        CardTextImage CardTextImage10 = new CardTextImage();
        CardTextImage10.setCardType(CardType.CARD_TEXT_IMAGE);

        mData.add(CardTextImage);
        mData.add(CardTextImage1);
        mData.add(CardTextImage2);
        mData.add(CardTextImage3);
        mData.add(CardTextImage4);
        mData.add(CardTextImage5);
        mData.add(CardTextImage6);
        mData.add(CardTextImage7);
        mData.add(CardTextImage8);
        mData.add(CardTextImage9);
        mData.add(CardTextImage10);

        mFeedAdapter.setData(mData);
    }

    private void initListeners() {
        mBinding.layoutToolbarFeed.imageAvatar.setOnClickListener(this);
        mBinding.layoutToolbarFeed.imageSearch.setOnClickListener(this);
        mBinding.layoutToolbarFeed.imageNotification.setOnClickListener(this);
    }

    private void initComponents() {
        mFeedAdapter = new FeedAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.recyclerFeed.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerFeed.setAdapter(mFeedAdapter);
        mFeedAdapter.setCallback(this);
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
                    CreatePostActivity.start(getActivity(), imageFilePath);
                }
                break;
        }
    }

    @Override
    public void onClickImageCreatePost() {
        Toast.makeText(getActivity(), "Click Image", Toast.LENGTH_SHORT).show();
        if (checkPermissionFeed()) {
            openCameraIntent();
        } else {
            requestPermissionCamera();
        }
    }

    @Override
    public void onClickVideoCreatePost() {
        Toast.makeText(getActivity(), "Click Video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickReviewCreatePost() {
        Toast.makeText(getActivity(), "Click Review", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickAvatarCreatePost() {
        Toast.makeText(getActivity(), "Click Avatar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickTextCreatePost() {
        CreatePostActivity.start(getActivity(), "");
    }

    @Override
    public void onClickUser(UserResponse userResponse, int position) {
        ProfileActivity.start(getActivity(), userResponse);
    }

    @Override
    public void onClickHeart(BaseFeed item, int position) {
        Toast.makeText(getActivity(), "Click Heart at" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickComment(BaseFeed item, int position) {
        CommentDialogFragment.newInstance(item.getId()).show(getChildFragmentManager(), null);
    }

    @Override
    public void onClickImage(BaseFeed item, int position) {
        Toast.makeText(getActivity(), "Click Image at" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickVideo(BaseFeed item, int position) {
        Toast.makeText(getActivity(), "Click Video at" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMovie(BaseFeed item, int position) {
        Toast.makeText(getActivity(), "Click Movie at" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_avatar:
                Toast.makeText(getActivity(), "Click Avatar at toolbar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_search:
                Toast.makeText(getActivity(), "Click Search at toolbar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_notification:
                Toast.makeText(getActivity(), "Click Notice at toolbar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE_TAKEN_BY_CAM);
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }
}
