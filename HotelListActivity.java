package com.aqib.mymedreminder.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqib.mymedreminder.adapter.HotelListAdapter;
import com.aqib.mymedreminder.R;
import com.aqib.mymedreminder.model.HotelCrate;
import com.aqib.mymedreminder.model.HotelResponse;
import com.aqib.mymedreminder.network.MyMedApiInterface;
import com.aqib.mymedreminder.network.MyMedDatabaseRetrofit;
import com.aqib.mymedreminder.utils.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListActivity extends AppCompatActivity {


    List<HotelCrate> hotelCrateList ;
    RecyclerView recList;

    HotelListAdapter hotelListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        hotelCrateList = new ArrayList<HotelCrate>();
        hotelListAdapter = new HotelListAdapter(hotelCrateList, HotelListActivity.this);
        recList.setAdapter(hotelListAdapter);
        fillList();

    }




    // please fetch the data from end point and setinto the list

    private void fillList() {
        MyMedApiInterface medApiInterface=MyMedDatabaseRetrofit.getMyMedApiInterface();
        medApiInterface.getHotels(Session.access_key,"1").enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if(response.isSuccessful()){
                    HotelResponse hotelResponse= response.body();
                    if(hotelResponse.getStatus()){
                        hotelCrateList.clear(); // Clear old data
                        hotelCrateList.addAll(hotelResponse.getData()); // Add new data to the same list
                        hotelListAdapter.notifyDataSetChanged(); // Refresh adapter
                    }
                    else {
                        Toast.makeText(HotelListActivity.this, hotelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(HotelListActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                Toast.makeText(HotelListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}