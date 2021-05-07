package edu.scranton.nesbittj3.factualfantasy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.scranton.nesbittj3.factualfantasy.model.ExamplePlayer;
import edu.scranton.nesbittj3.factualfantasy.model.ExampleStats;

public class CompareFragAdapter extends RecyclerView.Adapter<CompareFragAdapter.WatchViewHolder>{

    Context context;
    List<ExampleStats> statsList;


    public CompareFragAdapter(Context context, List<ExampleStats> statsList){
        this.context = context;
        this.statsList = statsList;
    }

    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.compare_frag_layout, parent, false);
        return new WatchViewHolder(view);
    }

    public void setStatsList(List<ExampleStats> stats){
        this.statsList = stats;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        ExampleStats currentRow = statsList.get(position);

        String rCmp = currentRow.getCmp();
        String pAtt = currentRow.getpAtt();
        String pYds = currentRow.getpYds();
        String cmpPer = currentRow.getCmpper();
        String pAvg = currentRow.getpAvg();
        String pTd = currentRow.getpTd();
        String pInt = currentRow.getInterceptions();
        String pLng = currentRow.getpLng();
        String sacks = currentRow.getSack();
        String rtg = currentRow.getRtg();
        String qbr = currentRow.getQbr();
        String rAtt = currentRow.getrAtt();
        String rYds = currentRow.getrYds();
        String rAvg = currentRow.getrAvg();
        String rTd = currentRow.getrTd();
        String rLng = currentRow.getrLng();


        holder.compView.setText(rCmp);
        holder.pAttView.setText(pAtt);
        holder.pYdsView.setText(pYds);
        holder.cmpPerView.setText(cmpPer);
        holder.pAvgView.setText(pAvg);
        holder.pTdView.setText(pTd);
        holder.pIntView.setText(pInt);
        holder.pLngView.setText(pLng);
        holder.sackView.setText(sacks);
        holder.rtgView.setText(rtg);
        holder.qbrView.setText(qbr);
        holder.rAttView.setText(rAtt);
        holder.rYdsView.setText(rYds);
        holder.rAvgView.setText(rAvg);
        holder.rTdView.setText(rTd);
        holder.rLngView.setText(rLng);


    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {

        private TextView compView;
        private TextView pAttView;
        private TextView pYdsView;
        private TextView cmpPerView;
        private TextView pAvgView;
        private TextView pTdView;
        private TextView pIntView;
        private TextView pLngView;
        private TextView sackView;
        private TextView rtgView;
        private TextView qbrView;
        private TextView rAttView;
        private TextView rYdsView;
        private TextView rAvgView;
        private TextView rTdView;
        private TextView rLngView;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            compView = itemView.findViewById(R.id.cmp_view);
            pAttView = itemView.findViewById(R.id.patt_view);
            pYdsView = itemView.findViewById(R.id.pyds_view);
            cmpPerView = itemView.findViewById(R.id.cmpper_view);
            pAvgView = itemView.findViewById(R.id.pavg_view);
            pTdView = itemView.findViewById(R.id.ptd_view);
            pIntView = itemView.findViewById(R.id.pInt_view);
            pLngView = itemView.findViewById(R.id.pLng_view);
            sackView = itemView.findViewById(R.id.sack_view);
            rtgView = itemView.findViewById(R.id.rtg_view);
            qbrView = itemView.findViewById(R.id.qbr_view);
            rAttView = itemView.findViewById(R.id.rAtt_view);
            rYdsView = itemView.findViewById(R.id.rYds_view);
            rAvgView = itemView.findViewById(R.id.rAvg_view);
            rYdsView = itemView.findViewById(R.id.rYds_view);
            rTdView = itemView.findViewById(R.id.rTd_view);
            rLngView = itemView.findViewById(R.id.rLng_view);
        }
    }
}
