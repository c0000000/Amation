package it.itsrizzoli.amation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.retrofit_helper.NetworkConfig;
import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.itsrizzoli.amation.model.AnimeModel;
import it.itsrizzoli.amation.model.UserModel;

public class MainActivity extends AppCompatActivity {

    UserModel user;
    boolean isGuest = false;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new InfopageFragment())
                .commit();
        RetrofitHelper.initialize("http://192.168.0.110:5000");
        NetworkConfig.enableDebugMode(true);

        Log.d("TAG", "BASE: " + NetworkConfig.getBaseUrl());

        RetrofitHelper.<AnimeModel>request("/anime-db")
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, animeModel, list) -> {
                    if (animeModel != null) {
                        Toast.makeText(this, "Anime trovato: " + animeModel, Toast.LENGTH_LONG).show();
                    }

                    if (list != null) {
                        Toast.makeText(this, "Anime trovati: " + list.size(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", "LISTA ANIME: " + list.get(0).getTitle());
                    }
                })
                .onFailure((call, t) -> {
                    Toast.makeText(this, "Errore nella chiamata", Toast.LENGTH_LONG).show();
                }).executeRequest(AnimeModel.class);


        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(this);
        sharedPrefsManager.clearAll();
        int idUtente = sharedPrefsManager.getUserId();
        if (idUtente != -1) {
            // Fare richeista profilo
            user = new UserModel();
            idUtente = 3;//user.getIdUtente();
            sharedPrefsManager.saveUserId(idUtente);

            if (user != null) {
                isGuest = false;
            }
        } else {
            isGuest = false;
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menu_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.menu_favorites) {
                selectedFragment = new PaginaPrefFragment();
            }  else if (item.getItemId() == R.id.menu_search) {
                selectedFragment = new PaginaCercaFragment();
            } else if (item.getItemId() == R.id.menu_rank) {
                selectedFragment = new ClassificaFragment();
            } else if (item.getItemId() == R.id.menu_profile) {
                selectedFragment = !isGuest ? new ProfiloFragment() : new ProfiloGuestFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
            }

            return true;
        });

    }

}