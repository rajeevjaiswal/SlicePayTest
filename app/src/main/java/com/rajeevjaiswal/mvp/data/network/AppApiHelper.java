
package com.rajeevjaiswal.mvp.data.network;

import android.database.Observable;

import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.PhotoResponse;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiCall mApiCall;

    @Inject
    public AppApiHelper( ApiCall apiCall) {
        mApiCall = apiCall;
    }


    @Override
    public Call<PhotoResponse> getPhotos(Map<String, String> queryMap) {
        return mApiCall.getPhotos(queryMap);
    }
}

