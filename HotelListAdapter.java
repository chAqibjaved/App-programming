package com.aqib.mymedreminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqib.mymedreminder.R;
import com.aqib.mymedreminder.activity.SignupActivity;
import com.aqib.mymedreminder.model.HotelCrate;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelListViewHolder> {

    private List<HotelCrate> hotelList;
    private Context context;

    public HotelListAdapter(List<HotelCrate> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_list_item, parent, false);
        return new HotelListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelListViewHolder holder, int position) {
        HotelCrate ci = hotelList.get(position);

        holder.vhotelName.setText(ci.getName());
        holder.vStrikeprice.setText("EUR: " + ci.getMincharges());
        holder.vhotellocation.setText(ci.getLocation());
        holder.vroomcount.setText("1");
        holder.vnightcount.setText("1");

        Glide.with(context)
                .load(ci.getMainimage_url())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.hotlimage);

        if (Double.parseDouble(ci.getHoteldiscount()) > 0) {
            holder.vdiscountval.setText(ci.getHoteldiscount() + "%");
            holder.speacialdealimg.setVisibility(View.VISIBLE);
            holder.vhotelprice.setVisibility(View.VISIBLE);
            holder.vdiscountlabel.setVisibility(View.VISIBLE);
            holder.vdiscountval.setVisibility(View.VISIBLE);
            holder.vStrikeprice.setPaintFlags(holder.vStrikeprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            double originalPrice = Double.parseDouble(ci.getMincharges());
            double discount = Double.parseDouble(ci.getHoteldiscount());
            double discountedPrice = originalPrice - (originalPrice * (discount / 100.0));
            holder.vhotelprice.setText("EUR " + discountedPrice);
        } else {
            holder.speacialdealimg.setVisibility(View.GONE);
            holder.vhotelprice.setVisibility(View.GONE);
            holder.vdiscountlabel.setVisibility(View.GONE);
            holder.vdiscountval.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SignupActivity.class);
            intent.putExtra("hotel_name", ci.getName());
            intent.putExtra("hotel_image", ci.getMainimage_url());
            intent.putExtra("hotel_price", ci.getMincharges());
            intent.putExtra("hotel_location", ci.getLocation());
            intent.putExtra("hotel_discount", ci.getHoteldiscount());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelListViewHolder extends RecyclerView.ViewHolder {

        TextView vhotelName, vStrikeprice, vhotelprice, vroomcount, vnightcount, vdiscountlabel, vdiscountval, vhotellocation;
        ImageView hotlimage, speacialdealimg;

        public HotelListViewHolder(View v) {
            super(v);
            vhotelName = v.findViewById(R.id.hotel_list_item_hotelname);
            vStrikeprice = v.findViewById(R.id.hotel_list_item_strike_through_price_txt);
            vhotelprice = v.findViewById(R.id.hotel_list_item_hotel_price_txt);
            vroomcount = v.findViewById(R.id.hotel_list_item_roomcount);
            vnightcount = v.findViewById(R.id.hotel_list_item_nightcount);
            vdiscountlabel = v.findViewById(R.id.hotel_list_item_discount_txt);
            vdiscountval = v.findViewById(R.id.hotel_list_item_discount_val);
            vhotellocation = v.findViewById(R.id.hotel_list_item_hotel_location);
            hotlimage = v.findViewById(R.id.hotel_list_item_photo_img);
            speacialdealimg = v.findViewById(R.id.hotel_list_item_specialdeal);
        }
    }
}
