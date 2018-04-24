package net.webnetworksolutions.mama.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.webnetworksolutions.mama.R;
import net.webnetworksolutions.mama.activity.BaringoCountyActivity;
import net.webnetworksolutions.mama.activity.BometCountyActivity;
import net.webnetworksolutions.mama.activity.BungomaCountyActivity;
import net.webnetworksolutions.mama.activity.BusiaCountyActivity;
import net.webnetworksolutions.mama.activity.ElgeyoMarakwetCountyActivity;
import net.webnetworksolutions.mama.activity.EmbuCountyActivity;
import net.webnetworksolutions.mama.activity.GarissaCountyActivity;
import net.webnetworksolutions.mama.activity.HomaBayCountyActivity;
import net.webnetworksolutions.mama.activity.IsioloCountyActivity;
import net.webnetworksolutions.mama.activity.KajiadoCountyActivity;
import net.webnetworksolutions.mama.activity.KakamegaCountyActivity;
import net.webnetworksolutions.mama.activity.KerichoCountyActivity;
import net.webnetworksolutions.mama.activity.KiambuCountyActivity;
import net.webnetworksolutions.mama.activity.KilifiCountyActivity;
import net.webnetworksolutions.mama.activity.KirinyagaCountyActivity;
import net.webnetworksolutions.mama.activity.KisiiCountyActivity;
import net.webnetworksolutions.mama.activity.KisumuCountyActivity;
import net.webnetworksolutions.mama.activity.KituiCountyActivity;
import net.webnetworksolutions.mama.activity.KwaleCountyActivity;
import net.webnetworksolutions.mama.activity.LaikipiaCountyActivity;
import net.webnetworksolutions.mama.activity.LamuCountyActivity;
import net.webnetworksolutions.mama.activity.MachakosCountyActivity;
import net.webnetworksolutions.mama.activity.MakueniCountyActivity;
import net.webnetworksolutions.mama.activity.ManderaCountyActivity;
import net.webnetworksolutions.mama.activity.MarsabitCountyActivity;
import net.webnetworksolutions.mama.activity.MeruCountyActivity;
import net.webnetworksolutions.mama.activity.MigoriCountyActivity;
import net.webnetworksolutions.mama.activity.MombasaCountyActivity;
import net.webnetworksolutions.mama.activity.MurangaCountyActivity;
import net.webnetworksolutions.mama.activity.NairobiCountyActivity;
import net.webnetworksolutions.mama.activity.NakuruCountyActivity;
import net.webnetworksolutions.mama.activity.NandiCountyActivity;
import net.webnetworksolutions.mama.activity.NarokCountyActivity;
import net.webnetworksolutions.mama.activity.NyamiraCountyActivity;
import net.webnetworksolutions.mama.activity.NyandaruaCountyActivity;
import net.webnetworksolutions.mama.activity.NyeriCountyActivity;
import net.webnetworksolutions.mama.activity.SamburuCountyActivity;
import net.webnetworksolutions.mama.activity.SiayaCountyActivity;
import net.webnetworksolutions.mama.activity.TaitaTavetaCountyActivity;
import net.webnetworksolutions.mama.activity.TanaRiverCountyActivity;
import net.webnetworksolutions.mama.activity.TharakaNithiCountyActivity;
import net.webnetworksolutions.mama.activity.TransNzoiaCountyActivity;
import net.webnetworksolutions.mama.activity.TurkanaCountyActivity;
import net.webnetworksolutions.mama.activity.UasinGichuCountyActivity;
import net.webnetworksolutions.mama.activity.VihigaCountyActivity;
import net.webnetworksolutions.mama.activity.WajirCountyActivity;
import net.webnetworksolutions.mama.activity.WestPokotCountyActivity;
import net.webnetworksolutions.mama.helpers.ItemClickListener;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Counties counties= countiesList.get(position);
        holder.title.setText(counties.getTitle());
        holder.hospitals.setText(counties.getHospitals());
        holder.countyImages.setImageResource(counties.getImg());

        // loading album cover using Glide library
        Glide.with(mContext).load(counties.getImg()).into(holder.countyImages);
        holder.countyImages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mContext=view.getContext();

              final Intent intent;
              switch (position){
                  case 0:
                      intent= new Intent(mContext, NairobiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 1:
                      intent= new Intent(mContext, MombasaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 2:
                      intent= new Intent(mContext, NakuruCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 3:
                      intent= new Intent(mContext, KisumuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 4:
                      intent= new Intent(mContext, KiambuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 5:
                      intent= new Intent(mContext, BaringoCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 6:
                      intent= new Intent(mContext, BometCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 7:
                      intent= new Intent(mContext, BungomaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 8:
                      intent= new Intent(mContext, BusiaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 9:
                      intent= new Intent(mContext, ElgeyoMarakwetCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 10:
                      intent= new Intent(mContext, EmbuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 11:
                      intent= new Intent(mContext, GarissaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 12:
                      intent= new Intent(mContext, HomaBayCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 13:
                      intent= new Intent(mContext, IsioloCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 14:
                      intent= new Intent(mContext, KajiadoCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 15:
                      intent= new Intent(mContext, KakamegaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 16:
                      intent= new Intent(mContext, KerichoCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 17:
                      intent= new Intent(mContext, KilifiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 18:
                      intent= new Intent(mContext, KirinyagaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 19:
                      intent= new Intent(mContext, KisiiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 20:
                      intent= new Intent(mContext, KituiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 21:
                      intent= new Intent(mContext, KwaleCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 22:
                      intent= new Intent(mContext, LaikipiaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 23:
                      intent= new Intent(mContext, LamuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 24:
                      intent= new Intent(mContext, MachakosCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 25:
                      intent= new Intent(mContext, MakueniCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 26:
                      intent= new Intent(mContext, ManderaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 27:
                      intent= new Intent(mContext, MeruCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 28:
                      intent= new Intent(mContext, MigoriCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 29:
                      intent= new Intent(mContext, MarsabitCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 30:
                      intent= new Intent(mContext, MurangaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 31:
                      intent= new Intent(mContext, NandiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 32:
                      intent= new Intent(mContext, NarokCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 33:
                      intent= new Intent(mContext, NyamiraCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 34:
                      intent= new Intent(mContext, NyandaruaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 35:
                      intent= new Intent(mContext, NyeriCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 36:
                      intent= new Intent(mContext, SamburuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 37:
                      intent= new Intent(mContext, SiayaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 38:
                      intent= new Intent(mContext, TaitaTavetaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 39:
                      intent= new Intent(mContext, TanaRiverCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 40:
                      intent= new Intent(mContext, TharakaNithiCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 41:
                      intent= new Intent(mContext, TransNzoiaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 42:
                      intent= new Intent(mContext, TurkanaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 43:
                      intent= new Intent(mContext, UasinGichuCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 44:
                      intent= new Intent(mContext, VihigaCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 45:
                      intent= new Intent(mContext, WajirCountyActivity.class);
                      mContext.startActivity(intent);
                      break;

                  case 46:
                      intent= new Intent(mContext, WestPokotCountyActivity.class);
                      mContext.startActivity(intent);
                      break;
              }
            }
        });

    }

    @Override
    public int getItemCount() {
        return countiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, hospitals;
        public ImageView countyImages;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            title= itemView. findViewById(R.id.title);
            hospitals= itemView.findViewById(R.id.count);
            countyImages= itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener= ic;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }
}