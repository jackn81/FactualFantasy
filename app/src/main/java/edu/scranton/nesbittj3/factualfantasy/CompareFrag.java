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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class CompareFrag extends Fragment {

    TextView p1Name;
    TextView p1Pos;
    TextView p1Team;
    ImageView player1Pic;

    TextView p2Name;
    TextView p2Pos;
    TextView p2Team;
    ImageView player2Pic;
    String player1Id;
    String player2Id;
    String player1Pos;
    String player2Pos;
    RecyclerView p1RV;
    RecyclerView p2RV;
    private CompareFragAdapter adapter1;
    private CompareFragAdapter adapter2;

    List<ExampleStats> exampleStats1;
    List<ExampleStats> exampleStats2;

    public static CompareFrag newInstance() {
        return new CompareFrag();
    }

    public CompareFrag() {
        //empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compare_frag, container, false);
        Context context = view.getContext();

        p1Name = view.findViewById(R.id.p1Name);
        p1Pos = view.findViewById(R.id.p1Pos);
        p1Team = view.findViewById(R.id.p1Team);
        player1Pic = view.findViewById(R.id.player1);

        p2Name = view.findViewById(R.id.p2Name);
        p2Pos = view.findViewById(R.id.p2Pos);
        p2Team = view.findViewById(R.id.p2Team);
        player2Pic = view.findViewById(R.id.player2);

        p1RV = (RecyclerView) view.findViewById(R.id.p1RV);
        p1RV.setLayoutManager(new GridLayoutManager((context), 3));
        p1RV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL));
        p1RV.setHasFixedSize(true);

        exampleStats1 = new ArrayList<>();
        exampleStats2 = new ArrayList<>();

        adapter1 = new CompareFragAdapter(context, exampleStats1);
        p1RV.setAdapter(adapter1);

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String player1Name = bundle.getString("Name1", null);
            player1Id = bundle.getString("ID1", null);
            player1Pos = bundle.getString("Position1", null);
            String player1Team = bundle.getString("Team1", null);
            String player2Name = bundle.getString("Name2", null);
            player2Id = bundle.getString("ID2", null);
            player2Pos = bundle.getString("Position2", null);
            String player2Team = bundle.getString("Team2", null);
            p1Name.setText(player1Name);

            p1Pos.setText(player1Pos);
            p1Team.setText(player1Team);
            p2Name.setText(player2Name);

            p2Pos.setText(player2Pos);
            p2Team.setText(player2Team);

            String imgURL = "https://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players"+
                    "/full/" + player1Id + ".png&w=350&h=254";
            Picasso.with(context).load(imgURL).into(player1Pic);

            String img2URL = "https://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players"+
                    "/full/" + player2Id + ".png&w=350&h=254";
            Picasso.with(context).load(img2URL).into(player2Pic);
        }

        Content1 content1 = new Content1();
        content1.execute();
        Content2 content2 = new Content2();
        content2.execute();

        return view;
    }

    private class Content1 extends AsyncTask<Void, Void, Void> {


        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

                try {
                    String url = "https://www.espn.com/nfl/player/gamelog/_/id/" + player1Id;

                    Document doc = Jsoup.connect(url).maxBodySize(100000)
                            .timeout(1000000)
                            .get();
                    Elements data = doc.select("tbody");

                    Elements check = doc.select("thead");
                    Elements check2 = doc.select("ul");
                    //check2.toString().contains("QB")
                    if (player1Pos.contains("QB")) {

                        //Webscrape if player only has regular season stats
                        if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                            Elements tr = data.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");


                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt = cols.get(12).text();
                                    String rYds = cols.get(13).text();
                                    String rAvg = cols.get(14).text();
                                    String rTd = cols.get(15).text();
                                    String rLng = cols.get(16).text();



                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));
                                    exampleStats1.add(new ExampleStats(null, null,null,
                                            "CMP", "PATT", "PYDS","CMPPER", "PAVG", "PTD",
                                            "INT", "PLNG", "SACKS", "RTG",
                                            "QBR", "RATT", "RYDS", "RAVG", "RTD",
                                            "RLNG"));
                                }


                            }

                            //webscrape if player did make it to playoffs
                        } else {
                            Element datas = doc.select("tbody").get(1);
                            Elements tr = datas.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");

                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt = cols.get(12).text();
                                    String rYds = cols.get(13).text();
                                    String rAvg = cols.get(14).text();
                                    String rTd = cols.get(15).text();
                                    String rLng = cols.get(16).text();


                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));
                                    exampleStats1.add(new ExampleStats(null, null,null,
                                            "CMP", "PATT", "PYDS","CMPPER", "PAVG", "PTD",
                                            "INT", "PLNG", "SACKS", "RTG",
                                            "QBR", "RATT", "RYDS", "RAVG", "RTD",
                                            "RLNG"));
                                }

                            }
                        }

                        //player 1 is an RB
                    }else if(player1Pos.contains("RB")){
                        //Webscrape if player only has regular season stats
                        if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                            Elements tr = data.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");

                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt =cols.get(12).text();
                                    String rYds =cols.get(13).text();
                                    String rAvg =cols.get(14).text();
                                    String rTd = cols.get(15).text();



                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));

                                     exampleStats1.add(new ExampleStats(null, null, null,
                                             "ATT", "RATT","RAVG", "RTD" ,"RLNG",
                                             "REC","TGTS","YDS","RECAVG",
                                             "RECTD", "RECLNG","FUM","LST",
                                             "FF","KB", null));
                                }


                            }

                            //webscrape if player did make it to playoffs
                        } else {
                            Element datas = doc.select("tbody").get(1);
                            Elements tr = datas.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");

                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt =cols.get(12).text();
                                    String rYds =cols.get(13).text();
                                    String rAvg =cols.get(14).text();
                                    String rTd = cols.get(15).text();
                                    //String rLng = "RLNG "+cols.get(16).text();


                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));

                                    exampleStats1.add(new ExampleStats(null, null, null,
                                            "ATT", "RATT","RAVG", "RTD" ,"RLNG",
                                            "REC","TGTS","YDS","RECAVG",
                                            "RECTD", "RECLNG","FUM","LST",
                                            "FF","KB", null));
                                }

                            }
                        }
                    }else if(player1Pos.contains("WR") || player1Pos.contains("TE")){
                        //Webscrape if player only has regular season stats
                        if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                            Elements tr = data.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");

                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt =cols.get(12).text();
                                    String rYds =cols.get(13).text();
                                    String rAvg =cols.get(14).text();
                                    String rTd = cols.get(15).text();


                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));

                                    exampleStats1.add(new ExampleStats(null, null, null,
                                            "REC","TGTS","RECYDS",
                                            "RECAVG","RECTD","RECLNG","RATT",
                                            "RYDS","RAVG","RLNG","RTD","FUM",
                                            "LST","FF","KB",null));
                                }


                            }

                            //webscrape if player did make it to playoffs
                        } else {
                            Element datas = doc.select("tbody").get(1);
                            Elements tr = datas.select("tr");

                            for (int i = 0; i < tr.size(); i++) {
                                Element row = tr.get(i);
                                Elements cols = row.select("td");
                                if (cols.get(0).text().contains("Regular")) {
                                    Log.d("abc", "doInBackground: " + row.text() + " ");

                                    String cmp = cols.get(1).text();
                                    String pAtt = cols.get(2).text();
                                    String pYds = cols.get(3).text();
                                    String cmpPer = cols.get(4).text();
                                    String pAvg = cols.get(5).text();
                                    String pTd = cols.get(6).text();
                                    String pInt = cols.get(7).text();
                                    String pLng = cols.get(8).text();
                                    String sack = cols.get(9).text();
                                    String rtg = cols.get(10).text();
                                    String qbr = cols.get(11).text();
                                    String rAtt =cols.get(12).text();
                                    String rYds =cols.get(13).text();
                                    String rAvg =cols.get(14).text();
                                    String rTd = cols.get(15).text();


                                    exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                            pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));

                                    exampleStats1.add(new ExampleStats(null, null, null,
                                            "REC","TGTS","RECYDS",
                                            "RECAVG","RECTD","RECLNG","RATT",
                                            "RYDS","RAVG","RLNG","RTD","FUM",
                                            "LST","FF","KB",null));
                                }

                            }
                        }
                    }

                } catch (IOException e) {
                e.printStackTrace();
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
            adapter1.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private class Content2 extends AsyncTask<Void, Void, Void> {


        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://www.espn.com/nfl/player/gamelog/_/id/" + player2Id;

                Document doc = Jsoup.connect(url).maxBodySize(100000)
                        .timeout(1000000)
                        .get();
                Elements data = doc.select("tbody");

                Elements check = doc.select("thead");
                Elements check2 = doc.select("ul");
                //check2.toString().contains("QB")
                if (player2Pos.contains("QB")) {

                    //Webscrape if player only has regular season stats
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt = cols.get(12).text();
                                String rYds = cols.get(13).text();
                                String rAvg = cols.get(14).text();
                                String rTd = cols.get(15).text();
                                String rLng = cols.get(16).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));
                            }


                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt = cols.get(12).text();
                                String rYds = cols.get(13).text();
                                String rAvg = cols.get(14).text();
                                String rTd = cols.get(15).text();
                                String rLng = cols.get(16).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, rLng));
                            }

                        }
                    }

                    //player 1 is an RB
                }else if(player2Pos.contains("RB")){
                    //Webscrape if player only has regular season stats
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt =cols.get(12).text();
                                String rYds =cols.get(13).text();
                                String rAvg =cols.get(14).text();
                                String rTd = cols.get(15).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));
                            }


                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt =cols.get(12).text();
                                String rYds =cols.get(13).text();
                                String rAvg =cols.get(14).text();
                                String rTd = cols.get(15).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));
                            }

                        }
                    }
                }else if(player2Pos.contains("WR") || player2Pos.contains("TE")){
                    //Webscrape if player only has regular season stats
                    if (check.select("tr").select("th").get(0).text().contains("Regular Season")) {

                        Elements tr = data.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt =cols.get(12).text();
                                String rYds =cols.get(13).text();
                                String rAvg =cols.get(14).text();
                                String rTd = cols.get(15).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));
                            }


                        }

                        //webscrape if player did make it to playoffs
                    } else {
                        Element datas = doc.select("tbody").get(1);
                        Elements tr = datas.select("tr");

                        for (int i = 0; i < tr.size(); i++) {
                            Element row = tr.get(i);
                            Elements cols = row.select("td");
                            if (cols.get(0).text().contains("Regular")) {
                                Log.d("abc", "doInBackground: " + row.text() + " ");

                                String cmp = cols.get(1).text();
                                String pAtt = cols.get(2).text();
                                String pYds = cols.get(3).text();
                                String cmpPer = cols.get(4).text();
                                String pAvg = cols.get(5).text();
                                String pTd = cols.get(6).text();
                                String pInt = cols.get(7).text();
                                String pLng = cols.get(8).text();
                                String sack = cols.get(9).text();
                                String rtg = cols.get(10).text();
                                String qbr = cols.get(11).text();
                                String rAtt =cols.get(12).text();
                                String rYds =cols.get(13).text();
                                String rAvg =cols.get(14).text();
                                String rTd = cols.get(15).text();


                                exampleStats1.add(new ExampleStats(null, null, null, cmp, pAtt, pYds, cmpPer, pAvg,
                                        pTd, pInt, pLng, sack, rtg, qbr, rAtt, rYds, rAvg, rTd, null));
                            }

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
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
            adapter1.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }
}

