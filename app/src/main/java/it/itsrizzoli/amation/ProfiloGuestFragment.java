package it.itsrizzoli.amation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfiloGuestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfiloGuestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfiloGuestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfiloGuestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfiloGuestFragment newInstance(String param1, String param2) {
        ProfiloGuestFragment fragment = new ProfiloGuestFragment();
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


        View view = inflater.inflate(R.layout.fragment_profilo_guest, container, false);
        MaterialButton buttonLogin = view.findViewById(R.id.btn_login);
        MaterialButton buttonRegistra = view.findViewById(R.id.btn_registrazione);

        buttonLogin.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new LoginFragment());
            transaction.addToBackStack(null);
            transaction.commit();

            Toast.makeText(getContext(), "Login eseguito con successo!", Toast.LENGTH_SHORT).show();
        });

        buttonRegistra.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new RegistraFragment());
            transaction.addToBackStack(null);
            transaction.commit();

            Toast.makeText(getContext(), "Registrazione completata!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}