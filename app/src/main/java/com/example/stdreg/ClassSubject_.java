package com.example.stdreg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ClassSubject_ extends Activity {
	
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	
	Spinner myclass;
	EditText subName;
	EditText subCode;
	Button home;
	
	public ListView list_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_subject_);
		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();

//****************************************Home Button Diclaration**********************//
		home = (Button) findViewById(R.id.class_sub_home);
		home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });
//****************************************Home Button Diclaration**********************//
		
		myclass=(Spinner)findViewById(R.id.class_list);
		subName=(EditText)findViewById(R.id.class_sub_nm);
		subCode=(EditText)findViewById(R.id.class_sub_code);


//****************************************Calss list Show*****************************//
		ArrayList<String> class_array = new ArrayList<String>();
		class_array = dbHelper.getclassList();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, class_array);
		myclass.setAdapter(adapter1);
//****************************************Calss list Show*****************************//	


//****************************************Save Subject list*****************************//
		Button save=(Button)findViewById(R.id.class_sub_save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String x=myclass.getSelectedItem().toString();
				String y=subName.getText().toString();
				String z=subCode.getText().toString();
				if(myclass.getSelectedItemId()==0 || y.isEmpty() || !(y.matches("[a-zA-Z 1-5]+"))|| z.isEmpty() || !(z.matches("[a-zA-Z0-9]+"))){
					if(myclass.getSelectedItemId()==0){
						
					}
					if(y.isEmpty() || !(y.matches("[a-zA-Z0-9]+"))){
						subName.setError("Invalid Subject Name.");
					}
					if(z.isEmpty() || !(z.matches("[a-zA-Z0-9]+"))){
						subCode.setError("Invalid Subject Code.");
					}
				}else{
					dbHelper.open();
					ArrayList<String> getInfoofClasssubject=new ArrayList<String>();
					getInfoofClasssubject.add(x);
					getInfoofClasssubject.add(y);
					getInfoofClasssubject.add(z);
					int confarm=dbHelper.newSubjectInsert(getInfoofClasssubject);
					subName.setText("");
					subCode.setText("");
					subListShow();
					if(confarm==1){
						Toast.makeText(ClassSubject_.this, "Inserted successfully", Toast.LENGTH_LONG).show();
					}else if(confarm==2){
						Toast.makeText(ClassSubject_.this, "Same Subject Name Can't  be same", Toast.LENGTH_LONG).show();
					}else if(confarm==3){
						Toast.makeText(ClassSubject_.this, "Same Subject ID Can't  be same", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ClassSubject_.this, "Please try again or restart System.", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
//****************************************Save Subject list*****************************//
		
		
		myclass.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				subListShow();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
		});
	}
	
	public void subListShow(){
		String getClass=myclass.getSelectedItem().toString();
		if(!(getClass.isEmpty())){
			dbHelper.open();
			ArrayList<String> fatchAll_subNID=new ArrayList<String>();
			ArrayList<String> subN=new ArrayList<String>();
			ArrayList<String> subID=new ArrayList<String>();
			list_view = (ListView) findViewById(R.id.listView_class_Sub_List);
			fatchAll_subNID=dbHelper.getsubList_by_CS(getClass);
			if(!(fatchAll_subNID.isEmpty())){
				for(int i=0;i<2;i++){
					int start=i*((fatchAll_subNID.size()/2));
					int end=start+((fatchAll_subNID.size()/2)-1);
					for(int j=start;j<=end;j++){
						if(i==0){
							subN.add(String.valueOf(fatchAll_subNID.get(j)));
						}
						if(i==1){
							subID.add(String.valueOf(fatchAll_subNID.get(j)));
						}
					}
				  }
				}else{
				Toast.makeText(ClassSubject_.this, "Please Restart your System", Toast.LENGTH_LONG).show();
			}
			
				SublistAdapter sublistadapter = new SublistAdapter(getApplicationContext(), subN,subID);
				list_view.removeAllViewsInLayout();
				list_view.setAdapter(sublistadapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class_subject_, menu);
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
		Intent i = new Intent(ClassSubject_.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
