package com.Toast.party;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;


public class AddEventActivity extends Activity {

    private EditText event_name;
    private EditText event_details;
    private EditText event_cost;
    private EditText event_address;
    private Button submit_button;

    private View.OnClickListener submitClick = new View.OnClickListener() {
        public void onClick(View v) {
            ParseObject new_event = new ParseObject("Event");
            new_event.put("name", event_name.getText().toString());
            new_event.put("description", event_details.getText().toString());
            try {
                new_event.put("cost", Integer.parseInt(event_cost.getText().toString()));
            } catch(Exception e) {
                new_event.put("cost", 0);
            }
            new_event.put("address", event_address.getText().toString());
            new_event.saveInBackground();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.initialize(this, "ZKDmVbrbA3n7jTPMTTHVP5g7eGDQgnqkxgle7mFK", "7RCqOWk1ksC5D4Ekpxw1tmc8xoLiNm1UsyhaCXce");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        event_name = (EditText) findViewById(R.id.event_name);
        event_details = (EditText) findViewById(R.id.event_details);
        event_cost = (EditText) findViewById(R.id.event_cost);
        event_address = (EditText) findViewById(R.id.event_address);
        submit_button = (Button) findViewById(R.id.submit);
        submit_button.setOnClickListener(submitClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_event, menu);
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
}
