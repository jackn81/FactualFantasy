package edu.scranton.nesbittj3.factualfantasy;
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


public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.WatchViewHolder> {
    private Context context;
    private ArrayList<ExamplePlayer> playersList;
    private ArrayList<ExamplePlayer> watchList;
    private TextView textView;
    private ArrayList<Integer> selected;
    WatchViewHolder watchViewHolder;

    public PassListAdapter(Context context, ArrayList<ExamplePlayer> nPlayersList){
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
        String pPos = currentPlayer.getpPosition();
        boolean pCheck = currentPlayer.getpCheck();

        holder.pNameTextView.setText(pName);
        holder.pTeamTextView.setText(pTeam);
        holder.pPosTextView.setText(pPos);
        holder.checkBox.setChecked(false); //set as false right now

        Picasso.with(context).load(pImageUrl).into(holder.pImageView);


    }


    @Override
    public int getItemCount() {
        return playersList.size();
    }


    public ArrayList<Integer> getSelected() {
        return selected;
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {
        private ImageView pImageView;
        private TextView pNameTextView;
        private TextView pTeamTextView;
        private TextView pPosTextView;
        private CheckBox checkBox;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            pImageView = itemView.findViewById(R.id.image_view);
            pNameTextView = itemView.findViewById(R.id.name_view);
            pTeamTextView = itemView.findViewById(R.id.team_view);
            pPosTextView = itemView.findViewById(R.id.pos_view);

            checkBox= itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    CheckBox c = (CheckBox)v;
                    if (c.isChecked()) {
                        //ExamplePlayer currentPlayer = playersList.get(position);
                        selected.add(position);
                        //watchList.add(new ExamplePlayer(currentPlayer.getpImageUrl(), currentPlayer.getpName(),
                        //        currentPlayer.getpTeam(), currentPlayer.getpPosition(), currentPlayer.getpId(), false));

                        //now add player to db
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
