package cn.com.spinachzzz.spinachuncle.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.ItemFolderActivity;
import cn.com.spinachzzz.spinachuncle.R;
import cn.com.spinachzzz.spinachuncle.service.SingleTaskService;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class MainListViewAdapter extends SimpleAdapter {
    protected static final String TAG = MainListViewAdapter.class
	    .getSimpleName();

    private LayoutInflater mInflater;
    private MainListViewHolder holder;
    private Context context;
    private List<? extends Map<String, ?>> data;

    public MainListViewAdapter(Context context,
	    List<? extends Map<String, ?>> data, int resource, String[] from,
	    int[] to) {
	super(context, data, resource, from, to);
	this.mInflater = LayoutInflater.from(context);

	this.context = context;
	this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

	if (convertView == null) {
	    holder = new MainListViewHolder();

	    convertView = mInflater.inflate(R.layout.main_list_item, null);
	    holder.setDownloadBtn((Button) convertView
		    .findViewById(R.id.main_list_bth_download));
	    holder.setViewBtn((Button) convertView
		    .findViewById(R.id.main_list_bth_view));
	    holder.setSettingBth((Button) convertView
		    .findViewById(R.id.main_list_bth_setting));

	    convertView.setTag(holder);
	} else {
	    holder = (MainListViewHolder) convertView.getTag();
	}

	final Map<String, ?> item = data.get(position);
	final String code = (String) item.get(TaskSettings.CODE);

	holder.getDownloadBtn().setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View view) {
		Intent intent = new Intent(context, SingleTaskService.class);
		intent.putExtra(Constants.TASK_CODE, code);
		context.startService(intent);
	    }
	});

	holder.getViewBtn().setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View view) {
		//if (code.equalsIgnoreCase(Constants.FLICKR_CODE)) {
		    Intent intent = new Intent();
		    intent.setClass(context, ItemFolderActivity.class);
		    intent.putExtra(Constants.TASK_CODE, code);
		    context.startActivity(intent);
		//}
	    }
	});

	holder.getSettingBth().setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View view) {
		    Activity activity = (Activity) context;
		    Bundle bundle = new Bundle();
		    bundle.putString(TaskSettings.CODE, code);
		    activity.showDialog(Constants.TASK_SETTING_DIALOG_ID+position,
			    bundle);
	    }
	});

	return super.getView(position, convertView, parent);
    }

}
