package com.cmb.googledorks;

import android.app.*;
import android.os.*;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.widget.AdapterView.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

import com.cmb.common.SimpleListViewFragment;

import android.content.*;
import android.net.*;
import android.widget.ShareActionProvider;

public class MainActivity extends FragmentActivity implements Constants
{
    Spinner catSpin;
	ListView listV;
	final GetGoogleDorks dorks = new GetGoogleDorks();
	public final String TAG = "MainActivity";
	
    ArrayAdapter<String> adapter;
	List<String> list= null;
	private ShareActionProvider mShare;
	protected String dorkSelected = "Nothing selected to share";
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		mShare = (ShareActionProvider)menu.findItem(R.id.action_share).getActionProvider();
		return true;
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
		listV.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        
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
					inten.setType("text/plain");
					inten.putExtra(Intent.EXTRA_TEXT, dorkSelected);
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
				break;
			case R.id.action_share:
				getShareItem();
				break;
			default:
				break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void setShareIntent(Intent shareIntent) {
    	if (mShare != null) {
    		mShare.setShareIntent(shareIntent);
    	}
    }
    
    private Intent getShareItem() {
    	Intent inten = new Intent(Intent.ACTION_SEND);
    	inten.setType("text/plain");
		inten.putExtra(Intent.EXTRA_TEXT, dorkSelected);
		mShare.setShareIntent(inten);
    	return inten;
    }

	public void onListItemClick(SimpleListViewFragment simpleListViewFragment,
			int position) {
		// TODO Auto-generated method stub
		
	}
}
