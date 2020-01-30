package com.frestau.restaurants.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.frestau.restaurants.LoginActivity;


public class LogoutFragment extends Fragment{


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

    }
