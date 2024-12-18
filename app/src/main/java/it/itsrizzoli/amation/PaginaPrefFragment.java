package it.itsrizzoli.amation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import it.itsrizzoli.amation.model.AnimeModel;

public class PaginaPrefFragment extends Fragment {

    private GridView gridViewAnimePref;
    private AnimeAdapter animeAdapter;
    private List<AnimeModel> animePrefList = new ArrayList<>();
    private List<AnimeModel> filteredAnimeList = new ArrayList<>(); // Lista filtrata
    private EditText editPref;
    private TextView textPref;
    private ImageView backArrow;

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

        textPref = view.findViewById(R.id.textPref);
        editPref = view.findViewById(R.id.editPref);
        ImageView searchIcon = view.findViewById(R.id.searchIcon);
        backArrow = view.findViewById(R.id.backArrow);
        gridViewAnimePref = view.findViewById(R.id.gridViewAnimePref);


        loadFavoriteAnimeData();


        gridViewAnimePref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                InfopageFragment infopage = new InfopageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("idAnime", filteredAnimeList.get(position).getId() + "");
                infopage.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, infopage);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        editPref.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Non fare nulla
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAnime(s.toString()); // Filtra gli anime in base alla query
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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

    private void loadFavoriteAnimeData() {

        animePrefList.clear();
        filteredAnimeList.clear();

        // Supponiamo che l'ID utente sia 0
        int userId = 0;
        String url = "/anime/preferiti/" + userId;

        RetrofitHelper.<AnimeModel>request(url)
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    if (list != null && !list.isEmpty()) {
                        animePrefList.addAll(list);
                        filteredAnimeList.addAll(list);
                        animeAdapter = new AnimeAdapter(getContext(), filteredAnimeList);
                        gridViewAnimePref.setAdapter(animeAdapter);


                        gridViewAnimePref.setOnItemClickListener((parent, clickedView, pos, itemId) -> {
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            InfopageFragment infopage = new InfopageFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("idAnime", filteredAnimeList.get(pos).getId() + "");
                            infopage.setArguments(bundle);
                            transaction.replace(R.id.nav_host_fragment, infopage);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        });

                        Log.d("PaginaPrefFragment", "Anime caricati: " + animePrefList.size());
                    } else {
                        Toast.makeText(getContext(), "Nessun anime trovato", Toast.LENGTH_SHORT).show();
                    }
                })
                .onFailure((call, throwable) -> {
                    Toast.makeText(getContext(), "Errore nel caricamento degli anime", Toast.LENGTH_SHORT).show();
                    Log.e("PaginaPrefFragment", "Errore: " + throwable.getMessage());
                })
                .executeRequest(AnimeModel.class);
    }

    private void filterAnime(String query) {
        filteredAnimeList.clear();

        if (query.isEmpty()) {
            filteredAnimeList.addAll(animePrefList);
        } else {
            for (AnimeModel anime : animePrefList) {
                if (anime.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredAnimeList.add(anime);
                }
            }
        }

        animeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

