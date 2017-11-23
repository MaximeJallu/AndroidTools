
import android.content.Context;

import java.util.HashMap;

import commons.network.OkHttpBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * In APP
 */
public class ApiHttpClientFactory implements ApiConfiguration.OkHttpClientFactory {

    private final Context mContext;
    private final HttpLoggingInterceptor.Level mLevel;
    private HashMap<String, OkHttpClient> mClientSet;

    public ApiHttpClientFactory(Context context, HttpLoggingInterceptor.Level level) {
        mContext = context;
        mLevel = level;
        mClientSet = new HashMap<>();
    }

    @Override
    public OkHttpClient create(String apiName, String apiKey, boolean addCache, boolean allowCookies) {
        /*on retourne le client si il existe déjà*/
        if (mClientSet.containsKey(apiName)) {
            return mClientSet.get(apiName);
        }

        OkHttpClient.Builder builder = new OkHttpBuilder(mContext)
                .apiKey(apiKey)
                .loggingLevel(mLevel)
                .buildBuilder();

//        builder.addInterceptor(new TokenInterceptor(sessionManager));
//        builder.authenticator(tokenAuthenticator);

        /*on save le client pour un usage future*/
        mClientSet.put(apiName, builder.build());
        return mClientSet.get(apiName);
    }
}