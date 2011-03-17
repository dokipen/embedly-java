package ly.embed.api;

class EmbedlyApi {
    private String key;
    private String host;
    private String userAgent;

    public EmbedlyApi(String key, String host, String userAgent) {
        this.key = key;
        this.host = host;
        this.userAgent = userAgent;
    }

    public String toString() {
        return "EmbedlyApi[key="+key+",host="+host+",userAgent="+userAgent+"]";
    }
}
