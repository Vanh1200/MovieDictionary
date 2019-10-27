package com.ptit.filmdictionary.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by vanh1200 on 22/10/2019
 */
public class ErrorHandlingExecutorCallAdapterFactory extends CallAdapter.Factory {
    private final Executor callbackExecutor;

    public ErrorHandlingExecutorCallAdapterFactory(Executor callbackExecutor) {
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, final Retrofit retrofit) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        final Type responseType = getCallResponseType(returnType);
        return new CallAdapter<Object, Call<?>>() {
            @Override public Type responseType() {
                return responseType;
            }

            @Override public Call<Object> adapt(Call<Object> call) {
                return new ExecutorCallbackCall<>(callbackExecutor, call, retrofit);
            }
        };
    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        final Executor callbackExecutor;
        final Call<T> delegate;
        private final Retrofit retrofit;

        ExecutorCallbackCall(Executor callbackExecutor, Call<T> delegate, Retrofit retrofit) {
            this.callbackExecutor = callbackExecutor;
            this.delegate = delegate;
            this.retrofit = retrofit;
        }

        @Override
        public void enqueue(final Callback<T> callback) {
            checkNotNull(callback, "callback == null");
            delegate.enqueue(new MyExecutorCallback<>(callbackExecutor, delegate, callback, this, retrofit));
        }

        @Override public boolean isExecuted() {
            return delegate.isExecuted();
        }

        @Override public Response<T> execute() throws IOException {
            return delegate.execute();
        }

        @Override public void cancel() {
            delegate.cancel();
        }

        @Override public boolean isCanceled() {
            return delegate.isCanceled();
        }

        @SuppressWarnings("CloneDoesntCallSuperClone") // Performing deep clone.
        @Override public Call<T> clone() {
            return new ExecutorCallbackCall<>(callbackExecutor, delegate.clone(), retrofit);
        }

        @Override public Request request() {
            return delegate.request();
        }
    }

    static class MyExecutorCallback<T> implements Callback<T>{
        final Executor callbackExecutor;
        final Call<T> delegate;
        final Callback<T> callback;
        final ExecutorCallbackCall<T> executorCallbackCall;
        private Retrofit retrofit;

        public MyExecutorCallback(Executor callbackExecutor, Call<T> delegate, Callback<T> callback, ExecutorCallbackCall<T> executorCallbackCall, Retrofit retrofit){
            this.callbackExecutor = callbackExecutor;
            this.delegate = delegate;
            this.callback = callback;
            this.executorCallbackCall = executorCallbackCall;
            this.retrofit = retrofit;
        }

        @Override
        public void onResponse(final Call<T> call, final Response<T> response) {
            if(response.isSuccessful()){
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(executorCallbackCall, response);
                    }
                });
            } else {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(executorCallbackCall, RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit));
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<T> call, final Throwable t) {

            RetrofitException exception;
            if (t instanceof IOException) {
                exception = RetrofitException.networkError((IOException) t);
            } else {
                exception = RetrofitException.unexpectedError(t);
            }
            final RetrofitException finalException = exception;
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(executorCallbackCall, finalException);
                }
            });
        }
    }


    public static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable r) {
            handler.post(r);
        }
    }

    static Type getCallResponseType(Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                    "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        return getParameterUpperBound(0, (ParameterizedType) returnType);
    }

    static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}