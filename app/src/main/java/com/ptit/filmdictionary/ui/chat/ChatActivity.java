package com.ptit.filmdictionary.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ActivityChatBinding;
import com.ptit.filmdictionary.utils.ImageHelper;
import com.ptit.filmdictionary.utils.ResizeWidthAnimation;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRAS_INTERACTIVE_USER = "interactive_user";
    public static final int PICK_IMAGE_FROM_GALLERY = 101;
    public static final int PICK_IMAGE_FROM_CAMERA = 102;
    public static final String TYPE_INTENT_PICK_IMAGE = "image/*";
    private static final int DURATION_300 = 300;
    private static final int WIDTH_0 = 0;
    private ActivityChatBinding mBinding;
    private ChatAdapter mChatAdapter;
    //    private UserResponse mCurrentUser;
    private String mCurrentUserId;
    private UserResponse mInteractiveUser;
    private boolean isTextMessageZoomOut;
    private int mWidthTextMessage;
    private int mWidthLayoutCollapse;

    @Inject
    ChatViewModel mViewModel;

    @Inject
    PreferenceUtil mPreferenceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        AndroidInjection.inject(this);
        getIncomingData();
        initComponents();
        initListeners();
        loadData();
        observeLiveData();
    }

    private void observeLiveData() {
        mViewModel.getLiveMessages().observe(this, data -> {
            mChatAdapter.setData(data);
        });
    }

    private void loadData() {
        mViewModel.loadMessages(mPreferenceUtil.getUserId(), "5d9cc76c3405950023ab0bd1");
    }

    private void initComponents() {
        ImageHelper.loadImageCircle(mBinding.layoutToolbarChat.imageAvatar, mInteractiveUser.getAvatar());
        mBinding.layoutToolbarChat.textName.setText(mInteractiveUser.getUserName());
        mWidthTextMessage = getResources().getDimensionPixelOffset(R.dimen.dp_180);
        mWidthLayoutCollapse = getResources().getDimensionPixelOffset(R.dimen.dp_84);
        mChatAdapter = new ChatAdapter(this, mCurrentUserId);
        mBinding.recyclerMessage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        mBinding.recyclerMessage.setAdapter(mChatAdapter);
        setupAnimationTextMessage();
    }

    private void initListeners() {
        mBinding.layoutBottomBarChat.imageAdd.setOnClickListener(this);
        mBinding.layoutBottomBarChat.imagePhoto.setOnClickListener(this);
        mBinding.layoutBottomBarChat.imageCamera.setOnClickListener(this);
        mBinding.layoutToolbarChat.imageBack.setOnClickListener(this);
    }

    private void getIncomingData() {
        mInteractiveUser = getIntent().getParcelableExtra(EXTRAS_INTERACTIVE_USER);
        mCurrentUserId = mPreferenceUtil.getUserId();
    }

    public static void start(Context context, UserResponse interactiveUser) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRAS_INTERACTIVE_USER, interactiveUser);
        context.startActivity(intent);
    }

    private void setupAnimationTextMessage() {
        mBinding.layoutBottomBarChat.textMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                zoomOutTextMessage();
                changeResourceImageAdd();
            }
        });
        mBinding.layoutBottomBarChat.textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOutTextMessage();
            }
        });
        mBinding.layoutBottomBarChat.textMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isTextMessageZoomOut && s.length() > 0) {
                    zoomOutTextMessage();
                }
                if (TextUtils.isEmpty(s) || count == 0) {
                    zoomInTextMessage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void changeResourceImageAdd() {
        if (!isTextMessageZoomOut) {
            mBinding.layoutBottomBarChat.imageAdd.setImageResource(R.drawable.ic_add);
        } else {
            mBinding.layoutBottomBarChat.imageAdd.setImageResource(R.drawable.ic_forward);
        }
    }

    private void zoomInTextMessage() {
        setChangeWidthAnimation(mBinding.layoutBottomBarChat.textMessage, mWidthTextMessage, DURATION_300);
        setChangeWidthAnimation(mBinding.layoutBottomBarChat.linearCollapse, mWidthLayoutCollapse, DURATION_300);
        isTextMessageZoomOut = false;
        changeResourceImageAdd();
    }

    private void zoomOutTextMessage() {
        setChangeWidthAnimation(mBinding.layoutBottomBarChat.textMessage,
                mWidthTextMessage + mWidthLayoutCollapse, DURATION_300);
        setChangeWidthAnimation(mBinding.layoutBottomBarChat.linearCollapse, WIDTH_0, DURATION_300);
        isTextMessageZoomOut = true;
        changeResourceImageAdd();
    }

    private void setChangeWidthAnimation(View view, int width, int duration) {
        ResizeWidthAnimation resizeWidthAnimation = new ResizeWidthAnimation(view, width);
        resizeWidthAnimation.setDuration(duration);
        view.clearAnimation();
        view.setAnimation(resizeWidthAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add:
                if (isTextMessageZoomOut) {
                    zoomInTextMessage();
                }
                break;
            case R.id.image_send:
                break;
            case R.id.image_photo:
                break;
            case R.id.image_camera:
                break;
            case R.id.image_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
