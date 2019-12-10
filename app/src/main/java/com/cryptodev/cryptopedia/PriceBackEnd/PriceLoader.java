package com.cryptodev.cryptopedia.PriceBackEnd;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class PriceLoader  extends AsyncTaskLoader<List<Price>> {
    /**
     * @param context
     * @deprecated
     */
    private String murl;
    private static final String LOG_TAG = PriceLoader.class.getName();

    public PriceLoader(Context context,String murl) {
        super(context);
        this.murl=murl;

    }

    @Override
    public List<Price> loadInBackground() {
        Log.i(LOG_TAG,"Test: loadInBackground()");
        List <Price> price=QueryUtils.fetchPriceData(murl);
        return price;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
