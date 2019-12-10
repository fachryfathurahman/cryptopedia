package com.cryptodev.cryptopedia;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cryptodev.cryptopedia.PriceBackEnd.Price;
import com.cryptodev.cryptopedia.PriceBackEnd.PriceLoader;
import com.cryptodev.cryptopedia.PriceBackEnd.RecycleViewAdapter;

import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Price>> {


    private static final int PRICE_LOADER_ID = 1;
    View v;
    private RecyclerView myRecycleView;
    private List<Price> lstPrice;
    public static final String LOG_TAG =HomeActivity.class.getName();
    RecycleViewAdapter recycleViewAdapter;
    private TextView mEmptyStateTextView;
    private ProgressBar prg;

    private String  mUrl="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_price, container, false);
        // Inflate the layout for this fragment

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG,"Test: onActivityCreate()");

            getLoaderManager().initLoader(PRICE_LOADER_ID,null,this);

     }

    @NonNull
    @Override
    public Loader<List<Price>> onCreateLoader(int id, @Nullable Bundle args) {
        return new PriceLoader(getActivity(),mUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Price>> loader, List<Price> data) {

        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);

        loadingIndicator.setVisibility(GONE);
        mEmptyStateTextView = getActivity().findViewById(R.id.empty_view);

        if (data!=null&& !data.isEmpty()){

            recycleViewAdapter= new RecycleViewAdapter(getContext(),data);
            myRecycleView = (RecyclerView) v.findViewById(R.id.price_RecycleView);


            myRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRecycleView.setAdapter(recycleViewAdapter);

        }
        else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }




        Log.i(LOG_TAG,"Test: onCreateView()");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Price>> loader) {

    }





}
