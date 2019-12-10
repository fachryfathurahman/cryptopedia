package com.cryptodev.cryptopedia.PriceBackEnd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cryptodev.cryptopedia.R;
import com.cryptodev.cryptopedia.testActivity;

import java.text.DecimalFormat;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    Context mContext;
    List<Price> mPrice;

    public RecycleViewAdapter(Context mContext, List<Price> mPrice) {
        this.mContext = mContext;
        this.mPrice = mPrice;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.item_price,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_name.setText(mPrice.get(position).getName());
        holder.tv_symbol.setText(mPrice.get(position).getSymbol());

        final String mDrawableName = mPrice.get(position).getSymbol().toLowerCase();
        final int resID = mContext.getResources().getIdentifier(mDrawableName,"drawable",mContext.getPackageName());
        holder.tv_imageView.setImageResource(resID);




        final double lPrice =mPrice.get(position).getLatest_price();
        final String pr = "USD "+formatPrice(lPrice);
        holder.tv_latest.setText(pr);

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, testActivity.class );
                intent.putExtra("name",mPrice.get(position).getName());
                intent.putExtra("symbol",mPrice.get(position).getSymbol());
                intent.putExtra("price",pr);
                intent.putExtra("imageName",mDrawableName);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPrice.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private TextView tv_symbol;
        private TextView tv_latest;
        private LinearLayout parent_layout;
        private ImageView tv_imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name= (TextView)itemView.findViewById(R.id.price_name);
            tv_symbol=(TextView)itemView.findViewById(R.id.price_symbol);
            tv_latest=(TextView)itemView.findViewById(R.id.price_lates);
            parent_layout= itemView.findViewById(R.id.parent_layout);
            tv_imageView = itemView.findViewById(R.id.price_image);
        }
    }

    private String formatPrice (double price){
        DecimalFormat formattedPrice =new DecimalFormat("0.0000");
        return formattedPrice.format(price);
    }
}
