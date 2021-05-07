package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.scranton.nesbittj3.factualfantasy.model.Tweet;


public class TweetListAdapter extends RecyclerView.Adapter<TweetListAdapter.WatchViewHolder> {
    private Context context;

    public List<Tweet> tweetList;

    public TweetListAdapter(Context context, List<Tweet> tweetList){
        this.context = context;
        this.tweetList = tweetList;
    }
    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tweet_layout, parent, false);
        return new WatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        Tweet currentTweet = tweetList.get(position);

        String userName = currentTweet.getUserName();
        String tweetBody = currentTweet.getTweetBody();

        holder.NameTextView.setText(userName.toString());
        holder.TweetContent.setText(tweetBody);
        //set as false right now

    }


    @Override
    public int getItemCount() {
        return tweetList.size();
    }


    public class WatchViewHolder extends RecyclerView.ViewHolder {

        private TextView NameTextView;
        private TextView TweetContent;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);

            NameTextView = itemView.findViewById(R.id.name_view);
            TweetContent = itemView.findViewById(R.id.text_view);

        }

    }

}

