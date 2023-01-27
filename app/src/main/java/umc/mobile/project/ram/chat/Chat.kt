package umc.mobile.project.ram.chat

data class Chat(
    var chat_id : Int,
    var created_at : String,
    var content : String,
    var status : String,
    var chatRoom_id : Int,
    var user_id : Int
)
