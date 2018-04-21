package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    public String fragmentWord = "";
    private Random random = new Random();
    private Button b1, b2;
    private TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        tv1 = (TextView)findViewById(R.id.ghostText);
        tv2 = (TextView)findViewById(R.id.gameStatus);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentWord = (String) tv1.getText();
                if(dictionary.isWord(fragmentWord)){
                    tv2.setText("Victory! You Won!!");
                }
                else{
                    if(dictionary.getGoodWordStartingWith(fragmentWord) != null){
                        tv2.setText("Oops! You lose. The winner is Computer.");
                        tv1.setText(dictionary.getGoodWordStartingWith(fragmentWord));
                    }
                    else {
                        tv2.setText("Victory! You Won!!");
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart(view);
            }
        });
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
            userTurn = false;
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        fragmentWord = (String) tv1.getText();
        if(fragmentWord.length()>=4 && dictionary.isWord(fragmentWord)){
            tv2.setText("Oops! You lose. The winner is Computer.");
        }
        else{
            String word = dictionary.getGoodWordStartingWith(fragmentWord);
            if(word == null){
                label.setText("Computer challenges you.. \nOops! You lose. The winner is Computer.");
            }
            else{
                label.setText(COMPUTER_TURN);
                char addL = word.charAt(fragmentWord.length());
                fragmentWord = fragmentWord+addL;
                tv1.setText(fragmentWord);
                userTurn = true;
                label.setText(USER_TURN);
            }

        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        char key = (char) event.getUnicodeChar();
        if(!((key>='A' && key<='Z') || (key>='a' && key<='z'))){
            return super.onKeyUp(keyCode, event);
        }

        fragmentWord = (String)tv1.getText();
        fragmentWord = fragmentWord+key;
        tv1.setText(fragmentWord);
        /*if(dictionary.isWord(fragmentWord)){
            tv2.setText("Word complete");
        }*/
        computerTurn();
        return false;
    };
}
