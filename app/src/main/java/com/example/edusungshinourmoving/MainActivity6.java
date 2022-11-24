package com.example.edusungshinourmoving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity6 extends AppCompatActivity {
    Button furniture;
    Button toilet;
    Button life1;
    Button life2;
    Button bed;
    Button kitchen;
    Button digital;
    Button security;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main6);
            furniture = (Button) findViewById(R.id.furniture);
            toilet = (Button) findViewById(R.id.toilet);
            life1= (Button) findViewById(R.id.life1);
            life2 = (Button) findViewById(R.id.life2);
            bed= (Button) findViewById(R.id.bed);
            kitchen = (Button) findViewById(R.id.kitchen);
            digital = (Button) findViewById(R.id.digital);
            security=(Button) findViewById(R.id.security) ;
            furniture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            toilet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            life1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            life2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            bed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            kitchen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            digital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });
            security.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity7.class);
                    startActivity(intent);
                }

            });

        }
}
