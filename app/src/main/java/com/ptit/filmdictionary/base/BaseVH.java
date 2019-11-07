package com.ptit.filmdictionary.base;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by vanh1200 on 07/11/2019
 */
public abstract class BaseVH extends RecyclerView.ViewHolder {
    protected BaseFeed data;

    public void setData(BaseFeed data) {
        this.data = data;
    }

    public BaseVH(@NonNull ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
    }

    public abstract void bindData(BaseFeed baseFeed);

}
