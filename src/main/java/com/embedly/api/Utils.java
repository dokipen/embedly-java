package com.embedly.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;

class Utils {
	private static Log log;
	private static Log noopLog = new NoopLog();
	
    /**
     * Utility method to join strings.  We don't use StringUtils because it
     * isn't available on the Android platform.
     */
    public static String stringJoin(ArrayList<String> parts, String seperator) {
    	StringBuffer buffer = new StringBuffer();
    	for (int i = 0; i < parts.size() - 1; ++i) {
    		buffer.append(parts.get(i));
    		buffer.append(seperator);
    	}
    	buffer.append(parts.get(parts.size() - 1));
    	return buffer.toString();
    }    

    public static String simpleHTTP(HttpClient httpClient, 
    		ResponseHandler<String> responseHandler,
    		String url, Map<String, String> headers) throws IOException {
        getLog().debug("calling  >> "+url);
        HttpGet httpget = new HttpGet(url);
        for (Map.Entry<String, String> header : headers.entrySet()) {
        	httpget.addHeader(header.getKey(), header.getValue());
        }
        String response = httpClient.execute(httpget, responseHandler);
        if (getLog().isDebugEnabled()) {
            getLog().debug("response << "+response);
        }
        return response;
    }
    
    public static void setLog(Log alog) {
    	log = alog;
    }
    
    public static Log getLog() {
    	if (null == log) {
    		log = noopLog;
    	}
    	return log;
    }
    
    public static class NoopLog implements Log {
		public void debug(Object arg0) {}
		public void debug(Object arg0, Throwable arg1) {}
		public void error(Object arg0) {}
		public void error(Object arg0, Throwable arg1) {}
		public void fatal(Object arg0) {}
		public void fatal(Object arg0, Throwable arg1) {}
		public void info(Object arg0) {}
		public void info(Object arg0, Throwable arg1) {}
		public boolean isDebugEnabled() {return false;}
		public boolean isErrorEnabled() {return false;}
		public boolean isFatalEnabled() {return false;}
		public boolean isInfoEnabled() {return false;}
		public boolean isTraceEnabled() {return false;}
		public boolean isWarnEnabled() {return false;}
		public void trace(Object arg0) {}
		public void trace(Object arg0, Throwable arg1) {}
		public void warn(Object arg0) {}
		public void warn(Object arg0, Throwable arg1) {}
	}
}
