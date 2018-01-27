
package com.rajeevjaiswal.mvp.data;


import android.content.Context;

import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.PhotoResponse;
import com.rajeevjaiswal.mvp.data.network.ApiHelper;
import com.rajeevjaiswal.mvp.di.ApplicationContext;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * Created by rajeev on 16/12/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          ApiHelper apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }


    @Override
    public Call<PhotoResponse> getPhotos(Map<String, String> queryMap) {
        return mApiHelper.getPhotos(queryMap);
    }
}
