package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {
    private TextView textViewHello, textViewAdditions;
    private CheckBox checkBoxMilk, checkBoxSugar, checkBoxLemon;
    private Spinner spinnerTea, spinnerCofee;

    private String name, password;
    private String drink;

    private StringBuilder builderAdditions;  // список добавок


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        textViewHello = findViewById(R.id.textViewHello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCofee = findViewById(R.id.spinnerCofee);

        builderAdditions = new StringBuilder();  //создаем объект;

        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")) { //проверка - имеются ли данные
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_pw);
        }
        drink = getString(R.string.tea); //по умолчанию Чай
        String hello = String.format(getString(R.string.hello_user), name);

        textViewHello.setText(hello);    // устанавливаем текст приветствия

        String additions = String.format(getString(R.string.additions), drink);

        textViewAdditions.setText(additions);      //формируем текст выбора для добавок


    }

    public void onClickChangeDrink(View view) {
        RadioButton radioButton = (RadioButton) view;   //получаем доступ к вызвавшему метод элементу
        int id = radioButton.getId(); //получаем ID данной радиокнопки

        if (id == R.id.radioButtonTea) {            //определяем вид напитка, в зависимости от нажатия радиокн.
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCofee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.radioButtonCoffee) {
            drink = getString(R.string.cofee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCofee.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }

        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);      //формируем текст выбора для добавок

    }

    public void onClickSendOrder(View view) {
        builderAdditions.setLength(0);   //очищаем формируем список добавок

        if (checkBoxMilk.isChecked()) {
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()) {
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }

        String optionOfDrink = "";    // формируем строку вид напитка из спинера
        if (drink.equals(getString(R.string.tea))) {
            optionOfDrink = (String) spinnerTea.getSelectedItem();
        }

        if (drink.equals(getString(R.string.cofee))) {
            optionOfDrink = (String) spinnerCofee.getSelectedItem();
        }

        String order = String.format(getString(R.string.order), name, password, drink, optionOfDrink);

        String additions;      // формируем строку добавок из чекбоксов
        if (builderAdditions.length() > 0) {
            additions = getString(R.string.need_additions) + " " + builderAdditions.toString();
        } else {
            additions = "";
        }

        String fullOrder = order + additions;

        Intent intent = new Intent(this, OrderDetaleActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }


}