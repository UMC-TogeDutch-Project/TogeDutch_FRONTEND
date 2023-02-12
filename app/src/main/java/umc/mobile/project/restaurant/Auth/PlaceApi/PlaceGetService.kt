package umc.mobile.project.restaurant.Auth.PlaceApi

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mobile.project.getRetrofit2
import umc.mobile.project.latitude_var
import umc.mobile.project.longtitude_var

class PlaceGetService {
    private lateinit var placeGetResult: PlaceGetResult


    fun setPlaceGetResult(placeGetResult: PlaceGetResult) {
        this.placeGetResult = placeGetResult
    }
//    location: LatLng,
    fun getPost( radius: Int, type: String, language: String, key: String){
        val postUploadDetailGetService = getRetrofit2().create(PlaceGetRetrofitInterfaces::class.java)
        val location = LatLng(latitude_var, longtitude_var)
        postUploadDetailGetService.getPost( radius, type, language, key).enqueue(object : Callback<PlaceGetResponse> {
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