package com.example.olio_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FoodActivity extends AppCompatActivity {


    int userIndex;

    Button saveButton;
    EditText beefNumb,fishNumb,porkNumb,dairyNumb,cheeseNumb,saladNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent inboundIntent = getIntent();
        userIndex = inboundIntent.getIntExtra("Index",-1);
        System.out.println("###USERINDEX in foodactivity:"+userIndex);

        saveButton = findViewById(R.id.saveButton);
        beefNumb = findViewById(R.id.beefNumb);
        fishNumb = findViewById(R.id.fishNumb);
        porkNumb = findViewById(R.id.porkNumb);
        dairyNumb = findViewById(R.id.dairyNumb);
        cheeseNumb = findViewById(R.id.cheeseNumb);
        saladNumb = findViewById(R.id.saladNumb);



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
                if (saladNumb.getText().toString().length() == 0){
                    saladNumb.setText("0");
                }
                int beef = Integer.parseInt(beefNumb.getText().toString());
                int fish = Integer.parseInt(fishNumb.getText().toString());
                int pork = Integer.parseInt(porkNumb.getText().toString());
                int dairy = Integer.parseInt(dairyNumb.getText().toString());
                int cheese = Integer.parseInt(cheeseNumb.getText().toString());
                int salad = Integer.parseInt(saladNumb.getText().toString());


                Intent intent = new Intent(FoodActivity.this,MainActivity.class);

                intent.putExtra("Index",userIndex);

                intent.putExtra("beef",beef);
                intent.putExtra("Fish",fish);
                intent.putExtra("Pork",pork);
                intent.putExtra("Dairy",dairy);
                intent.putExtra("Cheese",cheese);
                intent.putExtra("Salad",salad);
                System.out.println(beef);
                startActivity(intent);
            }
        });
    }
}