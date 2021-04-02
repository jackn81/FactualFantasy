package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;

import static android.app.Activity.RESULT_OK;


public class WatchListFrag extends Fragment {
    private RecyclerView watchListRV;
    private PassListAdapter watchListAdapter;
    //private ArrayList<ExamplePlayer> playersList;
    private List<ExamplePlayer> watchList;
    private RequestQueue pRequestQueue;
    private TextView textView;
    private Button rush;
    private Button pass;
    private Button receive;
    private Button remove;
    private PlayerViewModel playerViewModel;
    
    public static WatchListFrag newInstance() {
        return new WatchListFrag();
    }

    public WatchListFrag() {
        //empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.watch_list_frag, container, false);
        Context context = view.getContext();
        Button pass = (Button) view.findViewById(R.id.pass);
        Button rush = (Button) view.findViewById(R.id.rush);
        Button receive = (Button) view.findViewById(R.id.receive);
        Button remove = (Button) view.findViewById(R.id.remove);
        //variable declarations

        //retrofit stuff?

        //recyclerView Stuff
        watchListRV = (RecyclerView) view.findViewById(R.id.watchListRV);
        //watchListRV.setHasFixedSize(true);
        watchListRV.setLayoutManager(new LinearLayoutManager((context)));
        watchListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        watchListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));


        watchList = new ArrayList<>();





        watchListAdapter = new PassListAdapter(context, watchList);
        watchListRV.setAdapter(watchListAdapter);

        //Content content = new Content();
        //content.execute();
        watchListAdapter.notifyDataSetChanged();

        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        playerViewModel.getAllPlayers().observe(getViewLifecycleOwner(), new Observer<List<ExamplePlayer>>() {
            @Override
            public void onChanged(List<ExamplePlayer> players) {

                watchList.clear();
                watchList.addAll(players);

                watchListAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"onChanged", Toast.LENGTH_SHORT).show();
            }
        });



        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment passListFrag = PassListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_b, passListFrag, "passListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });

        rush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment runListFrag = RunListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_b, runListFrag, "runListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment receiveListFrag = ReceiveListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_b, receiveListFrag, "receiveListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });

        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selected = watchListAdapter.getSelected();
                int size = selected.size();
                for(int i = 0; i<size; i++) {
                    ExamplePlayer targetPlayer = watchList.get(selected.get(0));
                    watchList.remove(targetPlayer);
                    playerViewModel.delete(targetPlayer);
                    watchListAdapter.notifyDataSetChanged();

                }
                //selected.removeAll(selected);
                watchListAdapter.clearSelected();
            }
        });


        return view;


    }

    public void createItems() {
        //watchList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            watchList.add(new ExamplePlayer(null, "player-", null, null, null, false));
        }
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String imgUrl = data.getStringExtra(PassListFrag.EXTRA_IMGURL);
        String name = data.getStringExtra(PassListFrag.EXTRA_NAME);
        String team = data.getStringExtra(PassListFrag.EXTRA_TEAM);
        String position = data.getStringExtra(PassListFrag.EXTRA_POSITION);
        String id = data.getStringExtra(PassListFrag.EXTRA_ID);

        ExamplePlayer player = new ExamplePlayer(imgUrl, name, team, position, id, false);
        playerViewModel.insert(player);
    }*/


    public void onSelect(View view) {
        ArrayList<Integer> selected = watchListAdapter.getSelected();

        //watchListAdapter.setPlayersList(watchListAdapter.selected);
        textView.setText("You Selected: " + selected.toString());
        //adapter.resetSelected();
    }

    private class Content extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String url = "https://www.espn.com/nfl/stats/player/_/table/passing/sort/passingYards/dir/desc";

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

                    watchList.add(new ExamplePlayer(imgURL, athleteName, athleteTeam, athletePosition, athleteID, false));

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
            watchListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }



}

