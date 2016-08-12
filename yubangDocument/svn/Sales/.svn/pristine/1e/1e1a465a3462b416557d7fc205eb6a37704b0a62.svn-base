/*
 * @(#)BizStaticFileAppender.java        1.6 05/03/10
 *
 * Copyright (c) 2003-2005 ASPire Technologies, Inc.
 * 6/F,IER BUILDING, SOUTH AREA,SHENZHEN HI-TECH INDUSTRIAL PARK Mail Box:11# 12#.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ASPire Technologies, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Aspire.
 */
package com.common.log.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import com.common.log.constants.LogConstants;
import com.common.log.proxy.config.ChkfileCreatorObserver;
import com.common.log.util.LogUtils;


/**
 * <p>
 * Title: BizStaticFileAppender
 * </p>
 * <p>
 * Description: it record the biz log and output the statistics of files of biz
 * log
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Aspire Technologies
 * </p>
 * 
 * @author YanFeng
 * @version 1.6.5 history created at 2005/03/10
 * @CheckItem@ BUG-Yanfeng-20060810 锟斤拷锟�锟斤拷锟斤拷志锟斤拷锟斤拷锟绞憋拷锟斤拷瘸锟斤拷锟揭伙拷锟绞�锟斤拷锟角伙拷锟斤拷锟斤拷馗锟斤拷锟缴讹拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
 * @CheckItem@ REQ-huangbigui-20070522 锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟酵ㄖ拷锟斤拷叱蹋锟绞癸拷锟斤拷锟斤拷募锟斤拷芏锟绞憋拷锟斤拷
 */

