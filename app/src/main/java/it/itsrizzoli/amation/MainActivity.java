package it.itsrizzoli.amation;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
    boolean isGuest = true;



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new LoginFragment())
                .commit();
        RetrofitHelper.initialize("http://172.17.49.183:5000");
        NetworkConfig.enableDebugMode(true);

        Log.d("TAG", "BASE: " + NetworkConfig.getBaseUrl());

        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(this);
        sharedPrefsManager.clearAll();
        int idUtente = sharedPrefsManager.getUserId();
        if (idUtente != -1) {

            user = new UserModel();
            idUtente = 3;
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