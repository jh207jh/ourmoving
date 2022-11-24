package com.example.edusungshinourmoving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    private Button btn_confirm;
    private Button btn_back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        View edit_Num = findViewById(R.id.edit_Num);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        Button btn_back2 = (Button) findViewById(R.id.btn_back2);
        btn_back2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent= new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }

}