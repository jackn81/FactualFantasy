package edu.scranton.nesbittj3.factualfantasy.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.Entity;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "player_table")
public class ExamplePlayer {
    @PrimaryKey (autoGenerate = true) @NonNull
    public int autoID;
    public String pImageUrl;

    public String pName;
    public String pTeam;
    public String pPosition;

    public String pId;
    public boolean pCheck;

    public ExamplePlayer(){

    }

    public ExamplePlayer(String imageUrl, String name, String team, String position, String id, boolean check){
        pImageUrl = imageUrl;
        pName = name;
        pTeam = team;
        pPosition = position;
        pId = id;
        pCheck = check;
    }

    public String getpImageUrl(){
        return pImageUrl;
    }

    public String getpName(){
        return pName;
    }

    public String getpId(){ return pId;}

    public boolean getpCheck(){
        return pCheck;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpId(String pId){
        this.pId = pId;
    }

    public void setpCheck(boolean pCheck) {
        this.pCheck = pCheck;
    }

    public String getpTeam() {
        return pTeam;
    }

    public void setpTeam(String pTeam) {
        this.pTeam = pTeam;
    }

    public String getpPosition() {
        return pPosition;
    }

    public void setpPosition(String pPosition) {
        this.pPosition = pPosition;
    }
}

/*class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}*/

