package umc.mobile.project.restaurant.Auth.PlaceApi

import com.google.android.libraries.places.api.model.Place

interface PlaceGetResult {
    fun getPostSuccess(result: ArrayList<Place>)
    fun getPostFailure()
}