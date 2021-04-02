package edu.scranton.nesbittj3.factualfantasy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

//import edu.scranton.nesbittj3.factualfantasy.model.Converters;
import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;

@Dao
//@TypeConverters({Converters.class})
public interface PlayerDao {

    @Insert
    void insert(ExamplePlayer player);

    @Update
    void update(ExamplePlayer player);

    @Delete
    void delete(ExamplePlayer player);

    @Query("DELETE FROM player_table")
    void deleteAllPlayers();

    @Query("SELECT * FROM player_table")
    LiveData<List<ExamplePlayer>> getAllPlayers();
}
