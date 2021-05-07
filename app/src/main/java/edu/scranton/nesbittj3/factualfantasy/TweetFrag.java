package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;
import edu.scranton.nesbittj3.factualfantasy.model.Tweet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetFrag extends Fragment {
    private RecyclerView tweetRV;
    private TweetListAdapter tweetListAdapter;
    private RecyclerView RV;
    private List<Tweet> tweetList;
    private TextView textViewResult;
    private String firstName;
    private String lastName;

    public static TweetFrag newInstance() {
        return new TweetFrag();
    }

    public TweetFrag() {
        //empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_frag, container, false);
        Context context = view.getContext();

        textViewResult = view.findViewById(R.id.text_view_result);

        RV = (RecyclerView) view.findViewById(R.id.RV);

        RV.setLayoutManager(new LinearLayoutManager((context)));
        RV.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        tweetList = new ArrayList<>();

        tweetListAdapter = new TweetListAdapter(context, tweetList);
        RV.setAdapter(tweetListAdapter);
        Bundle bundle = this.getArguments();
        firstName = bundle.getString("FirstName", null);
        lastName = bundle.getString("LastName", null);

        Content content = new Content();
        content.execute();
        tweetListAdapter.notifyDataSetChanged();

        return view;
    }



    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{


                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("WDkKq7zbzGebZ3xKTnQ0qlt8c")
                        .setOAuthConsumerSecret("MktepkqGtzDDRa96tfLC5Ah3gxtjZUiuHVci6Rwkzcp4p8nJ71")
                        .setOAuthAccessToken("3484782315-6X9yCrGAsNURmD2eYuAZRUMVzBymN5Ca3A5Wuba")
                        .setOAuthAccessTokenSecret("arTfkJyRH80YbFOj63R7PX80cKBcNdPT7DrJCoFBegQcW")
                        .setTweetModeExtended(true);

                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();


                Query query = new Query(firstName + " " + lastName);
                QueryResult result = null;
                try {
                    result = twitter.search(query);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                for (twitter4j.Status status : result.getTweets()) {

                    System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                    String userName = status.getUser().getScreenName().toString();

                    String tweet = status.getText();
                    tweetList.add(new Tweet(userName, null, tweet));
                }

                Log.d("abc", "result: " + result.toString() );
                Log.d("abcc", "resulttweets: " + result.getTweets().toString() );

                } catch (Exception ex) {
                ex.printStackTrace();
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
            tweetListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }



}
