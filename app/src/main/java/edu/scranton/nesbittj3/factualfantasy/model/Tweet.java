package edu.scranton.nesbittj3.factualfantasy.model;

import com.google.gson.annotations.SerializedName;

public class Tweet {


    private String userName;

    private String userId;

    private String tweetBody;

    public Tweet(String userName, String userId, String tweetBody) {
        this.userName = userName;
        this.userId = userId;
        this.tweetBody = tweetBody;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getTweetBody() {
        return tweetBody;
    }
}
