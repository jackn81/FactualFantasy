package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.scranton.nesbittj3.factualfantasy.model.Player;

public class WatchListFrag extends Fragment {
    RecyclerView recyclerView;
    WatchListAdapter adapter;
    ArrayList<Player> players;
    TextView textView;

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
        RecyclerView recyclerView;
        TextView textView;
        //variable declarations

        //retrofit stuff?

        //recyclerView Stuff
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((context)));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        //temporary just to test
        createItems();
        //textView = view.findViewById(R.id.textView);
        //textView.setText("SIZE: " + players.size());
        adapter = new WatchListAdapter(context, players);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void createItems() {
        players = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            players.add(new Player(100 + i, "player-" + i));
        }
    }

    public void onSelect(View view) {
        ArrayList<Integer> selected = adapter.getSelected();
        textView.setText("You Selected: " + selected.toString());
        adapter.resetSelected();
    }

}
