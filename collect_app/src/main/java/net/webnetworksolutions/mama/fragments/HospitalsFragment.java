package net.webnetworksolutions.mama.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.webnetworksolutions.mama.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalsFragment extends Fragment {


    public HospitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospitals, container, false);
    }

}
