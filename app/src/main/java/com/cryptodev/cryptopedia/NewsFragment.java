package com.cryptodev.cryptopedia;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cryptodev.cryptopedia.NewsBackEnd.News;
import com.cryptodev.cryptopedia.NewsBackEnd.NewsLoader;
import com.cryptodev.cryptopedia.NewsBackEnd.NewsRecycleViewAdapter;

import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;
    private String mUrl="https://newsapi.org/v2/everything?q=bitcoin&from=2019-09-06&sortBy=popularity&apiKey=3913956a2dd746efa79b51aab35ee231";
    private RecyclerView mRecycleView;
    NewsRecycleViewAdapter mNewsRecycleViewAdapter;
    View v;
    private TextView mEmptyStateTextViewNews;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_news, container, false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(getActivity(),mUrl);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {

        View loadingIndicatorNews =getActivity().findViewById(R.id.loading_indicator_news);

        loadingIndicatorNews.setVisibility(GONE);
        mEmptyStateTextViewNews = getActivity().findViewById(R.id.empty_view_news);

        if (data!=null && !data.isEmpty()){
            mNewsRecycleViewAdapter = new NewsRecycleViewAdapter(getContext(),data);
            mRecycleView =(RecyclerView) v.findViewById(R.id.news_RecycleView);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecycleView.setAdapter(mNewsRecycleViewAdapter);
        }
        else {
            loadingIndicatorNews.setVisibility(GONE);
            mEmptyStateTextViewNews.setText("no internet connection");
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }
}
