
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * IN LIB
 * @see ApiKeyService
 * @see ApiConfiguration
 */
public class ApiBuilder {

    @NonNull
    private final Gson mGson;
    @NonNull
    private final ApiConfiguration mConfiguration;
    @Nullable
    private final CallAdapter.Factory mCallAdapterFactory;

    public ApiBuilder(@NonNull Gson gson, @NonNull ApiConfiguration configuration, @Nullable CallAdapter.Factory callAdapterFactory) {
        mGson = gson;
        mConfiguration = configuration;
        mCallAdapterFactory = callAdapterFactory;
    }

    public <T> T create(final Class<T> service) {
        checkViewType(service);
        ApiKeyService apiKeyService = service.getAnnotation(ApiKeyService.class);
        String proxyUrl = mConfiguration.getBaseUrl(apiKeyService.targetUrl());
        String apiKey = mConfiguration.getApiKey(apiKeyService.value());
        OkHttpClient client = mConfiguration.create(apiKeyService.value(), apiKey, apiKeyService.addCache(), apiKeyService.allowCookies());
        return build(proxyUrl, client).create(service);
    }

    private Retrofit build(String baseUrl, OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(client);
        if (mCallAdapterFactory != null) {
            builder.addCallAdapterFactory(mCallAdapterFactory);
        }
        return builder.build();
    }

    private void checkViewType(Class<?> service) {
        if (!service.isAnnotationPresent(ApiKeyService.class)) {
            throw new IllegalArgumentException(service.getSimpleName()
                    + "is not annoted by " + ApiKeyService.class.getSimpleName());
        }
    }
}