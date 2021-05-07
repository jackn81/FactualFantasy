package edu.scranton.nesbittj3.factualfantasy;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;

public class PlayerRepository {
    private PlayerDao playerDao;
    private LiveData<List<ExamplePlayer>> allPlayers;

    public PlayerRepository(Application application){
        PlayerDatabase database = PlayerDatabase.getInstance(application);
        playerDao = database.playerDao();
        allPlayers = playerDao.getAllPlayers();
    }

    public void insert(ExamplePlayer player){
        new InsertPlayerAsyncTask(playerDao).execute(player);
    }

    public void update(ExamplePlayer player){
        new UpdatePlayerAsyncTask(playerDao).execute(player);
    }

    public void delete(ExamplePlayer player){
        new DeletePlayerAsyncTask(playerDao).execute(player);
    }

    public void deleteAllPlayers(){
        new DeleteAllPlayerAsyncTask(playerDao).execute();
    }

    public LiveData<List<ExamplePlayer>> getAllPlayers(){
        return allPlayers;
    }

    private static class InsertPlayerAsyncTask extends AsyncTask<ExamplePlayer, Void, Void>{
        private PlayerDao playerDao;

        private InsertPlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(ExamplePlayer... players){
            playerDao.insert(players[0]);
            return null;
        }
    }

    private static class UpdatePlayerAsyncTask extends AsyncTask<ExamplePlayer, Void, Void>{
        private PlayerDao playerDao;

        private UpdatePlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(ExamplePlayer... players){
            playerDao.update(players[0]);
            return null;
        }
    }

    private static class DeletePlayerAsyncTask extends AsyncTask<ExamplePlayer, Void, Void>{
        private PlayerDao playerDao;

        private DeletePlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(ExamplePlayer... players){
            playerDao.delete(players[0]);
            return null;
        }
    }

    private static class DeleteAllPlayerAsyncTask extends AsyncTask<Void, Void, Void>{
        private PlayerDao playerDao;

        private DeleteAllPlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            playerDao.deleteAllPlayers();
            return null;
        }
    }
}
