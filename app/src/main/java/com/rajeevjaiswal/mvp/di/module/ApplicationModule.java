
package com.rajeevjaiswal.mvp.di.module;

import android.app.Application;
import android.content.Context;

import com.rajeevjaiswal.mvp.data.AppDataManager;
import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.network.ApiCall;
import com.rajeevjaiswal.mvp.data.network.ApiHelper;
import com.rajeevjaiswal.mvp.data.network.AppApiHelper;
import com.rajeevjaiswal.mvp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rajeev on 16/12/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiCall provideApiCall() {
        return ApiCall.Factory.create();
    }


}
