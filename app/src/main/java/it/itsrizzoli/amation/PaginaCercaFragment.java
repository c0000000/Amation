package it.itsrizzoli.amation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class PaginaCercaFragment extends Fragment {

    private EditText editCerca;
    private ImageView upArrow;
    private ImageView downArrow;
    private LinearLayout hiddenLayout;
    private boolean isExpanded = false;

    public PaginaCercaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagina_cerca, container, false);

        editCerca = view.findViewById(R.id.editCerca);
        upArrow = view.findViewById(R.id.upArrow);
        downArrow = view.findViewById(R.id.downArrow);
        hiddenLayout = view.findViewById(R.id.hiddenLayout);


        hiddenLayout.setVisibility(View.GONE);
        upArrow.setVisibility(View.GONE);
        downArrow.setVisibility(View.VISIBLE);


        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHiddenLayout();
            }
        });

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHiddenLayout();
            }
        });

        return view;
    }

    public void toggleHiddenLayout() {
        if (isExpanded) {
            hiddenLayout.setVisibility(View.GONE);
            upArrow.setVisibility(View.GONE);
            downArrow.setVisibility(View.VISIBLE);
            downArrow.setImageResource(android.R.drawable.arrow_down_float);
        } else {
            hiddenLayout.setVisibility(View.VISIBLE);
            upArrow.setVisibility(View.VISIBLE);
            downArrow.setVisibility(View.GONE);
            downArrow.setImageResource(android.R.drawable.arrow_up_float);
        }
        isExpanded = !isExpanded;
    }
}
