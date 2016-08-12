package com.common.log;
import org.apache.log4j.PatternLayout;  
import org.apache.log4j.helpers.PatternParser; 
public class ExPatternLayout extends PatternLayout{
	public ExPatternLayout(String pattern) {
		super(pattern);
	}
	public ExPatternLayout() { 
		super();
	}
	/** 
     * 閲嶅啓createPatternParser鏂规硶锛岃繑鍥濸atternParser鐨勫瓙绫�
     */ 
	@Override 
	protected PatternParser createPatternParser(String pattern) { 
		return new ExPatternParser(pattern);
	}
}
