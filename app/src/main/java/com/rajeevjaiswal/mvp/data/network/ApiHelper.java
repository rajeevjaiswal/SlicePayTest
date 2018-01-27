
package com.rajeevjaiswal.mvp.data.network;

import com.rajeevjaiswal.mvp.data.model.Contact;
import com.rajeevjaiswal.mvp.data.model.PhotoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by rajeev on 16/12/17.
 */

public interface ApiHelper {

    Call<PhotoResponse> getPhotos(Map<String, String> queryMap);
}
