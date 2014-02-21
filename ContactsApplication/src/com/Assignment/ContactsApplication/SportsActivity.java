package com.Assignment.ContactsApplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SportsActivity extends ListActivity implements OnItemClickListener
{
	private ListView list;
	
    String[] sports = {"Rugby", "Soccer", "Gaelic Football", "Hurling"};
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.sport, R.id.sportText,sports));
        
        list = getListView();
        list.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
	{
		Bundle bundle = new Bundle();
		String item = list.getItemAtPosition(position).toString();
		System.out.println(item);
		bundle.putString("param1", item);
    	
		Intent i = new Intent(this, ContactsListActivity.class);
		
		i.putExtras(bundle);
    	setResult(1, i);
    	finish();
	}
}