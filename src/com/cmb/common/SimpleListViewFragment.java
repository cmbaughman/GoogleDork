package com.cmb.common;

import android.support.v4.app.ListFragment;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import java.util.Arrays;
import java.util.ArrayList;
import android.os.Bundle;
import com.cmb.googledorks.*;

public class SimpleListViewFragment extends ListFragment {
	public static final String KEY_CONTENTS="contents";
	
	public static SimpleListViewFragment newInstance(String[] contents) {
		return(newInstance(new ArrayList<String>(Arrays.asList(contents))));		
	}
	
	public static SimpleListViewFragment newInstance(ArrayList<String> contents) {
		SimpleListViewFragment reslt = new SimpleListViewFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(KEY_CONTENTS, contents);
		reslt.setArguments(args);
		
		return(reslt);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		setContents(getArguments().getStringArrayList(KEY_CONTENTS));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		((MainActivity)getActivity()).onListItemClick(this, position);
	}
	
	void setContents(ArrayList<String> contents) {
		setListAdapter(new ArrayAdapter<String>(
				getActivity(), R.layout.simple_listview_fragment, contents));
	}
	

}
