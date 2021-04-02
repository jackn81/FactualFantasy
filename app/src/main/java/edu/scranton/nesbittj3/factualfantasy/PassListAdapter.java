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
import java.util.List;
import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.WatchViewHolder> {
    private Context context;
    private List<ExamplePlayer> playersList;
    private List<ExamplePlayer> watchList;
    private TextView textView;
    private ArrayList<Integer> selected;
    WatchViewHolder watchViewHolder;

    public PassListAdapter(Context context, List<ExamplePlayer> nPlayersList){
        this.context = context;
        this.playersList = nPlayersList;
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

    public void setPlayersList(List<ExamplePlayer> players){
        this.playersList = players;
        notifyDataSetChanged();
    }


    public ArrayList<Integer> getSelected() {
        return selected;
    }

    public void clearSelected(){
        selected.clear();
    }

    public Boolean containsPlayer(ExamplePlayer player){
        Boolean result = false;
        for(int i = 0; i<playersList.size(); i++){
            if(playersList.contains(player)){
                result = true;
            }else {
                result = false;
            }
        }
        return result;
    }

    public ExamplePlayer getPlayer(int position){
        return playersList.get(position);
    }

    public String getPlayerName(int position){
        ExamplePlayer currentPlayer = playersList.get(position);
        return currentPlayer.getpName();
    }

    public String getPlayerUrl(int position){
        ExamplePlayer currentPlayer = playersList.get(position);
        return currentPlayer.getpImageUrl();
    }

    public String getPlayerTeam(int position){
        ExamplePlayer currentPlayer = playersList.get(position);
        return currentPlayer.getpTeam();
    }

    public String getPlayerPos(int position){
        ExamplePlayer currentPlayer = playersList.get(position);
        return currentPlayer.getpPosition();
    }

    public String getPlayerId(int position){
        ExamplePlayer currentPlayer = playersList.get(position);
        return currentPlayer.getpId();
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
                        ExamplePlayer currentPlayer = playersList.get(position);
                        selected.add(position);
                        //watchList.add(currentPlayer);


                        //now add player to db
                        //Toast.makeText(context, "Checked: " + selected, Toast.LENGTH_LONG).show();
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
