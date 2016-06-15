package bom.dom.com.recipe.rest;

import java.util.ArrayList;

/**
 * Created by Админ on 13.06.2016.
 */
public class PageModel {
    private int count;
    private ArrayList<ReciptModel> recipes;


    @Override
    public String toString() {
        return "PageModel{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ReciptModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<ReciptModel> recipes) {
        this.recipes = recipes;
    }

    public PageModel(int count, ArrayList<ReciptModel> recipes) {
        this.count = count;
        this.recipes = recipes;
    }
}
