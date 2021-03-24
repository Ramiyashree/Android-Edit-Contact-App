package com.example.project1_cs478;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Boolean.parseBoolean;

public class EditActivity extends Activity {
    public static final String EXTRA_DATA = "EXTRA_DATA"; //Labelling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_contact);

        EditText edit_name = (EditText) findViewById(R.id.edited_name);

        edit_name.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            //OnEditorAction to retrieve data when edit action is performed
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //When DONE is pressed at the soft keyboard
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    final Intent data = new Intent();


                    //Input data processing
                    String name = v.getText().toString().trim();
                    String[] words = name.split(" ");
                    String count_of_words = String.valueOf(words.length);
                    String alpha_name = String.valueOf(name.matches("[a-zA-Z ]+"));

                    Log.i("hello", "in actvi"+name);
                    data.putExtra(EXTRA_DATA, name);

                    //condition for legal name : atleast first and last name & entire name alphabetical
                    if (Integer. parseInt(count_of_words) >= 2 && parseBoolean(alpha_name)) {
                        setResult(RESULT_OK, data);
                    }
                    else if(name == " ") {
                        setResult(RESULT_CANCELED, data);
                    }
                    finish(); //Returning back to previous activity
                    return true;
                }
                return false;
            }
        });
    }
}
