package com.cryptodev.cryptopedia.NewsBackEnd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;



public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;
    public NewsLoader(@NonNull Context context,String mUrl) {
        super(context);
        this.mUrl=mUrl;
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        List<News> news = NewsQueryUtils.fetchNewsData(mUrl);
        Log.i(LOG_TAG,"Test: loadInBackground()");
        return news;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"Test: onStartLoading()");
        forceLoad();
    }
}
