package edu.scranton.nesbittj3.factualfantasy;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository repository;
    private LiveData<List<ExamplePlayer>> allPlayers;


    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
        allPlayers = repository.getAllPlayers();
    }

    public void insert(ExamplePlayer player){
        repository.insert(player);
    }

    public void update(ExamplePlayer player){
        repository.update(player);
    }

    public void delete(ExamplePlayer player){
        repository.delete(player);
    }

    public void deleteAllPlayers(){
        repository.deleteAllPlayers();
    }

    public LiveData<List<ExamplePlayer>> getAllPlayers(){
        return allPlayers;
    }

}
