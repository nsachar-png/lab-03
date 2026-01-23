package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityArrayAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityArrayAdapter);

        // Click listener for the FAB (to add a new city)
        FloatingActionButton fab = findViewById(R.id.button_add_City);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCityFragment fragment = AddCityFragment.newInstance();
                fragment.show(getSupportFragmentManager(), "ADD_CITY");
            }
        });

        // Click listener for list items (to edit an existing city)
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City selectedCity = dataList.get(position);
                AddCityFragment fragment = AddCityFragment.newInstance(selectedCity);
                fragment.show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });
    }

    @Override
    public void addCity(City city) {
        dataList.add(city);
        cityArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city) {
        // The city object is already updated in the fragment
        // Just notify the adapter to refresh the view
        cityArrayAdapter.notifyDataSetChanged();
    }
}