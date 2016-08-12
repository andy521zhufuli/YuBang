package com.common.dc;

import java.util.List;
import java.util.Map;

public interface DCListenter {

	public void refresh(Map<String, Map<String, Map<String, List<String>>>> data,DCconfig dCconfig);
	
}
