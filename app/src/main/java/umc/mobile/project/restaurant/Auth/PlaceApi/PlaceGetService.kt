package umc.mobile.project.restaurant.Auth.PlaceApi

import android.util.Log
import com.google.android.libraries.places.api.model.Place
import umc.mobile.project.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.announcement.Auth.ApplyPost.Result
import umc.mobile.project.ram.Geocoder_location

class PlaceGetService {
    private lateinit var placeGetResult: PlaceGetResult


    fun setPlaceGetResult(placeGetResult: PlaceGetResult) {
        this.placeGetResult = placeGetResult
    }
    fun getPost(location: String, radius: Int, type: String, keyword: String, language: String, key: String){
        val postUploadDetailGetService = getRetrofit().create(PlaceGetRetrofitInterfaces::class.java)

        postUploadDetailGetService.getPost(location, radius, type, keyword, language, key).enqueue(object : Callback<PlaceGetResponse> {
            override fun onResponse(call: Call<PlaceGetResponse>, response: Response<PlaceGetResponse>,) {
                Log.d("PLACE-GET SUCCESS",response.toString())
                val resp : PlaceGetResponse = response.body()!!
                when(resp.status) {
//
                    "OK" -> placeGetResult.getPostSuccess(resp.result)
                    else -> placeGetResult.getPostFailure()
                }


            }

            override fun onFailure(call: Call<PlaceGetResponse>, t: Throwable) {
                Log.d("PLACE-GET FAILURE",t.message.toString())
            }
        })
    }
}