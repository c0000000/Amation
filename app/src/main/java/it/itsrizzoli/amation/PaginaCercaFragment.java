package it.itsrizzoli.amation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import it.itsrizzoli.amation.model.AnimeModel;

public class PaginaCercaFragment extends Fragment {

    private EditText editCerca;
    private GridView gridView;
    private AnimeAdapter animeAdapter;
    private List<AnimeModel> animeList = new ArrayList<>();
    private List<AnimeModel> filteredAnimeList = new ArrayList<>();

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
        gridView = view.findViewById(R.id.gridViewAnime);


        loadAnimeData();

        editCerca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAnime(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void loadAnimeData() {

        animeList.clear();
        filteredAnimeList.clear();

        RetrofitHelper.<AnimeModel>request("/anime-db")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    if (list != null) {
                        animeList.addAll(list);
                        filteredAnimeList.addAll(list);
                        animeAdapter = new AnimeAdapter(getContext(), filteredAnimeList);
                        gridView.setAdapter(animeAdapter);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    } else {
                        Toast.makeText(getContext(), "Nessun anime trovato", Toast.LENGTH_LONG).show();
                    }
                })
                .onFailure((call, t) -> {
                    Toast.makeText(getContext(), "Errore nella chiamata: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }).executeRequest(AnimeModel.class);
    }

    private void filterAnime(String query) {
        filteredAnimeList.clear();

        if (query.isEmpty()) {
            filteredAnimeList.addAll(animeList);
        } else {
            for (AnimeModel anime : animeList) {
                if (anime.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredAnimeList.add(anime);
                }
            }
        }

        animeAdapter.notifyDataSetChanged();
    }
}
