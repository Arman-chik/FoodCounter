package com.example.foodcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class Dobavlenie extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    TextView textOut;
    EditText ename, ekall, eugl, ezhiri, ebelki;
    Button btnDel, btnAdd, btnGet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobavlenie);

        dbHelper = new DBHelper(this);

        textOut = findViewById(R.id.textOut);

        ename = findViewById(R.id.editName);
        ekall = findViewById(R.id.editKall);
        eugl = findViewById(R.id.editUgl);
        ezhiri = findViewById(R.id.editZhiri);
        ebelki = findViewById(R.id.editBelki);

        btnDel = findViewById(R.id.buttonDel);
        btnAdd = findViewById(R.id.buttonAdd);
        btnGet = findViewById(R.id.buttonGet);

        btnDel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonDel:
                dbHelper.DeletedAll();
                getList();
                break;

            case R.id.buttonAdd:
                String name = ename.getText().toString();
                try {
                    double kall = Double.parseDouble(ekall.getText().toString());
                    double ugl = Double.parseDouble(eugl.getText().toString());
                    double zhiri = Double.parseDouble(ezhiri.getText().toString());
                    double belki = Double.parseDouble(ebelki.getText().toString());

                    Products products = new Products(0, name, kall, ugl, zhiri, belki);
                    dbHelper.AddOne(products);
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(Dobavlenie.this, "Неверные значение/ия!", Toast.LENGTH_LONG).show();
                }
                getList();
                break;

            case R.id.buttonGet:
                getList();
                break;
            default:
        }

    }

    void getList () {
        LinkedList<Products> list = dbHelper.GetAll();
        String text= "";
        for (Products pr:list) text = text + pr.name + " " + pr.calories + " " + pr.uglevod + " " + pr.zhiri + " " + pr.belki + "\n";

        textOut.setText(text);
    }
}