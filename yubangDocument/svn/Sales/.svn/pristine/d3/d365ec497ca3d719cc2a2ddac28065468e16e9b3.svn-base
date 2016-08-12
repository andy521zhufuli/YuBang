package com.common.log.newproxy;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.LoggingEvent;


/**
 * <p> * Title: BizTimeSizeRollingFileAppender * </p>
 * <p> * Description: 涓氬姟鏃ュ織杩藉姞瀹炵幇绫伙紝瀹炵幇鎸夋椂闂淬�澶у皬鍒嗛殧鏃ュ織锛屽苟鎸夋椂闂存牸寮忎骇鐢熷璐︽枃浠�* </p>
 * <p> * Copyright: Copyright (c) 2012 * </p>
 * <p> * Company: Aspire Technologies * </p>
 * 
 * @author jimin
 * @version 1.0.0 history
 * 
 */

public class BizTimeSizeRollingFileAppender extends FileAppender
{

    static final int TOP_OF_TROUBLE=-1;
    static final int TOP_OF_MINUTE = 0;
    static final int TOP_OF_HOUR   = 1;
    static final int HALF_DAY      = 2;
    static final int TOP_OF_DAY    = 3;
    static final int TOP_OF_WEEK   = 4;
    static final int TOP_OF_MONTH  = 5;
    static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    /**
     * 鏄惁浣跨敤璺熻釜绾跨▼锛屼互渚垮敖蹇敓鎴愬甯愭枃浠�
     */
    private String instantCreatChkfile = "false";
    
    /**
     * 鏃ュ織鏂囦欢涓殑鏃ユ湡鏍煎紡瀹氫箟:缂虹渷鏄�.'yyyy-MM-dd
     */
    private String datePattern = "'.'yyyy-MM-dd";
    
    /**
     * 瀵瑰笎鏂囦欢涓殑鏃ユ湡鏍煎紡瀹氫箟:缂虹渷鏄�.'yyyy-MM-dd'.999999'
     */
    private String stat_datepattern = "'.'yyyy-MM-dd'.999999'";
    
    /**
     * The default maximum file size is 10MB.
     */
    protected long maxFileSize = 10 * 1024 * 1024;
    
    //鏃ュ織鏂囦欢鎵╁睍鍚�
    private static final String BACKUP_SUFFIX = ".bak";
    //瀵硅处鏂囦欢涓存椂鎵╁睍鍚�
    private static final String CHECK_FILE_EXT=".chk";
    
    private static final String CHECK_FILE_SEPARATOR="|";
    private static final String NEXT_LINE=System.getProperty("line.separator");
    
    private String maxBackupIndex;
    //鏃ュ織鏂囦欢鐩稿叧鍙橀噺
    int nextNum =0;                 //澶у皬瓒呰繃鎸囧畾鍊肩殑涓嬫爣
    Date now = new Date();          //褰撳墠妫�煡鏃堕棿
    SimpleDateFormat sdf;
    RollingCalendar rc = new RollingCalendar();
    String scheduledFilename;       //涓嬩釜鏃ュ織鏂囦欢鏂囦欢鍚�
    private long nextCheck = System.currentTimeMillis () - 1;       //涓嬫妫�煡鏃堕棿
    
    //瀵硅处鏂囦欢鐩稿叧鍙橀噺
    Date stat_now = new Date();     //褰撳墠妫�煡鏃堕棿
    SimpleDateFormat stat_sdf;
    RollingCalendar stat_rc = new RollingCalendar();
    String stat_fileName;           //褰撳墠瀵硅处鏂囦欢鏂囦欢鍚�
    String stat_scheduledFilename;  //涓嬩釜瀵硅处鏂囦欢鏂囦欢鍚�
    private long stat_nextCheck = System.currentTimeMillis () - 1;  //涓嬫妫�煡鏃堕棿
    protected QuietWriter stat_qw;
    
    /**
     * The default constructor does nothing.
     */

    public BizTimeSizeRollingFileAppender()
    {

    }

    public BizTimeSizeRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException
    {

        super(layout, filename, true);
        this.datePattern = datePattern;
        activateOptions();
    }
    
