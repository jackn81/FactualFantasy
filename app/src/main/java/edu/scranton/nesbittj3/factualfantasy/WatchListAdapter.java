package edu.scranton.nesbittj3.factualfantasy;
import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchViewHolder> {
    private Context context;
    private ArrayList<ExamplePlayer> playersList;
    private TextView textView;
    private ArrayList<Integer> selected;
    WatchViewHolder watchViewHolder;

    public WatchListAdapter(Context context, ArrayList<ExamplePlayer> nPlayersList){
        this.context = context;
        this.playersList = nPlayersList;
        //this.textView = textView;
        selected = new ArrayList<>();
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
        //watchViewHolder = new WatchViewHolder(view); //might not need this?
        return new WatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        ExamplePlayer currentPlayer = playersList.get(position);

        String pImageUrl = currentPlayer.getpImageUrl();
        String pName = currentPlayer.getpName();
        String pTeam = currentPlayer.getpTeam();
        boolean pCheck = currentPlayer.getpCheck();

        holder.pNameTextView.setText(pName);
        holder.pTeamTextView.setText(pTeam);
        holder.checkBox.setChecked(false); //set as false right now
        //String pImageUrl = "https://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players/full/3122840.png&w=350&h=254";
        Picasso.with(context).load(pImageUrl).into(holder.pImageView);

        //watchViewHolder.bind(playersList.get(position));
    }


    @Override
    public int getItemCount() {
        return playersList.size();
    }

  /*  public void resetSelected() {
        //selected.clear();
        //notifyDataSetChanged();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer i: selected) list.add(i);
        selected.clear();

        for (Integer i: list) {
            notifyItemChanged(i);
        }
    }*/

    public ArrayList<Integer> getSelected() {
        return selected;
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImageView;
        private TextView pNameTextView;
        private TextView pTeamTextView;
        private CheckBox checkBox;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            pImageView = itemView.findViewById(R.id.image_view);
            pNameTextView = itemView.findViewById(R.id.name_view);
            pTeamTextView = itemView.findViewById(R.id.team_view);
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

    }
}
