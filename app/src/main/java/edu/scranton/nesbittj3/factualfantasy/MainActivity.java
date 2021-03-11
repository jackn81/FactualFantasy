package edu.scranton.nesbittj3.factualfantasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;

import java.io.IOException;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container_main)!=null){
            Fragment watchListFrag = WatchListFrag.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_main, watchListFrag, "WatchListFrag")
                    .commitNow();

        }

       /* new Thread(new Runnable()
        {
            final ExamplePlayer examplePlayer = new ExamplePlayer(null, null, false);

            @Override
            public void run() {
                try {
                    Document doc = (Document) Jsoup.connect("https://www.espn.com/nfl/stats/player/_/table/passing/sort/passingYards/dir/desc")
                            .timeout(6000).get();

                    Element pNames = (Element) doc.getElementById("s:20~1:28~a:3122840"); // deshaun watson name ID
                    examplePlayer.setpName(pNames.text());

                } catch (Exception e) {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){

                        }
                    });
                }
            }

        });*/
    }


}
