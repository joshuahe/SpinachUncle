package cn.com.spinachzzz.spinachuncle;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.com.spinachzzz.spinachuncle.adapter.ItemFolderListAdapter;
import cn.com.spinachzzz.spinachuncle.dao.LocalResourceDao;
import cn.com.spinachzzz.spinachuncle.util.StaticConfigUtils;
import cn.com.spinachzzz.spinachuncle.vo.ItemFolder;
import cn.com.spinachzzz.spinachuncle.vo.ItemFolderContents;
import cn.com.spinachzzz.spinachuncle.vo.ResourceType;

public class ItemFolderActivity extends Activity {
    protected static final String TAG = ItemFolderActivity.class
	    .getSimpleName();

    private LocalResourceDao dao = new LocalResourceDao();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.simple_list);

	listView = (ListView) this.findViewById(R.id.simple_list_view);

	Bundle bundle = this.getIntent().getExtras();
	final String taskCode = bundle.getString(Constants.TASK_CODE);

	final ItemFolder itemFolder = dao.getItemFolderByCode(taskCode);

	ItemFolderListAdapter adapter = new ItemFolderListAdapter(this,
		R.layout.simple_list_item, R.id.simple_list_item_title,
		itemFolder.getItems());

	listView.setAdapter(adapter);

	listView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> adapterView, View view,
		    int position, long id) {

		String picFolderName = itemFolder.getItems().get(position);

		ResourceType resourceType = StaticConfigUtils
			.getResourceTypeByCode(taskCode);

		if (resourceType == ResourceType.PICS) {

		    ItemFolderContents itemFolderContents = dao
			    .getItemFolderContents(itemFolder, picFolderName,
				    ".jpg");

		    if (itemFolderContents.getContents() != null
			    && itemFolderContents.getContents().length > 0) {
			Intent intent = new Intent();
			intent.putExtra(Constants.PICS,
				itemFolderContents.getContents());
			intent.setClass(ItemFolderActivity.this,
				ImageViewActivity.class);

			ItemFolderActivity.this.startActivity(intent);
		    } else {
			Toast.makeText(ItemFolderActivity.this,
				R.string.no_pictures_found, Toast.LENGTH_LONG)
				.show();
		    }
		} else if (resourceType == ResourceType.AUDIOS) {

		    ItemFolderContents itemFolderContents = dao
			    .getItemFolderContents(itemFolder, picFolderName,
				    ".mp3");
		    if (itemFolderContents.getContents() != null
			    && itemFolderContents.getContents().length > 0) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri data = Uri.fromFile(new File(itemFolderContents
				.getContents()[0]));
			intent.setDataAndType(data, "audio/mp3");
			startActivity(intent);
		    } else {
			Toast.makeText(ItemFolderActivity.this,
				R.string.no_audio_found, Toast.LENGTH_LONG)
				.show();
		    }

		}
	    }

	});

    }

}
