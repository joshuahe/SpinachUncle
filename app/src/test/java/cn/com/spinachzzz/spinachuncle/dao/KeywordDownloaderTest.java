package cn.com.spinachzzz.spinachuncle.dao;

import org.junit.Before;
import org.junit.Test;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;

import static org.junit.Assert.*;

public class KeywordDownloaderTest {

    private KeywordDownloader downloader;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDownload() throws Exception {

        Tasks task = new Tasks();
        task.setTargetUrl("https://www.flickr.com/explore");
        task.setKeywordBf("data-defer-src=\"");
        task.setKeywordSw("https");
        task.setKeywordEw(".jpg");
        task.setKeywordAf("\" ");

        task.setSavePath("/Users/Jing/Downloads");

        downloader = new KeywordDownloader(task);

        downloader.download();
    }
}