package com.game.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

public class MainActivity extends AppCompatActivity {

    //Create variables for views
    private CardView game;
    private ImageView gameIc;
    private TextView gameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise Views
        game = (CardView) findViewById(R.id.game);
        gameIc = (ImageView) findViewById(R.id.gameImg);
        gameTxt = (TextView) findViewById(R.id.gameText);

        //Open Game
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Game.class);

                //Create pairs for shared element transition
                Pair<View, String> bg = Pair.create((View) game, "bgGame");
                Pair<View, String> ic = Pair.create((View) gameIc, "icGame");
                Pair<View, String> tx = Pair.create((View) gameTxt, "txtGame");

                //pass animations and start activity
                ActivityOptionsCompat opt = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, bg, ic, tx);
                startActivity(intent, opt.toBundle());
            }
        });

    }
}
