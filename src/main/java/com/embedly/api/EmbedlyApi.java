package com.embedly.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

class Api {
    private String key;
    private String host;
    private String userAgent;

    public Api(String userAgent) {
        this(userAgent, null, null);
    }

    public Api(String userAgent, String key) {
        this(userAgent, key, null);
    }

    public Api(String userAgent, String key, String host) {
        this.userAgent = userAgent;
        this.key = key;
        this.host = host;

        if (this.userAgent == null) {
            throw new RuntimeException(
                    "You must specify a userAgent when constructing an " +
                    "Api object");
        }

        if (this.key == null && this.host == null) {
            this.host = "http://api.embed.ly";
        } else if (this.host == null) {
            this.host = "http://pro.embed.ly";
        }
    }

    public JSONArray oembed(Map<String, Object> params) {
        return this.apicall("1", "oembed", params);
    }

    public JSONArray objectify(Map<String, Object> params) {
        return this.apicall("2", "objectify", params);
    }

    public JSONArray preview(Map<String, Object> params) {
        return this.apicall("1", "preview", params);
    }

    public JSONArray apicall(String version, String action, Map<String, Object> params) {
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
    }

    public String toString() {
        return "com.embedly.api.Api[key="+key+",host="+host+",userAgent="+userAgent+"]";
    }
}
