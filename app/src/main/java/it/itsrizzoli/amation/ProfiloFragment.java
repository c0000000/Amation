package it.itsrizzoli.amation;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.itsrizzoli.amation.model.TotaleTempo;
import it.itsrizzoli.amation.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfiloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfiloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfiloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfiloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfiloFragment newInstance(String param1, String param2) {
        ProfiloFragment fragment = new ProfiloFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private UserModel userModel;
    private LineChart lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (getContext() != null) {
            SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(getContext());

            int idUtente = sharedPrefsManager.getUserId();


            userModel = new UserModel();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profilo, container, false);


        int idUtente = -1;
        SharedPrefsManager sharedPrefsManager;
        if (getContext() != null) {
            sharedPrefsManager = new SharedPrefsManager(getContext());
            sharedPrefsManager.clearAll();
            idUtente = sharedPrefsManager.getUserId();
        } else {
            sharedPrefsManager = null;
        }

        if (idUtente != -1) {
            return view;
        }
        idUtente = 0;

        getUtenteAsync(idUtente, view, sharedPrefsManager);


        return view;
    }

    private void getUtenteAsync(int idUtente, View view, SharedPrefsManager sharedPrefsManager) {
        RetrofitHelper.<UserModel>request("/profilo")
                .addQueryParam("idUtente", String.valueOf(idUtente))
                .method(RequestBuilder.HttpType.GET)
                .onSuccess((call, response, userModel, list) -> {
                    setInfoProfilo(userModel, view, sharedPrefsManager);
                }).onFailure((call, t) -> {
                    Toast.makeText(getContext(), "Errore durante il caricamento del profilo", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                })
                .executeRequest(UserModel.class);
    }

    private void setInfoProfilo(UserModel userModel, View view, SharedPrefsManager sharedPrefsManager) {
        if (userModel == null) {
            Toast.makeText(getContext(), "Profilo non trovato", Toast.LENGTH_LONG).show();
            return;
        }

        TextView txtUsername = view.findViewById(R.id.card_title);
        TextView txtNome = view.findViewById(R.id.txt_nome);
        TextView txtCognome = view.findViewById(R.id.txt_cognome);
        TextView txtEmail = view.findViewById(R.id.txt_email);
        TextView txtEta = view.findViewById(R.id.txt_eta);
        TextView txtTempoS = view.findViewById(R.id.valore_tempo_totale);
        TextView txtPassword = view.findViewById(R.id.txt_password);
        lineChart = view.findViewById(R.id.chart);

        txtUsername.setText(userModel.getUsername());
        txtNome.setText(userModel.getNome());
        txtCognome.setText(userModel.getCognome());
        txtEta.setText(String.valueOf(userModel.getDataNascita()));

        txtEmail.setText(userModel.getEmail());

        int totalTempo = userModel.getTotaleTempo().get(userModel.getTotaleTempo().size() - 1).getTempoS();

        txtTempoS.setText(formatSeconds(totalTempo));

        if (sharedPrefsManager == null) {
            Toast.makeText(getContext(), "Impossibile caricare il profilo", Toast.LENGTH_LONG).show();
        } else {
            sharedPrefsManager.saveUserId(userModel.getId());
            Toast.makeText(getContext(), "Profilo caricato", Toast.LENGTH_LONG).show();

        }

        ImageButton imgButtonWatch = view.findViewById(R.id.imgBtnWatch);

        imgButtonWatch.setOnClickListener(v -> {
            if (txtPassword.getText().equals("*****************")) {
                imgButtonWatch.setImageResource(R.drawable.ic_visibility_off);
                txtPassword.setText(userModel.getPassword());
            } else {
                imgButtonWatch.setImageResource(R.drawable.ic_visibility);
                txtPassword.setText("*****************");
            }
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
        });

    }

    public static String formatSeconds(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = ((seconds % 86400) % 3600) / 60;

        return String.format("%d d %02d h %02d m", days, hours, minutes);
    }


}
