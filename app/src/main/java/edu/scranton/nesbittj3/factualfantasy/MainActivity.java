package edu.scranton.nesbittj3.factualfantasy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;


public class MainActivity extends AppCompatActivity {

    public static PlayerViewModel playerViewModel;
    private Button rush;
    private Button pass;
    private Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final WatchListAdapter watchListAdapter = new WatchListAdapter();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.container_main)!=null){
            Fragment watchListFrag = WatchListFrag.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_main, watchListFrag, "WatchListFrag")
                    .commitNow();

        }

        


    }


}
