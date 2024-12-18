package it.itsrizzoli.amation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import it.itsrizzoli.amation.model.AnimeModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassificaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassificaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassificaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassificaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassificaFragment newInstance(String param1, String param2) {
        ClassificaFragment fragment = new ClassificaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RetrofitHelper.<AnimeModel>request("/anime-db")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    if (animeModel != null) {
                        Toast.makeText(getContext(), "Anime trovato: " + animeModel, Toast.LENGTH_LONG).show();
                    }

                    if (list != null) {
                        Toast.makeText(getContext(), "Anime trovati: " + list.size(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", "LISTA ANIME: " + list.get(0).getTitle());
                    }
                })
                .onFailure((call, t) -> {
                    Toast.makeText(getContext(), "Errore nella chiamata", Toast.LENGTH_LONG).show();
                }).executeRequest(AnimeModel.class);


        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_classifica, container, false);
        ImageView imageView = fragment.findViewById(R.id.card_anime_first_image);
        ImageView imageViewSecond = fragment.findViewById(R.id.card_anime_second_image);
        ImageView imageViewThird = fragment.findViewById(R.id.card_anime_third_image);

        Glide.with(this)
                .load(R.drawable.first_image_classifica_min_min)
                .into(imageView);

        Glide.with(this)
                .load(R.drawable.first_image_classifica_min_min)
                .into(imageViewSecond);
        Glide.with(this)
                .load(R.drawable.first_image_classifica_min_min)
                .into(imageViewThird);

        return inflater.inflate(R.layout.fragment_classifica, container, false);
    }
}