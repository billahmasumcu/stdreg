package com.example.stdreg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity5 extends Activity {
	protected static final int DATE_DIALOG = 0;
	Button home;
	Spinner act5_s1;
	Spinner act5_s2;
	Spinner act5_s6;
	Button act5_show;
	TextView getdate;
	
	TextView act5_a;
	TextView act5_b;
	TextView act5_c;
	
	private CountriesDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private String DATABASE_NAME = "eduInstitute_Menegment";
	private String SQLITE_TABLE1 = "class_list";
	private SQLiteDatabase mDb;
	
	ArrayList<String> section_array = new ArrayList<String>();
	ArrayList<String> subnm_array = new ArrayList<String>();
	ArrayList<String> myclass=new ArrayList<String>();
	ArrayAdapter<?> adapter2;
	ArrayAdapter<?> adapter3;
	ArrayAdapter<?> adapter4;
	ArrayAdapter<?> adapter5;
	ArrayAdapter<?> adapter6;

	Calendar myCalendar=Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main5);
		act5_s1=(Spinner)findViewById(R.id.act5_class_sp);
		act5_s2=(Spinner)findViewById(R.id.act5_section_sp);
		act5_s6=(Spinner)findViewById(R.id.act5_sub);
		act5_show=(Button)findViewById(R.id.act5_showList);
		
		getdate=(TextView)findViewById(R.id.dateget);
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
				String myformet="MM/dd/yy";
				SimpleDateFormat sdf=new SimpleDateFormat(myformet,Locale.US);
				getdate.setText(sdf.format(myCalendar.getTime()));
			}
		};
		
		getdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(act5_s1.getCount()<1 || act5_s2.getCount()<1 || act5_s6.getCount()<1){
					if(act5_s1.getCount()<1){
						Toast.makeText(MainActivity5.this, "Please Select Class", Toast.LENGTH_LONG).show();
					}if(act5_s2.getCount()<1){
						Toast.makeText(MainActivity5.this, "Please Select Section", Toast.LENGTH_LONG).show();
					}if(act5_s6.getCount()<1){
						Toast.makeText(MainActivity5.this, "Please Select Subject", Toast.LENGTH_LONG).show();
					}
				}else{
					new DatePickerDialog(MainActivity5.this, date, myCalendar
							.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
							myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				}
				
			}
		});
		
		//*************************************Get date month year***************************************//
		
//*************************************Get Class List Start***************************************//
		ArrayList<String> class_array = new ArrayList<String>();
		
		class_array = dbHelper.getclassList();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, class_array);
		act5_s1.setAdapter(adapter1);
//*************************************Get Class List End*****************************************//
		
//*************************************Get Section List by class Start****************************//
		act5_s1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String val = act5_s1.getSelectedItem().toString();
				getdate.setText("");
				if (val.isEmpty() || val=="Select Class"){
					act5_show.setEnabled(false);
					subnm_array.clear();
				    adapter6 = new ArrayAdapter(MainActivity5.this,android.R.layout.simple_spinner_item, subnm_array);
				    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    act5_s6.setAdapter(adapter6);
				    
				    section_array.clear();
					adapter2= new ArrayAdapter(MainActivity5.this,android.R.layout.simple_spinner_item,section_array);
					adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					act5_s2.setAdapter(adapter2);
				}else{
					act5_show.setEnabled(true);
					subnm_array.clear();
					subnm_array = dbHelper.getsubNMList_by_CS(val);
				    adapter6 = new ArrayAdapter(MainActivity5.this,android.R.layout.simple_spinner_item, subnm_array);
				    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    act5_s6.setAdapter(adapter6);
				    
				    section_array.clear();
					section_array = dbHelper.getsection_by_Class(val);
					adapter2= new ArrayAdapter(MainActivity5.this,android.R.layout.simple_spinner_item,section_array);
					adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					act5_s2.setAdapter(adapter2);
					
					if(act5_s6.getChildCount()<0){
						Toast.makeText(MainActivity5.this, "Sorry saki.", Toast.LENGTH_LONG).show();
					}
				}
			}
			

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
//*************************************Get Section List by class End*****************************************//

		
//*************************************Get Student List by class & section Start****************************//
		act5_s2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String getClass=act5_s1.getSelectedItem().toString();
				String getsection=act5_s2.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
