package com.ptit.filmdictionary.ui.follower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ItemHorizontalUserBinding;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class UserFollowAdapter extends RecyclerView.Adapter<UserFollowAdapter.ViewHolder> {
    private ItemHorizontalUserBinding mBinding;
    private List<UserResponse> mUsers = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private UserCallback mCallback;

    public UserFollowAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setCallback(UserCallback callback) {
        mCallback = callback;
    }

    public void setData(List<UserResponse> user) {
        mUsers.clear();
        mUsers.addAll(user);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(mInflater, R.layout.item_horizontal_user, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHorizontalUserBinding mBinding;
        private UserResponse mUser;

        public ViewHolder(@NonNull ItemHorizontalUserBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(view -> {
                if (mCallback != null) {
                    mCallback.onClickUser(mUser);
                }
            });
            mBinding.imageMore.setOnClickListener(view -> {
                if (mCallback != null) {
                    mCallback.onClickMore(mUser);
                }
            });
        }

        public void bindData (UserResponse user) {
            mUser = user;
            ImageHelper.loadImage(mBinding.imageAvatar, user.getAvatar());
            mBinding.textUserName.setText(user.getUserName());
        }
    }

    public interface UserCallback {
        void onClickUser(UserResponse user);
        void onClickMore(UserResponse user);
    }
}
