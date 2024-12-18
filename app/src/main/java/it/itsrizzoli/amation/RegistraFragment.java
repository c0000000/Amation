package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.itsrizzoli.amation.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistraFragment newInstance(String param1, String param2) {
        RegistraFragment fragment = new RegistraFragment();
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
        View view = inflater.inflate(R.layout.fragment_registra, container, false);

        EditText username = view.findViewById(R.id.userReg);
        EditText email = view.findViewById(R.id.emailReg);
        EditText password = view.findViewById(R.id.passReg);
        EditText passwordVer = view.findViewById(R.id.passVerReg);

        Button registraBtn = view.findViewById(R.id.RegBtn);
        Button goToLoginBtn = view.findViewById(R.id.gotoLog);
        Button goToGuestBtn = view.findViewById(R.id.gotoGuest);

        registraBtn.setOnClickListener(new View.OnClickListener() {
            Fragment passFragment = null;

            @Override
            public void onClick(View v) {
                String userText = String.valueOf(username.getText());
                String emailText = String.valueOf(email.getText());
                String passText = String.valueOf(password.getText());
                String passVerText = String.valueOf(passwordVer.getText());

                if (passText.equals(passVerText)) {
                    UserModel model = new UserModel();
                    model.setUsername(userText);
                    model.setEmail(emailText);
                    model.setPassword(passText);
                    Toast.makeText(getActivity(), "Registrazione avvenuta con successo!", Toast.LENGTH_SHORT).show();
                    passFragment = new LoginFragment();
                } else {
                    Toast.makeText(getActivity(), "I due password non coincidono!", Toast.LENGTH_SHORT).show();
                }
            }


        });

        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            Fragment passFragment = null;

            @Override
            public void onClick(View v) {
                passFragment = new LoginFragment();

            }
        });

        return view;
    }
}


