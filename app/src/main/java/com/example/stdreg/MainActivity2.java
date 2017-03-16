package com.example.stdreg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity2 extends Activity {
	private String[] arraySpinner1;
	private String[] arraySpinner2;
	Button show;
	Spinner s3;
	Spinner s4;
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;

	ArrayList<String> section_array = new ArrayList<String>();
	ArrayList<String> myclass=new ArrayList<String>();
	ArrayAdapter<?> adapter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		show=(Button)findViewById(R.id.act2_showList);
		s3 = (Spinner) findViewById(R.id.act2_class_sp);
		s4 = (Spinner) findViewById(R.id.act2_section_sp);
		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();
		
		ArrayList<String> class_array = new ArrayList<String>();
		class_array = dbHelper.getclassList();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, class_array);
	    s3.setAdapter(adapter1);
	    s3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String val = s3.getSelectedItem().toString();
					if (val.isEmpty() || s3.getSelectedItemId()==0){
						show.setEnabled(false);
						section_array.clear();
						adapter2= new ArrayAdapter(MainActivity2.this,android.R.layout.simple_spinner_item,section_array);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						s4.setAdapter(adapter2);
					
					}else{
						section_array.clear();
						section_array = dbHelper.getsection_by_Class(val);
						adapter2= new ArrayAdapter(MainActivity2.this,android.R.layout.simple_spinner_item,section_array);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						s4.setAdapter(adapter2);
						show.setEnabled(true);
					}
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayListView(s3.getSelectedItem().toString(),s4.getSelectedItem().toString());
			}
		});
	    
		Button x=(Button)findViewById(R.id.act2_btnHome);
		x.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });
	}
	private void displayListView(String x,String y) {
		dbHelper.open();
		 Cursor cursor = dbHelper.fetchAllCountries(x,y);
		  // The desired columns to be bound
		  String[] columns = new String[] {
		    "name",
		    "id",
		    "mail",
		    "ph"
		  };
		 
		  // the XML defined views which the data will be bound to
		  int[] to = new int[] { 
		    R.id.std_name,
		    R.id.std_id,
		    R.id.dept,
		    R.id.ph,
		  };
		  try{
		  dataAdapter = new SimpleCursorAdapter(
				    this, R.layout.std_listview, 
				    cursor, 
				    columns, 
				    to,
				    0);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  final ListView listView = (ListView) findViewById(R.id.listView1);
		  listView.setAdapter(dataAdapter);
		  listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		      }                 
		});

	} 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity2, menu);
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
	
	@Override
	public void onBackPressed() {
	    // do something on back.
		Intent i = new Intent(MainActivity2.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
