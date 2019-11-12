package com.ptit.filmdictionary.ui.search_movie;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.ptit.filmdictionary.data.model.Movie;

public class HorizontalMovieViewModel {
    public final ObservableField<Movie> movieObservableField;
    public final ObservableBoolean isFavoriteObservable;

    public HorizontalMovieViewModel() {
        movieObservableField = new ObservableField<>();
        isFavoriteObservable = new ObservableBoolean(false);
    }
}
