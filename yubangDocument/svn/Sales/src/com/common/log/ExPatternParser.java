package com.common.log;
import org.apache.log4j.helpers.FormattingInfo;  
import org.apache.log4j.helpers.PatternConverter;  
import org.apache.log4j.helpers.PatternParser;  
import org.apache.log4j.spi.LoggingEvent;
public class ExPatternParser extends PatternParser{
	
	public ExPatternParser(String pattern) {
		super(pattern);
	}
	/** 
     * 閲嶅啓finalizeConverter锛屽鐗瑰畾鐨勫崰浣嶇杩涜澶勭悊锛孴琛ㄧず绾跨▼ID鍗犱綅绗�
     */  
	@Override 
	protected void finalizeConverter(char c) {
		if (c == 'T') {
			this.addConverter(new ExPatternConverter(this.formattingInfo)); 
		}else{
			super.finalizeConverter(c);
		}
	}
	private static class ExPatternConverter extends PatternConverter{
			public ExPatternConverter(FormattingInfo fi) {
				super(fi);
			}
			/** 
		        * 褰撻渶瑕佹樉绀虹嚎绋婭D鐨勬椂鍊欙紝杩斿洖褰撳墠璋冪敤绾跨▼鐨処D
		        */  
			@Override 
			protected String convert(LoggingEvent event) {
				
				return String.valueOf(Thread.currentThread().getId());
		}
	
	}
      
}
