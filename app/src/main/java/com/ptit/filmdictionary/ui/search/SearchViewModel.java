package com.ptit.filmdictionary.ui.search;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.ptit.filmdictionary.base.BaseRepository;
import com.ptit.filmdictionary.base.BaseViewModel;
import com.ptit.filmdictionary.data.model.Genre;
import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.repository.HistoryRepository;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;
import com.ptit.filmdictionary.ui.search_movie.SearchNavigator;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {
    private static final int FIRST_PAGE = 1;
    public final ObservableList<Movie> searchResultObservable;
    public final CompositeDisposable compositeDisposable;
    public final ObservableInt totalResultObservable;
    public final ObservableBoolean isLoadedResults;
    public final ObservableBoolean isLoadingResults;
    public final ObservableBoolean isLoadingSearchUser;
    public final ObservableBoolean isLoadingMoreResults;
    public final ObservableBoolean isLoadMore;
    public final ObservableList<Genre> genresObservable;
    public MutableLiveData<List<UserResponse>> liveSearchUsers = new MutableLiveData<>();
    private MovieRepository mMovieRepository;
    private HistoryRepository mHistoryRepository;
    private AuthRepository mAuthRepository;
    private String mQuery;
    private int mCurrentPage;

    public SearchViewModel(BaseRepository repository, HistoryRepository historyRepository) {
        super(repository);
        mHistoryRepository = historyRepository;
        mCurrentPage = 1;
        mMovieRepository = (MovieRepository) repository;
        searchResultObservable = new ObservableArrayList<>();
        totalResultObservable = new ObservableInt();
        genresObservable = new ObservableArrayList<>();
        isLoadedResults = new ObservableBoolean(false);
        isLoadingResults = new ObservableBoolean(false);
        isLoadingSearchUser = new ObservableBoolean(false);
        isLoadingMoreResults = new ObservableBoolean(false);
        isLoadMore = new ObservableBoolean(false);
        compositeDisposable = new CompositeDisposable();
        loadGenres();
    }

    public void setAuthRepository(AuthRepository authRepository) {
        mAuthRepository = authRepository;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public void loadResultByKeyword(String query) {
        mQuery = query;
        resetCurrentPage();
        isLoadingResults.set(true);
        isLoadMore.set(false);
        Disposable disposable = mMovieRepository.searchMovieByName(query, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    searchResultObservable.clear();
                    searchResultObservable.addAll(movieResponse.getResults());
                    isLoadedResults.set(true);
                    isLoadingResults.set(false);
                    totalResultObservable.set(movieResponse.getTotalResult());
                });
        compositeDisposable.add(disposable);
    }

    public void searchUser(String userId, String query) {
        isLoadingSearchUser.set(true);
        if (mAuthRepository == null) return;
        Disposable disposable = mAuthRepository.searchUser(userId, query, "0") //todo add pagination later
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            liveSearchUsers.postValue(data.getData());
                            isLoadingSearchUser.set(false);
                        },
                        throwable -> {
                            isLoadingSearchUser.set(false);
                        });
        compositeDisposable.add(disposable);
    }

    private void loadGenres() {
        Disposable disposable = mMovieRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genreResponse -> {
                    genresObservable.addAll(genreResponse.getGenres());
                });
        compositeDisposable.add(disposable);
    }

    private void resetCurrentPage() {
        mCurrentPage = FIRST_PAGE;
    }

    public void loadMore() {
        isLoadMore.set(true);
        isLoadingMoreResults.set(true);
        Disposable disposable = mMovieRepository.searchMovieByName(mQuery, ++mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    searchResultObservable.clear();
                    searchResultObservable.addAll(movieResponse.getResults());
                    totalResultObservable.set(movieResponse.getTotalResult());
                    isLoadingMoreResults.set(false);
                });
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public void saveHistory(String query) {
        mHistoryRepository.addedHistory(new History(query));
    }
}
