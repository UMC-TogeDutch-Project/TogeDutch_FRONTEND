package umc.mobile.project.mypage.ChangePhoneNumber

import com.google.gson.annotations.SerializedName

data class PhoneNumberPatchRequest (
    @SerializedName(value =  "phone") val phone : String
)