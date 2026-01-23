package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city);
    }

    private AddCityDialogListener listener;
    private City cityToEdit = null;

    // Empty constructor (required)
    public AddCityFragment() {
    }

    // Static method to create a new instance for adding a city
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    // Static method to create a new instance for editing a city
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Check if we're editing an existing city
        if (getArguments() != null && getArguments().containsKey("city")) {
            cityToEdit = (City) getArguments().getSerializable("city");
        }

        View view = getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // If editing, pre-fill the fields
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(cityToEdit == null ? "Add a city" : "Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(cityToEdit == null ? "Add" : "OK", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityToEdit == null) {
                        // Adding a new city
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        // Editing existing city
                        cityToEdit.setName(cityName);
                        cityToEdit.setProvince(provinceName);
                        listener.editCity(cityToEdit);
                    }
                })
                .create();
    }
}