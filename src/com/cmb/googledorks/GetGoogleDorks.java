package com.cmb.googledorks;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.xml.sax.*;
import android.content.*;
import java.io.*;
import android.content.res.*;
import java.util.*;

public class GetGoogleDorks
{
	String feedRes = "files_containing_usernames.txt";
	
    public GetGoogleDorks() {  }
    
    public GetGoogleDorks(String feeds) {
        this.feedRes = feeds;
    }
    
	public String getFeedRes() {
		return this.feedRes;
	}
	
	public void setFeedRes(String val) {
		this.feedRes = val;
	}
	
	public List<String> getFeed(Context ctx) {
		Toast.makeText(ctx, this.getFeedRes(), Toast.LENGTH_SHORT).show();
        List<String> dlist = new ArrayList<String>();
		AssetManager amgr= ctx.getAssets();
		InputStream inpt= null;
		String line;
		
		try {
			inpt = amgr.open(this.getFeedRes());
			BufferedReader buf = new BufferedReader(new InputStreamReader(inpt));
			if (inpt != null) {
				while((line = buf.readLine()) != null) {
					if(line.length() > 3) {
						dlist.add(line);
					}
				}
				inpt.close();
			}
		}
		catch(IOException iex){
			Toast.makeText(ctx, iex.getMessage()
				, Toast.LENGTH_LONG).show();
		}
		
		return dlist;
	}
	
}
