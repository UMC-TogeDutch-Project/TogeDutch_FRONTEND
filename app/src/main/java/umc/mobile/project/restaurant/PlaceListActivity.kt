//package com.sadma.example.nearbyservices.ui
//
//import android.os.Bundle
//import android.support.v4.content.ContextCompat
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.GridLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.support.v7.widget.Toolbar
//import android.util.Log
//import android.view.MenuItem
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import java.util.ArrayList
//import com.sadma.example.nearbyservices.R
//import com.sadma.example.nearbyservices.adapter.PlaceListAdapter
//import com.sadma.example.nearbyservices.model.Place
//import com.sadma.example.nearbyservices.utils.GoogleApiUrl
//import umc.mobile.project.R
//import umc.mobile.project.restaurant.Auth.PlaceApi.Place
//import umc.mobile.project.restaurant.RestaurantRVAdapter
//
//class PlaceListActivity : AppCompatActivity() {
//    /**
//     * ArrayList to store the Near By Place List
//     */
//    private var mNearByPlaceArrayList: ArrayList<Place> = ArrayList()
//    private var mRecyclerView: RecyclerView? = null
//    private var mGridLayoutManager: GridLayoutManager? = null
//    private var mPlaceListAdapter: RestaurantRVAdapter? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_place_list)
//        /**
//         * get the intent and get the location Tag
//         */
//        val locationTag: String? = getIntent().getStringExtra(GoogleApiUrl.LOCATION_TYPE_EXTRA_TEXT)
//        val locationName: String? = getIntent().getStringExtra(GoogleApiUrl.LOCATION_NAME_EXTRA_TEXT)
//        val currentLocation: String? = getSharedPreferences(
//            GoogleApiUrl.CURRENT_LOCATION_SHARED_PREFERENCE_KEY, 0
//        )
//            .getString(GoogleApiUrl.CURRENT_LOCATION_DATA_KEY, null)
//        val locationQueryStringUrl: String =
//            GoogleApiUrl.BASE_URL + GoogleApiUrl.NEARBY_SEARCH_TAG.toString() + "/" +
//                    GoogleApiUrl.JSON_FORMAT_TAG.toString() + "?" + GoogleApiUrl.LOCATION_TAG.toString() + "=" +
//                    currentLocation + "&" + GoogleApiUrl.RADIUS_TAG.toString() + "=" +
//                    GoogleApiUrl.RADIUS_VALUE.toString() + "&" + GoogleApiUrl.PLACE_TYPE_TAG.toString() + "=" + locationTag +
//                    "&" + GoogleApiUrl.API_KEY_TAG.toString() + "=" + GoogleApiUrl.API_KEY
//        Log.d(TAG, locationQueryStringUrl)
//
//        mNearByPlaceArrayList = getIntent()
//            .getParcelableArrayListExtra(GoogleApiUrl.ALL_NEARBY_LOCATION_KEY)!!
//        mRecyclerView = findViewById(R.id.place_list_recycler_view) as RecyclerView?
//        if (mNearByPlaceArrayList.size() === 0) {
//            findViewById(R.id.empty_view).setVisibility(View.VISIBLE)
//            mRecyclerView.setVisibility(View.GONE)
//        } else {
//            findViewById(R.id.empty_view).setVisibility(View.GONE)
//            mRecyclerView.setVisibility(View.VISIBLE)
//            mGridLayoutManager = GridLayoutManager(this, 1)
//            mPlaceListAdapter = PlaceListAdapter(this, mNearByPlaceArrayList)
//            mRecyclerView.setLayoutManager(mGridLayoutManager)
//            mRecyclerView.setAdapter(mPlaceListAdapter)
//        }
//    }
//
//
//
//    companion object {
//        val TAG: String = PlaceListActivity::class.java.getSimpleName()
//    }
//}