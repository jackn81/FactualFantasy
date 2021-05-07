package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;



public class WatchListFrag extends Fragment {
    private RecyclerView watchListRV;
    private PassListAdapter watchListAdapter;
    private List<ExamplePlayer> watchList;
    private Button rush;
    private Button pass;
    private Button receive;
    private Button remove;
    private Button compare;

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
        pass = (Button) view.findViewById(R.id.pass);
        rush = (Button) view.findViewById(R.id.rush);
        receive = (Button) view.findViewById(R.id.receive);
        remove = (Button) view.findViewById(R.id.remove);
        compare = (Button) view.findViewById(R.id.compare);
        //variable declarations

        //retrofit stuff?

        //recyclerView Stuff
        watchListRV = (RecyclerView) view.findViewById(R.id.watchListRV);
        //watchListRV.setHasFixedSize(true);
        watchListRV.setLayoutManager(new LinearLayoutManager((context)));
        watchListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        watchListRV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));


        watchList = new ArrayList<>();



        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final ExamplePlayer empty = new ExamplePlayer();
        List<ExamplePlayer> emptyList = new ArrayList<>();
        emptyList.add(empty);
        watchListAdapter = new PassListAdapter(context, watchList, fragmentManager);
        watchListRV.setAdapter(watchListAdapter);
        watchListAdapter.notifyDataSetChanged();
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        playerViewModel.getAllPlayers().observe(getViewLifecycleOwner(), new Observer<List<ExamplePlayer>>() {
            @Override
            public void onChanged(List<ExamplePlayer> players) {
                watchList.clear();
                watchList.addAll(players);
                watchListAdapter.notifyDataSetChanged();
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
                watchListAdapter.clearSelected();
            }
        });

        compare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<Integer> selected = watchListAdapter.getSelected();
                if(selected.size()==2) {
                    ExamplePlayer player1 = watchList.get(selected.get(0));
                    ExamplePlayer player2 = watchList.get(selected.get(1));

                    Fragment compareFrag = CompareFrag.newInstance();
                    Bundle bundle = new Bundle();

                    String p1Name = player1.getpName();
                    String p1Team = player1.getpTeam();
                    String p1Pos = player1.getpPosition();
                    String p1Id = player1.getpId();

                    String p2Name = player2.getpName();
                    String p2Team = player2.getpTeam();
                    String p2Pos = player2.getpPosition();
                    String p2Id = player2.getpId();

                    bundle.putString("Name1", p1Name);
                    bundle.putString("Team1", p1Team);
                    bundle.putString("Position1", p1Pos);
                    bundle.putString("ID1", p1Id);

                    bundle.putString("Name2", p2Name);
                    bundle.putString("Team2", p2Team);
                    bundle.putString("Position2", p2Pos);
                    bundle.putString("ID2", p2Id);

                    compareFrag.setArguments(bundle);

                    if(p1Pos.equals(p2Pos)) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_b, compareFrag, "compareFrag")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
        return view;
    }
}

