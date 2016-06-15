package bom.dom.com.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import bom.dom.com.recipe.rest.DetailInfoModel;
import bom.dom.com.recipe.rest.ReciptModel;
import bom.dom.com.recipe.rest.RestClientV2;
import bom.dom.com.recipe.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInfo extends AppCompatActivity {

    private static final String TAG = DetailInfo.class.getSimpleName();
    private TextView title;
    private ImageView image;
    private TextView ingredients;
    private TextView author;
    private TextView rank;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        initContent();
        initCall();
    }

    private void initCall() {

        Call<DetailInfoModel> getDetaolInfo = new RestClientV2().getApiService().geiItemInfo(
                getIntent().getExtras().getString(Constants.ID)+"",
                Constants.KEY
        );
        getDetaolInfo.enqueue(new Callback<DetailInfoModel>() {
            @Override
            public void onResponse(Call<DetailInfoModel> call, Response<DetailInfoModel> response) {
                Log.i(TAG, "onResponse: "+response.code());
                Log.i(TAG, "onResponse: "+response.message());
                try {
                    Log.i(TAG, "onResponse: "+response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stUpContent(response.body().getRecipe());
            }

            @Override
            public void onFailure(Call<DetailInfoModel> call, Throwable t) {

            }
        });
    }

    private void stUpContent(ReciptModel body) {
        title.setText(body.getTitle());
        Picasso.with(this)
                .load(body.getImage_url())
                .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        fillIngridients(body.getIngredients());
        author.setText(body.getPublisher());
        rank.setText(body.getSocial_rank());
    }

    private void fillIngridients(String[] ingredients) {
        String listofIngr = "";
        for (int i=0;i<ingredients.length;i++){
            if(i<ingredients.length-1){
                listofIngr=listofIngr+ingredients[i]+"\n";
            }else{
                listofIngr=listofIngr+ingredients[i];
            }
        }
        Log.i(TAG, "fillIngridients: "+listofIngr);
        this.ingredients.setText(listofIngr);
    }

    private void initContent() {
        title = (TextView)findViewById(R.id.title);
        image = (ImageView)findViewById(R.id.image);
        ingredients = (TextView)findViewById(R.id.ingredients);
        author = (TextView)findViewById(R.id.author);
        rank = (TextView)findViewById(R.id.rank);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        ((ImageView)findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
