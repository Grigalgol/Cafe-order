package com.example.cafeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String drink;
    private String name;
    private String password;
    private StringBuilder stringBuilderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if(intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }

        textViewHello = findViewById(R.id.textviewHello);
        String hello = String.format("Привет %s ! Что будете заказывать?", name);
        textViewAdditions = findViewById(R.id.textviewEditions);
        String additions = String.format(getString(R.string.HelloUser), drink);
        textViewAdditions.setText(additions);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffe);
        drink = getString(R.string.Tea);
        stringBuilderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id == R.id.tea) {
            drink = getString(R.string.Tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.coffee) {
            drink = getString(R.string.Coffe);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }


    }

    public void sendOrder(View view) {
        stringBuilderAdditions.setLength(0);
        if(checkBoxMilk.isChecked()) {
            stringBuilderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if(checkBoxLemon.isChecked()) {
            stringBuilderAdditions.append(getString(R.string.Lemon)).append(" ");
        }
        if(checkBoxSugar.isChecked() && drink.equals(getString(R.string.Tea))) {
            stringBuilderAdditions.append(getString(R.string.Sugar)).append(" ");
        }
        String optionOfDrink = "";
        if(drink.equals(getString(R.string.Tea))) {
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        String order = String.format("Имя: %s\nПароль: %s\nНапиток: %s\nВид напитка: %s\n", name, password, drink, optionOfDrink);
        String additions;
        if(stringBuilderAdditions.length() > 0) additions = "Необходимые добавки" + stringBuilderAdditions.toString();
        else additions = "";
        String fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}