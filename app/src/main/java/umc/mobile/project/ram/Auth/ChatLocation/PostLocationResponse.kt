package umc.mobile.project.ram.Auth.ChatLocation

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.sql.Timestamp

data class PostLocationResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "chatLocationIdx") var chatLocationIdx : Int,
    @SerializedName(value =  "chatRoomId") var chatRoomId : Int,
    @SerializedName(value =  "userId") var userId : Int,
    @SerializedName(value =  "latitude") var latitude : BigDecimal,
    @SerializedName(value =  "longitude") var longitude : BigDecimal,
    @SerializedName(value = "createdAt") var createdAt : Timestamp
)


