package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    Button addBeef,addFish,addPork,addDairy,addCheese,addRice,addEgg,addSalad,addRestaurant,saveButton;
    EditText beefNumb,fishNumb,porkNumb,dairyNumb,cheeseNumb,riceNumb,eggNumb,saladNumb,restaurantNumb;

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
        saveButton = findViewById(R.id.saveButton);
        beefNumb = findViewById(R.id.beefNumb);
        fishNumb = findViewById(R.id.fishNumb);
        porkNumb = findViewById(R.id.porkNumb);
        dairyNumb = findViewById(R.id.dairyNumb);
        cheeseNumb = findViewById(R.id.cheeseNumb);
        riceNumb = findViewById(R.id.riceNumb);
        eggNumb = findViewById(R.id.eggNumb);
        saladNumb = findViewById(R.id.saladNumb);
        restaurantNumb = findViewById(R.id.restaurantNumb);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (beefNumb.getText().toString().length() == 0){
                    beefNumb.setText("0");
                }
                if (fishNumb.getText().toString().length() == 0){
                    fishNumb.setText("0");
                }
                if (porkNumb.getText().toString().length() == 0){
                    porkNumb.setText("0");
                }
                if (dairyNumb.getText().toString().length() == 0){
                    dairyNumb.setText("0");
                }
                if (cheeseNumb.getText().toString().length() == 0){
                    cheeseNumb.setText("0");
                }
                if (riceNumb.getText().toString().length() == 0){
                    riceNumb.setText("0");
                }
                if (eggNumb.getText().toString().length() == 0){
                    eggNumb.setText("0");
                }
                if (saladNumb.getText().toString().length() == 0){
                    saladNumb.setText("0");
                }
                if (restaurantNumb.getText().toString().length() == 0){
                    restaurantNumb.setText("0");
                }
                int beef = Integer.parseInt(beefNumb.getText().toString());
                int fish = Integer.parseInt(fishNumb.getText().toString());
                int pork = Integer.parseInt(porkNumb.getText().toString());
                int dairy = Integer.parseInt(dairyNumb.getText().toString());
                int cheese = Integer.parseInt(cheeseNumb.getText().toString());
                int rice = Integer.parseInt(riceNumb.getText().toString());
                int egg = Integer.parseInt(eggNumb.getText().toString());
                int salad = Integer.parseInt(saladNumb.getText().toString());
                int restaurant = Integer.parseInt(restaurantNumb.getText().toString());


                Intent intent = new Intent(FoodActivity.this,MainActivity.class);

                intent.putExtra("beef",beef);
                intent.putExtra("Fish",fish);
                intent.putExtra("Pork",pork);
                intent.putExtra("Dairy",dairy);
                intent.putExtra("Cheese",cheese);
                intent.putExtra("Rice",rice);
                intent.putExtra("Egg",egg);
                intent.putExtra("Salad",salad);
                intent.putExtra("Restaurant",restaurant);
                System.out.println(beef);
                startActivity(intent);
            }
        });
    }
}