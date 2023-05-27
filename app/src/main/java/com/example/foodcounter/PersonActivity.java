package com.example.foodcounter;

import static com.example.foodcounter.DBHelper.COLUMN_KALL;
import static com.example.foodcounter.DBHelper.COLUMN_UGL;
import static com.example.foodcounter.DBHelper.COLUMN_ZHIRI;
import static com.example.foodcounter.DBHelper.COLUMN_BELKI;


import com.example.foodcounter.MainActivity;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonActivity extends AppCompatActivity {
    private static final String USER_INFO_PREFERENCES = "UserInfo";
    private static final String USER_NAME_FIELD = "name";
    private static final String USER_VES_FIELD = "ves";
    private static final String USER_ROST_FIELD = "rost";
    private static final String FOOD_INFO_PREFERENCES = "FoodInfo";
    private static final String CALORIES_FIELD = "call";
    private static final String UGL_FIELD = "ugl";
    private static final String ZHIRI_FIELD = "zhiri";
    private static final String BELKI_FIELD = "belki";


    Button dobButton;
    ListView listView;
    TextView PersonInfo;

    Button ButtonOchist;

    TextView PersName;
    TextView PersVes;
    TextView PersRost;

    TextView PersCals,PersUgl, PersZhiri, PersBelki;


    ProductsListAdapter productsAdapter;
    SharedPreferences sharedPreferencesFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        SharedPreferences sharedPreferences= getSharedPreferences(USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferencesFood = getSharedPreferences(FOOD_INFO_PREFERENCES, Context.MODE_PRIVATE);


        PersName = findViewById(R.id.PersName);
        PersVes = findViewById(R.id.PersVes);
        PersRost = findViewById(R.id.PersRost);

        PersCals = findViewById(R.id.PersCals);
        PersUgl = findViewById(R.id.PersUgl);
        PersZhiri = findViewById(R.id.PersZhiri);
        PersBelki = findViewById(R.id.PersBelki);


        PersName.setText("Имя: " + sharedPreferences.getString(USER_NAME_FIELD, ""));
        PersVes.setText("Вес: " + sharedPreferences.getInt(USER_VES_FIELD,0 ));
        PersRost.setText("Рост: " + sharedPreferences.getInt(USER_ROST_FIELD,0 ));

        showInfo();


        dobButton = findViewById(R.id.dobavit);
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inten = new Intent(PersonActivity.this, Dobavlenie.class);
                startActivity(inten);
            }
        });



        listView = findViewById(R.id.products_lv);
        productsAdapter = new ProductsListAdapter(this);
        listView.setAdapter(productsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Products p =(Products) productsAdapter.getItem(i);
                EditText et = new EditText(PersonActivity.this);
                new AlertDialog.Builder(PersonActivity.this)
                        .setView(et)
                        .setTitle("Введите массу съеденного продукта (в граммах):")
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Посчитать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s = et.getText().toString();
                                int mass = 0;
                                try {
                                    mass = Integer.parseInt(s);
                                }catch (NumberFormatException ex){
                                    Toast.makeText(PersonActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                                    dialogInterface.dismiss();
                                    return;
                                }
                                double cal, ug , zh, bel;
                                cal = (p.calories * mass)/100;
                                ug = (p.uglevod * mass)/100;
                                zh = (p.zhiri* mass)/100;
                                bel = (p.belki * mass)/100;
                                SharedPreferences.Editor editor = sharedPreferencesFood.edit();
                                cal += sharedPreferencesFood.getFloat(CALORIES_FIELD, 0);
                                editor.putFloat(CALORIES_FIELD,(float) cal);
                                ug += sharedPreferencesFood.getFloat(UGL_FIELD, 0);
                                editor.putFloat(UGL_FIELD,(float) ug);
                                zh += sharedPreferencesFood.getFloat(ZHIRI_FIELD, 0);
                                editor.putFloat(ZHIRI_FIELD,(float) zh);
                                bel += sharedPreferencesFood.getFloat(BELKI_FIELD, 0);
                                editor.putFloat(BELKI_FIELD,(float) bel);
                                editor.commit();
                                showInfo();
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });

    }


    public void onResetCounterClicked(View view){
        SharedPreferences.Editor ed = sharedPreferencesFood.edit();
        ed.putFloat(CALORIES_FIELD, 0);
        ed.putFloat(UGL_FIELD, 0);
        ed.putFloat(ZHIRI_FIELD, 0);
        ed.putFloat(BELKI_FIELD, 0);
        ed.commit();
        showInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        productsAdapter.setArrayMyData(new DBHelper(this).GetAll());
    }
    public void showInfo(){
        PersCals.setText(String.format( "Калории: %.2f", sharedPreferencesFood.getFloat(CALORIES_FIELD,0 )));
        PersUgl.setText(String.format( "Углеводы: %.2f", sharedPreferencesFood.getFloat(UGL_FIELD,0 )));
        PersZhiri.setText(String.format( "Жиры: %.2f", sharedPreferencesFood.getFloat(ZHIRI_FIELD,0 )));
        PersBelki.setText(String.format( "Белки: %.2f", sharedPreferencesFood.getFloat(BELKI_FIELD,0 )));
    }


}
