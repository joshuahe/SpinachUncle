package cn.com.spinachzzz.spinachuncle.dao;

import android.os.Message;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import cn.com.spinachzzz.spinachuncle.exception.MessageException;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.util.CalcUtils;

public abstract class BaseDownloader implements Runnable {

    private static final String TAG = BaseDownloader.class.getSimpleName();

    protected TaskServiceMessageHandler handler;

    //protected TaskSettings taskSetting;

    //protected ConfigurationDao configurationDao;

    protected LocalResourceDao localResourceDao = new LocalResourceDao();

    public void setHandler(TaskServiceMessageHandler handler) {
	this.handler = handler;
    }

   // public void setTaskSetting(TaskSettings taskSetting) {
	//this.taskSetting = taskSetting;
    //}

    /**
    public void setConfigurationDao(ConfigurationDao configurationDao) {
	this.configurationDao = configurationDao;
    }
     **/

    @Override
    public void run() {
	handler.startNotify();

	try {
	    download();
        /**

	    localResourceDao.clean(taskSetting);

	    // configurationDao.updateTaskSetting(taskSetting);

	    Message message = new Message();
	    message.what = TaskServiceMessageHandler.DOWNLOAD_COMPLETE;
	    message.getData().putString(
		    TaskServiceMessageHandler.MSG_KEY,
		    taskSetting.getLabel() + " "
			    + handler.getString(R.string.download_finished));

	    handler.sendMessage(message);
         **/

	} catch (Exception e) {
	    Log.w(TAG, e);

	    Message message = new Message();
	    message.what = TaskServiceMessageHandler.DOWNLOAD_FAIL;
	    message.getData().putString(TaskServiceMessageHandler.MSG_KEY,
		    e.getMessage());
	    handler.sendMessage(message);

	}
    }

    protected abstract void download() throws Exception;

    protected void sendDownloadingMsg(String msg) {
	Message message = new Message();
	message.what = TaskServiceMessageHandler.DOWNLOADING;
	message.getData().putString(TaskServiceMessageHandler.MSG_KEY, msg);
	handler.sendMessage(message);
    }

    protected File downFile(String url, String fileName, String path, String msg)
	    throws IOException {
	if (path.charAt(path.length() - 1) != '/') {
	    path = path + "/";
	}

	File file = new File(path + fileName);

	URL myURL = new URL(url);
	URLConnection conn = myURL.openConnection();
	conn.connect();
	InputStream is = null;
	FileOutputStream fos = null;

	try {
	    is = conn.getInputStream();
	    int fileSize = conn.getContentLength();
	    if (fileSize <= 0)
		throw new MessageException("Unknown file size.");
	    if (is == null)
		throw new MessageException("Unknown source.");

	    BigDecimal total = BigDecimal.valueOf(fileSize);

	    fos = new FileOutputStream(file);

	    byte buf[] = new byte[1024 * 4];
	    int downLoadFileSize = 0;
	    int lastPercent = 0;
	    sendPercentMessage(downLoadFileSize, total, lastPercent, msg);
	    while (downLoadFileSize <= fileSize) {

		int numread = is.read(buf);
		if (numread == -1) {
		    break;
		}
		fos.write(buf, 0, numread);
		downLoadFileSize += numread;

		lastPercent = sendPercentMessage(downLoadFileSize, total,
			lastPercent, msg);
	    }
	} finally {
	    IOUtils.closeQuietly(is);
	    IOUtils.closeQuietly(fos);
	}

	return file;

    }

    private String createPercentMsg(int percent, String msg) {
	return msg + " " + percent + "%";
    }

    private int sendPercentMessage(int downLoadFileSize, BigDecimal total,
	    int lastPercent, String msg) {
	int percent = CalcUtils.calculatePercent(downLoadFileSize, total);
	if (percent - lastPercent >= 1) {
	  
	    sendDownloadingMsg(createPercentMsg(percent, msg));
	}
	return percent;
    }
}
