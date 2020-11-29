package com.example.conect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: Red, 1: yellow 2: Empty

    //keeps track of the player
    int activePlayer = 0;

    //saves the game state
    int []gameState = {2,2,2,2,2,2,2,2,2};

    //Defines the positions in which a player can win
    int winningPositions[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {2,4,6}, {0,4,8}};

    //Tracks if the game is active or not
    boolean gameActive = true;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        //keeps track of which player played on what spot (saves the game state)
        int tapCounter = Integer.parseInt(counter.getTag().toString());

        /*checks if that current location has being played or not before further operations
         *are carried out. Also checks if game play is still on.
         */
        if(gameState[tapCounter] == 2 && gameActive){

            //remove imageView from screen
            counter.setTranslationY(-1500);

            //tracks game state(spots played on)
            gameState[tapCounter] = activePlayer;

            //keeps track of which player's turn it is.
            if(activePlayer == 0){
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }else{
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).setDuration(500);

            //checking if any player as won
            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){

                    //disables players from being able to play further
                    gameActive = false;

                    //create signifying the winner
                    String winner = "";
                    if(activePlayer == 0){
                        winner = "yellow";
                    }else{
                        winner = "red";
                    }

                    String text = winner+" has won!";

                    TextView myTextView = (TextView) findViewById(R.id.textView);
                    Button button = (Button) findViewById(R.id.playAgainButton);

                    myTextView.setText(text);
                    myTextView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                }
            }
        }else{
            if(!gameActive){
                Toast.makeText(this, "Game is already over!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Spot played already!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void playAgain(View view){
        TextView myTextView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.playAgainButton);

        myTextView.setText("");
        myTextView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        GridLayout layout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<layout.getChildCount(); i++){
            ImageView imageView = (ImageView) layout.getChildAt(i);
            imageView.setImageDrawable(null);
        }

        gameActive = true;
        activePlayer = 0;
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
