package edu.scranton.nesbittj3.factualfantasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import android.os.Bundle;



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


    }


}
