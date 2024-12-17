package it.itsrizzoli.amation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfopageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfopageFragment extends Fragment {

    private Button episodiButton;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfopageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfopageFragment newInstance(String param1, String param2) {
        InfopageFragment fragment = new InfopageFragment();
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

        // Inflating il layout
        View view = inflater.inflate(R.layout.fragment_infopage, container, false);

        // Trova il bottone
        episodiButton = view.findViewById(R.id.episodiButton);

        // Imposta un listener per il click
        episodiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quando il bottone è cliccato, sostituire il fragment
                navigateToChecklistFragment();
            }
        });

        return inflater.inflate(R.layout.fragment_infopage, container, false);
    }


    private void navigateToChecklistFragment() {
        // Crea una nuova istanza del fragment checklist
        Fragment checklistFragment = new ChecklistBottoniepisodiFragment();

        // Inizia una transazione per sostituire il fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main, checklistFragment);  // R.id.fragment_container è l'ID del contenitore del fragment (nel tuo layout)
        transaction.addToBackStack(null);  // Aggiungi alla backstack se vuoi che l'utente possa tornare indietro
        transaction.commit();  // Esegui la transazione
    }
}