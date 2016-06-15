package bom.dom.com.recipe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import bom.dom.com.recipe.adapters.ViewPageAdaper;
import bom.dom.com.recipe.callBacks.SearchCallBack;
import bom.dom.com.recipe.callBacks.UpdateDataCallBack;
import bom.dom.com.recipe.utils.Constants;
import bom.dom.com.recipe.utils.Utils;

public class MainActivity extends AppCompatActivity implements SearchCallBack{

    private String TAG = MainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private int currentPosition;
    private TextView searchBtn;
    private EditText etSearch;
    private ViewPageAdaper pageAdaper;
    private TextView rSort;
    private TextView tSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Utils.hideKeyboard(this);
        initContent();
        setUpContent();
    }

    /**
     * Method for setUP click listener's and data to content
     */
    private void setUpContent() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Constants.SEACH_WORD=etSearch.getText().toString();
                    updateDataLists();
                    Utils.hideKeyboard(MainActivity.this);

            }
        });
        rSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SORT.equals("r")){
                    Constants.SORT="";
                }else{
                    Constants.SORT="r";
                }
                updateDataLists();
            }
        });
        tSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SORT.equals("t")){
                    Constants.SORT="";
                }else{
                    Constants.SORT="t";
                }
                updateDataLists();
            }
        });
    }

    private void updateDataLists() {
        UpdateDataCallBack updateDataCallBack;
        Log.i(TAG, "updateDataLists: "+currentPosition);
        switch (currentPosition){
            case Constants.LIST:;
                updateDataCallBack= (UpdateDataCallBack) pageAdaper.getItem(Constants.LIST-1);
                updateDataCallBack.UpdateData();
                break;
            case Constants.GRID:
                updateDataCallBack= (UpdateDataCallBack) pageAdaper.getItem(Constants.GRID-1);
                updateDataCallBack.UpdateData();
                break;
            default:
                break;
        }
    }

    /**
     * Method for init all content in MainActivity
     */
    private void initContent() {
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        pageAdaper = new ViewPageAdaper(getSupportFragmentManager(),this);
        mViewPager.setAdapter(pageAdaper);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.list_img);
        mTabLayout.getTabAt(1).setIcon(R.drawable.grid_img);
        searchBtn = (TextView)findViewById(R.id.search_btn);
        etSearch = (EditText)findViewById(R.id.et_search);
        rSort = (TextView)findViewById(R.id.r_sort);
        tSort  =   (TextView)findViewById(R.id.t_sort);
    }


    /**
     * @param tag of fagment
     * @return true if no such Fragment in backStack and false in othe case.
     */
    private boolean isInBackStack(String tag) {
        List<Fragment> fragment = getSupportFragmentManager().getFragments();
        if (fragment != null) {
            Log.i(TAG,"!null");
            Log.i(TAG," "+fragment.size());

            for (int i = 0; i < fragment.size(); i++) {
                if (fragment.get(i).getTag().equals(tag)) {
                    Log.i(TAG,tag);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method of interface for communication between fragment and main Activity
     * @param position of current visible fragment in viewPager
     */
    @Override
    public void setPosition(int position) {
        currentPosition=position;
        Log.i(TAG, "setPosition: "+position);
    }
}
