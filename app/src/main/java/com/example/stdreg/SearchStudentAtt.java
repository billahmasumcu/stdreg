package com.example.stdreg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchStudentAtt extends Activity {
	
	Button 	search_show;
	Button 	search_cal;
	EditText search_getid;
	TextView search_setdate;
	Button searchHome;
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	Calendar myCalendar=Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_student_att);
		search_show=(Button)findViewById(R.id.search_btn_show);
		search_cal=(Button)findViewById(R.id.search_btn_cal);
		search_getid=(EditText)findViewById(R.id.search_txt_stdid);
		search_setdate=(TextView)findViewById(R.id.search_txt_date);
		searchHome=(Button)findViewById(R.id.search_btn_home);
		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();
		//*************************************Get date month year***************************************//
		
		
				final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
						// TODO Auto-generated method stub
						  myCalendar.set(Calendar.YEAR, year);
					        myCalendar.set(Calendar.MONTH, month);
					        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						String myformet="dd/MM/yy";
						SimpleDateFormat sdf=new SimpleDateFormat(myformet,Locale.US);
						search_setdate.setText(sdf.format(myCalendar.getTime()));
					}
				};
				
				search_cal.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new DatePickerDialog(SearchStudentAtt.this, date, myCalendar
								.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
								myCalendar.get(Calendar.DAY_OF_MONTH)).show();
					}
				});
				
	//*************************************Get date month year***************************************//
				search_show.setOnClickListener( new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(search_setdate.getText().toString().isEmpty() || search_getid.getText().toString().isEmpty()){
							if(search_setdate.getText().toString().isEmpty()){
								search_setdate.setError("Please select date.");
							}
							if(search_getid.getText().toString().isEmpty()){
								search_getid.setError("Please provide student id.");
							}
						}else{
							String searchDate=search_setdate.getText().toString();
							String getid=search_getid.getText().toString();
							ArrayList<String> x=new ArrayList<String>();
							ArrayList<String> y=new ArrayList<String>();
							x.clear();
							y.clear();
							x=dbHelper.getAbsentInfobyIDdate(getid, searchDate);
							if(x.isEmpty()){
								search_getid.setError("Invalid student id.");
							}else{
								y=dbHelper.setStdAttlistsuarch();
								StdAttshowbyidAdoptar customAdapter = new StdAttshowbyidAdoptar(getApplicationContext(), x,y);
								ListView lv6=(ListView)findViewById(R.id.listView6);
								lv6.removeAllViewsInLayout();
								lv6.setAdapter(customAdapter);
							}
							
						}
						
					}
				});
				
	//*************************************home button*******************************************//
				searchHome.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
		                // TODO Auto-generated method stub
		            	onBackPressed();
		                
		            }
		        });
	//*************************************Home button******************************************//
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_student_att, menu);
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
		Intent i = new Intent(SearchStudentAtt.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
