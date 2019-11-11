package com.ptit.filmdictionary.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.ImageAndVideoModel;
import com.ptit.filmdictionary.databinding.ItemGalleryBinding;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vanh1200 on 09/11/2019
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<ImageAndVideoModel> mImageAndVideoModels = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemGalleryBinding mBinding;
    private List<ImageAndVideoModel> mSelectedItem = new ArrayList<>();
    private GalleryCallback mCallback;

    public void setData(List<ImageAndVideoModel> models) {
        if (models != null) {
            mImageAndVideoModels.clear();
            mImageAndVideoModels.addAll(models);
            notifyDataSetChanged();
        }
    }

    public GalleryAdapter(Context context, GalleryCallback callback) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(mInflater, R.layout.item_gallery, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mImageAndVideoModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageAndVideoModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageAndVideoModel mItem;
        private ItemGalleryBinding mBinding;

        ViewHolder(@NonNull ItemGalleryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(v -> {
                mItem.setSelect(!mItem.isSelect());
                if (mItem.isSelect()) {
                    mBinding.imageSelected.setVisibility(View.VISIBLE);
                    mSelectedItem.add(mItem);
                } else {
                    mBinding.imageSelected.setVisibility(View.GONE);
                    mSelectedItem.remove(mItem);
                }
                if (mCallback != null) {
                    mCallback.onChosenList(mSelectedItem);
                }
            });
        }

        public void bindData(ImageAndVideoModel item) {
            mItem = item;
            if (item.getMeDiaType() == MediaType.TYPE_IMAGE) {
                mBinding.llVideoInfo.setVisibility(View.GONE);
            }
            if (item.getMeDiaType() == MediaType.TYPE_VIDEO) {
                mBinding.llVideoInfo.setVisibility(View.VISIBLE);
                mBinding.textDuration.setText(calculateDuration(item.getVideoDur()));
            }
            if (item.isSelect()) {
                mBinding.imageSelected.setVisibility(View.VISIBLE);
            } else {
                mBinding.imageSelected.setVisibility(View.GONE);
            }
            ImageHelper.loadImage(mBinding.imageThumb, item.getUrl());
        }
    }

    private String calculateDuration(long duration) {
        int durationInSecond = (int) (duration / 1000);
        int hours = durationInSecond / 3600;
        int minutes = (durationInSecond / 60) - (hours * 60);
        int seconds = durationInSecond - (hours * 3600) - (minutes * 60);
        if (hours == 0) {
            return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        }
    }

    public interface GalleryCallback {
        void onChosenList(List<ImageAndVideoModel> models);
    }
}
