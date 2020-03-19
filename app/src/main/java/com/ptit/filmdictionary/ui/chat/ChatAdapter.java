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
import com.ptit.filmdictionary.databinding.ItemImageReceiveBinding;
import com.ptit.filmdictionary.databinding.ItemImageSendBinding;
import com.ptit.filmdictionary.databinding.ItemTextReceiveBinding;
import com.ptit.filmdictionary.databinding.ItemTextSendBinding;
import com.ptit.filmdictionary.databinding.LayoutLoadmoreChatBinding;
import com.ptit.filmdictionary.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MessageResponse> mMessages = new ArrayList<>();
    private String mCurrentUserId;

    public ChatAdapter(Context context, String currentUserId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCurrentUserId = currentUserId;
    }

    public void setData(List<MessageResponse> messages) {
        if (messages != null) {
            mMessages.clear();
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            notifyDataSetChanged();
        }
    }

    public void addData(List<MessageResponse> messages) {
        if (messages != null) {
            int oldPosition = mMessages.size();
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            mMessages.addAll(messages);
            notifyItemRangeChanged(oldPosition, messages.size() * 5);
        }
    }

    public void insertItem(MessageResponse message) {
        if (message != null) {
            mMessages.add(message);
            notifyItemChanged(mMessages.size());
        }
    }

    public void insertItemAtPosition(MessageResponse message, int position) {
        if (message != null) {
            mMessages.add(position, message);
            notifyItemInserted(position);
        }
    }

    public void removeItemAtPos(int position) {
        if (position >= 0 && position < mMessages.size()) {
            mMessages.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadMore() {
        if (mMessages.size() > 0) {
            MessageResponse more = new MessageResponse();
            more.setType(MessageType.LOAD_MORE);
            mMessages.add(more);
            notifyItemInserted(mMessages.size() - 1);
        }
    }

    public void removeLoadMore() {
        if (mMessages.size() > 0 && mMessages.get(mMessages.size() - 1).getType() == MessageType.LOAD_MORE) {
            mMessages.remove(mMessages.size() - 1);
            notifyItemRemoved(mMessages.size());
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
            case MessageType.LOAD_MORE:
                return MessageViewHolderType.LOAD_MORE;
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
            case MessageViewHolderType.LOAD_MORE:
                LayoutLoadmoreChatBinding binding4 = DataBindingUtil.inflate(mInflater, R.layout.layout_loadmore_chat, parent, false);
                return new LoadMoreVH(binding4);
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

    public class LoadMoreVH extends RecyclerView.ViewHolder {
        private LayoutLoadmoreChatBinding mBinding;

        LoadMoreVH(@NonNull LayoutLoadmoreChatBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }


    public class UnknownVH extends RecyclerView.ViewHolder {

        UnknownVH(View itemView) {
            super(itemView);
        }
    }

}
