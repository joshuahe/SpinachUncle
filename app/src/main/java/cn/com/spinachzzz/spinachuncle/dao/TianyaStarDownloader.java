package cn.com.spinachzzz.spinachuncle.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Message;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.R;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class TianyaStarDownloader {
    private static final String TAG = TianyaStarDownloader.class.getName();

    public static final String URL = "http://star.tianya.cn/tianyaxinggongchang/list_1.shtml";

    public static final String KEY_WORD = "http://bbs.tianya.cn/post-tianyamyself-";

    public static final String KEY_WORD_END = ".shtml";

    public static final String JPEG = ".jpg";

    private TaskServiceMessageHandler handler;

    private TaskSettings taskSetting;

    private LocalResourceDao localResourceDao = new LocalResourceDao();

    public void setHandler(TaskServiceMessageHandler handler) {
	this.handler = handler;
    }

    public void setTaskSetting(TaskSettings taskSetting) {
	this.taskSetting = taskSetting;
    }

    public void run() {
	try {
	    List<String> itemList = fetchItemList();

	    itemList = localResourceDao.filterItems(taskSetting, itemList);

	    for (int i = 0; i < itemList.size(); i++) {
		List<String> contentList = fetchContentList(itemList.get(i));
		contentList = localResourceDao.filterDuplicated(contentList);
		download(itemList.size(), i, itemList.get(i), contentList);
	    }
	    localResourceDao.clean(taskSetting);

	    Message message = new Message();
	    message.what = TaskServiceMessageHandler.DOWNLOAD_COMPLETE;
	    message.getData().putString(
		    TaskServiceMessageHandler.MSG_KEY,
		    taskSetting.getLabel() + " "
			    + handler.getString(R.string.download_finished));
	    handler.sendMessage(message);

	} catch (Exception e) {
	    Log.w(TAG, e);

	    Message message = new Message();
	    message.what = TaskServiceMessageHandler.DOWNLOAD_FAIL;
	    message.getData().putString(TaskServiceMessageHandler.MSG_KEY,
		    e.getMessage());
	    handler.sendMessage(message);

	}

    }

    private List<String> fetchItemList() throws IOException,
	    ClientProtocolException {
	List<String> itemList = new ArrayList<String>();
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = new HttpGet(URL);
	HttpResponse response = httpclient.execute(httpget);
	HttpEntity entity = response.getEntity();
	if (entity != null) {
	    InputStream instream = entity.getContent();
	    BufferedReader bis = new BufferedReader(new InputStreamReader(
		    instream));
	    try {
		String line = null;
		while ((line = bis.readLine()) != null) {
		    int pos = line.indexOf(KEY_WORD);
		    if (pos != -1) {
			int posEnd = line.indexOf(KEY_WORD_END, pos);
			if (posEnd != -1 && posEnd > pos + KEY_WORD.length()) {
			    line = line.substring(pos + KEY_WORD.length(),
				    posEnd);

			    Log.i(TAG, line);

			    itemList.add(line);
			}
		    }
		}
	    } finally {
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(instream);

	    }
	}

	return itemList;
    }

    private List<String> fetchContentList(String item) throws IOException,
	    ClientProtocolException {
	List<String> contentList = new ArrayList<String>();

	HttpClient httpclient = new DefaultHttpClient();
	HttpGet httpget = new HttpGet(KEY_WORD + item + KEY_WORD_END);
	HttpResponse response = httpclient.execute(httpget);
	HttpEntity entity = response.getEntity();
	if (entity != null) {
	    InputStream instream = entity.getContent();
	    BufferedReader bis = new BufferedReader(new InputStreamReader(
		    instream));
	    try {
		String line = null;
		while ((line = bis.readLine()) != null) {
		    int pos = line.indexOf("original=\"");
		    if (pos != -1) {
			int posEnd = line.indexOf(".jpg", pos);
			int cutStart = pos + "original=\"".length();
			int cutEnd = posEnd + ".jpg".length();
			if (posEnd != -1 && cutEnd > cutStart) {
			    line = line.substring(cutStart, cutEnd);

			    Log.i(TAG, line + "");

			    contentList.add(line);
			}
		    }
		}
	    } finally {
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(instream);

	    }
	}
	return contentList;
    }

    private void download(int itemSize, int itemIdx, String itemName,
	    List<String> contentList) throws ClientProtocolException,
	    IOException {

	int i = 0;
	int total = contentList.size();
	for (String link : contentList) {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet(link);
	    httpget.addHeader("Referer", "http://bbs.tianya.cn");
	    HttpResponse response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		File folder = new File(LocalResourceDao.ROOT_FOLDER
			+ taskSetting.getCode() + "/" + itemName);
		if (!folder.exists()) {
		    folder.mkdirs();
		}
		InputStream instream = entity.getContent();

		File file = new File(folder, Integer.toString(i) + JPEG+ Constants.TMP_EXT);
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream(file);
		    IOUtils.copyLarge(instream, fos);

		} finally {
		    IOUtils.closeQuietly(fos);
		    IOUtils.closeQuietly(instream);
		}
		localResourceDao.renameFromTmp(file);
		
		i++;

		Message message = new Message();
		message.what = TaskServiceMessageHandler.DOWNLOADING;
		message.getData().putString(
			TaskServiceMessageHandler.MSG_KEY,
			taskSetting.getLabel() + " "
				+ handler.getString(R.string.downloading) + ":"+itemSize+"/"
				+ (itemIdx+1) + "/" +  total + "/" + i);
		handler.sendMessage(message);
	    }
	}
    }
}
