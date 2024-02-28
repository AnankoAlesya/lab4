package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int comp_num = 0;
    Button guessBtn;
    TextView attemptsLeftTxt;
    TextView hintShowTxt;
    EditText enterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comp_num = GuessNum.rndCompNum();
        guessBtn = (Button) findViewById(R.id.guess_btn);
        attemptsLeftTxt = (TextView) findViewById(R.id.attempts_left_txt);
        hintShowTxt = (TextView) findViewById(R.id.hint_show_txt);
        enterNumber = (EditText) findViewById(R.id.num_user_txt);

        enterNumber.requestFocus();
        showSoftKeyboard(enterNumber);

        Context context = getApplicationContext();

        View.OnClickListener clckLstnr = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int attemptsLeft = Integer.parseInt(attemptsLeftTxt.getText().toString());
                String textNumber = enterNumber.getText().toString();
                if (textNumber.equals("")) return;

                int number = Integer.parseInt(enterNumber.getText().toString());

                if (attemptsLeft > 0)
                {
                    if (number == comp_num)
                    {
                        guessBtn.setText(R.string.guessed_str);
                        guessBtn.setClickable(false);
                        String message = getResources().getString(R.string.wishYou);
                        showMessage(context, message);
                        return;
                    }
                    else if (number < comp_num) hintShowTxt.setText(R.string.hint_more);
                    else hintShowTxt.setText(R.string.hint_less);

                    attemptsLeft--;
                    attemptsLeftTxt.setText(Integer.toString(attemptsLeft));
                }

                if (attemptsLeft == 0)
                {
                    hintShowTxt.setText(R.string.defeat);
                    guessBtn.setClickable(false);
                    String message = getResources().getString(R.string.defeat);
                    showMessage(context, message + " " + comp_num);
                }
            }
        };

        guessBtn.setOnClickListener(clckLstnr);
    }

    public void restart(View view) {
        comp_num = GuessNum.rndCompNum();
        guessBtn.setText(R.string.guess_str);
        attemptsLeftTxt.setText(R.string.attempts_left_str);
        hintShowTxt.setText(R.string.hint_show_str);
        guessBtn.setClickable(true);
        enterNumber.setText("");
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = getSystemService(InputMethodManager.class);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showMessage(Context context, String message)
    {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
        t.show();
    }
}