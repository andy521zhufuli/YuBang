package com.common.dc.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.annotation.Transactional;

import com.common.dc.DCDao;
import com.common.dc.DCListenter;
import com.common.dc.DCconfig;
import com.common.log.JLogger;
import com.common.log.LoggerUtils;

public class DCTimerTasker extends TimerTask {

	final private static JLogger logger = LoggerUtils
			.getLogger(DCTimerTasker.class);
	// dc配置
	private DCconfig dCconfig;

	@Override
	@Transactional
	public void run() {
		// TODO Auto-generated method stub
		Map<String, Map<String, Map<String, List<String>>>> data = getDCData();
		List<DCListenter> dcListenters = dCconfig.getListenters();
		for (DCListenter dcListenter : dcListenters) {
			dcListenter.refresh(data,dCconfig);
		}
	}

	private String buildSql() {
		// 创建全表查询语句
		String sql = "select * from " + dCconfig.getTableName();
		if (dCconfig.getCondition() != null) {
			for (int i = 0; i < dCconfig.getCondition().size(); i++) {
				if (i == 0) {
					sql += " where " + " " + dCconfig.getCondition().get(i);
				} else {
					sql += " " + dCconfig.getCondition().get(i);
				}
			}
		}
		return sql;
	}

	private Map<String, Map<String, Map<String, List<String>>>> getDCData() {
		DCDao dao = dCconfig.getDcDao();
		String sql = buildSql();

		// 创建数据库缓存
		Map<String, Map<String, Map<String, List<String>>>> cachedMap = new ConcurrentHashMap<String, Map<String, Map<String, List<String>>>>();

		for (String pk : dCconfig.getPkList()) {

			Map<String, Map<String, List<String>>> pkData = new ConcurrentHashMap<String, Map<String, List<String>>>();
			for (String colum : dCconfig.getColumnList()) {
				// 保存查询主键的数据
				Map<String, List<String>> columData = new ConcurrentHashMap<String, List<String>>();
				pkData.put(colum, columData);
			}

			cachedMap.put(pk, pkData);
		}

		// 获取数据
		List<Map<String, String>> datas = dao.excute(sql);

		// 构建查询缓存
		for (Map<String, String> map : datas) {
			for (String pk : dCconfig.getPkList()) {

				String pkValue = map.get(pk);

				if (pkValue != null) {

					// 获取该主键的缓存数据
					Map<String, Map<String, List<String>>> pkmap = cachedMap
							.get(pk);

					// 获取每一列的数据
					Map<String, List<String>> columMap = pkmap.get(pkValue);
					if(columMap == null){
						columMap = new ConcurrentHashMap<String, List<String>>();
						pkmap.put(pkValue, columMap);
					}
					for (String colum : dCconfig.getColumnList()) {

						if (colum != null) {
							// 获取这行记录colum的值进行缓存
							String columVal = map.get(colum);
							if (columVal != null) {
								List<String> values = columMap.get(colum);

								// colum的值添加到缓存
								if (values == null) {
									values = new ArrayList<String>();
									values.add(columVal);
									columMap.put(colum, values);
								} else {
									values.add(columVal);
								}
							}
						}
					}

				}
			}

		}

		return cachedMap;
	}

	public DCconfig getdCconfig() {
		return dCconfig;
	}

	public void setdCconfig(DCconfig dCconfig) {
		this.dCconfig = dCconfig;
	}

}
