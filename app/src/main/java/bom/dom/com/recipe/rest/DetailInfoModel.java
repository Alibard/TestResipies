package bom.dom.com.recipe.rest;

/**
 * Created by Админ on 15.06.2016.
 */
public class DetailInfoModel {
    private ReciptModel recipe;

    @Override
    public String toString() {
        return "DetailInfoModel{" +
                "recipe=" + recipe +
                '}';
    }

    public ReciptModel getRecipe() {
        return recipe;
    }

    public void setRecipe(ReciptModel recipe) {
        this.recipe = recipe;
    }

    public DetailInfoModel(ReciptModel recipe) {
        this.recipe = recipe;
    }
}
