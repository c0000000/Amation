package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import it.itsrizzoli.amation.model.AnimeModel;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfopageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfopageFragment extends Fragment {

    private Button episodiButton;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfopageFragment() {
        // Required empty public constructor
    }

    public static InfopageFragment newInstance(String IdAnime) {
        InfopageFragment fragment = new InfopageFragment();
        Bundle args = new Bundle();
        args.putString("IdAnime", IdAnime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_infopage, container, false);

        TextView titoloAnime = view.findViewById(R.id.titoloanime);

        TextView genere = view.findViewById(R.id.genere);

        TextView studio = view.findViewById(R.id.studio);

        TextView numeroEpisodi = view.findViewById(R.id.numeroepisodi);

        TextView stagione = view.findViewById(R.id.stagione);

        TextView descrizione = view.findViewById(R.id.descrizione);

        ImageView thumbnail = view.findViewById(R.id.thumbnail);

        RatingBar recensione = view.findViewById(R.id.stellerecensione);

        FloatingActionButton buttonFluttuante;


        Bundle bundle = getArguments();
        String idAnime;
        if (bundle != null) {
            idAnime = bundle.getString("idAnime");
        } else {
            idAnime = "-1";
        }

        RetrofitHelper.<AnimeModel>request("/anime/trova")
                .addQueryParam("idAnime", idAnime)
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    if (animeModel != null) {
                        titoloAnime.setText(animeModel.getTitle());
                        descrizione.setText(animeModel.getSynopsis());
                        stagione.setText(animeModel.getPremiered());
                        numeroEpisodi.setText(String.valueOf(animeModel.getEpisodes()));

                        String generi = String.join(", ", animeModel.getGenres());
                        genere.setText(generi);


                        String studi = String.join(", ", animeModel.getStudios());
                        studio.setText(studi);

                        recensione.setRating(Float.parseFloat(animeModel.getScore()) / 2f);

                        Glide.with(this)
                                .load(animeModel.getPicture())
                                .into(thumbnail);

                    }
                })
                .onFailure((call, throwable) -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Errore nel caricamento dei dati", Toast.LENGTH_SHORT).show();
                    }
                })
                .executeRequest(AnimeModel.class);



        buttonFluttuante = view.findViewById(R.id.buttonfluttuante);
        String finalIdAnime = idAnime;
        buttonFluttuante.setOnClickListener(v -> {
            addAnimeToFavorites(finalIdAnime);
        });


        ImageButton backButton = view.findViewById(R.id.infoBackButton);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }


    private void navigateToChecklistFragment() {
        Bundle args = getArguments();
        String idAnime = (args != null && args.getString("idAnime") != null)
                ? args.getString("idAnime")
                : "813"; // DEFAULT messo come fallback per prove


        Fragment checklistFragment = ChecklistBottoniepisodiFragment.newInstance(idAnime);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main, checklistFragment);
        transaction.addToBackStack("ChecklistFragment");

        if (!requireActivity().getSupportFragmentManager().isStateSaved()) {
            transaction.commit();
        } else {
            transaction.commitAllowingStateLoss();
        }
    }


    private void addAnimeToFavorites(String idAnime) {
        RetrofitHelper.<Void>request("/add-anime")
                .addQueryParam("idAnime", idAnime)

                .addQueryParam("idUtente", "0")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    Toast.makeText(getContext(), "Anime Aggiunto alla Lista dei Preferiti", Toast.LENGTH_LONG).show();
                }).executeRequest(Void.class);

    }
}