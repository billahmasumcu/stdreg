package com.example.stdreg;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	Context context;
	ArrayList<String> studentd_ID_List;
	ArrayList<String> studentd_Name_List;
	LayoutInflater inflter;
	public static ArrayList<String> selectedAnswers;
	
	public CustomAdapter(Context applicationContext, ArrayList<String> studentd_Name_List,ArrayList<String> studentd_ID_List) {
		this.context = context;
		this.studentd_Name_List = studentd_Name_List;
		this.studentd_ID_List = studentd_ID_List;
		// initialize arraylist and add static string for all the questions
		selectedAnswers = new ArrayList<>();
		for (int i = 0; i < studentd_ID_List.size(); i++) {
			selectedAnswers.add("null");
		}
		inflter = (LayoutInflater.from(applicationContext));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return studentd_ID_List.size();
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
	public View getView(final int i, View view, ViewGroup viewGroup) {
		view = inflter.inflate(R.layout.std_att_get_listview, null);
		// get the reference of TextView and Button's
		TextView name = (TextView)view.findViewById(R.id.act3_std_att_name);
		TextView id = (TextView) view.findViewById(R.id.act3_std_att_id);
		RadioButton yes = (RadioButton) view.findViewById(R.id.radio1);
		RadioButton no = (RadioButton) view.findViewById(R.id.radio0);
		// perform setOnCheckedChangeListener event on yes button
		yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// set Yes values in ArrayList if RadioButton is checked
		if (isChecked)
		selectedAnswers.set(i, "1");
		}
		});
		// perform setOnCheckedChangeListener event on no button
		no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// set No values in ArrayList if RadioButton is checked
		if (isChecked)
		selectedAnswers.set(i, "0");

		}
		});
		// set the value in TextView
		name.setText(studentd_Name_List.get(i));
		id.setText(studentd_ID_List.get(i));
		return view;
		}

}
