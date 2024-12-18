package it.itsrizzoli.amation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;




public class PaginaPrefFragment extends Fragment {


    public PaginaPrefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagina_pref, container, false);

        TextView textPref = view.findViewById(R.id.textPref);
        EditText editPref = view.findViewById(R.id.editPref);
        ImageView searchIcon = view.findViewById(R.id.searchIcon);
        ImageView backArrow = view.findViewById(R.id.backArrow);


        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPref.setVisibility(View.GONE);
                editPref.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.VISIBLE);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPref.setVisibility(View.GONE);
                textPref.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.GONE);
            }
        });

        return view;
    }
}