package umc.mobile.project

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class MemberData (
    @SerializedName(value =  "userIdx") val userIdx : Int,
    @SerializedName(value =  "keywordIdx") val keywordIdx : Int,
    @SerializedName(value =  "name") val name : String,
    @SerializedName(value =  "email") val email : String,
    @SerializedName(value =  "password") val password : String,
    @SerializedName(value =  "phone") val phone : String,
    @SerializedName(value =  "image") val image : String?,
    @SerializedName(value =  "status") val status : String,

    @SerializedName(value =  "created_at") val created_at : Timestamp,
    @SerializedName(value =  "updated_at") val updated_at : Timestamp?,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
    @SerializedName(value =  "jwt") val jwt : String?
)