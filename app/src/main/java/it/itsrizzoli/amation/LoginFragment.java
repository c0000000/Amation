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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText username = view.findViewById(R.id.userText);
        EditText password = view.findViewById(R.id.passwordTetx);
        Button logButton = view.findViewById(R.id.logBtn);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = String.valueOf(username.getText());
                String passText = String.valueOf(password.getText());
                if(userText.equals("admin") && passText.equals("admin")) {
                    Toast.makeText(getActivity(), "Loggato con successo!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Username errato!", Toast.LENGTH_SHORT).show();

                }
                UserModel model = new UserModel();
                model.setUsername(userText);
                model.setPassword(passText);
            }
        }
        );
        return inflater.inflate(R.layout.fragment_login, container, false);




    }

}