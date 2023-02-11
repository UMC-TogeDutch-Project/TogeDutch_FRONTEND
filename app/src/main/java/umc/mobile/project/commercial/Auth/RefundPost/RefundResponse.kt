package umc.mobile.project.commercial.Auth.RefundPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class RefundResponse (
    @SerializedName(value = "aid") val aid: String,
    @SerializedName(value = "tid") val tid : String,
    @SerializedName(value = "cid") val cid : String,
    @SerializedName(value = "status") val status : String,
    @SerializedName(value = "msg") val msg : String,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "partner_order_id") val partner_order_id : Int,
    @SerializedName(value = "partner_user_id") val partner_user_id : String,
    @SerializedName(value = "payment_method_type") val payment_method_type : String,

    @SerializedName(value = "amount") val amount : Amount?,
    @SerializedName(value = "approved_cancel_amount") val approved_cancel_amount : Approved_cancel_amount?,
    @SerializedName(value = "canceled_amount") val canceled_amount : Canceled_amount?,
    @SerializedName(value = "cancel_available_amount") val cancel_available_amount : Cancel_available_amount?,

    @SerializedName(value = "item_name") val item_name : String,
    @SerializedName(value = "item_code") val item_code : String,
    @SerializedName(value = "quantity") val quantity : Int,
    @SerializedName(value = "created_at") val created_at : Timestamp,
    @SerializedName(value = "approved_at") val approved_at : Timestamp,
    @SerializedName(value = "canceled_at") val canceled_at : Timestamp,
    @SerializedName(value = "payload") val payload : String

    )
data class Amount(
    @SerializedName(value =  "total") val total : Int,
    @SerializedName(value =  "tax_free") val tax_free : Int,
    @SerializedName(value =  "vat") val vat : Int,
    @SerializedName(value =  "point") val point : Int,
    @SerializedName(value =  "discount") val discount : Int
)
data class Approved_cancel_amount(
    @SerializedName(value =  "total") val total : Int,
    @SerializedName(value =  "tax_free") val tax_free : Int,
    @SerializedName(value =  "vat") val vat : Int,
    @SerializedName(value =  "point") val point : Int,
    @SerializedName(value =  "discount") val discount : Int,
    @SerializedName(value =  "green_deposit") val green_deposit : Int

)
data class Canceled_amount(
    @SerializedName(value =  "total") val total : Int,
    @SerializedName(value =  "tax_free") val tax_free : Int,
    @SerializedName(value =  "vat") val vat : Int,
    @SerializedName(value =  "point") val point : Int,
    @SerializedName(value =  "discount") val discount : Int,
    @SerializedName(value =  "green_deposit") val green_deposit : Int
)
data class Cancel_available_amount(
    @SerializedName(value =  "total") val total : Int,
    @SerializedName(value =  "tax_free") val tax_free : Int,
    @SerializedName(value =  "vat") val vat : Int,
    @SerializedName(value =  "point") val point : Int,
    @SerializedName(value =  "discount") val discount : Int,
    @SerializedName(value =  "green_deposit") val green_deposit : Int
)

