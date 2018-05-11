package com.example.caroline.foodme.GenerateFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.caroline.foodme.API_Interfaces.DataMuseNutritionIngr;
import com.example.caroline.foodme.API_Interfaces.DataMuseNutritionSearch;
import com.example.caroline.foodme.EdamamObjects.EntitySearch;
import com.example.caroline.foodme.EdamamObjects.Hints;
import com.example.caroline.foodme.EdamamObjects.NutritionResponse;
import com.example.caroline.foodme.EdamamObjects.fooddotjson;
import com.example.caroline.foodme.InputFoodsAdapter;
import com.example.caroline.foodme.R;
import com.example.caroline.foodme.RecyclerViewOnClick;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AutoGenerateFragment extends AppCompatActivity {

    private Button randomGenButton, inputCreateButton;
    private EditText inputIngrTextView;
    private LinearLayout inputtedListLinLayout;
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //TODO do we need LayoutInflater and View

    private ArrayList<NutritionResponse> nutritionResponses = new ArrayList<>();

//    private ArrayList<EntitySearch> entitySearches = new ArrayList<>();
    private ArrayList firstAPICallReturn = new ArrayList();
//    private ArrayList<Hints> hintsAutoFill = new ArrayList<>();
    final RecipeGeneratorMethods recipeGeneratorMethods = new RecipeGeneratorMethods(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_generate_fragment);

        final ArrayList<EntitySearch> entitySearches;
        wireWidgets();

        //holds list of the return info from searching for a food
//        final ArrayList<EntitySearch> entitySearches = new ArrayList<>();
//        String foodSearched = ""; //what we want to search for --> gotten from TextEdit
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(DataMuseNutritionIngr.baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        //
//        DataMuseNutritionIngr api = retrofit.create(DataMuseNutritionIngr.class);
//
//
//        Call<ArrayList<EntitySearch>> call = api.getIngrNutrient(foodSearched, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
//
//        call.enqueue(new Callback<ArrayList<EntitySearch>>() {
//            @Override
//            public void onResponse(Call<ArrayList<EntitySearch>> call, Response<ArrayList<EntitySearch>> response) {
//                entitySearches.clear();
//                entitySearches.addAll(response.body());
//            }
//            @Override
//            public void onFailure(Call<ArrayList<EntitySearch>> call, Throwable t) {
//                Toast.makeText(AutoGenerateFragment.this, "Invalid food!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        final fooddotjson fooddotjson = new fooddotjson(1);
//        String[] uriTwo = fooddotjson.findIngredient(entitySearches);
//        fooddotjson.addIngredient(uriTwo);
//
//        DataMuseNutritionSearch apiFoodPackage = retrofit.create(DataMuseNutritionSearch.class);
//        final NutritionResponse nutritionResponse = apiFoodPackage.sendFood(fooddotjson, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION); //TODO check assignment
        //assuming this is returning a package with the FoodResponse object information

        //return inflater.inflate(R.layout.fragment_create, container, false); TODO Why do we need it here?

        //TODO Have the two possibilities for onClick
        //Randomly generate recipe
        autoGeneratedIngrs(view);

        //Create RecyclerView with foods displayed w/ EditText inputs
        ArrayList<NutritionResponse> nutritionResponses = new ArrayList<>();
        String input = inputIngrTextView.getText().toString();
        //TODO Caroline: where to start on Wednesday
        setRecyclerView(nutritionResponses);


    }

    private void wireWidgets() {
        //TODO wire widgets
        inputtedListLinLayout = findViewById(R.id.list_input_foods);
        inputCreateButton = findViewById(R.id.button_choice_create);
        randomGenButton = findViewById(R.id.button_auto_create);
        inputIngrTextView = findViewById(R.id.editText_ingr_input);
        recyclerView = findViewById(R.id.recyclerView); //TODO Why isn't recyclerView_foods showing up?
    }

    /**
     * Creates a completely randomised recipe with nutrition of ingredients --> maybe they can choose which ones they want?
     * Called when Generate/Random button is clicked
     * @param view takes in a view
     */
    private void autoGeneratedIngrs(View view) {
        //TODO Have APIs and set-ups for autocreate --> ONLY uses that as the list ingredients
//        ArrayList<NutritionResponse> nutritionResponses = new ArrayList<>();
//
//        final ArrayList<EntitySearch> entitySearches = new ArrayList<>();
//        final ArrayList<Hints> hintsAutoFill = new ArrayList<>();
//        final RecipeGeneratorMethods recipeGeneratorMethods = new RecipeGeneratorMethods(this);
        ArrayList<ArrayList<String>> allFoods = recipeGeneratorMethods.listAllIngredients();
        /*
        TODO 1. Using foods chosen (specific #?) send each through database -DONE
        TODO 2. Get nutrient values we need -DONE --> Display in RF?
        TODO 3. Add them together
        TODO 4. Make recipe in format for recipe_layout.xml
        TODO 5. Display all information
         */

        //Tester

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataMuseNutritionIngr.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataMuseNutritionIngr api = retrofit.create(DataMuseNutritionIngr.class);

        /*
        This part goes through all the database parts --> goes through all the things that are in the strings that are chosen
        then gets the information for each
        --> TODO choose foods: maybe it should display a # from each then person can swipe to pick what ones they might want/not want?
        Displayed in Retrofit too?
         */
        int l = allFoods.size();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; i < l; j++) {
                String foodSearched = allFoods.get(i).get(j); //what we want to search for --> gotten from TextEdit
                firstAPICallReturn = this.IngrSearchAPICall(api, foodSearched, i); //should do all commented out code w/o rewriting

//                Call<ArrayList<EntitySearch>> callFirst = api.getIngrNutrient(foodSearched, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
//                callFirst.enqueue(new Callback<ArrayList<EntitySearch>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<EntitySearch>> call, Response<ArrayList<EntitySearch>> response) {
//                        entitySearches.clear();
//                        entitySearches.addAll(response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<EntitySearch>> call, Throwable t) {
//                        Toast.makeText(recipeGeneratorMethods, "DETECTED: Alien food. PROCEED: Code Red", Toast.LENGTH_SHORT).show();
//                        System.exit(1);
//                    }
//                });
//                int totalPages = entitySearches.get(i).getNumPages();
//
//                for(int k = 0; k < totalPages; k++){
//                    Call < ArrayList<Hints>> callSecond = api.getAllHints(foodSearched, k, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
//                    callSecond.enqueue(new Callback<ArrayList<Hints>>() {
//                        @Override
//                        public void onResponse(Call<ArrayList<Hints>> call, Response<ArrayList<Hints>> response) {
//                            hintsAutoFill.clear();
//                            hintsAutoFill.addAll(response.body());
//                        }
//
//                        @Override
//                        public void onFailure(Call<ArrayList<Hints>> call, Throwable t) {
//                            Toast.makeText(recipeGeneratorMethods, "Uh oh. No hints for you.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } //TODO Hints will be useful for suggestions + auto-fill
//            }
            }
//            final fooddotjson fooddotjson = new fooddotjson(1);
//            String[] uriTwo = fooddotjson.findIngredient(entitySearches.get(i));
//            fooddotjson.addIngredient(uriTwo);
            DataMuseNutritionSearch apiFoodPackage = retrofit.create(DataMuseNutritionSearch.class);
            nutritionResponses = nutritionSearchAPICall(apiFoodPackage, (ArrayList<EntitySearch>) firstAPICallReturn.get(0), i);

//            final NutritionResponse nutritionResponse = apiFoodPackage.sendFood(fooddotjson, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
//            nutritionResponses.add(nutritionResponse); //up until here gets all nutrition NOT DONE!!!
        }


    }

    /**
     * Takes user's choices of food and finds nutrient information from it and generates the recipe. TODO NOT DONE!
     * Called when button w/ Create is clicked
     * Intended to take a user's choices put into a recyclerView and generate a recipe --> maybe this needs to be in 1+ steps
     * @param view takes in a view
     */
    private void userChoiceIngrs(View view) { //how do we do this
        //TODO have user have input into textEdits in RecyclerView and then uses that as a matching
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DataMuseNutritionIngr.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataMuseNutritionIngr api = retrofit.create(DataMuseNutritionIngr.class);

        DataMuseNutritionSearch apiNutritionSearch = retrofit.create(DataMuseNutritionSearch.class);

        ArrayList<String> userIngrs = new ArrayList<>(); //to store strings we're matching
        RecipeGeneratorMethods recipeGeneratorMethods = new RecipeGeneratorMethods(this);
        recipeGeneratorMethods.getLists(); //sets raw xml to Strings
        recipeGeneratorMethods.sortIngredients(); //sees if it's in hard-coded lists
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 1: i = RecipeGeneratorMethods.CARB_INDEX;
                    String original = recipeGeneratorMethods.getStapleList();
                    int l = recipeGeneratorMethods.getCarbohydrates().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfCarb = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfCarb);
                        String test = original.substring(occuranceOfCarb, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 2: i = RecipeGeneratorMethods.FRUIT_INDEX;
                    original = recipeGeneratorMethods.getFruitList();
                    l = recipeGeneratorMethods.getFruits().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfFruit = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfFruit);
                        String test = original.substring(occuranceOfFruit, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 3: i = RecipeGeneratorMethods.OIL_INDEX;
                    original = recipeGeneratorMethods.getOilList();
                    l = recipeGeneratorMethods.getOils().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfOil = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfOil);
                        String test = original.substring(occuranceOfOil, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 4: i = RecipeGeneratorMethods.PROTEIN_INDEX;
                    original = recipeGeneratorMethods.getProteinList();
                    l = recipeGeneratorMethods.getProteins().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfProtein = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfProtein);
                        String test = original.substring(occuranceOfProtein, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 5: i = RecipeGeneratorMethods.SAUCE_INDEX;
                    original = recipeGeneratorMethods.getSauceList();
                    l = recipeGeneratorMethods.getSauces().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfSauce = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfSauce);
                        String test = original.substring(occuranceOfSauce, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 6: i = RecipeGeneratorMethods.SPICE_INDEX;
                    original = recipeGeneratorMethods.getSpiceList();
                    l = recipeGeneratorMethods.getSpices().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfSpice = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfSpice);
                        String test = original.substring(occuranceOfSpice, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
                case 7: i = RecipeGeneratorMethods.VEG_INDEX;
                    original = recipeGeneratorMethods.getVegetableList();
                    l = recipeGeneratorMethods.getVegetables().size();
                    for(int j = 0; j < l/2; j++){
                        //String userInput = recipeGeneratorMethods.getCarbohydrates().get(j * 2);
                        int occuranceOfVeg = Integer.parseInt(recipeGeneratorMethods.getCarbohydrates().get(j * 2 + 1));
                        int stop = original.indexOf(",", occuranceOfVeg);
                        String test = original.substring(occuranceOfVeg, stop);

                        firstAPICallReturn.add(IngrSearchAPICall(api, test, i));
                        ArrayList<EntitySearch> entitySearches = (ArrayList<EntitySearch>) firstAPICallReturn.get(0);
                        ArrayList<NutritionResponse> placeholderNutRes = nutritionSearchAPICall(apiNutritionSearch, entitySearches, i);
                        for(NutritionResponse nutResp : placeholderNutRes){
                            nutritionResponses.add(nutResp);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Sets RecyclerView for displaying food userInputs in AutoGen xml --> //TODO missing some parts
     * @param nutritionResponses contains what the recyclerView will be set to
     */
    private void setRecyclerView(ArrayList<NutritionResponse> nutritionResponses) {
        //https://developer.android.com/guide/topics/ui/layout/recyclerview#java
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InputFoodsAdapter(new ArrayList<NutritionResponse>(), new RecyclerViewOnClick() {
            @Override
            public void onClick(View v, int pos) {
                //stuff? TODO ?
            }
        });
        recyclerView.setAdapter(adapter);

        ViewGroup viewGroup = new ViewGroup(this) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        }; //check w/ Mr. Shorr
        int num = adapter.getItemCount();
        InputFoodsAdapter.MyViewHolder myViewHolder = (InputFoodsAdapter.MyViewHolder) adapter.onCreateViewHolder(viewGroup, num);
        adapter.bindViewHolder(myViewHolder, num);
    }

    /**
     * API Calls from the 7 categories after they're separated --> runs through multiple times
     * @param nutritionIngrAPI DatamuseNutritionSearch api call (declared earlier in method call)
     * @param foodSearched food that's wanted to get nutrient of
     * @param index identifies type of food [0, 6]
     * @return ArrayList w/ index 0 of AL of EntitySearches (later used to get Nutrients) + index 1 of AL of Hints (later for autofill)
     */
    private ArrayList IngrSearchAPICall(DataMuseNutritionIngr nutritionIngrAPI, String foodSearched, int index) {
        final ArrayList<EntitySearch> entitySearches = new ArrayList<>();
        final ArrayList<Hints> hintsAutoFill = new ArrayList<>();
        Call<ArrayList<EntitySearch>> callFirst = nutritionIngrAPI.getIngrNutrient(foodSearched, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
        callFirst.enqueue(new Callback<ArrayList<EntitySearch>>() {
            @Override
            public void onResponse(Call<ArrayList<EntitySearch>> call, Response<ArrayList<EntitySearch>> response) {
                entitySearches.clear();
                entitySearches.addAll(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<EntitySearch>> call, Throwable t) {
                Toast.makeText(recipeGeneratorMethods, "DETECTED: Alien food. PROCEED: Code Red", Toast.LENGTH_SHORT).show();
                System.exit(1);
            }
        });
        int totalPages = entitySearches.get(index).getNumPages();
        firstAPICallReturn.add(entitySearches);

        for (int k = 0; k < totalPages; k++) {
            Call<ArrayList<Hints>> callSecond = nutritionIngrAPI.getAllHints(foodSearched, k, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
            callSecond.enqueue(new Callback<ArrayList<Hints>>() {
                @Override
                public void onResponse(Call<ArrayList<Hints>> call, Response<ArrayList<Hints>> response) {
                    hintsAutoFill.clear();
                    hintsAutoFill.addAll(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<Hints>> call, Throwable t) {
                    Toast.makeText(recipeGeneratorMethods, "Uh oh. No hints for you.", Toast.LENGTH_SHORT).show();
                }
            });
        } //TODO This will be useful for suggestions + auto-fill
        firstAPICallReturn.add(hintsAutoFill);
        return firstAPICallReturn;
    }

    /**
     * 2nd API Call by sending each food JSON package to be analysed and gets back the full set of nutrient informations
     * @param nutritionSearchAPI DataMuseNutritionSearch api call
     * @param entitySearches AL of EntitySearches that's combed through
     * @param index identifies type of food [0, 6]
     * @return AL of NutritionResponse objects
     */
    private ArrayList<NutritionResponse> nutritionSearchAPICall(DataMuseNutritionSearch nutritionSearchAPI, ArrayList<EntitySearch> entitySearches, int index){
        fooddotjson fooddotjson = new fooddotjson(1);
        String[] uriTwo = fooddotjson.findIngredient(entitySearches.get(index));
        fooddotjson.addIngredient(uriTwo);
        final NutritionResponse nutritionResponse = nutritionSearchAPI.sendFood(fooddotjson, EdamamNutritionKeys.APP_ID_NUTRITION, EdamamNutritionKeys.APP_KEY_NUTRITION);
        nutritionResponses.add(nutritionResponse);
        return nutritionResponses;
    }
}

