package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import it.itsrizzoli.amation.model.AnimeModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChecklistBottoniepisodiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChecklistBottoniepisodiFragment extends Fragment {

    private GridView gridView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChecklistBottoniepisodiFragment() {
        // Required empty public constructor
    }

    public static ChecklistBottoniepisodiFragment newInstance(String IdAnime) {
        ChecklistBottoniepisodiFragment fragment = new ChecklistBottoniepisodiFragment();
        Bundle args = new Bundle();
        args.putString("IdAnime", IdAnime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checklist_bottoniepisodi, container, false);

        gridView = view.findViewById(R.id.gridViewChecklist);

        Bundle args = getArguments();
        if (args != null) {
            String idAnime = args.getString("idAnime");
            //ID TEMPORANEO PER FARE PROVE SENZA BUNDLE, RIMUOVERE
            idAnime = "813";

            RetrofitHelper.<AnimeModel>request("/anime/trova")
                    .addQueryParam("idAnime", idAnime)
                    .method(RequestBuilder.HttpType.GET)
                    .onSuccess((call, response, animeModel, list) -> {
                        if (animeModel != null) {
                            int numeroEpisodi = animeModel.getEpisodes();
                            setupGridView(view, numeroEpisodi);
                        }
                    })
                    .onFailure((call, throwable) -> {
                        Toast.makeText(getContext(), "Errore nel caricamento dei dati", Toast.LENGTH_SHORT).show();
                    }).executeRequest(AnimeModel.class);
        }

        ImageButton backButton = view.findViewById(R.id.checklistBackButton);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void setupGridView(View view, int numeroEpisodi) {
        ChecklistAdapter adapter = new ChecklistAdapter(getContext(), numeroEpisodi);
        gridView.setAdapter(adapter);
    }
}