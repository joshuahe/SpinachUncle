package cn.com.spinachzzz.spinachuncle;

import android.test.AndroidTestCase;
import cn.com.spinachzzz.spinachuncle.dao.FlickrDownloader;

public class FlickrDownloaderTest extends AndroidTestCase {
    
    public void testRun() {
        FlickrDownloader d = new FlickrDownloader();
        try {
            d.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
