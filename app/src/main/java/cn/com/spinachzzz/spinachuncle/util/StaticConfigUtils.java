package cn.com.spinachzzz.spinachuncle.util;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.vo.ResourceType;

public class StaticConfigUtils {

    public static ResourceType getResourceTypeByCode(String code) {
	if (code.equalsIgnoreCase(Constants.FEIYU_CODE)) {
	    return ResourceType.AUDIOS;
	} else {
	    return ResourceType.PICS;
	}
    }

}
