package umc.mobile.project.commercial.Auth.RefundPost

import com.google.gson.annotations.SerializedName

data class Refund(
    @SerializedName(value =  "tid") val tid : String,
    @SerializedName(value =  "cid") val cid : String,
    @SerializedName(value =  "cancel_amount") val cancel_amount : Int,
    @SerializedName(value =  "cancel_tax_free_amount") val cancel_tax_free_amount : Int,


    )
