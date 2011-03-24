package com.embedly.api;

import static com.embedly.api.Utils.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ResponseMaker {
	
	private JSONArray response;
	
	public ResponseMaker() {
		try {
			this.response = new JSONArray("[]");
		} catch(JSONException e) {
			getLog().error("This shouldn't ever happen", e);
			throw new RuntimeException("Unexpected fatal error");
	    }
	}

    /**
     * Filters invalid urls and prepares a JSONArray for response.
     *
     * The JSON array will have null values where responses will be inserted
     * and 401 responses where urls were invalid.  After calling the embedly
     * api, you can fill in the null values with the responses in the order
     * they come back in.
     *
     * @param urls     An ArrayList<String> of urls to check.  This will be
     *                 stripped of invalid urls.
     *
     * @param regex    A pattern to check urls against
     *
     * @return        List of urls that passed regex
     */
    public ArrayList<String> prepare(ArrayList<String> urls, Pattern regex) throws JSONException {
    	getLog().debug("checking urls against services");
    	ArrayList<String> rurls = new ArrayList<String>(urls);
        for (int i = rurls.size() - 1; i >= 0; --i) {
            String url = rurls.get(i);
            Matcher match = regex.matcher(url);
            if (match.matches()) {
            	getLog().debug("url: "+url+" is valid");
                response.put(i, (JSONObject)null);
            } else {
            	getLog().debug("url: "+url+" isn't valid");
                response.put(i, new JSONObject("" +
                    "{ url: \""+url+"\"" +
                    ", error_code: \"401\"" +
                    ", error_message: \"This service requires an Embedly Pro" +
                                       " account\"" +
                    ", type: \"error\"" +
                    ", version: \"1.0\"" +
                    "}"
                ));
                rurls.remove(i);
            }
        }
        return rurls;
    }

    /**
     * Takes a JSONArray with empty place holders and fills it with filler.
     *
     * This method modifies toFill
     *
     * @param toFill  JSONArray will null values where values should be written
     *
     * @param filler  JSONArray with values that will be written to toFill
     */
    public void fill(JSONArray filler) throws JSONException {
        int filler_index = 0;
        for (int i = 0; i < response.length(); ++i) {
            if (response.isNull(i)) {
                response.put(i, filler.getJSONObject(filler_index));
                if (filler_index >= filler.length()) {
                    // This should _never_ happen
                	getLog().error("we're on index "+filler_index+
                            " but real_resp only has "+
                            filler.length()+" members.");
                	getLog().debug("Current response: "+response.toString());
                    throw new RuntimeException("Something went " +
                            "terribly wrong parsing the response");
                }
                filler_index++;
            }
        }
    }
    
    public JSONArray getResponse() {
    	return response;
    }
}
