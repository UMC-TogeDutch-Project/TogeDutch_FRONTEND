package umc.mobile.project.commercial.Auth.CommercialGet

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class CommercialGet(
    @SerializedName(value =  "adIdx") val adIdx : Int,
    @SerializedName(value =  "store") val store : String,
    @SerializedName(value =  "information") val information : String,
    @SerializedName(value =  "mainMenu") val mainMenu : String,
    @SerializedName(value =  "deliveryTips") val deliveryTips : Int,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
    @SerializedName(value =  "request") val request : String,
    @SerializedName(value =  "createdAt") val createdAt : Timestamp,
    @SerializedName(value =  "updateAt") val updateAt : Timestamp,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "userIdx") val userIdx : Int,
    @SerializedName(value =  "image") val image : String?,
    @SerializedName(value =  "tid") val tid : String



    )
