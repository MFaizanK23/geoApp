package com.example.geoApp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements Adapter.OnNoteDeleteListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Coord> coordinates;
    SearchView searchView;
    int longitude, latitude;
    String address;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GeoDatabase db = new GeoDatabase(this);
        coordinates = db.getAddresses();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, coordinates, this);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });






    }

    public static String geoAddress(Context context, float latitude, float longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                result = sb.toString();
            }
        } catch (IOException e) {

        }
        return result;
    }

    public void updateAddress(Context context) {

        StringBuilder builder = new StringBuilder();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = "Not Found";

        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {

                    String addy = " " + address.getAddressLine(i) + ",";
                    builder.append(addy);
                }

                builder.deleteCharAt(builder.length() - 1);

                this.address = builder.toString();
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent i = new Intent(this, AddGeo.class);
            startActivity(i);
            Toast.makeText(this, "User Clicked on Add Note", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void filter(String query) {
        List<Coord> filteredCoords = new ArrayList<>();
        for (Coord coord : coordinates) {
            if (coord.getADDR().toLowerCase().contains(query.toLowerCase())) {
                filteredCoords.add(coord);
            }
        }
        adapter.updateN(filteredCoords);
    }

    @Override
    public void onNoteDelete(Coord coord) {

        GeoDatabase db = new GeoDatabase(this);
        db.deleteCoord(coord);
        coordinates = db.getAddresses();
        adapter.updateN(coordinates);
    }



}
