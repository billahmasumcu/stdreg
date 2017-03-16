package com.example.stdreg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity1 extends Activity {
	private String[] arraySpinner1;
	private String[] arraySpinner2;
	EditText a;
	Spinner s1;
	Spinner s2;
	EditText f;
	EditText g;
	Button save;
	private CountriesDbAdapter dbHelper;
	private String DATABASE_NAME = "eduInstitute_Menegment";
	private String SQLITE_TABLE1 = "class_list";
	private SQLiteDatabase mDb;
	ArrayList<String> section_array = new ArrayList<String>();
	ArrayList<String> myclass=new ArrayList<String>();
	ArrayAdapter<?> adapter2;
	;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		
		s1 = (Spinner) findViewById(R.id.spi_class);
		s2 = (Spinner) findViewById(R.id.spi_section);
		a=(EditText)findViewById(R.id.txt_std_name);
        f=(EditText)findViewById(R.id.txt_id);
        g=(EditText)findViewById(R.id.txt_mail);
        dbHelper = new CountriesDbAdapter(this);
        
        dbHelper.open();
        
		ArrayList<String> class_array = new ArrayList<String>();
		class_array = dbHelper.getclassList();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, class_array);
	    s1.setAdapter(adapter1);
	    s1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
					String val = s1.getSelectedItem().toString();
					if (val.isEmpty() || s1.getSelectedItemId()==0){
						save.setEnabled(false);
						section_array.clear();
						adapter2= new ArrayAdapter(MainActivity1.this,android.R.layout.simple_spinner_item,section_array);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						s2.setAdapter(adapter2);
						Toast.makeText(MainActivity1.this, "Please Select Student Class", Toast.LENGTH_LONG).show();
						
					}else{
						
						section_array.clear();
						
						section_array = dbHelper.getsection_by_Class(val);
						adapter2= new ArrayAdapter(MainActivity1.this,android.R.layout.simple_spinner_item,section_array);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						s2.setAdapter(adapter2);
						save.setEnabled(true);
					}
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    save=(Button)findViewById(R.id.btnSave);
	    save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name=a.getText().toString();
				String stdclass=s1.getSelectedItem().toString();
				String stdSection=s2.getSelectedItem().toString();
				String stdid=f.getText().toString();
				String stdmail=g.getText().toString();
				if(name.isEmpty() || name.length()<3|| !(name.matches("[a-zA-Z.  ]+")) ||
					stdclass.isEmpty() || stdSection.isEmpty() || 
					!(android.util.Patterns.PHONE.matcher(stdid).matches()) || stdmail.length()<12 ||
					!(android.util.Patterns.EMAIL_ADDRESS.matcher(stdmail).matches())|| stdmail.length()<12){
					if(name.isEmpty() || !(name.matches("[a-zA-Z.  ]+")) || name.length()<3){
						a.setError("Student name Can't be blank.");
					}
					if(!(android.util.Patterns.PHONE.matcher(stdid).matches()) || stdid.isEmpty() || !(stdid.length()==11) ){
						f.setError("Invalid Phone Number.");
					}if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(stdmail).matches()) || stdmail.length()<12){
						g.setError("Invalid Email Address.");
					}
				}else{
					dbHelper.open();
					// TODO Auto-generated method stub
					int RES = dbHelper.studentRegistration(name,stdclass,stdSection,stdid,stdmail);

					if(RES==4){
						
						Toast.makeText(MainActivity1.this, "Inserted successfully", Toast.LENGTH_LONG).show();
						a.setText("");
						s1.setSelection(0);
						s2.setSelection(0);
						f.setText("");
						g.setText("");
					}else{
						if(RES==0){
							Toast.makeText(MainActivity1.this, "sorry", Toast.LENGTH_LONG).show();
						}if(RES==1){
							g.setError("This mail Address already used.");
						}if(RES==2){
							f.setError("This Phone number already used.");
						}if(RES==3){
							Toast.makeText(MainActivity1.this, "Please restart System", Toast.LENGTH_LONG).show();
						}
						
						
					}
				}
				
			}
		});
////////////////////////////////////////////////////////*********Home Button*********//////////////////////////////////
        Button home=(Button)findViewById(R.id.act1_btnHome);
        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });
////////////////////////////////////////////////////////*********Home Button*********//////////////////////////////////
        
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity1, menu);
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
		Intent i = new Intent(MainActivity1.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
