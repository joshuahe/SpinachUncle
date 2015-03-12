package cn.com.spinachzzz.spinachuncle.dao;

import cn.com.spinachzzz.spinachuncle.Constants;

public class DownloaderFactory {

    public static BaseDownloader createDownloader() {
        BaseDownloader baseDownloader = null;

        String code = "";

        if (code.equalsIgnoreCase(Constants.FLICKR_CODE)) {
            baseDownloader = new FlickrDownloader();

        } else if (code.equalsIgnoreCase(Constants.MOKO_CODE)) {
        } else if (code.equalsIgnoreCase(Constants.PCONLINE_CODE)) {
            //baseDownloader = new PCOnlineDownloader();
        } else if (code.equalsIgnoreCase(Constants.FEIYU_CODE)) {
            baseDownloader = new FeiYuDownloader();
        }

        return baseDownloader;

    }

}
