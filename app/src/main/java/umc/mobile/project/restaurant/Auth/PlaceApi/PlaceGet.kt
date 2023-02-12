package umc.mobile.project.restaurant.Auth.PlaceApi

import android.location.Location
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PlaceGet (
    @SerializedName(value =  "place_id") val place_id : String,
    @SerializedName(value =  "international_phone_number") val international_phone_number : String,
    @SerializedName(value =  "name") val name : String,
//    @SerializedName(value =  "location") val location: location,
    @SerializedName(value =  "photos") val photos : ArrayList<PlacePhoto>?,
    @SerializedName(value =  "reviews") val reviews : ArrayList<PlaceReview>?,
    @SerializedName(value =  "editorial_summary") val editorial_summary : PlaceEditorialSummary?

    )


//data class location(
//    @SerializedName("lat")
//    val lat: Double,
//
//    @SerializedName("lng")
//    val lng: Double
//)
data class PlacePhoto(
    @SerializedName(value =  "height") val height : Int,
    @SerializedName(value =  "width") val width : Int,
    @SerializedName(value =  "photo_reference") val photo_reference : String

    )
data class PlaceReview(
    @SerializedName(value =  "author_name") val author_name : String,
    @SerializedName(value =  "relative_time_description") val relative_time_description : String,
    @SerializedName(value =  "language") val language : String,
    @SerializedName(value =  "profile_photo_url") val profile_photo_url : String,
    @SerializedName(value =  "rating") val rating : Number,
    @SerializedName(value =  "text") val text : String

)
data class PlaceEditorialSummary(
    @SerializedName(value =  "language") val language : String,
    @SerializedName(value =  "overview") val overview : String

)