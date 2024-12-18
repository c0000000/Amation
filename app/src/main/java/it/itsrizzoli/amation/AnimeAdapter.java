package it.itsrizzoli.amation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide; // Assicurati di avere Glide nel tuo build.gradle

import java.util.List;

import it.itsrizzoli.amation.R;
import it.itsrizzoli.amation.model.AnimeModel;

public class AnimeAdapter extends BaseAdapter {
    private Context context;
    private List<AnimeModel> animeList;

    public AnimeAdapter(Context context, List<AnimeModel> animeList) {
        this.context = context;
        this.animeList = animeList;
    }

    @Override
    public int getCount() {
        return animeList.size();
    }

    @Override
    public Object getItem(int position) {
        return animeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return animeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_scheda_anime, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.copertinaImageView);
        TextView textView = convertView.findViewById(R.id.titoloTextView);

        AnimeModel anime = animeList.get(position);
        textView.setText(anime.getTitle());

        // Carica l'immagine usando Glide
        Glide.with(context)
                .load(anime.getPicture())
                .into(imageView);


        return convertView;
    }
}
