package edu.scranton.nesbittj3.factualfantasy.model;

public class ExamplePlayer {


    private String pImageUrl;
    private String pName;
    private boolean pCheck;

    public ExamplePlayer(String imageUrl, String name, boolean check){
        pImageUrl = imageUrl;
        pName = name;
        pCheck = check;
    }

    public String getpImageUrl(){
        return pImageUrl;
    }

    public String getpName(){
        return pName;
    }

    public boolean getpCheck(){
        return pCheck;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpCheck(boolean pCheck) {
        this.pCheck = pCheck;
    }
}
