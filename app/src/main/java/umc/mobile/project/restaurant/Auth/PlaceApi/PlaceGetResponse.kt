package umc.mobile.project.restaurant.Auth.PlaceApi

import com.google.gson.annotations.SerializedName
import Post
import com.google.android.libraries.places.api.model.Place


data class PlaceGetResponse(
    @SerializedName(value = "html_attributions") val html_attributions: ArrayList<String>?,
    @SerializedName(value = "result") val result : ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>,
    @SerializedName(value = "status") val status : String,
    @SerializedName(value = "error_message") val error_message : String,
    @SerializedName(value = "info_messages") val info_messages : ArrayList<String>?,
    @SerializedName(value = "next_page_token") val next_page_token : String

    )

