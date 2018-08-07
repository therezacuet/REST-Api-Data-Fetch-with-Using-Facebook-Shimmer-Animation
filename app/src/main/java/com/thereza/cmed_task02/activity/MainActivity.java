package com.thereza.cmed_task02.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.thereza.cmed_task02.R;
import com.thereza.cmed_task02.adapter.UserAdapter;
import com.thereza.cmed_task02.data.AppConstant;
import com.thereza.cmed_task02.model.User;
import com.thereza.cmed_task02.model.UserList;
import com.thereza.cmed_task02.network.ApiClient;
import com.thereza.cmed_task02.utils.MyDividerItemDecoration;
import com.thereza.cmed_task02.utils.NetworkUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    // init variables
    private Context mContext;
    private Activity mActivity;

    //init ArrayList
    protected ArrayList<User> userList;
    private RecyclerView rvUser;

    //Adapter
    private UserAdapter userAdapter;

    //Facebook Shimmer
    private ShimmerFrameLayout mShimmerViewContainer;

    private BroadcastReceiver mInternetConnectivityChangeReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initFunctionality();
    }

    private void initVariable() {
        mContext = getApplicationContext();
        mActivity = MainActivity.this;
        userList = new ArrayList<User>();

    }


    private void initView() {
        setContentView(R.layout.activity_main);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        rvUser = findViewById(R.id.rv_user);
        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvUser.setLayoutManager(layoutManager);
        rvUser.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        // create an empty adapter and add it to the recycler view
        userAdapter = new UserAdapter(userList, this);
        rvUser.setAdapter(userAdapter);
    }

    private void initFunctionality() {

        netConnectionAvailability();

    }

    //Net connection check and player play/pause
    protected void netConnectionAvailability() {
        mInternetConnectivityChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int status = NetworkUtil.getConnectivityStatusInt(context);
                if (status == AppConstant.VALUE_ZERO) {
                    showSnackBar(mContext.getString(R.string.net_work_not_available), mContext.getString(R.string.action_connect));
                } else {
                    fetchUserData();
                }
            }
        };
    }

    private void fetchUserData(){
        ApiClient.getApiClient().getUserList().enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    userList.addAll(response.body().getUsers());
                    userAdapter.notifyDataSetChanged();

                    // stop animating Shimmer and hide the layout
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        registerReceiver(mInternetConnectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mInternetConnectivityChangeReceiver);
        super.onDestroy();
    }

}
