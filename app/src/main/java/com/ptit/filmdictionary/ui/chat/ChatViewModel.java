package com.ptit.filmdictionary.ui.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptit.filmdictionary.data.repository.MessageRepository;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {
    private MessageRepository mMessageRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<MessageResponse>> mLiveMessages = new MutableLiveData<>();

    @Inject
    public ChatViewModel(MessageRepository messageRepository) {
        mMessageRepository = messageRepository;
    }

    public MutableLiveData<List<MessageResponse>> getLiveMessages() {
        return mLiveMessages;
    }

    public void loadMessages(String currentUserId, String interactiveUserId) {
        Disposable disposable = mMessageRepository.getMessageByTwoUserId(currentUserId, interactiveUserId, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {mLiveMessages.postValue(data.getData());},
                        throwable -> {});
        mCompositeDisposable.add(disposable);
    }
}
