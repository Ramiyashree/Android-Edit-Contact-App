package com.example.project1_cs478;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EXAMPLE = 1;

    // Fields to be bound to GUI widgets
    protected Button editButton;
    protected Button viewButton ;
    public String okayVariable;
    public String name;

    private int resultCode = RESULT_CANCELED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Bind the interface elements to the corresponding fields
        editButton = (Button) findViewById(R.id.editContactButton);
        viewButton = (Button) findViewById(R.id.viewContactButton);

        //Setting onClickListener
        editButton.setOnClickListener(v -> switchToEditContact());
        viewButton.setOnClickListener(v -> switchToContact());
    }

    //Function to edit Contact
    private void switchToEditContact() {
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        startActivityForResult(i, REQUEST_CODE_EXAMPLE);
    }

    // Function to navigate to the Contact app
    private void switchToContact() {
        //If the Result_Code is OK
        if(okayVariable == "OK") {
            //opening contacts edit page with pre filled user name
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                startActivity(intent);
                okayVariable = "NULL";
        }
        //If the Result_Code is CANCEL Toast message is dispalyed
        else if(okayVariable == "CANCEL")
        {
            Log.i("MainActivity","in cancel");
            Toast.makeText(MainActivity.this, "Incorrect Name" + " " +name,LENGTH_LONG).show();
            okayVariable = "NULL";
        }
        else
        {
            Toast.makeText(MainActivity.this, "No Name Entered",LENGTH_LONG).show();
            okayVariable = "NULL";
        }
    }

    private boolean isValidUserName() {
        // Checks if the username is valid. Returns boolean.
        //
        // FullName is fetched from EditName Activity and assigned to username
        // only if it passes the validation in the EditName Activity.
        return !(name==null);
    }

    private void updateWelcomeText() {
        // Updates Welcome Text in the MainActivity
        // Customized Welcome text is displayed if valid username is available.
        TextView welcomeText = (TextView)findViewById(R.id.textView1);
        if(isValidUserName()) {
            welcomeText.setText("Welcome!!" +  name);
        } else {
            welcomeText.setText("Welcome");
        }
    }

    public void resetValues() {
        // This method is called when a new Contact is created, and the values need to be reset.
        name = null;
        updateWelcomeText();
        resultCode = RESULT_CANCELED; // result updated here
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //NULL VALUE
        if(data == null) {
            resetValues();
            return; //end here
        }

        // First we need to check if the requestCode matches the one we used.
        if(requestCode == REQUEST_CODE_EXAMPLE) {

            // Get the result from the returned Intent
            String result = data.getStringExtra(EditActivity.EXTRA_DATA);
            Log.i("hello", "hie"+result);
            name = result; // Global variable setup of the name fetched
            this.resultCode = resultCode;
            // Global variable setup to use it in Onclick function
            if(resultCode == RESULT_OK) {
                    okayVariable = "OK";
                }
                else if(resultCode == RESULT_CANCELED) {
                    Log.i("hello","in if cancel");
                    okayVariable = "CANCEL";
                }
           updateWelcomeText();
            }
        }

    }
