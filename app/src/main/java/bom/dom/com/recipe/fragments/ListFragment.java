package bom.dom.com.recipe.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import bom.dom.com.recipe.R;
import bom.dom.com.recipe.adapters.GridRecycleAdapter;
import bom.dom.com.recipe.adapters.ListRecycleAdapter;
import bom.dom.com.recipe.callBacks.SearchCallBack;
import bom.dom.com.recipe.callBacks.UpdateDataCallBack;
import bom.dom.com.recipe.rest.PageModel;
import bom.dom.com.recipe.rest.ReciptModel;
import bom.dom.com.recipe.rest.RestClientV2;
import bom.dom.com.recipe.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anton on 10.06.16.
 */
public class ListFragment extends Fragment implements UpdateDataCallBack {
    public static final String TAG = ListFragment.class.getSimpleName();
    private RecyclerView recipeList;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean loading = true;
    private int page = 0;
    private ArrayList<ReciptModel>reciptModels = new ArrayList<>();
    private SearchCallBack searchCallBack;
    private LinearLayoutManager mLayoutManager;
    private ListRecycleAdapter adapter;
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach+");
        searchCallBack = (SearchCallBack)getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment,container,false);
        initContent(view);
        return view;
    }


    /**
     * Override method for set current fragment in MAinActivity
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG,"setUserVisibleHint");
        if(searchCallBack!=null){
            searchCallBack.setPosition(Constants.LIST);
        }
    }

    /**
     * Method for initial all content in fragment
     * @param view of fragment
     */
    private void initContent(View view) {
        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ");
                page=0;
                setCall();
            }
        });
        recipeList = (RecyclerView)view.findViewById(R.id.list_recipies);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recipeList.setLayoutManager(mLayoutManager);
        recipeList .addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            setCall();
                        }
                    }
                }
            }
        });
        setCall();
    }

    /**
     * Method for sending call to API
     */
    private void setCall() {

        page++;
        Log.i(TAG, "setCall: " +page+" "+ Constants.SORT+ " "+Constants.SEACH_WORD);
        Call<PageModel> getFirstPage = new RestClientV2()
                .getApiService()
                .getPageItem(Constants.KEY,
                        page+"",
                        Constants.SORT,
                        Constants.SEACH_WORD);

        getFirstPage.enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                reciptModels = response.body().getRecipes();
                Log.i(TAG, "onResponse: "+response.body().toString());
                if(page>1){
                    adapter.updateData(reciptModels);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter = new ListRecycleAdapter(reciptModels, getActivity());
                    recipeList.setAdapter(adapter);
                }
                swipe.setRefreshing(false);
                loading =true;
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {
                t.printStackTrace();
                swipe.setRefreshing(false);
            }
        });


    }

    /**
     * Method for starting update data in fragment
     */
    @Override
    public void UpdateData() {
        page=0;
        setCall();
        Log.i(TAG, "UpdateData: ++++");
    }
}