    /**
     * 璁剧疆鐩稿叧鍙傛暟
     */
    public void activateOptions()
    {
        super.activateOptions();
        
        // 鍒濆鍖栧璐︽枃浠剁浉鍏冲彉閲�
        if (stat_datepattern != null && fileName != null)
        {
            stat_now.setTime(System.currentTimeMillis());
            stat_sdf = new SimpleDateFormat(stat_datepattern);
            int type = computeCheckPeriod(stat_datepattern);
            // printPeriodicity(type);
            stat_rc.setType(type);
            File file = new File(fileName);
            stat_scheduledFilename = this.getStatScheduledFilename(new Date(file.lastModified()));
            //stat_fileName = "stat_" + fileName+CHECK_FILE_EXT;
            resetStatQW();
        }
        else
        {
            LogLog.error("Either File or DatePattern options are not set for appender [" + name + "].");
        }
        
        //鍒濆鍖栨棩蹇楁枃浠剁浉鍏冲彉閲�
        if (datePattern != null && fileName != null)
        {
            now.setTime(System.currentTimeMillis());
            sdf = new SimpleDateFormat(datePattern);
            int type = computeCheckPeriod(datePattern);
            // printPeriodicity(type);
            rc.setType(type);
            File file = new File(fileName);
            //寰楀埌澶у皬闄愬埗鐨勪笅鏍�
            nextNum=getNextNum();
            scheduledFilename = this.getScheduledFilename(new Date(file.lastModified()));
        }
        else
        {
            LogLog.error("Either File or DatePattern options are not set for appender [" + name + "].");
        }
        
        // 鏄惁澧炲姞鍒颁睛鍚�
        LogLog.debug("this.instantCreatChkfile:" + this.instantCreatChkfile);
        if ("true".equals(this.instantCreatChkfile))
        {
             ChkfileCreatorObserver.addToListen(this);
        }
    }

    @Override
    protected void subAppend(LoggingEvent event)
    {

        long n = System.currentTimeMillis();
        if (n >= nextCheck)
        {
            now.setTime(n);
            nextCheck = rc.getNextCheckMillis(now);
            try
            {
                rollOverForTime();
            }
            catch (IOException ioe)
            {
                LogLog.error("rollOver() failed.", ioe);
            }
        }
        
        if(fileName != null && qw != null) {
            long size = ((CountingQuietWriter) qw).getCount();
            if (size >= maxFileSize ) {
                try
                {
                    rollOverForSize();
                }
                catch (IOException ioe)
                {
                    LogLog.error("rollOver() failed.", ioe);
                }
            }
        }
        
        if(event != null){
            super.subAppend(event);    
        }
    }

    @Override
    protected void setQWForFiles(Writer writer) {
        this.qw = new CountingQuietWriter(writer, errorHandler);
    }
    
