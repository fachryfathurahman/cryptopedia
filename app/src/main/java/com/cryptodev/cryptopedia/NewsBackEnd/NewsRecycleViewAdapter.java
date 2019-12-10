package com.cryptodev.cryptopedia.NewsBackEnd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cryptodev.cryptopedia.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecycleViewAdapter extends RecyclerView.Adapter<NewsRecycleViewAdapter.NewsViewHolder> {


    Context mContext;
    List<News> mNews;
    public NewsRecycleViewAdapter(Context mContext, List<News> mNews) {
        this.mContext = mContext;
        this.mNews = mNews;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        NewsViewHolder vHolder=new NewsViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {





        holder.tv_title.setText(mNews.get(position).getTitle());
        Picasso.get().load(mNews.get(position).getImageLink()).into(holder.tv_imageView);

        holder.tv_newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNews.get(position).getLink()));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;

        private LinearLayout tv_newsLayout;
        private  ImageView tv_imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
           tv_title = (TextView) itemView.findViewById(R.id.news_title);
            tv_imageView = (ImageView) itemView.findViewById(R.id.news_image);

          tv_newsLayout = itemView.findViewById(R.id.newsLayout);
        }
    }
}
