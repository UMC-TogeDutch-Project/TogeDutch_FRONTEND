package umc.mobile.project.ram.Auth.Application.GetUser

import com.google.gson.annotations.SerializedName

data class UserGet(
    @SerializedName(value =  "name") val name : String,
    @SerializedName(value =  "phone") val phone : String,
)
