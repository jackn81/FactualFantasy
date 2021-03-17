package edu.scranton.nesbittj3.factualfantasy.model;

public class ExamplePlayer {


    private String pImageUrl;
    private String pName;
    private String pTeam;
    private String pPosition;
    private String pId;
    private boolean pCheck;

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