    @Override
    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException
    {
        super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
        if (append)
        {
            File f = new File(fileName);
            (( CountingQuietWriter ) qw).setCount(f.length());
        }
    }
    @Override
    public void setFile(String file) {
        
        String val = file.trim();
        val = val.replace('/', File.separatorChar);
        fileName = LogHelper.getLogRootPath() + val;

        // create non-exist path
        LogLog.debug("fileName:" + fileName);

        int index = fileName.lastIndexOf(File.separatorChar);
        if (index > 0) {
            String sPath = fileName.substring(0, index);
            File path = new File(sPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            
            String t_fileName = fileName.substring(index+1);
            stat_fileName = sPath+File.separatorChar+"stat_" + t_fileName+CHECK_FILE_EXT;
        }else{
            stat_fileName = "stat_" + fileName+CHECK_FILE_EXT;
        }

        LogLog.debug("stat_fileName:" + stat_fileName);
    }
    
    /**
     * 褰撳ぇ灏忚秴杩囬檺鍒舵椂鎹㈡枃浠跺悕
     * @throws IOException
     */
    protected void rollOverForSize() throws IOException{
        this.nextNum++;
        rollOver(false);
        
    }
    /**
     * 褰撴椂闂磋秴杩囬檺鍒舵椂鎹㈡枃浠跺悕
     * @throws IOException
     */
    protected void rollOverForTime() throws IOException{
        if (datePattern == null) {
            errorHandler.error("Missing DatePattern option in rollOver().");
            return;
          }

        rollOver(true);
          
    }

    /**
     * 鎹㈡枃浠跺悕锛屾崲瀹屽悗闇�鍐欏璐︽枃浠�
     * @param isRollForTime
     * @throws IOException
     */
    void rollOver(boolean isRollForTime) throws IOException {
        
        String datedFilename = this.getScheduledFilename(now);
        if (scheduledFilename.equals(datedFilename)) {
          return;
        }
//      close current file, and rename it to datedFilename
        this.closeFile();

        File target  = new File(scheduledFilename);
        if (target.exists()) {
          target.delete();
        }

        File file = new File(fileName);
        boolean result = file.renameTo(target);
        if(result) {
          LogLog.debug(fileName +" -> "+ scheduledFilename);
        } else {
          LogLog.error("Failed to rename ["+fileName+"] to ["+scheduledFilename+"].");
        }

        try {
          // This will also close the file. This is OK since multiple
          // close operations are safe.
          this.setFile(fileName, true, this.bufferedIO, this.bufferSize);
        }
        catch(IOException e) {
          errorHandler.error("setFile("+fileName+", true) call failed.");
        }
        
        //璁板綍瀵硅处鏂囦欢
        appendStat(scheduledFilename);
        
        if(isRollForTime){
            this.nextNum=0;
        }
        scheduledFilename = this.getScheduledFilename(now);
    }
    
    /**
     * 灏嗘枃浠跺悕璁板綍鍒板璐︽枃浠�
     * @param filename
     */
    protected void appendStat(String filename)
    {

        File file = new File(filename);
        if (file.exists())
        {
        	StringBuffer sb = new StringBuffer();
            sb.append(file.getName());
//            sb.append(file.getName()).append(CHECK_FILE_SEPARATOR);
//            sb.append(file.length()).append(CHECK_FILE_SEPARATOR);
//            try
//            {
//                sb.append(this.countLines(filename));
//            }
//            catch (IOException e)
//            {
//                LogLog.error("write to check file error! filename:" + fileName + ",stat_filename:" + stat_fileName, e);
//            }
            sb.append(NEXT_LINE);
            // 灏嗘枃浠跺悕鍐欏叆瀵硅处鏂囦欢
            this.stat_qw.write(sb.toString());
            this.stat_qw.flush();
        }
        else
        {
            LogLog.error("log file is not exists! filename:" + filename);
        }

        //妫�煡鏄惁闇�鏇存崲瀵硅处鏂囦欢鐨勬枃浠跺悕
        long n = System.currentTimeMillis();
        if (n >= stat_nextCheck)
        {
            stat_now.setTime(n);
            stat_nextCheck = stat_rc.getNextCheckMillis(stat_now);
            try
            {
                rollOverStat();
            }
            catch (IOException ioe)
            {
                LogLog.error("rollOver() failed.", ioe);
            }
        }
    }
    /**
     * 淇敼瀵硅处鏂囦欢鏂囦欢鍚�
     * @throws IOException 
     *
     */
    private void rollOverStat() throws IOException {
        /* Compute filename, but only if datePattern is specified */
        if (stat_datepattern == null) {
          errorHandler.error("Missing DatePattern option in rollOver().");
          return;
        }

        String datedFilename = this.getStatScheduledFilename(stat_now);
        // It is too early to roll over because we are still within the
        // bounds of the current interval. Rollover will occur once the
        // next interval is reached.
        if (stat_scheduledFilename.equals(datedFilename)) {
          return;
        }

        File target  = new File(stat_scheduledFilename);
        if (target.exists()) {
          target.delete();
        }

        //鍐欏叆涓�999999锛屽苟鏀瑰悕鎴愮洰鏍囧璐︽枃浠�
        File file = new File(stat_fileName);
        String line = "999999"+NEXT_LINE;
        this.stat_qw.write(line);
        this.stat_qw.flush();
        this.stat_qw.close();
        
        boolean result = file.renameTo(target);
        if(result) {
          LogLog.debug(stat_fileName +" -> "+ stat_scheduledFilename);
        } else {
          LogLog.error("Failed to rename ["+stat_fileName+"] to ["+stat_scheduledFilename+"].");
        }
        stat_scheduledFilename = datedFilename;
        this.resetStatQW();
    }


    /**
     * 閲嶇疆瀵硅处鏂囦欢鐨凲uietWriter
     *
     */
    private void resetStatQW()
    {
        try
        {
            if (stat_qw != null)
            {
                stat_qw.close();
                stat_qw = null;
            }
            this.stat_qw = new QuietWriter(new FileWriter(stat_fileName, true), errorHandler);
        }
        catch (IOException e)
        {
            LogLog.error("reset stat qw error.",e);
        }
    }
    
    /**
     * 寰楀埌涓嬩竴涓棩蹇楁枃浠剁殑鏂囦欢鍚�
     * 濡傛灉澶у皬闄愬埗鐨勪笅鏍囧ぇ浜�鍒欏姞涓嬫爣
     * @param now
     * @return
     */
    private String getScheduledFilename(Date now){
        StringBuffer sb = new StringBuffer();
        sb.append(fileName).append(sdf.format(now));
        if(nextNum>0){
            sb.append(".").append(nextNum);
        }
        sb.append(BACKUP_SUFFIX);
        return sb.toString();
    }
    
    /**
     * 寰楀埌涓嬩釜瀵硅处鏂囦欢鐨勬枃浠跺悕
     * @param now
     * @return
     */
    private String getStatScheduledFilename(Date now){
        StringBuffer sb = new StringBuffer();
        sb.append(fileName).append(stat_sdf.format(now));        
        return sb.toString();
    }
    
    /**
     * 寰楀埌鏂囦欢鍚嶇殑涓嬩釜搴忓彿
     * @return
     */
    private int getNextNum()
    {
        int ret = 0;
        // 鍏堜粠瀵硅处鏂囦欢涓彇锛屽鏋滄病鏈夊璐︽枃浠讹紝鍒欎粠鏃ュ織鐩綍涓彇锛屽鏋滀篃娌℃湁锛屽垯杩斿洖0
        try
        {
            File stat_file = new File(stat_fileName);
            if (stat_file.exists())
            {
                List list = FileUtils.readLines(stat_file);
                if (list.size() > 0)
                {
                    String lastLine = ( String ) list.get(list.size() - 1);
                    String fileName = stat_file.getName() + sdf.format(stat_now);
                    String num = lastLine.substring(fileName.length() + 1, lastLine.indexOf(BACKUP_SUFFIX));
                    ret = Integer.valueOf(num) + 1;
                }
                return ret;
            }
            else
            {
                LogLog.debug("check file is not exists.");
            }
        }
        catch (Exception e)
        {
            //璇ュ紓甯镐笉浣滆褰�
            //LogLog.error("get NextNum from check file error!", e);
        }
        
        try
        {
            File file = new File(fileName);
            String fileName = file.getName() + sdf.format(stat_now);
            String[] files = file.getParentFile().list();
            for (String s : files)
            {
                try
                {
                    if (s.startsWith(fileName)&&s.endsWith(BACKUP_SUFFIX))
                    {
                        String num = s.substring(fileName.length() + 1, s.indexOf(BACKUP_SUFFIX));
                        int i = Integer.valueOf(num).intValue()+1;
                        ret = ret < i?i:ret;
                    }
                }
                catch (Exception e)
                {
                    // 璇ュ紓甯镐笉浣滆褰�
                }
            }
        }catch(Exception e){
            LogLog.error("get NextNum from log file error!", e);
        }
        return ret;
    }
    
    /**
     * 寰楀埌鏂囦欢琛屾暟
     * @param filename
     * @return
     * @throws IOException
     */
    private int countLines(String filename) throws IOException
    {
        LineNumberReader reader = new LineNumberReader(new FileReader(filename));
        int cnt = 0;
        while ((reader.readLine()) != null)
        {
        }
        cnt = reader.getLineNumber();
        reader.close();
        return cnt;
    }  
    
    /**
     * 鏍规嵁鏃ユ湡鏍煎紡杩斿洖鏃ユ湡绫诲瀷
     * 
     * @param pattern
     * @return
     */
    int computeCheckPeriod(String pattern) {
        RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.getDefault());
        // set sate to 1970-01-01 00:00:00 GMT
        Date epoch = new Date(0);
        if(pattern != null) {
          for(int i = TOP_OF_MINUTE; i <= TOP_OF_MONTH; i++) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(gmtTimeZone); // do all date formatting in GMT
        String r0 = simpleDateFormat.format(epoch);
        rollingCalendar.setType(i);
        Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
        String r1 =  simpleDateFormat.format(next);
        if(r0 != null && r1 != null && !r0.equals(r1)) {
          return i;
        }
          }
        }
        return TOP_OF_TROUBLE; // Deliberately head for trouble...
      }
    
    public void setMaxFileSize(String value) {
        maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1);
        // maxFileSize=value;
    }

    public void setMaximumFileSize(long value) {
        // maxFileSize=OptionConverter.toFileSize(value,maxFileSize+1);
        maxFileSize = value;
    }
    public String getDatePattern()
    {
        return datePattern;
    }
    
    public void setDatePattern(String datePattern)
    {
        this.datePattern = datePattern;
    }
    
    public String getInstantCreatChkfile()
    {
        return instantCreatChkfile;
    }

    public void setInstantCreatChkfile(String instantCreatChkfile)
    {
        this.instantCreatChkfile = instantCreatChkfile;
    }
    
    public String getStat_datepattern()
    {
        return stat_datepattern;
    }

    public void setStat_datepattern(String stat_datepattern)
    {
        this.stat_datepattern = stat_datepattern;
    }
    

    /**
     *  RollingCalendar is a helper class to DailyRollingFileAppender.
     *  Given a periodicity type and the current time, it computes the
     *  start of the next interval.  
     * */
    class RollingCalendar extends GregorianCalendar {
      private static final long serialVersionUID = -3560331770601814177L;

      int type = TOP_OF_TROUBLE;

      RollingCalendar() {
        super();
      }  

      RollingCalendar(TimeZone tz, Locale locale) {
        super(tz, locale);
      }  

      void setType(int type) {
        this.type = type;
      }

      public long getNextCheckMillis(Date now) {
        return getNextCheckDate(now).getTime();
      }

      public Date getNextCheckDate(Date now) {
        this.setTime(now);

        switch(type) {
        case TOP_OF_MINUTE:
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        this.add(Calendar.MINUTE, 1);
        break;
        case TOP_OF_HOUR:
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        this.add(Calendar.HOUR_OF_DAY, 1);
        break;
        case HALF_DAY:
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        int hour = get(Calendar.HOUR_OF_DAY);
        if(hour < 12) {
          this.set(Calendar.HOUR_OF_DAY, 12);
        } else {
          this.set(Calendar.HOUR_OF_DAY, 0);
          this.add(Calendar.DAY_OF_MONTH, 1);
        }
        break;
        case TOP_OF_DAY:
        this.set(Calendar.HOUR_OF_DAY, 0);
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        this.add(Calendar.DATE, 1);
        break;
        case TOP_OF_WEEK:
        this.set(Calendar.DAY_OF_WEEK, getFirstDayOfWeek());
        this.set(Calendar.HOUR_OF_DAY, 0);
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        this.add(Calendar.WEEK_OF_YEAR, 1);
        break;
        case TOP_OF_MONTH:
        this.set(Calendar.DATE, 1);
        this.set(Calendar.HOUR_OF_DAY, 0);
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        this.add(Calendar.MONTH, 1);
        break;
        default:
        throw new IllegalStateException("Unknown periodicity type.");
        }
        return getTime();
      }
    }


	public String getMaxBackupIndex() {
		return maxBackupIndex;
	}

	public void setMaxBackupIndex(String maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}
    
}
