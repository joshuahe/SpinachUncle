package cn.com.spinachzzz.spinachuncle.dao;

import java.io.File;
import java.util.Date;

import cn.com.spinachzzz.spinachuncle.util.DateUtils;

public class FeiYuDownloader extends BaseDownloader {

    public static String URL = "http://mod.cri.cn/eng/ez/morning/2014/ezm";
    public static String EXT = ".mp3";
    public static String TMP_EXT = ".tmp";

    @Override
    protected void download() throws Exception {
	String fileName = DateUtils.formatEasyFmDate(new Date());

	String url = URL + fileName + EXT;

	File folder = new File(LocalResourceDao.ROOT_FOLDER
		+ taskSetting.getCode() + "/" + fileName);
	if (!folder.exists()) {
	    folder.mkdirs();
	}

	File file = super.downFile(url, fileName + EXT + TMP_EXT, folder.getAbsolutePath(), "Download FeiYuShow");

	localResourceDao.renameFromTmp(file);

    }

}
