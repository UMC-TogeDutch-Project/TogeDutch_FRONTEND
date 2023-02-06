package umc.mobile.project.ram.chat

import Post
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityChattingBinding
import umc.mobile.project.ram.Auth.Application.GetUser.UserGet
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetResult
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetResult
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetService
import umc.mobile.project.ram.Auth.Chat.ChatPost.PostChatResult
import umc.mobile.project.ram.Auth.Chat.ChatPost.PostChatService
import umc.mobile.project.ram.Auth.Chat.ChatPost.Result
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.Auth.Post.GetPostJoin.PostJoinGetService
import umc.mobile.project.ram.my_application_1.*
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList


class ChattingActivity: AppCompatActivity(), PostDetailGetResult, UserGetResult, PostGetAllResult, PostChatResult, ChatAllGetResult {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatRVAdapter: ChatRVAdapter
    var chat_post_result = Chat(chat_id = 1, chatRoom_id = 1, user_id = 1, created_at = Timestamp(Date().time), content = "", writer = "", type = "", viewType = 1)
    var chatList = ArrayList<Chat>()

    lateinit var edit_message : EditText
    lateinit var btn_submit : AppCompatButton
    lateinit var more_btn : AppCompatButton

    var chatRoom_id_get : Int = 0


    /// stomp 연결
    private var url = "ws://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/"
//    private var url = "ws://localhost:9000/stomp/chat"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatRoom_id_get = intent.getIntExtra("chatRoom_id", 0)

        val postGetAllService = PostGetAllService() // 공고 전체 불러오기
        postGetAllService.setPostGetResult(this)
        postGetAllService.getPostAll()

        initActionBar()

        // 채팅 부분
        var chatRoomId = intent.getIntExtra("chatRoom_id", -1)
        Log.d("ChattinActivity 넘어온 chatRoom_id: $chatRoomId", "")

        if(chatRoomId != -1){
            try {
                // 이전에 했던 채팅들 불러오기
                val chatAllGetService = ChatAllGetService()
                chatAllGetService.setChatAllGetResult(this)
                chatAllGetService.getChatAll(chatRoomId)

                runStomp(chatRoomId, user_id_logined)
            } catch (e:Exception){
                Log.d("ERROR", "stomp 자체의 오류")
                Log.d("ERROR", e.message.toString())
            }
        }

        edit_message = binding.editMessage
        edit_message!!.addTextChangedListener(textWatcher)

        binding.btnSubmit.setOnClickListener {
            sendStomp(binding.editMessage.text.toString(), chatRoomId, user_id_logined)
        }







        /// 버튼 클릭

        binding.itemProfileImg.setOnClickListener{
            val dlg = PostDetailPopupDialog(this)
            dlg.start()
        }

        binding.etcBtn.setOnClickListener {
            // 다이얼로그 부분
            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog.setContentView(R.layout.chat_bottom_dialog_content)

            val phone_btn = dialog.findViewById<AppCompatButton>(R.id.phone_btn)
            phone_btn?.setOnClickListener {
                Toast.makeText(this, "전화 걸기 클릭", Toast.LENGTH_LONG).show()
            }

            val declaration_btn = dialog.findViewById<AppCompatButton>(R.id.declaration_btn)
            declaration_btn?.setOnClickListener {
                Toast.makeText(this, "신고 버튼 클릭", Toast.LENGTH_LONG).show()

                val dlg = DeclarationPopupDialog(this)
                dlg.start()

            }

            val exit_btn = dialog.findViewById<AppCompatButton>(R.id.exit_btn)
            exit_btn?.setOnClickListener {
                Toast.makeText(this, "나가기 클릭", Toast.LENGTH_LONG).show()

            }
            dialog.show()
        }

