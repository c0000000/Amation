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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChecklistBottoniepisodiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChecklistBottoniepisodiFragment newInstance(String param1, String param2) {
        ChecklistBottoniepisodiFragment fragment = new ChecklistBottoniepisodiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

            RetrofitHelper.<AnimeModel>request("/anime/trova/{id_anime}")
                    .addPathParam("id_anime", idAnime)
                    .method(RequestBuilder.HttpType.GET)
                    .onSuccess((call, response, animeModel, list) -> {
                        if (animeModel != null) {
                            int numeroEpisodi = animeModel.getEpisodes();
                            setupGridView(view, numeroEpisodi);
                        }
                    })
                    .onFailure((call, throwable) -> {
                        Toast.makeText(getContext(), "Errore nel caricamento dei dati", Toast.LENGTH_SHORT).show();
                    });
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