package com.thereza.cmed_task02.network;

import com.thereza.cmed_task02.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by theReza on 8/2/2018.
 */

public interface ApiInterface {

    @GET(HttpParams.API_END_POINT)
    Call<UserList> getUserList();

}
