package com.ptit.filmdictionary.ui.search_user;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentSearchUserBinding;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;
import com.ptit.filmdictionary.ui.search.SearchViewModel;


public class SearchUserFragment extends Fragment implements UserHorizontalAdapter.UserCallback {
    private FragmentSearchUserBinding mBinding;
    private SearchViewModel mSearchViewModel;
    private UserHorizontalAdapter mUserAdapter;

    public void setViewModel(SearchViewModel searchViewModel) {
        mSearchViewModel = searchViewModel;
    }

    public static SearchUserFragment newInstance() {

        Bundle args = new Bundle();

        SearchUserFragment fragment = new SearchUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user, container, false);
        initComponents();
        observeLiveData();
        return mBinding.getRoot();
    }

    private void observeLiveData() {
        mSearchViewModel.liveSearchUsers.observe(this, data -> {
            mUserAdapter.setData(data);
        });
    }

    private void initComponents() {
        mUserAdapter = new UserHorizontalAdapter(getActivity());
        mUserAdapter.setCallback(this);
        mBinding.recyclerUsers.setAdapter(mUserAdapter);
    }


    @Override
    public void onClickUser(UserResponse user) {
        ProfileActivity.start(getActivity(), user);
    }

    @Override
    public void onClickMore(UserResponse user) {

    }
}
