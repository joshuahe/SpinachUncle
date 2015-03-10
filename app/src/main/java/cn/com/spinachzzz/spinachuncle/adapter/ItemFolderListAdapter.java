package cn.com.spinachzzz.spinachuncle.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ItemFolderListAdapter extends ArrayAdapter<String> {
   
    public ItemFolderListAdapter(Context context, int resource,
	    int textViewResourceId, List<String> objects) {
	super(context, resource, textViewResourceId, objects);
    }

    

}
