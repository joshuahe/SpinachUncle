package cn.com.spinachzzz.spinachuncle.business;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;

/**
 * Created by Jing on 12/03/15.
 */
public class TasksService {

    public List<? extends Map<String, ?>> convertToAdapterMap(List<Tasks> tasks) {


        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Tasks task : tasks) {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("code", task.getCode());
            map.put("label", task.getLabel());

            list.add(map);

        }

        return list;
    }
}
