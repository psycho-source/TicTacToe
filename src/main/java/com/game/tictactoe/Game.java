package com.game.tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.game.tictactoe.utils.GameAI;
import com.game.tictactoe.utils.TicTacBoard;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private ImageView back;
    private ImageView[] b = new ImageView[9];
    private RelativeLayout parentLayout;
    private TextView p1, p2;
    private Button reset;
    private Boolean p1Turn, isSingle;
    private TicTacBoard board;
    private int turn, cpuRole;
    private GameAI cpu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Total turns made
        turn = 0;

        //If it's player1's turn
        p1Turn = true;

        //If in single player mode
        isSingle = false;

        //Determine which player is CPU
        cpuRole = 0;

        parentLayout = (RelativeLayout) findViewById(R.id.rootView);
        back = (ImageView) findViewById(R.id.back);
        b[0] = (ImageView) findViewById(R.id.b0);
        b[1] = (ImageView) findViewById(R.id.b1);
        b[2] = (ImageView) findViewById(R.id.b2);
        b[3] = (ImageView) findViewById(R.id.b3);
        b[4] = (ImageView) findViewById(R.id.b4);
        b[5] = (ImageView) findViewById(R.id.b5);
        b[6] = (ImageView) findViewById(R.id.b6);
        b[7] = (ImageView) findViewById(R.id.b7);
        b[8] = (ImageView) findViewById(R.id.b8);
        p1 = (TextView) findViewById(R.id.p1);
        p2 = (TextView) findViewById(R.id.p2);
        reset = (Button) findViewById(R.id.reset);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chooseMode();

        board = new TicTacBoard();

        //Cell click listeners
        clickListeners();

        //Reset the game board
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ImageView img : b) {
                    img.setFocusable(true);
                    img.setClickable(true);
                    img.setImageDrawable(getResources().getDrawable(R.drawable.ic_blank));
                    img.setColorFilter(ContextCompat.getColor(Game.this, android.R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                p1Turn = true;
                turn = 0;
                board.reset();
                swapTurn();
            }
        });

    }

    //Override default back behaviour for closing animation
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    private void chooseMode() {
        AlertDialog.Builder b = new AlertDialog.Builder(Game.this);
        b.setTitle("Game Mode");
        String[] choice = {"Player vs CPU", "Player vs Player"};
        b.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        isSingle = true;
                        choiceSeed();
                        break;
                    case 1:
                        p1.setText(getResources().getString(R.string.player1, "X"));
                        p2.setText(getResources().getString(R.string.player2, "O"));

                        //Start the game
                        swapTurn();
                        break;
                }
            }
        });
        Dialog diag = b.create();
        diag.setCancelable(false);
        diag.getWindow().setBackgroundDrawableResource(R.drawable.diag_back);
        diag.show();
    }

    private void choiceSeed() {
        AlertDialog.Builder b = new AlertDialog.Builder(Game.this);
        b.setTitle("Who goes first? (X)");
        String[] choice = {"Player", "CPU"};
        b.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        p1.setText(getResources().getString(R.string.player, "X"));
                        p2.setText(getResources().getString(R.string.cpu, "O"));
                        cpuRole = 1;
                        break;
                    case 1:
                        p1.setText(getResources().getString(R.string.cpu, "X"));
                        p2.setText(getResources().getString(R.string.player, "O"));
                        cpuRole = 0;
                        break;
                }

                //Initialise the AI
                cpu = new GameAI(board, cpuRole);

                //Start the game
                swapTurn();
            }
        });
        Dialog diag = b.create();
        diag.setCancelable(false);
        diag.getWindow().setBackgroundDrawableResource(R.drawable.diag_back);
        diag.show();
    }

    //OnClickListeners for all ImageViews, would be messy in onCreate method
    void clickListeners() {

        b[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[0].setFocusable(false);
                b[0].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(0))
                        b[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(0))
                        b[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[1].setFocusable(false);
                b[1].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(1))
                        b[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(1))
                        b[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[2].setFocusable(false);
                b[2].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(2))
                        b[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(2))
                        b[2].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[3].setFocusable(false);
                b[3].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(3))
                        b[3].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(3))
                        b[3].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[4].setFocusable(false);
                b[4].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(4))
                        b[4].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(4))
                        b[4].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[5].setFocusable(false);
                b[5].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(5))
                        b[5].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(5))
                        b[5].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[6].setFocusable(false);
                b[6].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(6))
                        b[6].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(6))
                        b[6].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[7].setFocusable(false);
                b[7].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(7))
                        b[7].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(7))
                        b[7].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

        b[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Disable after click
                b[8].setFocusable(false);
                b[8].setClickable(false);

                if (p1Turn) {
                    p1Turn = false;
                    if (board.addx(8))
                        b[8].setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));

                } else {
                    p1Turn = true;
                    if (board.addo(8))
                        b[8].setImageDrawable(getResources().getDrawable(R.drawable.ic_circle));
                }
                isWinning();
            }
        });

    }

    void isWinning() {
        turn++;
        if (!board.checkWin((!p1Turn) ? "X" : "O").equalsIgnoreCase("Draw")) {

            //get winning sequence and color the combo
            ArrayList<Integer> winCol = board.getWinningSequence((!p1Turn) ? "X" : "O");
            if (winCol != null) {
                for (Integer i : winCol) {
                    b[i].setColorFilter(ContextCompat.getColor(Game.this, R.color.winColor), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }

            //Display winner
            String winner = board.checkWin((!p1Turn) ? "X" : "O");
            if (isSingle){

                if (winner.equalsIgnoreCase("Player1")) {
                    if (cpuRole == 0) {
                        winner = "CPU";
                    } else {
                        winner = "Player";
                    }
                }

                if (winner.equalsIgnoreCase("Player2")) {
                    if (cpuRole == 1) {
                        winner = "CPU";
                    } else {
                        winner = "Player";
                    }
                }

            }

            Snackbar.make(parentLayout,  winner + " wins the Game!", Snackbar.LENGTH_INDEFINITE).setAction("Play Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reset.performClick();
                }
            }).show();

            return;

        } else if (turn >= 9 && board.checkWin((!p1Turn) ? "X" : "O").equalsIgnoreCase("Draw")) {

            //Display Draw
            Snackbar.make(parentLayout, "Game Draw", Snackbar.LENGTH_INDEFINITE).setAction("Play Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reset.performClick();
                }
            }).show();

            return;
        }

        swapTurn();
    }

    void swapTurn() {

        //If in single player mode
        if (isSingle) {

            if (((cpuRole == 0) && p1Turn) || ((cpuRole == 1) && !p1Turn)) {
                b[cpu.makeMove()].performClick();
            }

            //Indicate user's chance
            if (cpuRole == 0)
                p2.setTextColor(getColor(R.color.colorAccent));
            else
                p1.setTextColor(getColor(R.color.colorAccent));

        } else {

            //If in 2 player mode

            //Notify whose turn it is
            if (p1Turn) {
                p1.setTextColor(getColor(R.color.colorAccent));
                p2.setTextColor(getColor(android.R.color.black));
            } else {
                p1.setTextColor(getColor(android.R.color.black));
                p2.setTextColor(getColor(R.color.colorAccent));
            }
        }
    }

}
