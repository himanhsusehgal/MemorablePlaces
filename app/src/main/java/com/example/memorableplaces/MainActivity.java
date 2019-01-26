package com.example.memorableplaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    ListView listView;
    ArrayList<String> latitudes;
    ArrayList<String> longitudes;


   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        SharedPreferences sharedPreferences= this.getSharedPreferences("com.example.memorableplaces",MODE_PRIVATE);

        latitudes = new ArrayList<>();
        longitudes =  new ArrayList<>();

        arrayList.clear();
        latitudes.clear();
        longitudes.clear();
        locations.clear();


        try {

            arrayList =(ArrayList<String>) ObjectSeriazier.deserialize(sharedPreferences.getString("places",ObjectSeriazier.serialize(new ArrayList<String>())));
            latitudes =(ArrayList<String>) ObjectSeriazier.deserialize(sharedPreferences.getString("latitudes",ObjectSeriazier.serialize(new ArrayList<String>())));
            longitudes =(ArrayList<String>) ObjectSeriazier.deserialize(sharedPreferences.getString("longitudes",ObjectSeriazier.serialize(new ArrayList<String>())));


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(arrayList.size() >0 && latitudes.size() > 0 && longitudes.size() >0){
            if(arrayList.size() == latitudes.size() && latitudes.size() == longitudes.size()){


                for(int i = 0 ; i < latitudes.size() ; i++){

                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));

                }

            }
        }else {

            locations.add(new LatLng(0, 0));
            arrayList.add("Add a new place...");
        }

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placenumber" , position);
                startActivity(intent);
            }
        });
    }
}
