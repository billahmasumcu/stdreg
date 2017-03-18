package com.example.stdreg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity4 extends Activity {
	EditText a;
	EditText b;
	Button x;
	Button y;
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ArrayAdapter<String> dataAdapter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main4);
		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();
		displayListView();
		
		a=(EditText)findViewById(R.id.act4_class);
		b=(EditText)findViewById(R.id.act4_section);
		
		//******************Home Page Location******************//
		Button x=(Button)findViewById(R.id.act4_btnHome);
		x.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });
		//******************Home Page Location******************//
		
		Button y=(Button)findViewById(R.id.class_regBTN);
		y.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String getclass=a.getText().toString();
				String getsec=b.getText().toString();
				String mygetclass;
				if(getclass.isEmpty() || getclass.length()>2 || getsec.isEmpty()){
					if(getclass.isEmpty() || getclass.length()>2 || getclass.matches("[0-9]+")){
						a.setError("Invalid year.");
					}if(getsec.isEmpty() || getsec.matches("[a-zA-Z0-9]+")){
						b.setError("Invalid semester.");
					}
				}else{
					if(getclass.length()<2){
						mygetclass="0"+getclass;
					}else{
						mygetclass=getclass;
					}
					int RES = dbHelper.classInsart(mygetclass, getsec);
					if(RES==1){
						Toast.makeText(MainActivity4.this, "New year and semester successfully Inserted", Toast.LENGTH_LONG).show();
					}else if(RES==0){
						Toast.makeText(MainActivity4.this, "Please try again.", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(MainActivity4.this, "This Year and semester already exists", Toast.LENGTH_LONG).show();
					}
					//dataAdapter.notifyDataSetChanged();
					a.setText("");
					b.setText("");
					displayListView();
				}
				
			}
		});
	}
	
	private void displayListView() {
		 Cursor cursor = dbHelper.fetchAllClass();
		 
		  // The desired columns to be bound
		  String[] columns = new String[] {
		    "Year",
		    "Semester"
		  };
		 
		  // the XML defined views which the data will be bound to
		  int[] to = new int[] { 
		    R.id.txt_classListShow,
		    R.id.txt_sectionListShow,
		  };
		  try{
		  dataAdapter = new SimpleCursorAdapter(
				    this, R.layout.class_listview, 
				    cursor, 
				    columns, 
				    to,
				    0);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  
		  ListView listView = (ListView) findViewById(R.id.listView2);
		  listView.removeAllViewsInLayout();
		  listView.setAdapter(dataAdapter);
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity4, menu);
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
		Intent i = new Intent(MainActivity4.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
