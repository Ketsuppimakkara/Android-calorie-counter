package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FoodActivity extends AppCompatActivity {

    //Calories/100g
    //Beef 209kcal
    //Fish 127kcal
    //Pork  216kcal
    //dairy 97kcal
    //Cheese    272kcal
    //Rice  348kcal
    //Egg   134kcal
    //salad 16kcal
    //restaurant
    Button addBeef;
    Button addFish;
    Button addPork;
    Button addDairy;
    Button addCheese;
    Button addRice;
    Button addEgg;
    Button addSalad;
    Button addRestaurant;
    EditText beefNumb;
    EditText fishNumb;
    EditText porkNumb;
    EditText dairyNumb;
    EditText cheeseNumb;
    EditText riceNumb;
    EditText eggNumb;
    EditText saladNumb;
    EditText restaurantNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        addBeef = findViewById(R.id.addBeef);
        addFish = findViewById(R.id.addFish);
        addPork = findViewById(R.id.addPork);
        addDairy = findViewById(R.id.addDairy);
        addCheese = findViewById(R.id.addCheese);
        addRice = findViewById(R.id.addRice);
        addEgg = findViewById(R.id.addEgg);
        addSalad = findViewById(R.id.addSalad);
        addRestaurant = findViewById(R.id.addRestaurant);
        beefNumb = findViewById(R.id.beefNumb);
        fishNumb = findViewById(R.id.fishNumb);
        porkNumb = findViewById(R.id.porkNumb);
        dairyNumb = findViewById(R.id.dairyNumb);
        cheeseNumb = findViewById(R.id.cheeseNumb);
        riceNumb = findViewById(R.id.riceNumb);
        eggNumb = findViewById(R.id.eggNumb);
        saladNumb = findViewById(R.id.saladNumb);
        restaurantNumb = findViewById(R.id.restaurantNumb);
    }
}