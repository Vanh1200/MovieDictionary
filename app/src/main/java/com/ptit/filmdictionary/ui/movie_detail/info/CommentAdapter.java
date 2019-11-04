package com.ptit.filmdictionary.ui.movie_detail.info;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.databinding.ItemCommentBinding;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanh1200 on 21/10/2019
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentResponse> mComments = new ArrayList<>();
    private LayoutInflater mInflater;

    public CommentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<CommentResponse> comments) {
        mComments.clear();
        mComments.addAll(comments);
        notifyDataSetChanged();
    }

    public void addItem(CommentResponse comment) {
        mComments.add(comment);
        notifyItemInserted(mComments.size() - 1);
    }

    public void removeItem(CommentResponse comment) {
        int pos = mComments.indexOf(comment);
        mComments.remove(comment);
        notifyItemRemoved(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCommentBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_comment, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mComments.get(i));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding mBinding;

        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(CommentResponse comment) {
            ImageHelper.loadImageCircle(mBinding.imageAvatar, comment.getUser().getAvatar());
            mBinding.textComment.setText(comment.getContent());
            mBinding.textUserName.setText(comment.getUser().getUserName());
            if (comment.getCreatedAt() != 0) {
                mBinding.textTimeAgo.setText(BaseHelper.convertTimeStampToTimeAgo(comment.getCreatedAt()));
            }
        }

    }
}
