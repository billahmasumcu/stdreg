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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AttendenceRecive extends Activity {
	
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private String DATABASE_NAME = "eduInstitute_Menegment";
	private String SQLITE_TABLE1 = "class_list";
	private SQLiteDatabase mDb;
	public static ArrayList<String> selectedAnswers;
	public RadioButton yes;
	public RadioButton no;
	public ListView list_view;

	//private ListView listView;
	ArrayList<String> section_array = new ArrayList<String>();
	ArrayList<String> myclass=new ArrayList<String>();
	ArrayAdapter<?> adapter2;
	ArrayAdapter<?> adapter3;
	ArrayAdapter<?> adapter4;
	ArrayList<String> fatchAll_stdNameID=new ArrayList<String>();
	ArrayList<String> get_std_name=new ArrayList<String>();
	ArrayList<String> get_std_id=new ArrayList<String>();
	
	Button x;
	Spinner act3_s1;
	Spinner act3_s2;
	Spinner act3_s3;
	Spinner act3_s4;
	Button act3_b2;
	Button save_btn;
	private String[] arraySpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main3);
		
		dbHelper = new CountriesDbAdapter(this);
		dbHelper.open();
		
		act3_s1=(Spinner)findViewById(R.id.act3_class_sp);
		act3_s2=(Spinner)findViewById(R.id.act3_section_sp);
		act3_s3=(Spinner)findViewById(R.id.act3_sub_name_sp);
		act3_s4=(Spinner)findViewById(R.id.act3_sub_code_sp);
		act3_s4.setEnabled(false);
		yes = (RadioButton)findViewById(R.id.radio1);
		no = (RadioButton)findViewById(R.id.radio0);
		
		act3_s1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String val = act3_s1.getSelectedItem().toString();
					if (val.isEmpty() || act3_s1.getSelectedItemId()==0){
						
						act3_b2.setEnabled(false);
						
						section_array.clear();
						adapter2= new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item,section_array);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						act3_s2.setAdapter(adapter2);
						
						ArrayList<String> null1 = new ArrayList<String>();
					    adapter3 = new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item, null1);
					    act3_s3.setAdapter(adapter3);

					    ArrayList<String> null2 = new ArrayList<String>();
					    adapter4 = new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item, null2);
					    act3_s4.setAdapter(adapter4);
					}else{
					section_array.clear();
					section_array = dbHelper.getsection_by_Class(val);
					adapter2= new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item,section_array);
					adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					act3_s2.setAdapter(adapter2);
					
					//***********************************************Subject name & ID list get mothord****************************************//
				    ArrayList<String> subnmid_array = new ArrayList<String>();
				    ArrayList<String> subnm_array = new ArrayList<String>();
				    ArrayList<String> subid_array = new ArrayList<String>();
				    
				    subnmid_array = dbHelper.getsubList_by_CS(val);
					    for(int i=0;i<2;i++){
							int start=i*((subnmid_array.size()/2));
							int end=start+((subnmid_array.size()/2)-1);
							for(int j=start;j<=end;j++){
								if(i==0){
									subnm_array.add(String.valueOf(subnmid_array.get(j)));
								}
								if(i==1){
									subid_array.add(String.valueOf(subnmid_array.get(j)));
								}
							}
						  }
				    adapter3 = new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item, subnm_array);
				    act3_s3.setAdapter(adapter3);
				    adapter4 = new ArrayAdapter(AttendenceRecive.this,android.R.layout.simple_spinner_item, subid_array);
				    act3_s4.setAdapter(adapter4);
				    
				    act3_b2.setEnabled(true);
			//***********************************************Subject name & ID list get mothord****************************************//
					}
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		ArrayList<String> class_array = new ArrayList<String>();
		class_array = dbHelper.getclassList();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, class_array);
	    act3_s1.setAdapter(adapter1);

	    act3_s3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int x=Integer.valueOf((int) act3_s3.getSelectedItemId());
				act3_s4.setSelection(x);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    act3_b2=(Button)findViewById(R.id.act3_showList);
	    act3_b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String a=act3_s1.getSelectedItem().toString();
				String b=act3_s2.getSelectedItem().toString();
				String c=act3_s3.getSelectedItem().toString();
				String d=act3_s4.getSelectedItem().toString();
				if(a.isEmpty()||b.isEmpty()||c.isEmpty()||d.isEmpty()){
					Toast.makeText(AttendenceRecive.this, "Please Provide all info.", Toast.LENGTH_LONG).show();
				}else{
					dbHelper.open();
					
					selectedAnswers = new ArrayList<>();
					ArrayList<String> fatchAll_stdNameID=new ArrayList<String>();
					
					
					list_view = (ListView) findViewById(R.id.listView3);
					
					fatchAll_stdNameID=dbHelper.getstdList_by_CS(act3_s1.getSelectedItem().toString(),act3_s2.getSelectedItem().toString());
					if(!(fatchAll_stdNameID.isEmpty())){
						get_std_name.clear();
						get_std_id.clear();
						for(int i=0;i<2;i++){
							int start=i*((fatchAll_stdNameID.size()/2));
							int end=start+((fatchAll_stdNameID.size()/2)-1);
							for(int j=start;j<=end;j++){
								if(i==0){
									get_std_name.add(String.valueOf(fatchAll_stdNameID.get(j)));
								}
								if(i==1){
									get_std_id.add(String.valueOf(fatchAll_stdNameID.get(j)));
								}
							}
						  }
						}else{
						Toast.makeText(AttendenceRecive.this, "Please Restart your Syatem", Toast.LENGTH_LONG).show();
					}
							
					CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), get_std_name,get_std_id);
					list_view.setAdapter(customAdapter);
				}
				
			}
		});
	    
		x=(Button)findViewById(R.id.act3_btnHome);
		x.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });

		save_btn=(Button)findViewById(R.id.act3_sav);
		save_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			ArrayList<String> att=CustomAdapter.selectedAnswers;
			list_view = (ListView) findViewById(R.id.listView3);
				if(list_view.getCount()<=0){
					
				}else{
					if(att.contains("null")){
						Toast.makeText(AttendenceRecive.this, "Please take all students Attendence.", Toast.LENGTH_LONG).show();
					}else{
							ArrayList<String> comm=new ArrayList<String>();
							comm.add(act3_s1.getSelectedItem().toString());
							comm.add(act3_s2.getSelectedItem().toString());
							comm.add(act3_s3.getSelectedItem().toString());
							comm.add(act3_s4.getSelectedItem().toString());
							
							
							int getConfarm=dbHelper.studentAtt_insart(get_std_name,get_std_id,att,comm);
								if(getConfarm==1){
									Toast.makeText(AttendenceRecive.this, 
									"thanks to giving "+att.size()+" number of student Attendence of class "+comm.get(0)+" of section "+comm.get(1), Toast.LENGTH_LONG).show();
								}else if(getConfarm==2){
									Toast.makeText(AttendenceRecive.this, 
											"May be some student's attendence is missing please retack all student's attendence", Toast.LENGTH_LONG).show();
								}else if(getConfarm==3){
									Toast.makeText(AttendenceRecive.this, 
											"Class "+comm.get(0)+" Section "+comm.get(1)+" attendence alridy have taken", Toast.LENGTH_LONG).show();
								}else{
									Toast.makeText(AttendenceRecive.this, "Please Restart your Syatem", Toast.LENGTH_LONG).show();
								}
							}
				}
			
				}
			});
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity3, menu);
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
		Intent i = new Intent(AttendenceRecive.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
