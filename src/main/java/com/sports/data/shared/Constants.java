package com.sports.data.shared;

public class Constants {

    // Sofascore endpoints
    private final static String SOFASCORE_API_BASEPATH = "https://api.sofascore.com/api/v1";
    public final static String SOFASCORE_API_RANKINGS_TENNIS = "/rankings/type/5";
    public final static String SOFASCORE_API_GET_TEAM = "/team";
    public final static String SOFASCORE_API_GET_EVENTS_DATE = "/category/3/scheduled-events";


    // Sofascore http configuration
    public final static String SOFASCORE_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";

    /**
     * Build the complete Sofascore url given the endpoint path
     *
     * @param path the endpoint path
     * @return the complete Sofascore url
     */
    public static String buildSofascoreEndpoint(String path) {
        return SOFASCORE_API_BASEPATH + path;
    }
}
