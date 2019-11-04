package com.ptit.filmdictionary.ui.history;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.repository.HistoryRepository;

public class HistoryViewModel {
    private HistoryRepository mRepository;
    public final ObservableList<History> historiesObservable;

    public HistoryViewModel(HistoryRepository repository) {
        mRepository = repository;
        historiesObservable = new ObservableArrayList<>();
        loadHistories();
    }

    public void loadHistories() {
        historiesObservable.addAll(mRepository.getAllHistories());
    }

    public boolean deleteHistories(History history) {
        return mRepository.deletedHistory(history);
    }
}
