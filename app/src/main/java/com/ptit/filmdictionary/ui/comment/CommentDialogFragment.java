package com.ptit.filmdictionary.ui.comment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.databinding.FragmentCommentBinding;
import com.ptit.filmdictionary.ui.movie_detail.info.CommentAdapter;
import com.ptit.filmdictionary.utils.Constants;
import com.ptit.filmdictionary.utils.ImageHelper;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by vanh1200 on 23/10/2019
 */
public class CommentDialogFragment extends BottomSheetDialogFragment {
    private FragmentCommentBinding mBinding;
    private int movieId;
    private CommentAdapter mCommentAdapter;
    private CommentViewModel mViewModel;

    @Inject
    ViewModelFactory mViewModelFactory;

    @Inject
    PreferenceUtil mPreferenceUtil;

    public static CommentDialogFragment newInstance(int movieId) {

        Bundle args = new Bundle();
        args.putInt(Constants.ARGUMENT_MOVIE_ID, movieId);
        CommentDialogFragment fragment = new CommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
        movieId = getArguments().getInt(Constants.ARGUMENT_MOVIE_ID);
    }

    private void observeData() {
        mViewModel.getLiveComments().observe(this, data -> {
            if (data != null) {
                mCommentAdapter.setData(data);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CommentViewModel.class);
        initViews();
        initAdapters();
        observeData();
        mViewModel.loadComments(movieId);
        return mBinding.getRoot();
    }

    private void initViews() {
        ImageHelper.loadImage(mBinding.layoutPostComment.imageAvatar, mPreferenceUtil.getUserAvatar());
    }

    private void initAdapters() {
        mCommentAdapter = new CommentAdapter(getContext());
        mBinding.recyclerComment.setAdapter(mCommentAdapter);
        mBinding.recyclerComment.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal =
                    d.findViewById(android.support.design.R.id.design_bottom_sheet);
            if (bottomSheetInternal == null) {
                return;
            }
            bottomSheetInternal.setBackground(null);
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheetInternal);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
    }
}
