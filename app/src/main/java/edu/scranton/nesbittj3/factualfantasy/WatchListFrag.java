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

import java.io.IOException;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class WatchListFrag extends Fragment {
    private RecyclerView watchListRV;
    private WatchListAdapter watchListAdapter;
    private ArrayList<ExamplePlayer> playersList;
    private RequestQueue pRequestQueue;
    private TextView textView;
    private Button rush;
    private Button pass;
    private Button receive;

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
        //variable declarations

        //retrofit stuff?

        //recyclerView Stuff
        watchListRV = (RecyclerView) view.findViewById(R.id.watchListRV);
        watchListRV.setHasFixedSize(true);
        watchListRV.setLayoutManager(new LinearLayoutManager((context)));
        watchListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        playersList = new ArrayList<>();


        watchListAdapter = new WatchListAdapter(context, playersList);
        watchListRV.setAdapter(watchListAdapter);
        //FragmentManager fragmentManager = getSupportFragmentManager();

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment passListFrag = PassListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_main, passListFrag, "passListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });

        rush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment runListFrag = RunListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_main, runListFrag, "runListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment receiveListFrag = ReceiveListFrag.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_main, receiveListFrag, "receiveListFrag" )
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;


    }

    private void parseJSON() {
        String url = "";
    }



    public void onSelect(View view) {
        ArrayList<Integer> selected = watchListAdapter.getSelected();
        textView.setText("You Selected: " + selected.toString());
        //adapter.resetSelected();
    }


}

