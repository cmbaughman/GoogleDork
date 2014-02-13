package com.cmb.googledorks;

import android.app.*;
import android.os.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.widget.AdapterView.*;

import java.io.UnsupportedEncodingException;
import java.net.*;
import android.content.*;
import android.net.*;
import android.support.v4.view.*;
import android.support.v7.widget.ShareActionProvider;

public class MainActivity extends Activity implements Constants
{
    Spinner catSpin;
	ListView listV;
	final GetGoogleDorks dorks = new GetGoogleDorks();
	public final String TAG = "MainActivity";
	
    ArrayAdapter<String> adapter;
	List<String> list= null;
	private ShareActionProvider mShare;
	protected String dorkSelected = "";
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		MenuItem shareItem = menu.findItem(R.id.action_share);
		mShare = (android.support.v7.widget.ShareActionProvider)MenuItemCompat.getActionProvider(shareItem);
		mShare.setShareIntent(getShareItem());
		return super.onCreateOptionsMenu(menu);
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.main);
		
        if (BuildConfig.DEBUG) { Log.d(TAG, LOG + " onCreate Called"); }
        
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
                //required to implement 
            }
        });
        
        listV.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int pos, long arg3) {
					dorkSelected = list.get(pos).toString();
					Intent inten = new Intent(Intent.ACTION_SEND);
					inten.putExtra(Intent.EXTRA_TEXT, dorkSelected);
					inten.setType("text/plain");
					setShareIntent(inten);
					return false;
			}
		});
        
		listV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long idt) {
				Uri uuri = null;
				if(list.get(pos).toString().startsWith("http")) {
					uuri = Uri.parse(list.get(pos).toString());
					startActivity(new Intent(Intent.ACTION_VIEW, uuri));
				}
				else {
					try {
						uuri = Uri.parse("http://www.google.com/#q=" + URLEncoder.encode(list.get(pos), "UTF-8"));
					}
					catch(UnsupportedEncodingException uex) {
						Log.e(TAG, LOG, uex);
					}
					startActivity(new Intent(Intent.ACTION_VIEW, uuri));
				}
				
				Toast.makeText(getBaseContext(), uuri.toString(), Toast.LENGTH_LONG).show();
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
			case R.id.action_help:
				// location found
				ShowHelp sh = new ShowHelp(this);
				sh.setTitle("About Google Dorks by CMB");
				sh.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
        }
    }
    
    private void setShareIntent(Intent shareIntent) {
    	if (mShare != null) {
    		mShare.setShareIntent(shareIntent);
    	}
    }
    
    private Intent getShareItem() {
    	Intent inten = new Intent(Intent.ACTION_SEND);
		inten.setType("image/*");
    	return inten;
    }
}
