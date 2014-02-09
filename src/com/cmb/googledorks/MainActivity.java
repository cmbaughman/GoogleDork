package com.cmb.googledorks;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import java.util.*;
import android.widget.AdapterView.*;
import java.net.*;
import android.content.*;
import android.net.*;

public class MainActivity extends Activity
{
    Spinner catSpin;
	ListView listV;
	final GetGoogleDorks dorks = new GetGoogleDorks();
	final public int ABOUT = 0;
	
    ArrayAdapter<String> adapter;
	List<String> list= null;
	private ShareActionProvider mShare;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.main);
		
		listV = (ListView)findViewById(R.id.listBox);
        catSpin = (Spinner)findViewById(R.id.catSpin);
		
        list = dorks.getFeed(getBaseContext());
        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
		listV.setAdapter(adapter);
        
        catSpin.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long idt) {
                switch(pos) {
                    case 0:
						dorks.setFeedRes("files_containing_usernames.txt");
                        break;
                    case 1:
                        dorks.setFeedRes("files_containing_juicy_info.txt");
                        break;
                    case 2:
						dorks.setFeedRes("files_containing_passwords.txt");
                        break;
					case 3:
						dorks.setFeedRes("various_connected_devices.txt");
                        break;
					case 4:
						dorks.setFeedRes("footholds.txt");
                        break;
					case 5:
						dorks.setFeedRes("online_shopping_info.txt");
                        break;
                    default:
						dorks.setFeedRes("files_containing_usernames.txt");
                        break;     
                        
                }
				adapter.clear();
                list = dorks.getFeed(getBaseContext());
				adapter.addAll(list);
                adapter.notifyDataSetChanged();
				listV.refreshDrawableState();
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //required implememt 
            }
        });
        
		listV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long idt) {
				if(list.get(pos).toString().startsWith("http")) {
					startActivity(new Intent(Intent.ACTION_VIEW, 
						Uri.parse(list.get(pos))));
				}
				else {
					startActivity(new Intent(Intent.ACTION_VIEW, 
						Uri.parse("http://www.google.com/#q=" 
						+ URLEncoder.encode(list.get(pos)))));
				}
				
				Toast.makeText(getBaseContext()
				, "Opening http://google.com/#q=" + URLEncoder.encode(list.get(pos)), Toast.LENGTH_LONG).show();
			}
		});
    }
	
	/**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
			case R.id.action_refresh:
				// search action
				Intent intent_settings = new Intent(this, SettingsActivity.class);
				this.startActivity(intent_settings);
				return true;
			case R.id.action_help:
				// location found
				ShowHelp sh = new ShowHelp(this);
				sh.setTitle("About Google Dorks by CMB");
				sh.show();
				return true;
			case R.id.action_share:
				mShare = (ShareActionProvider)item.getActionProvider();
				return true;
			default:
				return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launching new activity
     * */
    private void ShowShare() {
        Intent i = new Intent(MainActivity.this, ShowHelp.class);
        startActivity(i);
    }
}
