package com.example.stdreg;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class SublistAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<String> sub_name_arr;
	ArrayList<String> sub_id_arr;
	LayoutInflater inflter;
	public static ArrayList<String> selectedAnswers;
	
	public SublistAdapter(Context applicationContext, ArrayList<String> sub_name_arr,ArrayList<String> sub_id_arr) {
		this.context = context;
		this.sub_name_arr = sub_name_arr;
		this.sub_id_arr = sub_id_arr;
		// initialize arraylist and add static string for all the questions
		selectedAnswers = new ArrayList<>();
		for (int i = 0; i < sub_id_arr.size(); i++) {
			selectedAnswers.add("null");
		}
		inflter = (LayoutInflater.from(applicationContext));
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sub_id_arr.size();
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
	public View getView(final int i, View view, ViewGroup parent) {
		view = inflter.inflate(R.layout.clsub_listview, null);
		// TODO Auto-generated method stub
		TextView x = (TextView)view.findViewById(R.id.sub_name);
		TextView y = (TextView) view.findViewById(R.id.sub_id);
		x.setText(sub_name_arr.get(i));
		y.setText(sub_id_arr.get(i));
		return view;
	}

}
