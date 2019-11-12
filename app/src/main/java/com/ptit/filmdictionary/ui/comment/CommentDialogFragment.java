package com.ptit.filmdictionary.ui.comment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.socket.SocketData;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.request.CommentBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.FragmentCommentBinding;
import com.ptit.filmdictionary.ui.movie_detail.info.CommentAdapter;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.Constants;
import com.ptit.filmdictionary.utils.ImageHelper;
import com.ptit.filmdictionary.utils.MyApplication;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by vanh1200 on 23/10/2019
 */
public class CommentDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener, CommentAdapter.OnCommentListener {
    private static final String TAG = "CommentDialogFragment";
    private FragmentCommentBinding mBinding;
    private int movieId;
    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mCommentLayoutManager;
    private CommentViewModel mViewModel;
    private CommentResponse mCommentResponse; // comment response to add local before done comment
    private Socket mSocket;

    private static final int TYPING_TIMER_LENGTH = 600;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();

    private Boolean isConnected = true;

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
        connectSocket();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnectSocket();
    }

    private void disconnectSocket() {
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off(SocketData.Event.NEW_COMMENT, onNewComment);
        mSocket.off(SocketData.Event.COMMENT_TYPING, onTyping);
        mSocket.off(SocketData.Event.COMMENT_STOP_TYPING, onStopTyping);
    }

    private void connectSocket() {
        mSocket = ((MyApplication) getActivity().getApplication()).getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(SocketData.Event.NEW_COMMENT, onNewComment);
        mSocket.on(SocketData.Event.COMMENT_TYPING, onTyping);
        mSocket.on(SocketData.Event.COMMENT_STOP_TYPING, onStopTyping);
        mSocket.connect();
    }

    private void observeData() {
        mViewModel.getLiveComments().observe(this, data -> {
            if (data != null) {
                mCommentAdapter.setData(data);
            }
        });
        mViewModel.getLivePostComment().observe(this, data -> {
            if (data != null) {
                mCommentResponse.setCreatedAt(data.getCreatedAt());
                mCommentAdapter.notifyDataSetChanged();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(SocketData.Key.TRAILER_ID, movieId + "");
                jsonObject.add(SocketData.Key.COMMENT_JSON, new Gson().toJsonTree(data));

                mSocket.emit(SocketData.Event.NEW_COMMENT, new Gson().toJson(jsonObject));
                Log.d(TAG, "observeData: " + new Gson().toJson(jsonObject));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CommentViewModel.class);
        initViews();
        initListeners();
        initAdapters();
        observeData();
        mViewModel.loadComments(movieId);
        return mBinding.getRoot();
    }

    private void initListeners() {
        mBinding.layoutPostComment.imageSend.setOnClickListener(this);
        mBinding.layoutPostComment.textComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    mSocket.emit(SocketData.Event.COMMENT_TYPING, movieId + "");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initViews() {
        ImageHelper.loadImage(mBinding.layoutPostComment.imageAvatar, mPreferenceUtil.getUserAvatar());
    }

    private void initAdapters() {
        mCommentAdapter = new CommentAdapter(getContext());
        mCommentAdapter.setListener(this);
        mCommentLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerComment.setAdapter(mCommentAdapter);
        mBinding.recyclerComment.setLayoutManager(mCommentLayoutManager);
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
                    d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheetInternal == null) {
                return;
            }
            bottomSheetInternal.setBackground(null);
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheetInternal);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_send:
                handleSendComment();
                break;
            default:
                break;
        }
    }

    private void handleSendComment() {
        if (!BaseHelper.isInternetOn(getContext())) {
//            BaseHelper.showCustomSnackbarView(mBinding.viewOne, getContext(), getString(R.string.text_no_internet));
            return;
        }
        if (mBinding.layoutPostComment.textComment.getText().toString().isEmpty()) {
            BaseHelper.showToast(getContext(), getContext().getResources().getString(R.string.comment_invalid_input));
            return;
        }
        mViewModel.postComments(movieId,
                new CommentBody(mPreferenceUtil.getUserId(), mBinding.layoutPostComment.textComment.getText().toString()));
        mCommentResponse = new CommentResponse();
        mCommentResponse.setTrailerId(movieId + "");
        UserResponse userResponse = new UserResponse();
        userResponse.setAvatar(mPreferenceUtil.getUserAvatar());
        userResponse.setUserName(mPreferenceUtil.getUserName());
        userResponse.setId(mPreferenceUtil.getUserId());
        mCommentResponse.setContent(mBinding.layoutPostComment.textComment.getText().toString());
        mCommentResponse.setUser(userResponse);
        mCommentAdapter.addItem(mCommentResponse);
        mCommentLayoutManager.scrollToPosition(mCommentAdapter.getItemCount() - 1);
        mBinding.layoutPostComment.textComment.setText("");
        BaseHelper.hideKeyboardFrom(getContext(), mBinding.getRoot());
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        Log.i(TAG, "connected");
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewComment = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String movieId = "";
                    CommentResponse comment = null;
                    String data = (String) args[0];
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        movieId = jsonObject.optString(SocketData.Key.TRAILER_ID);
                        JSONObject commentObject = jsonObject.optJSONObject(SocketData.Key.COMMENT_JSON);
                        comment = new Gson().fromJson(commentObject.toString(), CommentResponse.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!movieId.isEmpty() && movieId.equals(CommentDialogFragment.this.movieId + "") && comment != null) {
                        addComment(comment);
                    }
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String movieId = (String) args[0];
                    if (!movieId.isEmpty() && movieId.equals(CommentDialogFragment.this.movieId + "")) {
                        addTyping();
                    }
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String movieId = (String) args[0];
                    if (!movieId.isEmpty() && movieId.equals(CommentDialogFragment.this.movieId + "")) {
                        removeTyping();
                    }
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            mSocket.emit(SocketData.Event.COMMENT_STOP_TYPING, movieId + "");
        }
    };

    private void addTyping() {
        mBinding.layoutSomeoneTyping.getRoot().setVisibility(View.VISIBLE);
    }

    private void removeTyping() {
        mBinding.layoutSomeoneTyping.getRoot().setVisibility(View.GONE);
    }

    private void addComment(CommentResponse comment) {
        mCommentAdapter.addItem(comment);
        mCommentAdapter.notifyDataSetChanged();
        mCommentLayoutManager.scrollToPosition(mCommentAdapter.getItemCount() - 1);
    }

    @Override
    public void onClickItem(CommentResponse comment, int position) {
        ProfileActivity.start(getActivity(), comment.getUser());
    }
}
