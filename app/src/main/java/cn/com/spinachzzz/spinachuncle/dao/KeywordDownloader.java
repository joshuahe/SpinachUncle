package cn.com.spinachzzz.spinachuncle.dao;

import android.util.Log;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.R;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.util.CommonUtils;
import cn.com.spinachzzz.spinachuncle.vo.TaskParamVO;

public class KeywordDownloader extends BaseDownloader {

    private static final String TAG = KeywordDownloader.class.getName();

    private List<String> list = new ArrayList<String>();

    public KeywordDownloader(TaskParamVO taskParam) {
        this.taskParam = taskParam;
    }

    private void fetchList() throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(taskParam.getTask().getTargetUrl());
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader bis = new BufferedReader(new InputStreamReader(
                    is));
            try {
                String line = null;
                while ((line = bis.readLine()) != null) {
                    checkToAddItem(line);

                }
            } finally {
                IOUtils.closeQuietly(bis);
                IOUtils.closeQuietly(is);

            }
        }
    }

    private void checkToAddItem(String line) {
        Tasks tasks = taskParam.getTask();

        StringBuilder start = new StringBuilder();
        start.append(org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordBf()));
        start.append(org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordSw()));

        int startPoint = line.indexOf(start.toString());
        if (startPoint != -1) {

            startPoint = startPoint + org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordBf()).length();

            StringBuilder end = new StringBuilder();
            end.append(org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordEw()));
            end.append(org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordAf()));

            int endPoint = line.indexOf(end.toString());

            if (endPoint != -1) {
                endPoint = endPoint + org.apache.commons.lang3.StringUtils.defaultString(tasks.getKeywordEw()).length();

                if (endPoint > startPoint) {
                    String aLine = line.substring(startPoint, endPoint);
                    Log.i(TAG, aLine);
                    list.add(aLine);
                }
            }
        }
    }

    protected void download() throws IOException {
        fetchList();

        int i = 0;
        int total = list.size();
        for (String link : list) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(link);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {

                InputStream instream = entity.getContent();

                File file = new File(taskParam.getTask().getSavePath(), FilenameUtils.getName(link)
                        + Constants.TMP_EXT);

                CommonUtils.forceMkdir(file);


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    IOUtils.copyLarge(instream, fos);

                } finally {
                    IOUtils.closeQuietly(fos);
                    IOUtils.closeQuietly(instream);
                }

                CommonUtils.renameFromTmp(file);

                i++;

                sendDownloadingMsg(taskParam.getTask().getLabel() + " "
                        + handler.getString(R.string.download) + ": " + i + "/"
                        + total);

            }

        }

    }

}
