package com.tramtime.prdik.tramtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/*
MainActivity
User enters station and time
station - string
time - positive integer
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int hours, minutes;

        Calendar c = Calendar.getInstance();

        //finding editText objects
        EditText editHours = (EditText) findViewById(R.id.editTextHours);
        EditText editMinutes = (EditText) findViewById(R.id.editTextMinutes);

        //getting current time
        hours = c.get(Calendar.HOUR);
        minutes = c.get(Calendar.MINUTE);

        //setting current time to editTextHours and editTextFields fields
        editHours.setText(String.valueOf(hours));
        editMinutes.setText(String.valueOf(minutes));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //when send button is clicked
    public void OnSendClickButton(View view) {

        //finding editText objects
        EditText editHours = (EditText) findViewById(R.id.editTextHours);
        EditText editMinutes = (EditText) findViewById(R.id.editTextMinutes);
        EditText editStation = (EditText) findViewById(R.id.editTextStation);

        Intent getNameScreenIntent = new Intent(this, Output.class);

        //current time to send to server
        int curhours, curminutes;

        //get hours
        String editt = editHours.getText().toString();
        curhours = Integer.parseInt(editt);

        //get minutes
        editt = editMinutes.getText().toString();
        curminutes = Integer.parseInt(editt);

        //get station
        editt = editStation.getText().toString();

        //if editTextStation is empty
        if(editt.isEmpty()){
            //empty editTextStation
            Toast.makeText(this, "You need to enter station", Toast.LENGTH_SHORT).show();
        }else{
            //if time format is wrong
            if (curhours > 23 || curminutes > 59) {
                //wrong time format
                Toast.makeText(this, "Wrong input time! (HH:MM)", Toast.LENGTH_SHORT).show();
            } else {
                //send data to server (not yet implemented)
                //Toast.makeText(this, String.valueOf(curhours) + ":" + String.valueOf(curminutes), Toast.LENGTH_SHORT).show();
                //view times (start of new activity)
                //startActivity(getNameScreenIntent);
                getNameScreenIntent.putExtra("station", editt);
                getNameScreenIntent.putExtra("hours", String.valueOf(curhours));
                getNameScreenIntent.putExtra("minutes", String.valueOf(curminutes));

                startActivity(getNameScreenIntent);
            }
        }
    }
}
