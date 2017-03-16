package com.example.stdreg;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StdAttshowbyidAdoptar extends BaseAdapter{
	Context context;
	ArrayList<String> sub_Name_List;
	ArrayList<String> studentd_Att_List;
	LayoutInflater inflter;
	public static ArrayList<String> selectedAnswers;
	
	public StdAttshowbyidAdoptar(Context applicationContext, ArrayList<String> studentd_Att_List,ArrayList<String> sub_Name_List) {
		this.context = context;
		this.sub_Name_List = sub_Name_List;
		this.studentd_Att_List = studentd_Att_List;
		// initialize arraylist and add static string for all the questions
		
		
		selectedAnswers = new ArrayList<>();
		for (int i = 0; i < studentd_Att_List.size(); i++) {
			selectedAnswers.add("null");
		}
		inflter = (LayoutInflater.from(applicationContext));
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sub_Name_List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int i, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflter.inflate(R.layout.absence_student_list, null);
		// get the reference of TextView and Button's
		TextView name = (TextView)convertView.findViewById(R.id.lv5_name);
		TextView id = (TextView) convertView.findViewById(R.id.lv5_id);
		TextView att = (TextView) convertView.findViewById(R.id.lv5_att);
				
		// set the value in TextView
		name.setText(sub_Name_List.get(i));
		id.setText(studentd_Att_List.get(i));
		att.setText("");
		return convertView;
	}

}
