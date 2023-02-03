package umc.mobile.project.commercial.Auth.CommercialPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostRecordResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "tid") val tid : String,
    @SerializedName(value =  "next_redirect_pc_url") val next_redirect_pc_url : String,
    @SerializedName(value =  "next_redirect_mobile_url") val next_redirect_mobile_url : String,
    @SerializedName(value =  "partner_order_id") val partner_order_id : Int,
    @SerializedName(value =  "created_at") val created_at : Int,

)
