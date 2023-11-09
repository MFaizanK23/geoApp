package com.example.geoApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddGeo extends AppCompatActivity {
    Toolbar toolbar;
    EditText longitude, address, latitude;
    private boolean isEditMode = false;
    private Coord changer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Address");

        address = findViewById(R.id.address);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);


        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "coordinate Deleted", Toast.LENGTH_SHORT).show();
            rmain();
        }
        if(item.getItemId() == R.id.save){
            Coord NN = new Coord(address.getText().toString(), longitude.getText().toString(), latitude.getText().toString());

            NN.setADDR(MainActivity.geoAddress(this, Float.parseFloat(NN.getLATITUDE()), Float.parseFloat(NN.getLATITUDE())));
            GeoDatabase db = new GeoDatabase(this);

            db.addN(NN);
            Toast.makeText(this, "coordinate Saved", Toast.LENGTH_SHORT).show();
            rmain();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteExistingNote() {
        if (changer != null) {
            // Delete the existing note from the database
            GeoDatabase db = new GeoDatabase(this);
            db.deleteCoord(changer);

            // Indicate that you've left edit mode
            isEditMode = false;

            // Notify the user that the note has been deleted
            Toast.makeText(this, "Note Has Been Deleted.", Toast.LENGTH_SHORT).show();

            // Return to the main activity
            rmain();
        }
    }

    private void rmain(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}