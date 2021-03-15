package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class WatchListFrag extends Fragment {
    private RecyclerView recyclerView;
    private WatchListAdapter adapter;
    private ArrayList<ExamplePlayer> playersList;
    private RequestQueue pRequestQueue;
    private TextView textView;

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

        //variable declarations

        //retrofit stuff?

        //recyclerView Stuff
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((context)));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        //temporary just to test
        //playersList = new ArrayList<>();

        //pRequestQueue = Volley.newRequestQueue(context);
        //parseJSON();

        createItems();
        //ExamplePlayer examplePlayer = new ExamplePlayer(null, null, false);
        //examplePlayer.setpName("Deshaun Watson");
        //playersList.add(examplePlayer);
        adapter = new WatchListAdapter(context, playersList);
        recyclerView.setAdapter(adapter);
        Content content = new Content();
        content.execute();

        /*getActivity().runOnUiThread(new Runnable()
        {
            ExamplePlayer examplePlayer = new ExamplePlayer("Deshaun Watson", null, false);

            @Override
            public void run() {
                try {
                    Document doc = (Document) Jsoup.connect("https://www.espn.com/nfl/stats/player/_/table/passing/sort/passingYards/dir/desc")
                            .timeout(6000).get();

                    Element pNames = (Element) doc.getElementById("s:20~1:28~a:3122840"); // deshaun watson name ID
                    //examplePlayer.setpName(pNames.text());
                    examplePlayer.setpName("Deshaun Watson");
                    playersList.add(examplePlayer);


                } catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run(){

                        }
                    });
                }
            }

        });*/
        //adapter = new WatchListAdapter(context, playersList);
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        //recyclerView.setAdapter(adapter);

        return view;


    }

    private void parseJSON() {
        String url = "";
    }

    private void createItems() {
        playersList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            playersList.add(new ExamplePlayer(null, "player-",  false));
        }
    }

    public void onSelect(View view) {
        ArrayList<Integer> selected = adapter.getSelected();
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
                //Elements data2 = doc.select("h1.Image__Wrapper"); //Image__Wrapper--relative
                int size = data.size();

                for(int i = 0; i< size; i ++){
                    /*String imgUrl = data.select("h1.Image__Wrapper") // Image__Wrapper--relative
                            .select("img")
                            .eq(i)
                            .attr("alt");*/
                    String athleteName = data.select("a.AnchorLink")
                            .select("a")
                            .eq(i)
                            .text();
                    playersList.add(new ExamplePlayer(null, athleteName, false));
                    //Log.d("items", "name: " + athleteName );
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
