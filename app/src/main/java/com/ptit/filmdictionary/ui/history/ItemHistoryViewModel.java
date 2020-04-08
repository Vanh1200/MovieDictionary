package com.ptit.filmdictionary.ui.history;

import androidx.databinding.ObservableField;

import com.ptit.filmdictionary.data.model.History;

public class ItemHistoryViewModel {
    public final ObservableField<History> historyObservableField;

    public ItemHistoryViewModel() {
        historyObservableField = new ObservableField<>();
    }
}
