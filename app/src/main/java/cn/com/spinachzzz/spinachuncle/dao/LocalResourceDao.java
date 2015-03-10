package cn.com.spinachzzz.spinachuncle.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;

import android.os.Environment;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.vo.ItemFolder;
import cn.com.spinachzzz.spinachuncle.vo.ItemFolderContents;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class LocalResourceDao {

    private static final String TAG = LocalResourceDao.class.getSimpleName();

    public static final String ROOT_FOLDER = Environment
	    .getExternalStorageDirectory()
	    + "/"
	    + Constants.EXTERNAL_ROOT_FOLDER + "/";

    public ItemFolder getItemFolderByCode(String code) {
	String folder = code;
	File folderFile = new File(ROOT_FOLDER + folder + "/");
	List<String> list = new ArrayList<String>();

	if (folderFile.exists()) {
	    File[] files = folderFile.listFiles();
	    for (File file : files) {
		if (file.isDirectory()) {
		    list.add(file.getName());
		}
	    }
	}

	Collections.sort(list, new Comparator<String>() {
	    @Override
	    public int compare(String lhs, String rhs) {
		return rhs.compareTo(lhs);
	    }

	});

	ItemFolder imageFolder = new ItemFolder();
	imageFolder.setCode(code);
	imageFolder.setFolder(folder);
	imageFolder.setItems(list);

	return imageFolder;
    }

    public ItemFolderContents getItemFolderContents(ItemFolder itemFolder,
	    String item, String ext) {
	List<String> list = new ArrayList<String>();

	File folder = new File(ROOT_FOLDER + itemFolder.getFolder() + "/"
		+ item);
	if (folder.exists()) {
	    File[] files = folder.listFiles();
	    for (File file : files) {
		if (file.getName().toLowerCase(Locale.US).endsWith(ext)) {
		    list.add(file.getAbsolutePath());
		}
	    }
	}

	String[] contents = list.toArray(new String[0]);

	ItemFolderContents itemFolderContents = new ItemFolderContents();
	itemFolderContents.setItemFolder(itemFolder);
	itemFolderContents.setContents(contents);

	return itemFolderContents;
    }

    public List<String> filterItems(TaskSettings taskSetting,
	    List<String> itemList) {
	itemList = filterDuplicated(itemList);
	
	ItemFolder itemFolder = getItemFolderByCode(taskSetting.getCode());

	List<String> res = new ArrayList<String>();

	String firstExistItem = "";
	if (itemFolder.getItems().size() > 0) {
	    firstExistItem = itemFolder.getItems().get(0);
	}

	int i = 0;
	for (String targetItem : itemList) {
	    if (targetItem.compareTo(firstExistItem) > 0
		    && !itemFolder.getItems().contains(targetItem)) {
		if (i < taskSetting.getMaxKeep().intValue()) {
			res.add(targetItem);
			i++;

		}
	    }
	}

	return res;
    }
    
    public List<String> filterDuplicated(List<String> list){
	 List<String> res = new ArrayList<String>();
	 
	 for(String item : list){
	     if(!res.contains(item)){
		 res.add(item);
	     }
	 }
	 
	 return res;
    }

    public void clean(TaskSettings taskSetting) throws IOException {
	ItemFolder itemFolder = getItemFolderByCode(taskSetting.getCode());
	clean(taskSetting, itemFolder);
    }

    private void clean(TaskSettings taskSetting, ItemFolder itemFolder)
	    throws IOException {
	if (taskSetting.getMaxKeep() != null
		&& taskSetting.getMaxKeep().intValue() > 0) {
	    if (itemFolder.getItems().size() > taskSetting.getMaxKeep()
		    .intValue()) {
		for (int i = taskSetting.getMaxKeep(); i < itemFolder
			.getItems().size(); i++) {
		    deleteFolder(itemFolder, itemFolder.getItems().get(i));
		}
	    }
	}
    }

    private void deleteFolder(ItemFolder itemFolder, String item)
	    throws IOException {
	File folder = new File(ROOT_FOLDER + itemFolder.getFolder() + "/"
		+ item);
	if (folder.exists() && folder.isDirectory()) {
	    Log.i(TAG, "deleting: " + folder.getAbsolutePath());
	    FileUtils.deleteDirectory(folder);
	}
    }
    
    public void renameFromTmp(File file){
	if(file.isFile()&&file.getName().endsWith(Constants.TMP_EXT)){
	    int pos = file.getName().lastIndexOf(Constants.TMP_EXT);
	    if(pos>0){
		String newName = file.getName().substring(0, pos);
		file.renameTo(new File(file.getParentFile().getAbsolutePath()+"/"+newName));
	    }
	}
    }

}
