package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit_helper.RequestBuilder;
import com.example.retrofit_helper.RetrofitHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.itsrizzoli.amation.model.AnimeModel;
import it.itsrizzoli.amation.model.UserModel;
import retrofit2.Response;

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

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText username = view.findViewById(R.id.userText);
        EditText password = view.findViewById(R.id.passwordTetx);

        Button logButton = view.findViewById(R.id.logBtn);
        Button gotoRegistraBtn = view.findViewById(R.id.gotoRegistra);
        BottomNavigationView bottomNavigationView = null;
        if (getActivity() != null) {
            bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }

        logButton.setOnClickListener(new View.OnClickListener() {
            Fragment passFragment = null;

            @Override
            public void onClick(View v) {
                String userText = String.valueOf(username.getText());
                String passText = String.valueOf(password.getText());

                RetrofitHelper.<UserModel>request("/login")
                        .method(RequestBuilder.HttpType.POST)
                        .withRequestBody(new UserModel(userText, passText))
                        .onFailure(new RequestBuilder.OnResponseHandlerError() {
                            @Override
                            public void handle(Response<?> response, Throwable throwable) {
                                Toast.makeText(getContext(), "Username o password errato!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSuccess((call, response, UserModel, list) -> {
                            if (UserModel != null) {
                                Toast.makeText(getContext(), "Loggato con successo!", Toast.LENGTH_SHORT).show();
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment, new HomeFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                                if (getActivity() != null) {
                                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                                    bottomNavigationView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(getContext(), "Username o password errato!", Toast.LENGTH_SHORT).show();
                            }

                        }).executeRequest(UserModel.class);
            }
        });

        gotoRegistraBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Stai andando nella pagina registrazione", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new RegistraFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}

