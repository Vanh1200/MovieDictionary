package com.ptit.filmdictionary.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ChatActivity extends AppCompatActivity {
    public static final String EXTRAS_INTERACTIVE_USER = "interactive_user";
    private ActivityChatBinding mBinding;
    private ChatAdapter mChatAdapter;
//    private UserResponse mCurrentUser;
    private String mCurrentUserId;
    private UserResponse mInteractiveUser;

    @Inject
    ChatViewModel mViewModel;

    @Inject
    PreferenceUtil mPreferenceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        AndroidInjection.inject(this);
        getIncomingData(savedInstanceState);
        initComponents();
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
        ImageHelper.loadImageCircle(mBinding.toolBar.imageAvatar, mInteractiveUser.getAvatar());
        mBinding.toolBar.textName.setText(mInteractiveUser.getUserName());
        mChatAdapter = new ChatAdapter(this, mCurrentUserId);
        mBinding.recyclerMessage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        mBinding.recyclerMessage.setAdapter(mChatAdapter);
    }

    private void getIncomingData(Bundle bundle) {
        if (bundle != null) {
            mInteractiveUser = bundle.getParcelable(EXTRAS_INTERACTIVE_USER);
        }
        mCurrentUserId = mPreferenceUtil.getUserId();
    }

    public static void start (Context context, UserResponse interactiveUser) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRAS_INTERACTIVE_USER, interactiveUser);
        context.startActivity(intent);
    }
}
