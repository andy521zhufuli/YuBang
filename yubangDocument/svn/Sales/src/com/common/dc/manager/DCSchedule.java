package com.common.dc.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.common.dc.timer.DCTimer;

public class DCSchedule {

	private List<DCTimer> dcTimers;
	
	public static ConcurrentHashMap<String, String> workingMap = new ConcurrentHashMap<String, String>();
	
	public void Schedule(){
	    //Ö´ÐÐ¶¨Ê±Æ÷
		for (DCTimer dcTimer : dcTimers) {
			dcTimer.begin();
		}
	}

	public List<DCTimer> getDcTimers() {
		return dcTimers;
	}

	public void setDcTimers(List<DCTimer> dcTimers) {
		this.dcTimers = dcTimers;
	}
	
	
}
