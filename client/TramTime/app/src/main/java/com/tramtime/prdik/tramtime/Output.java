package com.tramtime.prdik.tramtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Prdik on 15.11.2014.
 */
public class Output extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        Intent intentData= getIntent();
        String selectedStation = intentData.getExtras().getString("station");
        String selectedHours = intentData.getExtras().getString("hours");
        String selectedMinutes = intentData.getExtras().getString("minutes");

        Toast.makeText(this, selectedStation + "  " + selectedHours + ":" + selectedMinutes, Toast.LENGTH_SHORT).show();

        boolean fileExists = true;
        File timetable = new File("timetable.txt");
        try {
            if (!timetable.exists()) {
                //Toast.makeText(this, "creating file", Toast.LENGTH_SHORT).show();
                fileExists = false;
                timetable.createNewFile();
            }
            //FileOutputStream fOut = openFileOutput("timetable.txt", MODE_WORLD_READABLE);
            FileOutputStream oFile = new FileOutputStream(timetable, false);


            //Toast.makeText(this, "writing into file", Toast.LENGTH_SHORT).show();
            String test = new String("Karlovo namesti");
            oFile.write(Integer.parseInt(test));
            oFile.close();

            //FileInputStream fIn = openFileInput(timetable);



        }catch (IOException ioe)
        {ioe.printStackTrace();}

        //times of trams from server (not yet implemented)
    }

    //when back button is clicked
    public void OnBackClickButton(View view) {
        //return to previous screen
        finish();
    }
}
