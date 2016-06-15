package bom.dom.com.recipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bom.dom.com.recipe.DetailInfo;
import bom.dom.com.recipe.R;
import bom.dom.com.recipe.rest.ReciptModel;
import bom.dom.com.recipe.utils.Constants;

/**
 * Created by anton on 10.06.16.
 */
public class GridRecycleAdapter extends RecyclerView.Adapter<GridRecycleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ReciptModel>reciptModels;
    public GridRecycleAdapter(ArrayList<ReciptModel> reciptModels, FragmentActivity activity) {
        this.reciptModels = reciptModels;
        this.context = activity;
    }

    @Override
    public GridRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GridRecycleAdapter.ViewHolder holder, final int position) {

        Picasso.with(context).load(reciptModels.get(position).getImage_url())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        final Intent intent = new Intent(context, DetailInfo.class);
        holder.rank.setText(reciptModels.get(position).getSocial_rank());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.ID,reciptModels.get(position).getRecipe_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reciptModels.size();
    }

    public void updateData(ArrayList<ReciptModel> reciptModels) {
        this.reciptModels.addAll(reciptModels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView rank;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.view);
            rank = (TextView)itemView.findViewById(R.id.rank);
            progressBar = (ProgressBar )itemView.findViewById(R.id.progressBar);
        }
    }
}