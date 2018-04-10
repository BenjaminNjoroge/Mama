package net.webnetworksolutions.mama.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.webnetworksolutions.mama.R;
import net.webnetworksolutions.mama.pojo.Counties;

import java.util.List;

/**
 * Created by Benja on 4/4/2018.
 */

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Counties> countiesList;

    public CountiesAdapter(Context mContext, List<Counties> countiesList) {
        this.mContext = mContext;
        this.countiesList = countiesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.county_model,parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Counties counties= countiesList.get(position);
        holder.title.setText(counties.getTitle());
        holder.hospitals.setText(counties.getHospitals());

        // loading album cover using Glide library
        Glide.with(mContext).load(counties.getImg()).into(holder.countyImages);
    }

    @Override
    public int getItemCount() {
        return countiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, hospitals;
        public ImageView countyImages;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= itemView. findViewById(R.id.title);
            hospitals= itemView.findViewById(R.id.count);
            countyImages= itemView.findViewById(R.id.thumbnail);
        }
    }
}