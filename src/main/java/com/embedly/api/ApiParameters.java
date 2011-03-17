package com.embedly.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

class ApiParameters {
    private Map<String, ArrayList<String>> params;

    public ApiParameters() {
        this.params = new HashMap<String, ArrayList<String>>();
    }

    public void pushToParam(String name, String value) {

    }

    public void pushToParam(String name, String[] value) {

    }

    public String toQuery() {
        return "";
    }

    public void noop() {
        /*
        JSONArray resp = null;
        try {
            resp = new JSONArray("[]");
            Object url = params.get("url");
            String urls = null;
            if (null != url) {
                urls = URLEncoder.encode(""+url, "utf-8");
            }
            if (null == urls) {
                ArrayList<String> encodedUrls = new ArrayList<String>();
                for (Object i : (String[])params.get("urls")) {
                    encodedUrls.add(URLEncoder.encode(""+i, "utf-8"));
                }
                urls = StringUtils.join(encodedUrls, ",");
            }
            HttpClient httpclient = new DefaultHttpClient();
            String call = this.host+"/"+version+"/"+action+"?urls="+urls;
            if (this.key != null) {
                call = call+"&key="+this.key;
            }
            // System.out.println("calling: "+call);
            HttpGet httpget = new HttpGet(call);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resp = new JSONArray(httpclient.execute(httpget, responseHandler));
        } catch (JSONException e) {
            // TODO: add more details of call and stack trace
            throw new RuntimeException("Failed to parse JSON in response");
        } catch (UnsupportedEncodingException e) {
            // TODO: add more details of call and stack trace
            throw new RuntimeException("Parameters couldn't be encoded with utf-8");
        } catch (IOException e) {
            // TODO: add more details of call and stack trace
            throw new RuntimeException("HTTP call failed");
        }
        return resp;
        */
    }

    public String toString() {
        return "com.embedly.api.ApiParameters["+this.params+"]";
    }
}
