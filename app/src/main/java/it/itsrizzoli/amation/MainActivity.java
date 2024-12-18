package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new ClassificaFragment())
                .commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
              /*  case R.id.menu_home:
                    // Handle "Home" menu item click
                    break;

                case R.id.menu_favorites:
                    // Handle "Preferiti" menu item click
                    break;

                case R.id.menu_search:
                    // Handle "Cerca" menu item click
                    break;

                case R.id.menu_rank:
                    // Handle "Classifica" menu item click
                    break;

                case R.id.menu_profile:
                    // Handle "Profilo" menu item click
                    break;
           */
                case 1:
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