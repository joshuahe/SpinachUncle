package cn.com.spinachzzz.spinachuncle.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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
import cn.com.spinachzzz.spinachuncle.util.DateUtils;

public class FlickrDownloader extends BaseDownloader {
    public static final String URL = "http://www.flickr.com/explore";

    private static final String TAG = FlickrDownloader.class.getName();

    public static final String KEY_WORD = "data-defer-src=";

    public static final String JPEG = ".jpg";

    private List<String> list = new ArrayList<String>();

    private void fetchList() throws IOException, ClientProtocolException {
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
			int posEnd = line.indexOf(JPEG, pos);
			if (posEnd != -1
				&& posEnd + JPEG.length() > pos
					+ KEY_WORD.length()) {
			    line = line.substring(pos + KEY_WORD.length(),
				    posEnd + JPEG.length());
			    String[] sa = line.split("\"");
			    for (String s : sa) {
				if (s.startsWith("http://")) {
				    list.add(s);
				    Log.i(TAG, line);
				}
			    }
			}

		    }

		}
	    } finally {
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(instream);

	    }
	}
    }

    protected void download() throws ClientProtocolException, IOException {
	fetchList();

	int i = 0;
	int total = list.size();
	for (String link : list) {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet(link);
	    HttpResponse response = httpclient.execute(httpget);
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		File folder = new File(LocalResourceDao.ROOT_FOLDER
			+ taskSetting.getCode() + "/"
			+ DateUtils.formatSimpleDay(new Date()));

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
			+ handler.getString(R.string.download) + ": " + i + "/"
			+ total);

	    }

	}

    }

}
