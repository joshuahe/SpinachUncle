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

import android.util.Log;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.R;

public class MokoDownloader extends BaseDownloader {
    private static final String TAG = MokoDownloader.class.getName();

    public static final String URL = "http://www.moko.cc/moko/post/1.html";

    public static final String KEY_WORD = "<a href=\"/post/";

    public static final String KEY_WORD_END = ".html";

    public static final String ITEM_ROOT_URL = "http://www.moko.cc/post/";

    public static final String JPEG = ".jpg";

    public void download() throws ClientProtocolException, IOException {
	List<String> itemList = fetchItemList();

	itemList = localResourceDao.filterItems(taskSetting, itemList);

	for (int i = 0; i < itemList.size(); i++) {
	    List<String> contentList = fetchContentList(itemList.get(i));
	    contentList = localResourceDao.filterDuplicated(contentList);
	    downloadContents(itemList.size(), i, itemList.get(i), contentList);
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
	HttpGet httpget = new HttpGet(ITEM_ROOT_URL + item + KEY_WORD_END);
	HttpResponse response = httpclient.execute(httpget);
	HttpEntity entity = response.getEntity();
	if (entity != null) {
	    InputStream instream = entity.getContent();
	    BufferedReader bis = new BufferedReader(new InputStreamReader(
		    instream));
	    try {
		String line = null;
		while ((line = bis.readLine()) != null) {
		    int pos = line.indexOf("http://img2");
		    if (pos != -1 && line.indexOf("logo") == -1
			    && line.indexOf("cover") == -1
			    && line.indexOf("mokoshow") == -1) {
			int posEnd = line.indexOf(".jpg", pos);
			int cutStart = pos + "http://img2".length();
			int cutEnd = posEnd + ".jpg".length();
			if (posEnd != -1 && cutEnd > cutStart) {
			    line = line.substring(cutStart, cutEnd);

			    Log.i(TAG, line + "");

			    contentList.add("http://img2" + line);
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

    private void downloadContents(int itemSize, int itemIdx, String itemName,
	    List<String> contentList) throws ClientProtocolException,
	    IOException {

	int i = 0;
	int total = contentList.size();
	for (String link : contentList) {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet(link);
	    HttpResponse response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		File folder = new File(LocalResourceDao.ROOT_FOLDER
			+ taskSetting.getCode() + "/" + itemName);
		if (!folder.exists()) {
		    folder.mkdirs();
		}
		InputStream instream = entity.getContent();

		File file = new File(folder, Integer.toString(i) + JPEG
			+ Constants.TMP_EXT);
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

		sendDownloadingMsg(taskSetting.getLabel() + " "
			+ handler.getString(R.string.downloading) + ":"
			+ itemSize + "/" + (itemIdx + 1) + "/" + total + "/"
			+ i);

	    }

	}
    }

}