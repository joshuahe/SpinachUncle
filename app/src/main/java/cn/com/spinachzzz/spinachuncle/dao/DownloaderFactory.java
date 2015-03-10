package cn.com.spinachzzz.spinachuncle.dao;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class DownloaderFactory {

    public static BaseDownloader createDownloader(TaskSettings taskSettng) {
	BaseDownloader baseDownloader = null;

	String code = taskSettng.getCode();

	if (code.equalsIgnoreCase(Constants.FLICKR_CODE)) {
	    baseDownloader = new FlickrDownloader();

	} else if (code.equalsIgnoreCase(Constants.MOKO_CODE)) {
	    baseDownloader = new MokoDownloader();
	} else if (code.equalsIgnoreCase(Constants.PCONLINE_CODE)) {
	    baseDownloader = new PCOnlineDownloader();
	} else if (code.equalsIgnoreCase(Constants.FEIYU_CODE)) {
	    baseDownloader = new FeiYuDownloader();
	}

	return baseDownloader;

    }

}
