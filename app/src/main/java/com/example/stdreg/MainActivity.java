package com.example.stdreg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	Button a;
	Button b;
	Button c;
	Button d;
	Button e;
	//Button f;
	Button g;
	//Button h;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = (Button) findViewById(R.id.list);
        b = (Button) findViewById(R.id.reg);
        c = (Button) findViewById(R.id.cl_setting);
        d = (Button) findViewById(R.id.att);
        e = (Button) findViewById(R.id.attR);
        //f = (Button) findViewById(R.id.exit);
        g = (Button) findViewById(R.id.subList);
        //h = (Button) findViewById(R.id.stdattbyid);
        
        
        a.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, MainActivity2.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, MainActivity1.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        c.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, MainActivity4.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        d.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, AttendenceRecive.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        e.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, MainActivity5.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        /* f.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, MainActivity6.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });*/
        g.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, ClassSubject_.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
    }
        /* h.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i = new Intent(MainActivity.this, SearchStudentAtt.class);
            	finish();  //Kill the activity from which you will go to next activity 
            	startActivity(i);
                
            }
        });
        }*/

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
}
