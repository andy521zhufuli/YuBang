package com.common.dc.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.transaction.annotation.Transactional;

import com.common.dc.DCDao;
import com.common.dc.DCListenter;
import com.common.dc.DCconfig;
import com.common.log.JLogger;
import com.common.log.LoggerUtils;

public class DCTimer extends Timer {

	final private static JLogger logger = LoggerUtils
			.getLogger(DCTimer.class);
	
	private DCTimerTasker dcTimerTasker;
	
	public DCTimer(DCTimerTasker dcTimerTasker){
		this.dcTimerTasker = dcTimerTasker;
	}
	
	public void begin(){
		schedule(dcTimerTasker,0,dcTimerTasker.getdCconfig().getRefreshTime());
	}

	public DCTimerTasker getDcTimerTasker() {
		return dcTimerTasker;
	}

	public void setDcTimerTasker(DCTimerTasker dcTimerTasker) {
		this.dcTimerTasker = dcTimerTasker;
	}
	
	

}
