package bom.dom.com.recipe.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import bom.dom.com.recipe.MainActivity;
import bom.dom.com.recipe.R;
import bom.dom.com.recipe.rest.ReciptModel;
import bom.dom.com.recipe.utils.Constants;

/**
 * Created by Админ on 13.06.2016.
 */
public class ListRecycleAdapter  extends RecyclerView.Adapter<ListRecycleAdapter.ViewHolder>{


    private static final String TAG = ListRecycleAdapter.class.getSimpleName();
    private ArrayList<ReciptModel> myList;
    private Context context;

    public ListRecycleAdapter(ArrayList<ReciptModel> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public ListRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListRecycleAdapter.ViewHolder holder, final int position) {
        Picasso.with(context)
                .load(myList.get(position).getImage_url())
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });


        final Intent intent = new Intent(context, DetailInfo.class);
        holder.title.setText(myList.get(position).getTitle());
        holder.rating.setText(myList.get(position).getSocial_rank());
        holder.namePoster.setText(myList.get(position).getPublisher());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+myList.get(position).getRecipe_id());
                intent.putExtra(Constants.ID,myList.get(position).getRecipe_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void updateData(ArrayList<ReciptModel> reciptModels) {
        myList.addAll(reciptModels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView title;
        private TextView namePoster;
        private ImageView image;
        private ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            namePoster = (TextView) itemView.findViewById(R.id.name_poster);
            rating = (TextView) itemView.findViewById(R.id.rating);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
