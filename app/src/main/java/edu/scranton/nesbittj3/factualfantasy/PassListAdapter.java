package edu.scranton.nesbittj3.factualfantasy;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.WatchViewHolder> implements Filterable {
    private Context context;
    private List<ExamplePlayer> playersList;
    private List<ExamplePlayer> playersListFull;
    private ArrayList<Integer> selected;
    public FragmentManager fragmentManager;


    public PassListAdapter(Context context, List<ExamplePlayer> nPlayersList, FragmentManager fragmentManager){
        this.context = context;
        this.playersList = nPlayersList;
        this.fragmentManager = fragmentManager;

        selected = new ArrayList<>();
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false);
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
        String pId = currentPlayer.getpId();

        holder.pNameTextView.setText(pName);
        holder.pTeamTextView.setText(pTeam);
        holder.pPosTextView.setText(pPos);
        holder.checkBox.setChecked(false);
        holder.pIdTextView.setText(pId);
        //set as false right now

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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExamplePlayer> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(playersListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ExamplePlayer player : playersListFull){
                    if(player.getpName().toLowerCase().contains(filterPattern)){
                        filteredList.add(player);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            playersList.clear();
            playersList.addAll((List) results.values);

            notifyDataSetChanged();
        }
    };


    public class WatchViewHolder extends RecyclerView.ViewHolder {
        private ImageButton pImageView;
        private TextView pNameTextView;
        private TextView pTeamTextView;
        private TextView pPosTextView;
        private CheckBox checkBox;
        private TextView pIdTextView;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            pImageView = itemView.findViewById(R.id.image_view);
            pNameTextView = itemView.findViewById(R.id.name_view);
            pTeamTextView = itemView.findViewById(R.id.team_view);
            pPosTextView = itemView.findViewById(R.id.pos_view);
            pIdTextView = itemView.findViewById(R.id.id_view);

            checkBox= itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    CheckBox c = (CheckBox)v;
                    if (c.isChecked()) {
                        selected.add(position);
                    }
                    else {
                        selected.remove(new Integer(position));
                    }
                }
            } );

            pImageView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v){
                   Fragment playerDetails = PlayerDetailsFrag.newInstance();
                   Bundle bundle = new Bundle();


                   String pName = pNameTextView.getText().toString();
                   String pTeam = pTeamTextView.getText().toString();
                   String pPos = pPosTextView.getText().toString();
                   String pId = pIdTextView.getText().toString();

                   bundle.putString("Name", pName);
                   bundle.putString("Team", pTeam);
                   bundle.putString("Position", pPos);
                   bundle.putString("ID", pId);

                   playerDetails.setArguments(bundle);
                   fragmentManager.beginTransaction().replace(R.id.container_b,playerDetails)
                           .commit();

               }
            });
        }

    }


}
