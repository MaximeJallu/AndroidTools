
import android.support.annotation.Nullable;

import java.util.HashMap;

import okhttp3.OkHttpClient;

/**
 * IN LIB
 */
public class ApiConfiguration {

    private final HashMap<String, String> mBaseUrls;
    private final HashMap<String, String> mMapApiKey;
    private final OkHttpClientFactory mFactory;

    public ApiConfiguration(HashMap<String, String> baseUrls, HashMap<String, String> mapApiKey, OkHttpClientFactory factory) {
        mBaseUrls = baseUrls;
        mMapApiKey = mapApiKey;
        mFactory = factory;
    }

    public String getBaseUrl(String targetUrl) {
        return mBaseUrls.get(targetUrl);
    }

    public HashMap<String, String> getMapApiKey() {
        return mMapApiKey;
    }

    @Nullable
    public String getApiKey(String apiName) {
        return mMapApiKey.get(apiName);
    }

    public OkHttpClient create(final String apiName, final String apiKey, final boolean addCache, final boolean allowCookies) {
        return mFactory.create(apiName, apiKey, addCache, allowCookies);
    }

    interface OkHttpClientFactory {
        OkHttpClient create(final String apiName, final String apiKey, final boolean addCache, final boolean allowCookies);
    }
}