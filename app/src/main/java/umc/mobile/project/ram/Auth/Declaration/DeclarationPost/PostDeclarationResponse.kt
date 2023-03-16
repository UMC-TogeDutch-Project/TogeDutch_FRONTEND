package umc.mobile.project.ram.Auth.Declaration.DeclarationPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostDeclarationResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "declarationIdx") var declarationIdx : Int,
    @SerializedName(value =  "content") var content : String,
    @SerializedName(value =  "created_at") var created_at : Timestamp,
    @SerializedName(value =  "status") var status : String,
    @SerializedName(value = "chatRoomIdx") var chatRoomIdx : Int
)

data class declarationPost(
    @SerializedName(value =  "content") var content : String,
)
