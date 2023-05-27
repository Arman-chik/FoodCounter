package com.example.foodcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String USER_INFO_PREFERENCES = "UserInfo";
    public static final String USER_NAME_FIELD = "name";
    public static final String USER_VES_FIELD = "ves";
    public static final String USER_ROST_FIELD = "rost";

    TextView textView;
    Button nextBtn;
    EditText editTextName;
    EditText editTextVes;
    EditText editTextRost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences= getSharedPreferences(USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textView);

        editTextName = (EditText)findViewById(R.id.Name);
        editTextVes = (EditText)findViewById(R.id.Ves);
        editTextRost = (EditText)findViewById(R.id.Rost);

        nextBtn = findViewById(R.id.button2);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_NAME_FIELD, editTextName.getText().toString());
                String vesString = editTextVes.getText().toString();
                try {
                    int ves = Integer.parseInt(vesString);
                    if (ves <= 0) {
                        Toast.makeText(MainActivity.this, "Неверное значение веса", Toast.LENGTH_LONG).show();
                        return;
                    }
                    editor.putInt(USER_VES_FIELD, ves);
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Неверное значение веса", Toast.LENGTH_LONG).show();
                    return;
                }


                String rostString = editTextRost.getText().toString();
                try {
                    int rost = Integer.parseInt(rostString);
                    if(rost<=0){
                        Toast.makeText(MainActivity.this, "Неверное значение роста", Toast.LENGTH_LONG).show();
                        return;
                    }
                    editor.putInt(USER_ROST_FIELD, rost);
                }catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Неверное значение роста", Toast.LENGTH_LONG).show();
                    return;
                }

                editor.apply();


                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        });



        editTextName.setText(sharedPreferences.getString(USER_NAME_FIELD, ""));
        editTextVes.setText("" + sharedPreferences.getInt(USER_VES_FIELD,0 ));
        editTextRost.setText("" + sharedPreferences.getInt(USER_ROST_FIELD,0 ));
    }


    public void nextList(){
    }

}