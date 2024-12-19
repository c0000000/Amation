package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.arrayadapterutils.ArrayAdapterUtils;
import com.example.arrayadapterutils.DynamicListAdapter;
import com.example.retrofit_helper.NetworkConfig;
import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;

import java.util.List;

import it.itsrizzoli.amation.model.AnimeModel;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean isSelectedEstate = false;

    private boolean isSelectedPrimavera = false;




    DynamicListAdapter<AnimeModel> adapter;
    List<AnimeModel> allAnimeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View viewFragment = inflater.inflate(R.layout.fragment_home, container, false);


        allAnime(viewFragment);


        Button btnSpring = viewFragment.findViewById(R.id.btnPrimavera);

        Button btnEstate = viewFragment.findViewById(R.id.btnEstate);
        btnSpring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isSelectedPrimavera){

                    HomeFragment.this.extracted(viewFragment, "1989", "Spring");

                    Toast.makeText(getContext(), "Refresh ture", Toast.LENGTH_SHORT).show();

                    isSelectedPrimavera = true;

                }else if(adapter != null) {

                    adapter.clearItems();
                    allAnime(viewFragment);

                    Toast.makeText(getContext(), "Refresh falsee", Toast.LENGTH_SHORT).show();
                    isSelectedPrimavera = false;
                }


            }
            // Inflate the layout for this fragment
        });

        btnEstate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isSelectedEstate){

                    HomeFragment.this.extracted(viewFragment, "2011", "Fall");
                    Toast.makeText(getContext(), "Refresh ture", Toast.LENGTH_SHORT).show();
                    isSelectedEstate = true;

                }else if(adapter != null) {

                    adapter.clearItems();
                    allAnime(viewFragment);
                    Toast.makeText(getContext(), "Refresh falsee", Toast.LENGTH_SHORT).show();
                    isSelectedEstate = false;
                }





            }
            // Inflate the layout for this fragment
        });
        return viewFragment;
    }

    private void allAnime(View viewFragment) {
        RetrofitHelper.<AnimeModel>request("/anime-db")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {

                    if (list == null) {
                        Toast.makeText(getContext(), "Anime null", Toast.LENGTH_LONG).show();
                        return;

                    }
                    allAnimeList = list;
                    adapter = ArrayAdapterUtils.with(getContext(), list)
                            .setLayoutRes(R.layout.img_name_anime)
                            .setBinder((viewHolder, anime, i) -> {
                                // è una item List View
                                if (anime == null) {
                                    Toast.makeText(getContext(), "Anime getView null", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                ImageView imgAnime = viewHolder.findViewById(R.id.imageView1);
                                TextView nomeAnime = viewHolder.findViewById(R.id.textView1);

                                // Imposta il titolo dell'anime
                                nomeAnime.setText(anime.getTitle());

                                // Carica l'immagine dell'anime utilizzando Glide
                                Glide.with(getContext())
                                        .load(anime.getPicture())
                                        .placeholder(R.drawable.card_image_placehodlerpng) // Your placeholder image
                                        .error(R.drawable.card_image_placehodlerpng) // Image to show if the load fails
                                        .dontAnimate()
                                        .into(imgAnime);


                            })
                            .setOnItemClick((parent, clickedView, pos, itemId) -> {
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                InfopageFragment infopage = new InfopageFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("idAnime", list.get(pos).getId() + "");
                                infopage.setArguments(bundle);
                                transaction.replace(R.id.nav_host_fragment, infopage);
                                transaction.addToBackStack(null);
                                transaction.commit();

                                Toast.makeText(getContext(), "Posizione: " + (pos + 1), Toast.LENGTH_SHORT).show();

                            })
                            .applyTo(R.id.lista_anime, viewFragment);


                }).executeRequest(AnimeModel.class);
    }

    private void extracted(View viewFragment, String anno, String stagione) {
        RetrofitHelper.<AnimeModel>request("/anime-stagionali")
                .method(RequestBuilder.HttpType.GET)
                .addQueryParam("anno", anno)
                .addQueryParam("stagione", stagione)
                .onSuccess((call, response, animeModel, list) -> {

                    if (list == null) {
                        Toast.makeText(getContext(), "Anime null", Toast.LENGTH_LONG).show();
                        return;

                    }
                    adapter.clearItems();

                    adapter = ArrayAdapterUtils.with(getContext(), list)
                            .setLayoutRes(R.layout.img_name_anime)
                            .setBinder((viewHolder, anime, i) -> {
                                // è una item List View
                                if (anime == null) {
                                    Toast.makeText(getContext(), "Anime getView null", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                ImageView imgAnime = viewHolder.findViewById(R.id.imageView1);
                                TextView nomeAnime = viewHolder.findViewById(R.id.textView1);

                                // Imposta il titolo dell'anime
                                nomeAnime.setText(anime.getTitle());

                                // Carica l'immagine dell'anime utilizzando Glide
                                Glide.with(getContext())
                                        .load(anime.getPicture())
                                        .placeholder(R.drawable.card_image_placehodlerpng) // Your placeholder image
                                        .error(R.drawable.card_image_placehodlerpng) // Image to show if the load fails
                                        .dontAnimate()
                                        .into(imgAnime);


                            })
                            .setOnItemClick((parent, clickedView, pos, itemId) -> {
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                InfopageFragment infopage = new InfopageFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("idAnime", list.get(pos).getId() + "");
                                infopage.setArguments(bundle);
                                transaction.replace(R.id.nav_host_fragment, infopage);
                                transaction.addToBackStack(null);
                                transaction.commit();

                                Toast.makeText(getContext(), "Posizione: " + (pos + 1), Toast.LENGTH_SHORT).show();

                            })
                            .applyTo(R.id.lista_anime, viewFragment);


                }).onFailure(new RequestBuilder.OnResponseHandlerError() {
                    @Override
                    public void handle(Response<?> response, Throwable throwable) {

                    }
                })
                .executeRequest(AnimeModel.class);
    }

}