public class BizStaticFileAppender extends TimeSizeRollingFileAppender
		implements ErrorCode {
	/**
	 * record the biz log stastics
	 */
	// private static File lastStatFile;
	// private boolean newStartup=false;
	// private static FileWriter fw;
	private long nextCheck = System.currentTimeMillis() - 1;
	/**
	 * the current date for biz log stastics
	 */
	// private static Date currStatDate=new Date();
	private static final String NEXT_LINE = System
			.getProperty("line.separator");
	private final static String FILE_SEP = System.getProperty("file.separator");
	private final static String PUBLISHED_CHECKFILE = ".pub";
	private final static String CHECKFILE_PATH = "stat";
	/**
	 * 锟斤拷志锟斤拷业锟斤拷时锟斤拷锟斤拷锟斤拷锟绞嘉伙拷锟斤拷0锟斤拷始
	 */
	// private int ts_start;
	/**
	 * 为锟斤拷锟斤拷锟斤拷锟街拘э拷剩锟街伙拷锟�锟斤拷前锟斤拷指锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟揭碉拷锟斤拷锟街撅拷锟斤拷锟斤拷 锟斤拷锟斤拷为锟斤拷位
	 */
	private int ts_offset;
	/**
	 * 锟斤拷识锟斤拷一锟斤拷越锟斤拷一锟斤拷 缺省锟斤拷true,锟皆憋拷系统锟斤拷前一锟斤拷停止锟斤拷锟节讹拷锟斤拷锟斤拷锟斤拷锟斤拷时也锟斤拷执锟叫凤拷锟斤拷锟斤拷锟斤拷锟侥硷拷 锟斤拷使锟截革拷锟斤拷锟斤拷锟斤拷也锟斤拷锟斤拷锟截革拷锟斤拷锟斤拷
	 */
	private boolean first_on_day = true;
	/**
	 * 锟斤拷前锟侥讹拷锟斤拷锟侥硷拷
	 */
	private File statFile;
	/**
	 * 锟斤拷前锟斤拷锟斤拷锟斤拷锟斤拷志锟侥硷拷
	 */
	private File extFile;
	/**
	 * 锟较回比较的碉拷前时锟斤拷锟斤拷锟斤拷时锟斤拷牟锟�锟斤拷值锟斤拷一锟斤拷锟节从ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷
	 */
	// private long last_ts_trap;
	/**
	 * 锟斤拷一锟斤拷锟斤拷锟叫达拷锟斤拷锟街撅拷募锟角白猴拷锟�只锟斤拷锟斤拷锟节ｏ拷锟斤拷锟斤拷.n.bak,锟斤拷Pullbiz.log2005-09-14-23
	 */
	private String lastLogFilePrefix;
	/**
	 * 锟斤拷一锟斤拷锟斤拷锟叫达拷锟斤拷锟街撅拷募锟斤拷挚锟斤拷锟斤拷 锟斤拷锟睫ｏ拷锟斤拷为0;
	 */
	private int lastLogFileSize;
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷,锟斤拷锟节比斤拷锟角凤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	private long currDate = getCurrDate();
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷时锟戒。锟斤拷锟斤拷锟叫讹拷锟角凤拷要锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷只锟叫碉拷锟斤拷锟铰碉拷一锟斤拷锟斤拷说却锟斤拷诤锟侥碉拷一锟斤拷锟斤拷志锟斤拷锟斤拷
	 */
	private long lastDayTime = getCurrDate();
	/**
	 * 锟斤拷锟斤拷锟侥硷拷锟叫碉拷锟斤拷锟节革拷式锟斤拷锟斤拷:缺省锟斤拷'.'yyyy-MM-dd'.999999'
	 */
	private String stat_datepattern = "'.'yyyy-MM-dd'.999999'";

	/**
	 * 锟角凤拷使锟矫革拷锟斤拷锟竭程ｏ拷锟皆便尽锟斤拷锟斤拷啥锟斤拷锟斤拷募锟�
	 */
	private String instantCreatChkfile = "false";

	/**
	 * The default constructor does nothing.
	 */

	public BizStaticFileAppender() {
		super();
		// newStartup=true;
		LogLog.debug("New BizStaticFileAppender instance");
	}

	public BizStaticFileAppender(Layout layout, String filename,
			String datePattern) throws IOException {
		super(layout, filename, datePattern);
		// newStartup=true;
		LogLog.debug("New BizStaticFileAppender instance");
	}

	public void activateOptions() {
		super.activateOptions();
		// 锟角凤拷锟斤拷锟接碉拷锟斤拷锟斤拷
		LogLog.debug("this.instantCreatChkfile:" + this.instantCreatChkfile);
		if ("true".equals(this.instantCreatChkfile)) {
			ChkfileCreatorObserver.addToListen(this);
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷未锟斤拷锟斤拷锟侥讹拷锟斤拷锟侥硷拷
	 */
	private void publishOldCheckFile() {
		String statPath = ServerInfo.getAppRootPath() + FILE_SEP + "log"
				+ FILE_SEP + CHECKFILE_PATH + FILE_SEP;
		File path = new File(statPath);

		File[] checkFiles = path.listFiles();
		if (checkFiles == null) {// 锟斤拷锟矫伙拷锟絪tat目录锟斤拷锟斤拷直锟接凤拷锟斤拷
			return;
		}
		HashMap extLogMap = new HashMap();
		HashMap chkLogMap = new HashMap();
		ArrayList keyDate = new ArrayList();
		for (int i = 0; i < checkFiles.length; i++) {
			File oldChkFile = checkFiles[i];
			String tmpfileName = oldChkFile.getName();
			if (!tmpfileName.startsWith(currFile.getName())) {// 锟斤拷锟斤拷潜锟斤拷锟揭碉拷锟斤拷锟街撅拷募锟斤拷锟斤拷锟斤拷锟斤拷
				continue;
			}
			if (tmpfileName.endsWith(".bak")) {// 锟斤拷锟斤拷一锟斤拷未锟斤拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷锟街撅拷募锟斤拷锟斤拷锟揭拷拥锟斤拷锟接︼拷亩锟斤拷锟斤拷募锟斤拷锟�
				// int index=tmpfileName.indexOf(".log");
				// String fileDate=tmpfileName.substring(index+4,index+4+10);
				// String bizName=tmpfileName.substring(0,index+4);
				int index = fileName.lastIndexOf(FILE_SEP);
				// 锟矫碉拷去锟斤拷路锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
				String bizName = fileName.substring(index + 1);
				String fileDate = getUnidateFromFileName(tmpfileName, bizName,
						datePattern);
				extLogMap.put(bizName + fileDate, oldChkFile);
			} else if (tmpfileName.endsWith(".999999")) {// 锟斤拷锟斤拷一锟斤拷未锟斤拷锟斤拷锟侥讹拷锟斤拷锟侥硷拷锟斤拷锟斤拷要锟斤拷锟斤拷
				int index = fileName.lastIndexOf(FILE_SEP);
				// 锟矫碉拷去锟斤拷路锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
				String bizName = fileName.substring(index + 1);
				String fileDate = getUnidateFromFileName(tmpfileName, bizName,
						stat_datepattern);
				keyDate.add(bizName + fileDate);
				// 锟斤拷前锟斤拷锟斤拷
				// String strCurrDate = StringUtils.toString(new
				// Date(),"yyyyMMdd");
				String yesterday = getLastDate();
				if (yesterday.equals(fileDate)) { // 只锟斤拷锟斤拷锟斤拷锟斤拷亩锟斤拷锟斤拷募锟�锟斤拷锟斤拷前也没锟斤拷锟斤拷锟斤拷,锟截憋拷锟斤拷锟较低筹拷锟斤拷锟斤拷锟斤拷锟斤拷
					chkLogMap.put(bizName + fileDate, oldChkFile);
				}
			}
		}
		for (int i = 0; i < keyDate.size(); i++) {// 锟斤拷苑锟斤拷值锟矫恳伙拷锟轿达拷锟斤拷锟斤拷亩锟斤拷锟斤拷募锟�
			String logDay = (String) keyDate.get(i);
			if (chkLogMap.containsKey(logDay)) {// 锟斤拷一锟斤拷未锟斤拷锟斤拷锟侥讹拷锟斤拷锟侥硷拷
				File checkFile = (File) chkLogMap.get(logDay);
				try {
					File targetFile = new File(currFile.getParentFile(),
							checkFile.getName());
					FileWriter fw = new FileWriter(checkFile, true);

					if (extLogMap.containsKey(logDay)) { // 一锟斤拷未锟斤拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷锟街撅拷募锟�
						File extFile = (File) extLogMap.get(logDay);
						// 锟斤拷未锟斤拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷锟街撅拷募锟叫达拷锟斤拷应锟侥讹拷锟斤拷锟侥硷拷
						fw.write(extFile.getName() + NEXT_LINE);
						// 锟斤拷未锟斤拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷锟街撅拷募锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥柯�
						File targetExtFile = new File(currFile.getParentFile(),
								extFile.getName());
						LogUtils.fileCopy(extFile, targetExtFile);
						// 锟斤拷锟捷憋拷识锟窖硷拷锟斤拷锟斤拷锟斤拷募锟�
						File publishedExtFile = new File(extFile
								.getParentFile(), extFile.getName()
								+ PUBLISHED_CHECKFILE);
						extFile.renameTo(publishedExtFile);

					}
					fw.write("999999" + NEXT_LINE);
					fw.flush();
					fw.close();
					LogUtils.fileCopy(checkFile, targetFile);
					// 锟斤拷锟窖凤拷锟斤拷锟侥讹拷锟斤拷锟侥硷拷锟斤拷锟捷憋拷识
					File publishedCheckFile = new File(checkFile
							.getParentFile(), checkFile.getName()
							+ PUBLISHED_CHECKFILE);
					checkFile.renameTo(publishedCheckFile);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		}
		extLogMap.clear();
		extLogMap = null;
		chkLogMap.clear();
		chkLogMap = null;
		keyDate.clear();
		keyDate = null;

	}

	/**
	 * 锟斤拷莸锟角笆憋拷锟斤拷卸锟斤拷欠锟揭拷锟斤拷锟揭碉拷锟绞憋拷锟斤拷锟斤拷锟�
	 * 
	 * @return boolean
	 */
	private boolean inCheckTrap() {

		long n = System.currentTimeMillis();
		long check = currDate + ts_offset * 1000;
		long ts_trap = n - check;
		if (ts_trap < 0) {// 锟节硷拷榉段э拷冢锟斤拷锟揭拷锟斤拷
			return true;
		}
		if (first_on_day) { // 锟斤拷一锟斤拷越锟斤拷一锟斤拷
			// 锟斤拷锟斤拷前锟斤拷锟斤拷亩锟斤拷锟斤拷募锟�
			publishOldCheckFile();
			first_on_day = false;

		}
		/*
		 * if (lastDayTime<currDate) {//某锟斤拷锟斤拷锟斤拷映锟斤拷诤锟侥碉拷一锟斤拷锟斤拷志锟斤拷锟斤拷要锟结供锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟侥硷拷 if
		 * (first_on_day) {//锟斤拷一锟斤拷越锟斤拷一锟斤拷 first_on_day = false; //锟斤拷锟斤拷前一锟斤拷亩锟斤拷锟斤拷募锟�//
		 * publishCheckFile();
		 * 
		 * //删锟斤拷锟窖撅拷锟斤拷锟竭的伙拷锟斤拷锟侥硷拷 //statFile.delete(); } }
		 */
		// last_ts_trap=ts_trap;
		return false;
	}

	/**
	 * 锟叫讹拷锟角凤拷锟角把碉拷前锟斤拷志追锟接碉拷锟斤拷锟斤拷锟斤拷锟街撅拷募锟斤拷锟�
	 * 
	 * @param msg
	 *            Object
	 * @return boolean
	 */
	private boolean isLog2Extra(Object msg) {
		/*
		 */if (msg instanceof BizLogContent) { // 锟斤拷通锟斤拷锟斤拷锟斤拷锟斤拷冉锟斤拷欠锟叫达拷锟斤拷锟揭伙拷募锟�

			long bizTsDate = ((BizLogContent) msg).getTimestamp();
			if (bizTsDate > 0) { // 锟叫革拷值锟斤拷锟叫比斤拷
				if (currDate > bizTsDate) { // 锟斤拷前系统时锟斤拷锟揭碉拷锟斤拷锟街臼憋拷锟襟，硷拷锟斤拷锟斤拷锟剿第讹拷锟斤拷
					// lastDay.append(bizTsDate);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 锟斤拷锟角耙伙拷锟侥诧拷锟斤拷锟斤拷锟街撅拷募锟斤拷锟�
	 * 
	 * @return String
	 */
	private String getExtraLogFileName() {
		int index = lastLogFilePrefix.lastIndexOf(FILE_SEP);
		// 锟矫碉拷去锟斤拷路锟斤拷锟斤拷锟侥硷拷锟斤拷
		String realName = lastLogFilePrefix.substring(index + 1);

		String extraFileName = realName + "." + (lastLogFileSize + 1)
				+ LogConstants.LOG_BACKUP_SUFFIX;
		return extraFileName;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷业锟斤拷时锟斤拷锟叫★拷诘锟角跋低呈憋拷锟侥ｏ拷锟窖革拷锟斤拷志写锟斤拷一锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷
	 * 
	 * @param event
	 *            LoggingEvent
	 * @return boolean 锟斤拷锟斤拷欠锟叫达拷锟斤拷锟斤拷锟斤拷锟街�
	 */
	private boolean log2extraFile(LoggingEvent event) {
		// 锟斤拷锟斤拷欠锟叫达拷锟斤拷锟斤拷锟斤拷锟街�
		boolean hasLog = false;

		// StringBuffer lastDay=new StringBuffer();
		if (inCheckTrap() && event != null) {
			Object msg = event.getMessage();
			boolean needAppendExtra = isLog2Extra(msg);
			if (needAppendExtra) {// 锟节硷拷锟绞憋拷锟轿ｏ拷锟斤拷锟斤拷业锟斤拷时锟斤拷锟叫★拷诘锟角跋低呈憋拷锟�写锟斤拷锟斤拷锟斤拷锟侥硷拷
				String statPath = ServerInfo.getAppRootPath() + FILE_SEP
						+ "log" + FILE_SEP + CHECKFILE_PATH + FILE_SEP;
				File path = new File(statPath);
				if (!path.exists()) {
					path.mkdir();
				}

				String extraFileName = getExtraLogFileName();
				extFile = new File(path, extraFileName);
				OutputStreamWriter exSw;
				FileOutputStream exFw;
				try {
					if (extFile.exists()) {// 锟斤拷写锟斤拷锟窖达拷锟节的讹拷锟斤拷锟侥硷拷锟斤拷锟斤拷
						exFw = new FileOutputStream(extFile, true);
					} else {// 锟铰斤拷锟斤拷锟斤拷锟侥硷拷
						exFw = new FileOutputStream(extFile);
					}
					exSw = new OutputStreamWriter(exFw, encoding);
					if (msg instanceof String) {
						exSw.write((String) msg + NEXT_LINE);
						hasLog = true;
					} else if (msg instanceof BizLogContent) {
						String content = ((BizLogContent) msg).toString();
						exSw.write(content + NEXT_LINE);
						hasLog = true;
					}
					exSw.flush();
					exFw.flush();
					exSw.close();
					exFw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		}
		return hasLog;
	}

	private long getCurrDate() {
		// 取锟矫碉拷锟斤拷锟斤拷锟斤拷锟�
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		Date currStatDate = cal2.getTime();
		// currDate锟角碉拷锟斤拷锟�锟斤拷
		currDate = currStatDate.getTime();
		if (currDate > lastDayTime) {// 锟斤拷锟角帮拷锟斤拷锟斤拷锟较次硷拷锟斤拷锟斤拷锟揭拷锟剿碉拷锟斤拷锟斤拷锟斤拷碌锟揭伙拷锟�
			first_on_day = true;
		}
		return currDate;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�锟斤拷式为yyyyMMdd
	 * 
	 * @return long
	 */
	private String getLastDate() {
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		cal2.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = LogUtils.toString(new Date(cal2.getTimeInMillis()),
				"yyyyMMdd");
		return yesterday;
	}

	private long getDateOfFile(long fileTime) {
		// 锟斤拷莶锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(fileTime);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		return cal2.getTimeInMillis();
	}

	/**
	 * 锟斤拷业锟斤拷时锟斤拷锟斤拷锟斤拷诘锟角帮拷锟斤拷锟绞憋拷锟叫达拷锟斤拷细锟叫∈憋拷锟斤拷锟街�
	 * 
	 * @param event
	 *            LoggingEvent
	 */
	public void subAppend(LoggingEvent event) {
		long n = System.currentTimeMillis();
		// boolean log2last=append2LastHour(event.getMessage());

		// if ((n >= nextCheck)&&!log2last)
		LogLog.debug("n:" + n + "nextCheck:" + nextCheck);
		if (n >= nextCheck) {// 锟斤拷要锟斤拷志锟斤拷锟斤拷,锟斤拷锟斤拷一小时
			LogLog.debug("Now rollOverForTime");
			now.setTime(n);
			// 锟斤拷住锟较革拷小时锟斤拷应锟斤拷锟斤拷时时锟斤拷
			lastDayTime = currDate;
			// currDate锟斤拷锟斤拷为锟斤拷锟斤拷0锟斤拷
			getCurrDate();

			nextCheck = rc.getNextCheckMillis(now);
			try {
				rollOverForTime();
			} catch (IOException ioe) {
				LogLog.error("rollOver() failed.", ioe);
			}
		}
		// time roll first
		if ((fileName != null)
				&& ((CountingQuietWriter) qw).getCount() >= maxFileSize) {
			rollOverForSize();
		}
		if (!log2extraFile(event)) {// 没锟斤拷写锟斤拷锟斤拷锟斤拷锟斤拷志锟斤拷锟斤拷写锟斤拷锟斤拷志锟斤拷锟斤拷锟斤拷锟叫达拷锟�
			if (event != null) {
				super.directSubAppend(event);
			}
		}
	}

	/**
	 * 锟斤拷锟斤拷锟街撅拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷模式锟斤拷锟矫碉拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷械锟斤拷锟斤拷锟�
	 * 
	 * @param pFileName
	 *            String实锟绞碉拷锟侥硷拷锟斤拷
	 * @param typeName
	 *            String锟侥硷拷锟斤拷锟斤拷锟斤拷
	 * @param pattern
	 *            String锟侥硷拷锟斤拷锟叫碉拷锟斤拷锟节革拷式
	 * @return String 统一锟斤拷yyyyMMdd锟侥革拷式锟斤拷锟斤拷
	 */
	private String getUnidateFromFileName(String pFileName, String typeName,
			String pattern) {
		// 锟矫碉拷锟斤拷模锟藉定锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟絧ullbiz.log.yyyy-MM-dd.bak
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			if (ch != '\'') {// 去锟斤拷'锟斤拷占位
				strBuf.append(ch);
			}
		}
		String templateFileName = typeName + strBuf.toString();
		int indYear = templateFileName.indexOf("yyyy");
		String year = pFileName.substring(indYear, indYear + 4);
		int indMonth = templateFileName.indexOf("MM");
		String month = pFileName.substring(indMonth, indMonth + 2);
		int indDay = templateFileName.indexOf("dd");
		String day = pFileName.substring(indDay, indDay + 2);
		return year + month + day;
	}

	/**
	 * 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟睫革拷时锟斤拷锟叫讹拷锟斤拷锟接︼拷亩锟斤拷锟斤拷募锟斤拷欠锟斤拷逊锟斤拷锟�
	 * 
	 * @param fileDate
	 *            锟侥硷拷锟斤拷锟斤拷锟斤拷薷锟绞憋拷锟�
	 * @return boolean
	 */
	private boolean isCheckReportPublished(long fileDate) {
		String statPath = ServerInfo.getAppRootPath() + FILE_SEP + "log"
				+ FILE_SEP + CHECKFILE_PATH + FILE_SEP;
		int index = fileName.lastIndexOf(FILE_SEP);
		// 锟矫碉拷去锟斤拷路锟斤拷锟斤拷锟侥硷拷锟斤拷
		String realName = fileName.substring(index + 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				stat_datepattern);
		// 锟斤拷煤锟斤拷募锟斤拷锟斤拷诙锟接︼拷亩锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷诤锟阶猴拷锟斤拷锟�006-06-14.999999
		String statDate = simpleDateFormat.format(new Date(
				getDateOfFile(fileDate)));
		String statFileName = realName + statDate;
		// 锟窖凤拷锟斤拷锟侥憋拷锟捷讹拷锟斤拷锟侥硷拷
		File statBakFile = new File(statPath, statFileName
				+ PUBLISHED_CHECKFILE);
		if (statBakFile.exists()) { // 锟斤拷锟斤拷锟侥硷拷锟窖凤拷锟斤拷锟斤拷锟酵诧拷锟斤拷写锟斤拷锟斤拷锟侥硷拷锟斤拷
			return true;
		}
		return false;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟借定时锟轿猴拷锟斤拷志锟斤拷锟斤拷锟斤拷志锟侥硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷
	 * 
	 * @param logFileName
	 *            String the roll file name like biz.log.2005-03-08-14.1.bak
	 * @param seq
	 *            String the roll file 's sequence No
	 */
	private void logStastics(String logFileName, String seq) {
		// get the start index of .bak
		int rIndex = logFileName.lastIndexOf(LogConstants.LOG_BACKUP_SUFFIX);
		if (seq != null) {// the seq is the sequence number when log roll over
							// size
			rIndex = rIndex - seq.length() - 1;
		}
		// get the end index of raw file
		int lIndex = fileName.length();
		String datePart = logFileName.substring(lIndex, rIndex);
		try {
			// the date of the biz log file output
			Date rawDate = sdf.parse(datePart);
			Calendar cal = Calendar.getInstance();
			cal.setTime(rawDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			// 锟斤拷锟斤拷志锟斤拷录锟斤拷时锟斤拷,锟斤拷确锟斤拷锟斤拷
			Date logDate = cal.getTime();
			// String statPattern="'.'yyyy-MM-dd'.999999'";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					stat_datepattern);
			// get the string of stat log date
			String statDate = simpleDateFormat.format(logDate);

			int index = fileName.lastIndexOf(FILE_SEP);
			// 锟矫碉拷去锟斤拷路锟斤拷锟斤拷锟侥硷拷锟斤拷
			String realName = fileName.substring(index + 1);
			// 锟斤拷时锟斤拷stat目录锟斤拷锟斤拷log锟斤拷
			String statPath = ServerInfo.getAppRootPath() + FILE_SEP + "log"
					+ FILE_SEP + CHECKFILE_PATH + FILE_SEP;
			File path = new File(statPath);
			if (!path.exists()) {
				path.mkdir();
			}
			String statFileName = realName + statDate;
			statFile = new File(path, statFileName);
			/*
			 * if (isCheckReportPublished()) {//锟斤拷锟秸的讹拷锟斤拷锟侥硷拷锟窖凤拷锟斤拷锟斤拷logCheck.sh锟斤拷锟斤拷锟斤拷,锟酵诧拷锟截革拷锟斤拷锟斤拷
			 * return; }
			 */
			FileWriter fw = null;
			if (statFile.exists()) {
				fw = new FileWriter(statFile, true);
			} else {
				fw = new FileWriter(statFile);
			}
			// add timestamp---will removed at production
			/*
			 * SimpleDateFormat sdfTemp=new SimpleDateFormat("yyyyMMdd
			 * HH:mm:ss-SSS"); Date now=new Date();
			 * fw.write(sdfTemp.format(now));
			 */
			// add timestamp---will removed at production
			int lastFSIndex = logFileName.lastIndexOf(FILE_SEP);
			fw.write(logFileName.substring(lastFSIndex + 1) + NEXT_LINE);
			fw.flush();
			fw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * reload this method to generate the stastics
	 * 
	 * @throws IOException
	 */
	public void rollOverForTime() throws IOException {
		// 每锟截伙拷锟斤拷时锟斤拷锟斤拷锟较达拷锟斤拷志锟斤拷锟斤拷锟�
		lastLogFilePrefix = null;
		/* Compute filename, but only if datePattern is specified */
		LogLog.debug("Let's rock and roll");
		if (datePattern == null) {
			LogLog.debug("datePattern==null");
			errorHandler.error("Missing DatePattern option in rollOver().");
			return;
		}

		String datedFilename = fileName + sdf.format(now);
		LogLog.debug("datedFilename:" + datedFilename);
		// It is too early to roll over because we are still within the
		// bounds of the current interval. Rollover will occur once the
		// next interval is reached.
		if (scheduledFilename.equals(datedFilename)) {
			LogLog.debug("scheduledFilename.equals(datedFilename)");
			return;
		}

		// close current file, and rename it to datedFilename
		this.closeFile();
		long currFileDate = currFile.lastModified();
		LogLog.debug("isCheckReportPublished(currFileDate):"
				+ isCheckReportPublished(currFileDate));
		if (isCheckReportPublished(currFileDate)) {// 锟斤拷锟侥硷拷锟斤拷锟节讹拷应锟侥讹拷锟斤拷锟侥硷拷锟窖凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷志锟侥硷拷
			currFile.delete();
			for (int i = 1; i <= maxBackupIndex; i++) {// 锟斤拷然currFile锟窖撅拷锟斤拷锟节ｏ拷锟斤拷锟斤拷锟斤拷xx.log.1,xxxlog.2锟斤拷锟斤拷锟斤拷
				String before = fileName + "." + i;
				File files = new File(before);
				if (files.exists()) {
					files.delete();
				}
			}
			setFile(fileName, false, this.bufferedIO, this.bufferSize);
			return;
		}
		File target = new File(scheduledFilename
				+ LogConstants.LOG_BACKUP_SUFFIX);
		if (target.exists()) {
			target.delete();
		}

		File file = new File(fileName);
		for (int i = 1; i <= maxBackupIndex; i++) { // roll for all size-backup
													// files
			String before = fileName + "." + i;
			File files = new File(before);
			String after = scheduledFilename + "." + i
					+ LogConstants.LOG_BACKUP_SUFFIX;
			if (files.exists()) { // only backup existed one
				File targets = new File(after);
				if (targets.exists()) {
					targets.delete();
				}
				boolean result = files.renameTo(targets);
				if (result) {
					// 只锟斤拷i锟斤拷锟斤拷锟角革拷
					lastLogFileSize = i;
					logStastics(after, String.valueOf(i));
					LogLog.debug(before + " -> " + after);
				} else {
					LogLog.error("Failed to rename [" + before + "] to ["
							+ after + "].");
				}
			} else {
				break;
			}
		}

		boolean result = file.renameTo(target);
		LogLog.debug("file.renameTo(target) result:" + result);
		if (result) {
			// 锟斤拷录锟较达拷锟斤拷志锟斤拷锟斤拷募锟角白�
			logStastics(target.getAbsolutePath(), null);
			lastLogFilePrefix = scheduledFilename;
			LogLog.debug(fileName + " -> " + scheduledFilename);

		} else {
			LogLog.error("Failed to rename [" + fileName + "] to ["
					+ scheduledFilename + "].");
		}

		try {
			// This will also close the file. This is OK since multiple
			// close operations are safe.
			this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
		} catch (IOException e) {
			errorHandler.error("setFile(" + fileName + ", false) call failed.");
		}
		scheduledFilename = datedFilename;
		LogLog.debug("scheduledFilename after roll:" + scheduledFilename);
	}

	public void setTs_offset(int ts_offset) {
		this.ts_offset = ts_offset;
	}

	public void setStat_datepattern(String stat_datepattern) {
		this.stat_datepattern = stat_datepattern;
	}

	/**
	 * @return Returns the instantCreatChkfile.
	 */
	public String getInstantCreatChkfile() {
		return instantCreatChkfile;
	}

	/**
	 * @param instantCreatChkfile
	 *            The instantCreatChkfile to set.
	 */
	public void setInstantCreatChkfile(String instantCreatChkfile) {
		this.instantCreatChkfile = instantCreatChkfile;
	}

}
