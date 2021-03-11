package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.scranton.nesbittj3.factualfantasy.model.Player;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchViewHolder> {
    private Context context;
    private ArrayList<Player> players;
    private TextView textView;
    private ArrayList<Integer> selected;
    WatchViewHolder watchViewHolder;

    public WatchListAdapter(Context context, ArrayList<Player> players){
        this.context = context;
        this.players = players;
        //this.textView = textView;
        selected = new ArrayList<>();
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        watchViewHolder = new WatchViewHolder(view);
        return new WatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        watchViewHolder.bind(players.get(position));
    }


    @Override
    public int getItemCount() {
        return players.size();
    }

    public void resetSelected() {
        //selected.clear();
        //notifyDataSetChanged();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer i: selected) list.add(i);
        selected.clear();

        for (Integer i: list) {
            notifyItemChanged(i);
        }
    }

    public ArrayList<Integer> getSelected() {
        return selected;
    }

    class WatchViewHolder extends RecyclerView.ViewHolder {
        private TextView idView;
        private TextView nameView;
        private CheckBox checkBox;

        WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.id_view);
            nameView = itemView.findViewById(R.id.name_view);
            checkBox= itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    CheckBox c = (CheckBox)v;
                    if (c.isChecked()) {
                        selected.add(position);
                        Toast.makeText(context, "Checked: " + selected, Toast.LENGTH_LONG).show();
                    }
                    else {
                        selected.remove(new Integer(position));
                        Toast.makeText(context, "UnChecked: " + selected, Toast.LENGTH_LONG).show();
                    }
                }
            } );
        }
        public void bind(Player players) {
            idView.setText("" + players.id);
            nameView.setText(players.name);

            if (selected.contains(new Integer(getLayoutPosition()))) {
                checkBox.setSelected(true);
            }
            else {
                checkBox.setSelected(false);
            }
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
