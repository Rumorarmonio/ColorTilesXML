package com.example.colortiles;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import android.os.CountDownTimer;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkColor;
    int brightColor;
    View[][] tiles = new View[4][4];
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //скрывает панель уведомлений на активности

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Resources r = getResources();
        darkColor = r.getColor(R.color.dark);
        brightColor = r.getColor(R.color.bright);

        tiles[0][0] = findViewById(R.id.t00);
        tiles[0][1] = findViewById(R.id.t01);
        tiles[0][2] = findViewById(R.id.t02);
        tiles[0][3] = findViewById(R.id.t03);

        tiles[1][0] = findViewById(R.id.t10);
        tiles[1][1] = findViewById(R.id.t11);
        tiles[1][2] = findViewById(R.id.t12);
        tiles[1][3] = findViewById(R.id.t13);

        tiles[2][0] = findViewById(R.id.t20);
        tiles[2][1] = findViewById(R.id.t21);
        tiles[2][2] = findViewById(R.id.t22);
        tiles[2][3] = findViewById(R.id.t23);

        tiles[3][0] = findViewById(R.id.t30);
        tiles[3][1] = findViewById(R.id.t31);
        tiles[3][2] = findViewById(R.id.t32);
        tiles[3][3] = findViewById(R.id.t33);

        blendColors();
    }

    public void blendColors() {
        for (int i = 0; i <= 3; i++)
            for (int j = 0; j <= 3; j++)
                tiles[i][j].setBackgroundColor(new Random().nextBoolean() ? darkColor : brightColor);
    }

    public void changeColor(View v) {
        ColorDrawable d = (ColorDrawable) v.getBackground();
        v.setBackgroundColor(d.getColor() == brightColor ? darkColor : brightColor);
    }

    public void changeColorXY(int x, int y) {
        for (int i = 0; i <= 3; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }
    }

    public void check() {
        int counter = 0;
        for (int i = 0; i <= 3; i++)
            for (int j = 0; j <= 3; j++) {
                ColorDrawable drawable = (ColorDrawable) tiles[i][j].getBackground();
                if (drawable.getColor() == brightColor)
                    counter++;
            }

        if (counter == 16 || counter == 0) {
            score++;
            Toast.makeText(this, "Congratulations! " + score, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        String tag = v.getTag().toString(); // получение тэга на кнопке

        int x, y; // координаты тайла и строки вида "00"

        y = Integer.parseInt(tag) % 10;
        x = Integer.parseInt(tag) / 10;

//      изменение цвета на самом тайле и всех тайлах с таким же x и таким же y
        changeColor(v);
        changeColorXY(x, y);
        check();
    }

    public void madness() {
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i <= 300; i++) {
                    blendColors();
                    check();
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.randomize) {
            blendColors();
            return true;
        }

        if (id == R.id.madness) {
            madness();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}