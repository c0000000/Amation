package it.itsrizzoli.amation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


public class ChecklistAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int numeroEpisodi;

    public ChecklistAdapter(Context context, int numeroEpisodi) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.numeroEpisodi = numeroEpisodi;
    }

    @Override
    public int getCount() {
        return numeroEpisodi;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.template_singolo_bottoneiepisodi, parent, false);
        }

        Button button = convertView.findViewById(R.id.toggleEpisodio);
        button.setText(String.valueOf(position + 1));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = button.isSelected();

                button.setSelected(!isSelected);

                if (button.isSelected()) {
                    button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_done_24, 0, 0, 0);
                } else {
                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

                notifyDataSetChanged();
            }
        });

        return convertView;
    }


}
