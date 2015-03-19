package cn.com.spinachzzz.spinachuncle.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.MainActivity;
import cn.com.spinachzzz.spinachuncle.R;

public class MainListViewAdapter extends SimpleAdapter {
    protected static final String TAG = MainListViewAdapter.class
            .getSimpleName();

    private LayoutInflater mInflater;
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
            convertView = mInflater.inflate(R.layout.main_list_item, null);
        }

        final View cv = convertView;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, ?> item = data.get(position);
                String code = (String) item.get("code");

                MainActivity activity = (MainActivity) context;
                Bundle bundle = new Bundle();
                bundle.putString("code", code);

                activity.getDialogs().createTaskPopup(bundle, cv, mInflater);

            }
        });

        return super.getView(position, convertView, parent);
    }

}