        binding.moreBtn.setOnClickListener {
            // 다이얼로그 부분
            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog.setContentView(R.layout.chat_bottom_dialog_more)

            val photo_btn = dialog.findViewById<AppCompatButton>(R.id.btn_photo)
            photo_btn?.setOnClickListener {
                Toast.makeText(this, "사진 클릭", Toast.LENGTH_LONG).show()
            }

            val camera_btn = dialog.findViewById<AppCompatButton>(R.id.btn_camera)
            camera_btn?.setOnClickListener {
                Toast.makeText(this, "카메라 클릭", Toast.LENGTH_LONG).show()

            }

            val location_btn = dialog.findViewById<AppCompatButton>(R.id.btn_location)
            location_btn?.setOnClickListener {

                val dlg = LocationPopupDialog(this)
                dlg.start()

            }

            val calendar_btn = dialog.findViewById<AppCompatButton>(R.id.btn_calendar)
            calendar_btn?.setOnClickListener {
                val dlg = PromisePopupDialog(this)
                dlg.start()

            }
            dialog.show()
        }

    }

    @SuppressLint("CheckResult")
    private fun runStomp(chatRoom_id : Int, user_id : Int){ // user_id는 현재 로그인한 유저 아이디!!!
        stompClient.connect()

        stompClient.topic("sub/chat/room/{$chatRoom_id}").subscribe { topicMessage ->
            Log.d("message Receive", topicMessage.payload)
            val sender = JSONObject(topicMessage.payload).getString("userId").toInt() // 메세지 보낸 user_id 가져오기

            if(sender != user_id){
                val chat_id = JSONObject(topicMessage.payload).getInt("chat_id")
                val chatRoom_id = JSONObject(topicMessage.payload).getInt("chatRoom_id")
                val user_id = JSONObject(topicMessage.payload).getInt("user_id")

                val created_at_before = JSONObject(topicMessage.payload).getString("created_at")
//                var txt_hour = created_at_before.substring(11 until 13)
//                var txt_minute = created_at_before.substring(14 until 16)
                var created_at : Timestamp = Timestamp.valueOf(created_at_before)

                val content = JSONObject(topicMessage.payload).getString("content")
                val status = JSONObject(topicMessage.payload).getString("status")
                val writer = JSONObject(topicMessage.payload).getString("writer")

                chatRVAdapter.addItem(Chat(chat_id, chatRoom_id, user_id, created_at, content, status, writer, 1))

                runOnUiThread{
                    chatRVAdapter.notifyDataSetChanged()
                }
            }

        }

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when(lifecycleEvent.type){
                LifecycleEvent.Type.OPENED -> {
                    Log.d("OPENED", "opened")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d("CLOSED", "closed")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d("ERROR", "error")
                    Log.i("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.d("else", lifecycleEvent.message)
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
//            sendStomp
        }
    }

    fun sendStomp(content: String, chatRoom_id: Int, user_id: Int){
        // 서버에 post해서 데이터베이스에서 chat_id 값까지 생성된 chat 데이터 가져오기
        val postChatService = PostChatService() // chat 보내놓기
        postChatService.setChatResult(this)
        postChatService.sendChat(chatRoom_id, user_id, content)

        val data = JSONObject()
        data.put("chatId", chat_post_result.chat_id.toString())
        data.put("chatRoomId", chat_post_result.chatRoom_id.toString())
        data.put("userId", chat_post_result.user_id.toString())
        data.put("createdAt", chat_post_result.created_at.toString())
        data.put("content", chat_post_result.content)
        data.put("writer", chat_post_result.writer)
        data.put("type", chat_post_result.type)

        stompClient.send("pub/chat/message", data.toString()).subscribe()
        Log.d("Message Send", "내가 보낸 메세지 : " + content)

        chatRVAdapter.addItem(Chat(chat_post_result.chat_id, chatRoom_id, user_id, chat_post_result.created_at, content, chat_post_result.writer, chat_post_result.type, 2))
        runOnUiThread {
            chatRVAdapter.notifyDataSetChanged()
        }
    }

    override fun AcceptSuccess(result: Result) {
        Log.d("=============================== Message Post 성공!!!!!!!!!!!!!!!!!!!!", "==================================================" )
        chat_post_result.chat_id = result.chatId
        chat_post_result.chatRoom_id = result.chatRoomId
        chat_post_result.user_id = result.userId
        chat_post_result.created_at = result.createdAt
        chat_post_result.content = result.content
        chat_post_result.writer = result.writer
        chat_post_result.type = result.type

    }

    override fun AcceptFailure() {
        Log.d("=============================== Message Post 실패!!!!!!!!!!!!!!!!!!!!", "==================================================" )
    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "채팅"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    override fun getPostAllSuccess(code: Int, result: ArrayList<Post>) {

        var id = result.find { it.chatRoom_id == chatRoom_id_get }

        user_id_chatroom = id!!.user_id  // 그 post의 user_id 저장
        post_id_chatroom = id!!.post_id


        // 찾은 user_id, post_id로 화면 데이터 불러오기
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        Log.d("post_id $post_id_chatroom, user_id $user_id_chatroom", "")
        postDetailGetService.getPostDetail(post_id_chatroom , user_id_chatroom) // 임의로 지정
    }

    override fun getPostAllFailure(code: Int, message: String) {
        Log.d("getPostAllFailure ===============================================", code.toString())
    }

    override fun getPostUploadSuccess(code: Int, result: Post) {

        binding.itemContentTxt.text = result.title
        Glide.with(this).load(result.image).override(38,38).into(binding.itemProfileImg) // 이미지 가져오기

        val userGetService = UserGetService()
        userGetService.setUserGetResult(this)
        userGetService.getUser(result.user_id)
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("실패 ===============================================", code.toString())
    }

    override fun getUserSuccess(code: Int, result: UserGet) {
        binding.itemIdTxt.text = result.name
    }

    override fun getUserFailure(code: Int, message: String) {
        Log.d("getUserFailure ===============================================", code.toString())
    }

    override fun getChatAllSuccess(code: Int, result: ArrayList<Chat>) {
        Log.d("=============================== getChatAllSuccess!!!!!!!!!!!!!!!!!!!!", "==================================================" )
        chatList.addAll(result)

        chatRVAdapter = ChatRVAdapter(this, chatList)
        binding.recyclerMessages.adapter = chatRVAdapter
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)

        chatRVAdapter.notifyDataSetChanged()
    }

    override fun getChatAllFailure(code: Int, message: String) {
        Log.d("=============================== getChatAllFailure!!!!!!!!!!!!!!!!!!!!", "==================================================" )
    }

    val textWatcher = object : TextWatcher {

        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            TODO("Not yet implemented")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stompClient.disconnect()
    }


}