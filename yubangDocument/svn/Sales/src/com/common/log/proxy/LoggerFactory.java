
package com.common.log.proxy;

/**
 * <p>Title: LoggerFactory</p>
 * <p>Description: the factory to construct different logger</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire</p>
 * @author x_biran
 * @version 1.0 history: created at 3/11/2007
 */
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.common.log.JLogger;
import com.common.log.constants.LogConstants;
import com.common.log.proxy.config.ErrInfoInitiator;

public class LoggerFactory
{

    private static HashMap bizLoggerMap = new HashMap(10);

    private static ErrInfoInitiator errInfo;

    private static String appRootPath;

    public LoggerFactory()
    {

    }

    public static JLogger getLogger(String className)
    /**
     * get the logger trace the running status
     */
    {

        JLogger runLog = null;
        if (bizLoggerMap.containsKey(className))
        {// if has stored return reference
            return ( JLogger ) bizLoggerMap.get(className);
        }
        // else new one and store it
        if (className.startsWith(LogConstants.BIZ_LOG_TYPE))
        {// biz logger
            runLog = new JBLogger(className);
        }
        else
        {// non-biz logger
            runLog = new JLogger(className);
        }
        bizLoggerMap.put(className, runLog);
        return runLog;
    }

    public static JLogger getLogger(Class className)
    /**
     * get the logger trace the running status
     */
    {

        JLogger runLog = new JLogger(className);
        return runLog;
    }

    public static void configLog(String file, String refresh){
    	configLog( file,  refresh,  "/");
    }
    
    /**
     *
     * @param file log4j.xml锟侥硷拷锟斤拷锟斤拷(路锟斤拷
     * @param refresh 刷锟斤拷时锟斤拷
     * @param appRootPath 系统应锟矫伙拷锟斤拷锟斤拷路锟斤拷锟斤拷锟斤拷路锟斤拷锟街革拷锟斤拷尾
     */
    public static void configLog(String file, String refresh, String appRootPath)
    {

        try
        {

            if ((appRootPath == null) || appRootPath.equals(""))
            {// no config file found
                System.err.println("appRootPath not set");
                return;
            }
            LoggerFactory.appRootPath = appRootPath;
            if ((file == null) || file.equals(""))
            {// no config file found
                System.err.println("log4j.xml not set");
                return;
            }
            String configFile = file.replace('/',
                                             LogConstants.FILE_SEP.charAt(0));


            File confFile = new File(configFile);
            if (!confFile.exists())
            {
                System.out.println("log4j.xml not found");
                return;
            }
            if ((refresh == null) || refresh.equals(""))
            {// no refresh provided
                if (configFile.endsWith(".properties"))
                {
                    PropertyConfigurator.configure(configFile);
                }
                else if (configFile.endsWith(".xml"))
                {
                    DOMConfigurator.configure(configFile);
                }
            }
            else
            {// refresh needed
                long period = Long.parseLong(refresh);
                if (configFile.endsWith(".properties"))
                {
                    PropertyConfigurator.configureAndWatch(configFile,
                                                           period * 1000);

                }
                else if (configFile.endsWith(".xml"))
                {
                    DOMConfigurator.configureAndWatch(configFile, period * 1000);

                }
            }

        }

        catch (NumberFormatException e)
        {
            DOMConfigurator.configureAndWatch(file);
            e.printStackTrace();
            System.err.println("refresh period is not long type:" + refresh);
        }
    }

    public static void configLog(String file)
    {

        if ((file == null) || file.equals(""))
        {// no config file found
            System.err.println("log4j.xml not set");
            return;
        }
        if (file.endsWith(".properties"))
        {
            PropertyConfigurator.configure(file);

        }
        else if (file.endsWith(".xml"))
        {

            DOMConfigurator.configure(file);

        }
        System.err.println("load log config successfully");
    }

    public static void configLog(InputStream is)
    {

        if (is == null)
        {// no config file found
            System.err.println("log4j.xml not set");
            return;
        }
        new DOMConfigurator().doConfigure(is, LogManager.getLoggerRepository());
        System.err.println("load log config successfully");
    }

    /**
     * output log to console before read log4j.xml as default log
     */
    public static void configLog()
    {

        BasicConfigurator.configure();
        System.err.println("config default log successfully");
    }

    public static void closeAllLog(JLogger log)
    {

        Logger logger = log.getInternalLog();
        logger.getLoggerRepository().setThreshold(Level.OFF);
    }

    public static void releaseAllLog()
    {

        LogManager.shutdown();
    }

    /**
     * load the error.properties file to get the error message
     *
     * @param file the file path
     */
    public static void loadErrorInfo(String file)
    {

        if ((file == null) || file.equals(""))
        {// no config file found
            System.err.println("error.properties not set");
            return;
        }
        String configFile = file.replace('/', LogConstants.FILE_SEP.charAt(0));


        File confFile = new File(configFile);
        if (!confFile.exists())
        {
            System.err.println("error.properties not found");
            return;
        }

        errInfo = new ErrInfoInitiator();
        errInfo.loadErrorInfo(configFile);

    }

    public static void loadErrorInfo(InputStream is)
    {

        if (is == null)
        {// no config file found
            System.err.println("error.properties not set");
            return;
        }
        errInfo = new ErrInfoInitiator();
        errInfo.loadErrorInfo(is);
        System.err.println("load error info successfully");
    }

    /**
     * get the error message according to the error code
     *
     * @param errorCode predefined code
     * @return error message
     */
  public  static String getErrorMsg(int errorCode)
    {

        if (errInfo == null)
        {
            System.err.println("error message not initialized for code:"
                               + errorCode);
            return null;
        }
        return errInfo.getErrMsg(errorCode);
    }

    /**
     * 锟斤拷锟截碉拷前应锟斤拷系统锟斤拷锟斤拷要锟斤拷母锟侥柯�
     * @return String
     */
    public static String getAppRootPath()
    {

        return appRootPath;
    }
}
