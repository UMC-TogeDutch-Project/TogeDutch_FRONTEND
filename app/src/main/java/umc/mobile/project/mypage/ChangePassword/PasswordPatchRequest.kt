package umc.mobile.project.mypage.ChangePassword

import com.google.gson.annotations.SerializedName

data class PasswordPatchRequest (
    @SerializedName(value =  "password") val password : String
)