//*************************************Get Student List by class & section Start****************************//
		
		
//*************************************Home Button Action start*************************************//		
		home=(Button)findViewById(R.id.act5_btnHome);
		home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	onBackPressed();
                
            }
        });
//*************************************Home Button Action End*************************************//
			
		act5_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchDate=getdate.getText().toString();
				String cl=act5_s1.getSelectedItem().toString();
				String cl_s=act5_s2.getSelectedItem().toString();
				
				if(cl.isEmpty() || cl_s.isEmpty() || searchDate.isEmpty() || act5_s6.getSelectedItem().toString().isEmpty()){
					Toast.makeText(MainActivity5.this, "Please provide all info.", Toast.LENGTH_LONG).show();
				}else{
					if(act5_s6.getSelectedItem().toString().isEmpty()){
						
					}else{
						String sub=act5_s6.getSelectedItem().toString();
						String myDate=searchDate.substring(3, 5);
						String myMonth=searchDate.substring(0, 2);
						String myYear=searchDate.substring(6, 8);
						dbHelper.open();
						ArrayList<String> listInfoget=new ArrayList<String>();
						ArrayList<String> get_std_name=new ArrayList<String>();
						ArrayList<String> get_std_id=new ArrayList<String>();
						ArrayList<String> get_std_att=new ArrayList<String>();
						listInfoget.add(cl);//0
						listInfoget.add(cl_s);//1
						listInfoget.add(myDate);//2
						listInfoget.add(myMonth);//3
						listInfoget.add(myYear);//4
						listInfoget.add(sub);//5
						ArrayList<String> listInfoset=new ArrayList<String>();
					
						listInfoset=dbHelper.getstdAtt(listInfoget);
						
						act5_a=(TextView)findViewById(R.id.totalSTD);
						act5_b=(TextView)findViewById(R.id.pSTD);
						act5_c=(TextView)findViewById(R.id.aSTD);
						
						act5_a.setText(listInfoset.get(0));
						act5_b.setText(listInfoset.get(1));
						act5_c.setText(listInfoset.get(2));
						ListView list_view = (ListView) findViewById(R.id.listView5);
						if(listInfoset.size()>3){
							ArrayList<String> absenceSTDInfo=new ArrayList<String>();
							
							for(int i=3;i<listInfoset.size();i++){
								absenceSTDInfo.add(listInfoset.get(i));
							}
							for(int i=0;i<3;i++){
								int start=i*((absenceSTDInfo.size()/3));
								int end=start+((absenceSTDInfo.size()/3)-1);
								for(int j=start;j<=end;j++){
									if(i==0){
										get_std_name.add(String.valueOf(absenceSTDInfo.get(j)));
									}
									if(i==1){
										get_std_id.add(String.valueOf(absenceSTDInfo.get(j)));
									}
									if(i==2){
										get_std_att.add(String.valueOf(absenceSTDInfo.get(j)));
									}
								}
							  }
							
							AbsenceListAdpotar absencelistadpotar = new AbsenceListAdpotar(getApplicationContext(), get_std_name,get_std_id,get_std_att);
							list_view.setAdapter(absencelistadpotar);
						}else{
							ArrayList< String>  null1=new ArrayList<String>();
							ArrayList< String>  null2=new ArrayList<String>();
							ArrayList< String>  null3=new ArrayList<String>();
							AbsenceListAdpotar absencelistadpotar = new AbsenceListAdpotar(getApplicationContext(), null1,null2,null3);
							list_view.setAdapter(absencelistadpotar);
						}
					}
					
					
				}
				
			}
			
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity5, menu);
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
		Intent i = new Intent(MainActivity5.this, MainActivity.class);
    	finish();  //Kill the activity from which you will go to next activity 
    	startActivity(i);
	}
}
