package com.Assignment.ContactsApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends Activity implements OnItemClickListener, OnFocusChangeListener, OnClickListener
{
	int requestCode = 1;
	
	static int sentCode = 20;
	
	ContactsListActivity aa = new ContactsListActivity();
	ContactManager cm = new ContactManager(this);
	
    private EditText firstName;
    private EditText lastName;
    private EditText mobile;
    private EditText phone;
    private EditText hobby;
    private EditText location;
    
    private Button save;
    private Button cancel;
    private Button seeLocation;
    
    public static String getContactLocation;
	
    String[] days = {"Sport"};
    
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        mobile = (EditText) findViewById(R.id.mobile);
        phone = (EditText) findViewById(R.id.phone);
        hobby = (EditText) findViewById(R.id.hobby);
        location = (EditText) findViewById(R.id.location);
        
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        seeLocation = (Button) findViewById(R.id.seeLocation);
        
        
        hobby.setKeyListener(null);
        hobby.setOnFocusChangeListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        seeLocation.setOnClickListener(this);
    }
    
    public int getCode()
    {
    	return sentCode;
    }
    
    public void setCode()
    {
    	sentCode = 20;
    }

    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
    	Intent i = new Intent(this.getApplicationContext(), SportsActivity.class);
    	startActivityForResult(i, 0);
	}
	
	public String getLocation()
	{
		return getContactLocation;
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v == cancel)
		{
			finish();
		}
		
		else if(v == seeLocation)
		{
			sentCode = 25;
			getContactLocation = location.getText().toString();
			Intent i = new Intent(this.getApplicationContext(), MapLocationActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("param1", "AddActivity");
			
	    	i.putExtras(bundle);
	    	startActivity(i);
		}
		
		else if(v == save)
		{
			String f = getString(firstName);
			if(firstName.getText().length() == 0)
			{
				Toast.makeText(this, "Contact must have first name", Toast.LENGTH_LONG).show();
			}
			else
			{
				String l = getString(lastName);
				String m = getString(mobile);
				String p = getString(phone);
				String h = getString(hobby);
				String lo = getString(location);
				cm.open();
				cm.insertPerson(f, l, m, p, h, lo);
				cm.close();
				Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	} 
	
	private String getString(EditText string)
	{
		String result = string.getText().toString();
		return result;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if(resultCode == 1)
        {
        	Bundle bundle = data.getExtras();
    		String[] param1 = {bundle.getString("param1")};
    		hobby.setText(param1[0]);
        }
    }
	
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if(hasFocus == true)
		{
			Intent i = new Intent(this.getApplicationContext(), SportsActivity.class);
	    	startActivityForResult(i, 0);
		}
	}
}
