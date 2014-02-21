package com.Assignment.ContactsApplication;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactsListActivity extends ListActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener{ 


		private ArrayList<Integer> getSingleContact;
		private ArrayList<String> getContactInfo;
		static String contactName;
		static String singleContactName;
		static String[] tokens;


	    private Button createContact;
	    private ListView list;
	    
	    private long idNumber;
	    long deletePersonId;
	    
	    static ListAdapter adapter;
	    
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contactpage);
	        
	        createContact = (Button) findViewById(R.id.createContactButton);
	        
	        createContact.setOnClickListener(this);
	        
	        getUpdatedContacts();
	        
	        list = getListView();
	        list.setOnItemClickListener(this);
	        list.setOnItemLongClickListener(this);

	        registerForContextMenu(list); 
	    }
	    
	    
	    public void getUpdatedContacts()
	    {
	    	ContactManager cm = new ContactManager(getBaseContext());
	    	  
	        cm.open();
	        Cursor contacts = cm.getAllContacts();
	        
	        String[] columns = cm.getColumns();
	        
	        int[] destinations = new int [] {R.id.fN, R.id.lN, R.id.phone, R.id.mobile, R.id.hobby, R.id.location};
	        
	        adapter = new SimpleCursorAdapter(this, R.layout.contacts, contacts, columns, destinations);
	        
	        setListAdapter(adapter);
	    }
	    
	    
	    public boolean onContextItemSelected(MenuItem item) 
	    {
	    	if(item.getTitle() =="Delete")
	    	{
	    		item.getItemId();
	            
	            ContactManager cm = new ContactManager(getBaseContext());
	            cm.open();
	        	cm.deletePerson(deletePersonId);
	        	getUpdatedContacts();
	        	cm.close();
	    	} 
	    	else if(item.getTitle() =="Call")
	    	{
	    		item.getItemId();
	            
	            ContactManager cm = new ContactManager(getBaseContext());
	            cm.open();
	        	Cursor phone = cm.getPerson(idNumber);
	        	cm.close();
	        	String number = phone.getString(3).toString();
	        	
	        	if(number.equals(""))
	        	{
	        		Toast.makeText(this, "Contact does not have a number", Toast.LENGTH_LONG).show();
	        	}
	        	else
	        	{
		            Intent callIntent = new Intent(Intent.ACTION_CALL); 
		            callIntent.setData(Uri.parse("tel:"+number));
		            startActivity(callIntent);
	        	}
	    	} 
	    	else 
	    	{
	    		return false;
	    	}
	    	return true;
	    }
	    
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	    {
	    	super.onCreateContextMenu(menu, v, menuInfo);
	    	menu.setHeaderTitle("Context Menu");
	    	menu.add(0, v.getId(), 0, "Delete");
	    	menu.add(0, v.getId(), 0, "Call");
	    }

	    
	    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
	    {
	    	Cursor c = (Cursor) this.getListAdapter().getItem(position);
	        contactName = c.getString(1);
	           
	        getSingleContactInfo(c);
	        getContactId(c);
	        
	        String personId = Integer.toString(getSingleContact.get(0));
	        
	        singleContactName = personId + ";" + 
	        					getContactInfo.get(0) + ";" + 
	        					getContactInfo.get(1) + ";" + 
	        					getContactInfo.get(2) + ";" + 
	        					getContactInfo.get(3) + ";" + 
	        					getContactInfo.get(4) + ";" + 
	        					getContactInfo.get(5);

	        String delims = ";";
	        tokens = singleContactName.split(delims, -1);
	        
			
	        Intent i = new Intent(this.getApplicationContext(), EditContactActivity.class);

	    	startActivityForResult(i, 0);
		}
	    
	    public String[] getContactDetails()
	    {
	    	return tokens;
	    }
	    		
	    
	    @Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) 
	    {
	    	deletePersonId = id;
			Cursor c = (Cursor) this.getListAdapter().getItem(position);
	        contactName = c.getString(1);
	        
	        idNumber = id;
	           
	        getContactId(c);
	        
			return false;
		}
	    
	    public void onClick(View v)
	    {
	    	Intent i = new Intent(this.getApplicationContext(), AddContactActivity.class);
	    	startActivityForResult(i, 0);
	    }
	    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
	        super.onActivityResult(requestCode, resultCode, data);
	        getUpdatedContacts();	//updates contacts list after creating contact
		}
	    
	    
	    
	    
	    
	    
	    
	 
	    public void getContactId(Cursor cursor)
	    {
	    	if (!cursor.moveToFirst()) return;
	    	getSingleContact = new ArrayList<Integer>();
	    	do 
	    	{
	    		String a = cursor.getString(1);
	    		if(a.equals(contactName))
		    	{
	    			getSingleContact.add(cursor.getInt(0));	 
		    	}
	    	} while (cursor.moveToNext());
	    }
	    
	    public void getSingleContactInfo(Cursor cursor)
	    {
	    	if (!cursor.moveToFirst()) return;
	    	getContactInfo = new ArrayList<String>();
	    	do 
	    	{
	    		String a = cursor.getString(1);
	    		if(a.equals(contactName))
		    	{
	    			getContactInfo.add(cursor.getString(1));
	    			getContactInfo.add(cursor.getString(2));
	    			getContactInfo.add(cursor.getString(3));
	    			getContactInfo.add(cursor.getString(4));
	    			getContactInfo.add(cursor.getString(5));
	    			getContactInfo.add(cursor.getString(6));
		    	}
	    	} while (cursor.moveToNext());
	    }
}
