package com.ptit.filmdictionary.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.MessageType;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.databinding.ItemImageReceiveBinding;
import com.ptit.filmdictionary.databinding.ItemImageSendBinding;
import com.ptit.filmdictionary.databinding.ItemTextReceiveBinding;
import com.ptit.filmdictionary.databinding.ItemTextSendBinding;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MessageResponse> mMessages = new ArrayList<>();
    private String  mCurrentUserId;

    public ChatAdapter(Context context, String currentUserId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCurrentUserId = currentUserId;
    }

    public void setData(List<MessageResponse> messages) {
        if (messages != null) {
            mMessages.clear();
            mMessages.addAll(messages);
            notifyDataSetChanged();
        }
    }

    public void insertItem(MessageResponse message) {
        if (message != null) {
            mMessages.add(message);
            notifyItemChanged(mMessages.size());
        }
    }

    public void removeItemAtPos(int position) {
        if (position >= 0 && position < mMessages.size()) {
            mMessages.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int serverType = mMessages.get(position).getType();
        switch (serverType) {
            case MessageType.TEXT_MESSAGE:
                if (mMessages.get(position).getSernder().getId().equals(mCurrentUserId))
                    return MessageViewHolderType.SENT_TEXT_MESSAGE;
                return MessageViewHolderType.RECEIVED_TEXT_MESSAGE;
            case MessageType.IMAGE_MESSAGE:
                if (mMessages.get(position).getSernder().getId().equals(mCurrentUserId))
                    return MessageViewHolderType.SENT_IMAGE_MESSAGE;
                return MessageViewHolderType.RECEIVED_IMAGE_MESSAGE;
            default:
                return 0;
         }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MessageViewHolderType.SENT_TEXT_MESSAGE:
                ItemTextSendBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_text_send, parent, false);
                return new SentTextVH(binding);
            case MessageViewHolderType.RECEIVED_TEXT_MESSAGE:
                ItemTextReceiveBinding binding1 = DataBindingUtil.inflate(mInflater, R.layout.item_text_receive, parent, false);
                return new ReceivedTextVH(binding1);
            case MessageViewHolderType.SENT_IMAGE_MESSAGE:
                ItemImageSendBinding binding2 = DataBindingUtil.inflate(mInflater, R.layout.item_image_send, parent, false);
                return new SentImageVH(binding2);
            case MessageViewHolderType.RECEIVED_IMAGE_MESSAGE:
                ItemImageReceiveBinding binding3 = DataBindingUtil.inflate(mInflater, R.layout.item_image_receive, parent, false);
                return new ReceivedImageVH(binding3);
            default:
                return new UnknownVH(null);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MessageViewHolderType.SENT_TEXT_MESSAGE:
                ((SentTextVH) holder).bindData(mMessages.get(position));
                break;
            case MessageViewHolderType.RECEIVED_TEXT_MESSAGE:
                ((ReceivedTextVH) holder).bindData(mMessages.get(position));
                break;
            case MessageViewHolderType.SENT_IMAGE_MESSAGE:
                ((SentImageVH) holder).bindData(mMessages.get(position));
                break;
            case MessageViewHolderType.RECEIVED_IMAGE_MESSAGE:
                ((ReceivedImageVH) holder).bindData(mMessages.get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    public class SentTextVH extends RecyclerView.ViewHolder {
        private ItemTextSendBinding mBinding;

        SentTextVH(@NonNull ItemTextSendBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(MessageResponse message) {
            mBinding.textMessage.setText(message.getText());
        }
    }

    public class ReceivedTextVH extends RecyclerView.ViewHolder {
        private ItemTextReceiveBinding mBinding;

        ReceivedTextVH(@NonNull ItemTextReceiveBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(MessageResponse message) {
            mBinding.textMessage.setText(message.getText());
            ImageHelper.loadImageCircle(mBinding.imageAvatar, message.getSernder().getAvatar());
        }
    }

    public class SentImageVH extends RecyclerView.ViewHolder {
        private ItemImageSendBinding mBinding;

        SentImageVH(@NonNull ItemImageSendBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(MessageResponse message) {
            ImageHelper.loadImage(mBinding.imageMessage, message.getText());
        }
    }

    public class ReceivedImageVH extends RecyclerView.ViewHolder {
        private ItemImageReceiveBinding mBinding;

        ReceivedImageVH(@NonNull ItemImageReceiveBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindData(MessageResponse message) {
            ImageHelper.loadImage(mBinding.imageMessage, message.getText());
            ImageHelper.loadImageCircle(mBinding.imageAvatar, message.getSernder().getAvatar());
        }
    }

    public class UnknownVH extends RecyclerView.ViewHolder {

        UnknownVH(View itemView) {
            super(itemView);
        }
    }

}
