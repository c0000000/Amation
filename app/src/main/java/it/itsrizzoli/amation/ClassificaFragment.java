package it.itsrizzoli.amation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import it.itsrizzoli.amation.libs.ArrayAdapterUtils;
import it.itsrizzoli.amation.libs.DynamicListAdapter;
import it.itsrizzoli.amation.model.AnimeModel;
import it.itsrizzoli.amation.model.AnimeRanking;
import retrofit2.Call;
import retrofit2.Response;

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

    List<AnimeModel> animeModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifica, container, false);


        RetrofitHelper.<AnimeModel>request("/anime-db")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeRanking, rankingList) -> {
                    if (rankingList == null) {
                        Toast.makeText(getContext(), "AnimeRanking null", Toast.LENGTH_LONG).show();
                        return;
                    }
                    rankingList.sort(Comparator.comparingInt(anime -> Integer.parseInt(anime.getRanked().replace("#", ""))));
                    fillAnimeRankAdapter(rankingList, view);

                    Toast.makeText(getContext(), "Anime caricati", Toast.LENGTH_LONG).show();
                })
                .onFailure((call, t) -> {
                    Toast.makeText(getContext(), "Ricaricare la pagina", Toast.LENGTH_LONG).show();

                }).executeRequest(AnimeModel.class);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    DynamicListAdapter<AnimeModel> dynamicListAdapter;

    private void fillAnimeRankAdapter(List<AnimeModel> animeList, View view) {

        dynamicListAdapter = ArrayAdapterUtils.with(getContext(), animeList)
                .setLayoutRes(R.layout.item_card_anime)
                .setBinder((viewHolder, animeModel, i) -> {
                    // è una item List View
                    if (animeModel == null) {
                        Toast.makeText(getContext(), "Anime getView null", Toast.LENGTH_LONG).show();
                        return;
                    }
                    TextView titleTextView = viewHolder.findViewById(R.id.card_anime_title);
                    ImageView imageView = viewHolder.findViewById(R.id.card_image_anime);
                    TextView txtPostion = viewHolder.findViewById(R.id.position_rank);
                    // Imposta il titolo dell'anime
                    titleTextView.setText(animeModel.getTitle());
                    txtPostion.setText(String.valueOf(i + 1));

                    // Carica l'immagine dell'anime utilizzando Glide
                    Glide.with(getContext())
                            .load(animeModel.getPicture())
                            .placeholder(R.drawable.card_image_placehodlerpng) // Your placeholder image
                            .error(R.drawable.card_image_placehodlerpng) // Image to show if the load fails
                            .dontAnimate()
                            .into(imageView);


                })
                .setOnItemClick((parent, clickedView, pos, itemId) -> {
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    InfopageFragment infopage = new InfopageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("idAnime", animeList.get(pos).getId() + "");
                    infopage.setArguments(bundle);
                    transaction.replace(R.id.nav_host_fragment, infopage);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    Toast.makeText(getContext(), "Posizione: " + (pos + 1), Toast.LENGTH_SHORT).show();

                })
                .applyTo(R.id.lista_rank_from_4, view);
    }
}