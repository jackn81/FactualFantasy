package edu.scranton.nesbittj3.factualfantasy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.scranton.nesbittj3.factualfantasy.model.ExampleStats;


public class PlayerDetailsFrag extends Fragment {
    private RecyclerView recyclerView2;
    TextView player_name;
    ImageView imageView;
    TextView playerId;
    TextView player_pos;
    TextView player_team;
    String pId;
    String pPos;
    private PlayerDetailsAdapter adapter;
    List<ExampleStats> exampleStats;

    public static PlayerDetailsFrag newInstance() {
        return new PlayerDetailsFrag();
    }

    public PlayerDetailsFrag(){
        //empty constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.details_frag, container, false);
        Context context = view.getContext();
        //exampleStats = new ArrayList();
        player_name = view.findViewById(R.id.player_name);
        playerId = view.findViewById(R.id.player_id);
        imageView = view.findViewById(R.id.imageView);
        player_pos = view.findViewById(R.id.player_pos);
        player_team = view.findViewById(R.id.player_team);

        exampleStats = new ArrayList<>();

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager((context)));
        recyclerView2.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));



        adapter = new PlayerDetailsAdapter(context, exampleStats);
        recyclerView2.setAdapter(adapter);



        Content content = new Content();
        content.execute();
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String pName = bundle.getString("Name", null);
            pId = bundle.getString("ID", null);
            pPos = bundle.getString("Position", null);
            String pTeam = bundle.getString("Team", null);
            player_name.setText(pName);
            playerId.setText(pId);
            player_pos.setText(pPos);
            player_team.setText(pTeam);

            String imgURL = "https://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players"+
                    "/full/" + pId + ".png&w=350&h=254";
            Picasso.with(context).load(imgURL).into(imageView);
        }

        return view;
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.espn.com/nfl/player/gamelog/_/id/" + pId;

                Document doc = Jsoup.connect(url).maxBodySize(100000)
                        .timeout(1000000)
                        .get();
                Elements data = doc.select("tbody");

                Elements check = doc.select("thead");
                Elements check2 = doc.select("ul");
                //check2.toString().contains("QB")
                if (pPos.contains("QB")) {
                    exampleStats.add(new ExampleStats("DATE", "        OPP", "       RES", "    CMP", "PATT",
                            "PYDS", "CMP%", "PAVG", "PTD", "INT", "PLNG",
                            "SACK", "RTG", "QBR", "RATT", "RYDS", "RAVG", "RTD", "RLNG"));

                    //Webscrape if player only has regular season stats
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i =0; i<25; i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if(cols.get(0).text().contains("Regular")){
                                    break;
                                }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String cmp = cols.get(3).text();
                            String pAtt = cols.get(4).text();
                            String pYds = cols.get(5).text();
                            String cmpPer = cols.get(6).text();
                            String pAvg = cols.get(7).text();
                            String pTd = cols.get(8).text();
                            String pInt = cols.get(9).text();
                            String pLng = cols.get(10).text();
                            String sack = cols.get(11).text();
                            String rtg = cols.get(12).text();
                            String qbr = cols.get(13).text();
                            String rAtt = cols.get(14).text();
                            String rYds = cols.get(15).text();
                            String rAvg = cols.get(16).text();
                            String rTd = cols.get(17).text();
                            String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, cmp, pAtt, pYds, cmpPer, pAvg,
                                  pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));



                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i =0; i<25; i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if(cols.get(0).text().contains("Regular")){
                                break;
                            }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String cmp = cols.get(3).text();
                            String pAtt = cols.get(4).text();
                            String pYds = cols.get(5).text();
                            String cmpPer = cols.get(6).text();
                            String pAvg = cols.get(7).text();
                            String pTd = cols.get(8).text();
                            String pInt = cols.get(9).text();
                            String pLng = cols.get(10).text();
                            String sack = cols.get(11).text();
                            String rtg = cols.get(12).text();
                            String qbr = cols.get(13).text();
                            String rAtt = cols.get(14).text();
                            String rYds = cols.get(15).text();
                            String rAvg = cols.get(16).text();
                            String rTd = cols.get(17).text();
                            String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, cmp, pAtt, pYds, cmpPer, pAvg,
                                    pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));

                            }
                        }


                    ///////////
                    //RB CASE//
                    ///////////

                }else if (pPos.contains("RB")) {
                    exampleStats.add(new ExampleStats("DATE", "        OPP", "       RES", "       ATT", "RYDS",
                            "RAVG", "RTD", "RLNG", "REC", "TGTS", "RECYDS",
                            "RECAVG", "RECTD", "RECLNG", "FUM", "LST", "FF", "KB", null));
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i =0; i<25; i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if(cols.get(0).text().contains("Regular")){
                               break;
                            }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String rAtt = cols.get(3).text();
                            String rYds = cols.get(4).text();
                            String rAvg = cols.get(5).text();
                            String rTd = cols.get(6).text();
                            String rLng = cols.get(7).text();
                            String rec = cols.get(8).text();
                            String tgts = cols.get(9).text();
                            String recYds = cols.get(10).text();
                            String recAvg = cols.get(11).text();
                            String recTd = cols.get(12).text();
                            String recLng = cols.get(13).text();
                            String fum = cols.get(14).text();
                            String lst = cols.get(15).text();
                            String ff = cols.get(16).text();
                            String kb = cols.get(17).text();
                            //String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, rAtt, rYds, rAvg, rTd, rLng,
                                    rec, tgts, recYds, recAvg, recTd, recLng, fum, lst, ff, kb, null));



                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i =0; i<25; i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if(cols.get(0).text().contains("Regular")){
                                break;
                            }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String rAtt = cols.get(3).text();
                            String rYds = cols.get(4).text();
                            String rAvg = cols.get(5).text();
                            String rTd = cols.get(6).text();
                            String rLng = cols.get(7).text();
                            String rec = cols.get(8).text();
                            String tgts = cols.get(9).text();
                            String recYds = cols.get(10).text();
                            String recAvg = cols.get(11).text();
                            String recTd = cols.get(12).text();
                            String recLng = cols.get(13).text();
                            String fum = cols.get(14).text();
                            String lst = cols.get(15).text();
                            String ff = cols.get(16).text();
                            String kb = cols.get(17).text();
                            //String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, rAtt, rYds, rAvg, rTd, rLng,
                                    rec, tgts, recYds, recAvg, recTd, recLng, fum, lst, ff, kb, null));


                        }
                    }
                }else if(pPos.contains("WR")) {
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i = 0; i < 25; i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                break;
                            }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String rec = cols.get(3).text();
                            String tgts = cols.get(4).text();
                            String recYds = cols.get(5).text();
                            String recAvg = cols.get(6).text();
                            String recTd = cols.get(7).text();
                            String recLng = cols.get(8).text();
                            String rAtt = cols.get(9).text();
                            String rYds = cols.get(10).text();
                            String rAvg = cols.get(11).text();
                            String rLng = cols.get(12).text();
                            String rTd = cols.get(13).text();
                            String fum = cols.get(14).text();
                            String lst = cols.get(15).text();
                            String ff = cols.get(16).text();
                            String kb = cols.get(17).text();
                            //String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, rec, tgts, recYds, recAvg,
                                    recTd, recLng, rAtt, rYds, rAvg, rLng, rTd,
                                    fum, lst, ff, kb, null));


                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i = 0; i < 25; i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                break;
                            }
                            Log.d("abc", "doInBackground: " + row.text() + " ");
                            String date = cols.get(0).text();
                            String opp = cols.get(1).text();
                            String res = cols.get(2).text();
                            String rec = cols.get(3).text();
                            String tgts = cols.get(4).text();
                            String recYds = cols.get(5).text();
                            String recAvg = cols.get(6).text();
                            String recTd = cols.get(7).text();
                            String recLng = cols.get(8).text();
                            String rAtt = cols.get(9).text();
                            String rYds = cols.get(10).text();
                            String rAvg = cols.get(11).text();
                            String rLng = cols.get(12).text();
                            String rTd = cols.get(13).text();
                            String fum = cols.get(14).text();
                            String lst = cols.get(15).text();
                            String ff = cols.get(16).text();
                            String kb = cols.get(17).text();
                            //String rLng = cols.get(18).text();


                            exampleStats.add(new ExampleStats(date, opp, res, rec, tgts, recYds, recAvg,
                                    recTd, recLng, rAtt, rYds, rAvg, rLng, rTd,
                                    fum, lst, ff, kb, null));
                        }
                    }
                }
                }catch(IOException e){
                    e.printStackTrace();
                    ;
                }
                return null;
            }


            @Override
            protected void onPreExecute () {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute (Void aVoid){
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onCancelled () {
                super.onCancelled();
            }


    }
}
