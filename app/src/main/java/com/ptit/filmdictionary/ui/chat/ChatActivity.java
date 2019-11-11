package com.ptit.filmdictionary.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.MessageType;
import com.ptit.filmdictionary.data.socket.SocketData;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ActivityChatBinding;
import com.ptit.filmdictionary.utils.BaseHelper;
import com.ptit.filmdictionary.utils.ImageHelper;
import com.ptit.filmdictionary.utils.MyApplication;
import com.ptit.filmdictionary.utils.ResizeWidthAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChatActivity";
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
    private LinearLayoutManager mLinearLayoutManager;

    private MessageResponse mMessageResponse; // comment response to add local before done comment
    private Socket mSocket;

    private static final int TYPING_TIMER_LENGTH = 600;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private Boolean isConnected = true;

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
        mSocket.off(SocketData.Event.NEW_MESSAGE, onNewMessage);
        mSocket.off(SocketData.Event.CHAT_TYPING, onTyping);
        mSocket.off(SocketData.Event.CHAT_STOP_TYPING, onStopTyping);
    }

    private void connectSocket() {
        mSocket = ((MyApplication) getApplication()).getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(SocketData.Event.NEW_MESSAGE, onNewMessage);
        mSocket.on(SocketData.Event.CHAT_TYPING, onTyping);
        mSocket.on(SocketData.Event.CHAT_STOP_TYPING, onStopTyping);
        mSocket.connect();
    }

    private void observeLiveData() {
        mViewModel.getLiveGetMessages().observe(this, data -> {
            mChatAdapter.setData(data);
        });
        mViewModel.getLiveSendMessages().observe(this, data -> {
            if (data != null) {
                mMessageResponse.setCreatedAt(data.getCreatedAt());
                mChatAdapter.notifyDataSetChanged();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(SocketData.Key.SENDER_ID, mCurrentUserId);
                jsonObject.addProperty(SocketData.Key.RECEIVER_ID, mInteractiveUser.getId());
                jsonObject.add(SocketData.Key.MESSAGE_JSON, new Gson().toJsonTree(data));

                mSocket.emit(SocketData.Event.NEW_MESSAGE, new Gson().toJson(jsonObject));
                Log.d(TAG, "observeData: " + new Gson().toJson(jsonObject));
            }
        });
    }

    private void loadData() {
        mViewModel.loadMessages(mPreferenceUtil.getUserId(), mInteractiveUser.getId());
    }

    private void initComponents() {
        ImageHelper.loadImageCircle(mBinding.layoutToolbarChat.imageAvatar, mInteractiveUser.getAvatar());
        mBinding.layoutToolbarChat.textName.setText(mInteractiveUser.getUserName());
        mWidthTextMessage = getResources().getDimensionPixelOffset(R.dimen.dp_180);
        mWidthLayoutCollapse = getResources().getDimensionPixelOffset(R.dimen.dp_84);
        mChatAdapter = new ChatAdapter(this, mCurrentUserId);
        mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        mBinding.recyclerMessage.setLayoutManager(mLinearLayoutManager);
        mBinding.recyclerMessage.setAdapter(mChatAdapter);
        setupAnimationTextMessage();
    }

    private void initListeners() {
        mBinding.layoutBottomBarChat.imageAdd.setOnClickListener(this);
        mBinding.layoutBottomBarChat.imagePhoto.setOnClickListener(this);
        mBinding.layoutBottomBarChat.imageCamera.setOnClickListener(this);
        mBinding.layoutToolbarChat.imageBack.setOnClickListener(this);
        mBinding.layoutBottomBarChat.imageSend.setOnClickListener(this);
        mBinding.layoutBottomBarChat.textMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(SocketData.Key.SENDER_ID, mCurrentUserId);
                    jsonObject.addProperty(SocketData.Key.RECEIVER_ID, mInteractiveUser.getId());
                    mSocket.emit(SocketData.Event.CHAT_TYPING, new Gson().toJson(jsonObject));
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
                sendMessage();
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

    private void sendMessage() {
        if (!BaseHelper.isInternetOn(this)) {
//            BaseHelper.showCustomSnackbarView(mBinding.viewOne, getContext(), getString(R.string.text_no_internet));
            return;
        }
        String text = mBinding.layoutBottomBarChat.textMessage.getText().toString();
        if (text.isEmpty()) {
            BaseHelper.showToast(this, getResources().getString(R.string.comment_invalid_input));
            return;
        }
        mViewModel.sendMessage(mCurrentUserId, mInteractiveUser.getId(), text);
        mMessageResponse = new MessageResponse();
        UserResponse sender = new UserResponse();
        sender.setAvatar(mPreferenceUtil.getUserAvatar());
        sender.setUserName(mPreferenceUtil.getUserName());
        sender.setId(mPreferenceUtil.getUserId());
        mMessageResponse.setType(MessageType.TEXT_MESSAGE); //todo text type, add other type later

        mMessageResponse.setText(mBinding.layoutBottomBarChat.textMessage.getText().toString());
        mMessageResponse.setSernder(sender);
        mMessageResponse.setReceiver(mInteractiveUser);
        mLinearLayoutManager.scrollToPosition(mChatAdapter.getItemCount() - 1);
        mBinding.layoutBottomBarChat.textMessage.setText("");
        BaseHelper.hideKeyboardFrom(this, mBinding.getRoot());
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
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
            runOnUiThread(new Runnable() {
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String senderId = "";
                    String receiverId = "";
                    MessageResponse mess = null;
                    String data = (String) args[0];
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        senderId = jsonObject.optString(SocketData.Key.SENDER_ID);
                        receiverId = jsonObject.optString(SocketData.Key.RECEIVER_ID);
                        JSONObject messageObject = jsonObject.optJSONObject(SocketData.Key.MESSAGE_JSON);
                        mess = new Gson().fromJson(messageObject.toString(), MessageResponse.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!senderId.isEmpty() && !receiverId.isEmpty() && mess != null) {
                        if (senderId.equals(mInteractiveUser.getId()) && receiverId.equals(mCurrentUserId))
                            addMessage(mess);
                    }
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String senderId = "";
                    String receiverId = "";
                    String data = (String) args[0];
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        senderId = jsonObject.optString(SocketData.Key.SENDER_ID);
                        receiverId = jsonObject.optString(SocketData.Key.RECEIVER_ID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!senderId.isEmpty() && !receiverId.isEmpty()) {
                        if (senderId.equals(mInteractiveUser.getId()) && receiverId.equals(mCurrentUserId)) {
                            addTyping();
                        }
                    }
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String senderId = "";
                    String receiverId = "";
                    String data = (String) args[0];
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        senderId = jsonObject.optString(SocketData.Key.SENDER_ID);
                        receiverId = jsonObject.optString(SocketData.Key.RECEIVER_ID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!senderId.isEmpty() && !receiverId.isEmpty()) {
                        if (senderId.equals(mInteractiveUser.getId()) && receiverId.equals(mCurrentUserId)) {
                            removeTyping();
                        }
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
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(SocketData.Key.SENDER_ID, mCurrentUserId);
            jsonObject.addProperty(SocketData.Key.RECEIVER_ID, mInteractiveUser.getId());
            mSocket.emit(SocketData.Event.CHAT_STOP_TYPING, new Gson().toJson(jsonObject));
        }
    };

    private void addTyping() {
        mBinding.layoutSomeoneTyping.setVisibility(View.VISIBLE);
    }

    private void removeTyping() {
        mBinding.layoutSomeoneTyping.setVisibility(View.GONE);
    }

    private void addMessage(MessageResponse message) {
        mChatAdapter.insertItem(message);
//        mCommentAdapter.notifyDataSetChanged();
        mLinearLayoutManager.scrollToPosition(mChatAdapter.getItemCount() - 1);
    }

}
