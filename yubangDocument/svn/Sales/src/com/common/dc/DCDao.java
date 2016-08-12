package com.common.dc;

import java.util.List;
import java.util.Map;

public interface DCDao {
	
	public List<Map<String, String>> excute(String sql);

}
