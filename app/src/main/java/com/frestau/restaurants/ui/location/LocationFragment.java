package com.frestau.restaurants.ui.location;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frestau.restaurants.LocationActivity;
import com.frestau.restaurants.R;

public class LocationFragment extends Fragment {

    private LocationViewModel locationViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        locationViewModel =
                ViewModelProviders.of(this).get(LocationViewModel.class);

        View root = inflater.inflate(R.layout.fragment_location, container, false);
        final TextView textView = root.findViewById(R.id.text_location);


        Button myButton = (Button) root.findViewById(R.id.gpsBtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });

        locationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });
        return root;

    }

}


