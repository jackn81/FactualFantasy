package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class RunListFrag extends Fragment {
    private RecyclerView recyclerView;
    private PassListAdapter adapter;
    private List<ExamplePlayer> playersList;
    private PlayerViewModel viewModel;



    public static RunListFrag newInstance() {
        return new RunListFrag();
    }

    public RunListFrag() {
        //empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.pass_list_frag, container, false);
        Context context = view.getContext();
        Button back = (Button) view.findViewById(R.id.back);
        //variable declarations

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((context)));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));


        playersList = new ArrayList<>();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        viewModel = new ViewModelProvider(this.getActivity()).get(PlayerViewModel.class);
        adapter = new PassListAdapter(context, playersList, fragmentManager);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();

        adapter.notifyDataSetChanged();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selected = adapter.getSelected();


                int size = selected.size();
                for(int i = 0; i<size; i++){
                    ExamplePlayer targetPlayer = new ExamplePlayer();
                    String playerIMG = adapter.getPlayerUrl(selected.get(i));
                    String playerName = adapter.getPlayerName(selected.get(i));
                    String playerTeam = adapter.getPlayerTeam(selected.get(i));
                    String playerPos = adapter.getPlayerPos(selected.get(i));
                    String playerId = adapter.getPlayerId(selected.get(i));
                    targetPlayer.setpImageUrl(playerIMG);
                    targetPlayer.setpName(playerName);
                    targetPlayer.setpTeam(playerTeam);
                    targetPlayer.setpPosition(playerPos);
                    targetPlayer.setpId(playerId);
                    targetPlayer.setpCheck(false);
                    viewModel.insert(targetPlayer);
                    Toast.makeText(getContext(), "Checked: " + playerName, Toast.LENGTH_LONG).show();

                }
                adapter.clearSelected();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return view;


    }

    private void parseJSON() {
        String url = "";
    }

    public void createItems() {
        playersList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            playersList.add(new ExamplePlayer(null, "player-", null, null, null, false));
        }
    }



    private class Content extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String url = "https://www.espn.com/nfl/stats/player/_/stat/rushing/table/rushing/sort/rushingYards/dir/desc";

                Document doc = Jsoup.connect(url).get();


                Elements data = doc.select("td.Table__TD");

                int size = data.size();

                for(int i = 0; i< 50; i ++){

                    String athleteName = data.select("a.AnchorLink")
                            .select("a")
                            .eq(i)
                            .text();

                    String athleteID = data.select("a.AnchorLink")          //this is working, now
                            .select("a")                                    //we just need the
                            .eq(i)                                          //end of the string
                            .attr("data-player-uid");

                    String athleteTeam = data.select("span.pl2.n10.athleteCell__teamAbbrev")
                            .select("span")
                            .eq(i)
                            .text();

                    String athletePosition = data.select("div.position")
                            .select("div")
                            .eq(i)
                            .text();

                    athleteID = athleteID.substring(12);       //extracts ESPN's ID for a player

                    String imgURL = "https://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players"+
                            "/full/" + athleteID + ".png&w=350&h=254";

                    playersList.add(new ExamplePlayer(imgURL, athleteName, athleteTeam, athletePosition, athleteID, false));
                    Log.d("image", "url: " + imgURL );
                }
            }catch(IOException e){
                e.printStackTrace();;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
