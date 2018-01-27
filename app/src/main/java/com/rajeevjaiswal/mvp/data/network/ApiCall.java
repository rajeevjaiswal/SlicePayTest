
package com.rajeevjaiswal.mvp.data.network;

import com.rajeevjaiswal.mvp.BuildConfig;
import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.PhotoResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface ApiCall {

    @GET("services/rest")
    Call<PhotoResponse> getPhotos(@QueryMap Map<String, String> queryMap);


    class Factory {

        private static final int NETWORK_CALL_TIMEOUT = 60;

        public static ApiCall create() {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();


            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(
                    BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            builder.addInterceptor(logging);

            builder.readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS);

            builder.writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS);

            OkHttpClient httpClient = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(ApiCall.class);
        }
    }
}
