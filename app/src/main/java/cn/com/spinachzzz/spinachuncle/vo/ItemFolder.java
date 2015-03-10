package cn.com.spinachzzz.spinachuncle.vo;

import java.util.ArrayList;
import java.util.List;

public class ItemFolder {
    
    private String code;
    private String folder;

    private List<String> items = new ArrayList<String>();
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    
    
    
}
