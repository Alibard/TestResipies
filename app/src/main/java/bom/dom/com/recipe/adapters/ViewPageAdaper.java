package bom.dom.com.recipe.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

import bom.dom.com.recipe.R;
import bom.dom.com.recipe.fragments.GridFragment;

/**
 * Created by Админ on 13.06.2016.
 */
public class ViewPageAdaper extends FragmentPagerAdapter {
    List<Fragment> mFragmentList = new ArrayList<>();
    private Context context;
    private int[] imageResId = {
            R.drawable.list_img,
            R.drawable.grid_img

    };
    public ViewPageAdaper(FragmentManager fm, Context context) {
        super(fm);
        mFragmentList.add(new bom.dom.com.recipe.fragments.ListFragment());
        mFragmentList.add(new GridFragment());
        this.context = context;

    }
    public Fragment getIems(){
        return mFragmentList.get(1);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
