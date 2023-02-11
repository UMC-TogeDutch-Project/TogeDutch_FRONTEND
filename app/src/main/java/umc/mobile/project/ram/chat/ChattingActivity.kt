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
import umc.mobile.project.ram.Auth.Chat.ChatPost.chatPost
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChattingActivity: AppCompatActivity(), PostDetailGetResult, UserGetResult, PostGetAllResult, PostChatResult, ChatAllGetResult {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatRVAdapter: ChatRVAdapter
    var timestamp = Timestamp(Date().time)
    var chatList = ArrayList<Chat>()

    lateinit var edit_message : EditText
    lateinit var btn_submit : AppCompatButton
    lateinit var more_btn : AppCompatButton
    var chatRoom_id_get : Int = 0


    /// stomp 연결
    private var url = "ws://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/stomp/chat/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postGetAllService = PostGetAllService() // 공고 전체 불러오기
        postGetAllService.setPostGetResult(this)
        postGetAllService.getPostAll()

        initActionBar()

        // 채팅 부분
        chatRoom_id_get = intent.getIntExtra("chatRoom_id", -1)
        Log.d("ChattinActivity 넘어온 chatRoom_id: $chatRoom_id_get", "")

        if(chatRoom_id_get != -1){
            try {
                runStomp(chatRoom_id_get, user_id_logined)
            } catch (e:Exception){
                Log.d("ERROR", "stomp 자체의 오류")
                Log.d("ERROR", e.message.toString())
            }
        }

        edit_message = binding.editMessage
        edit_message!!.addTextChangedListener(textWatcher)


        // dummy data
        var chatDatas = mutableListOf<Chat>()
        chatRVAdapter = ChatRVAdapter(this)
        binding.recyclerMessages.adapter = chatRVAdapter
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)

        chatRVAdapter.chatList = chatDatas
        chatRVAdapter.notifyDataSetChanged()

        // 이전에 했던 채팅들 불러오기
        val chatAllGetService = ChatAllGetService()
        chatAllGetService.setChatAllGetResult(this)
        chatAllGetService.getChatAll(chatRoom_id_get)


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

        stompClient.topic("/sub/chat/room/{$chatRoom_id}").subscribe { topicMessage ->
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
            sendStomp(binding.editMessage.text.toString(), chatRoom_id_get, user_id_logined)
        }
    }

    fun getChatPost(content: String) : chatPost{

        return chatPost(content)
    }

    fun sendStomp(content: String, chatRoom_id: Int, user_id: Int){
        // 서버에 post해서 데이터베이스에서 chat_id 값까지 생성된 chat 데이터 가져오기
        val postChatService = PostChatService() // chat 보내놓기
        postChatService.setChatResult(this)
        var chatPost = getChatPost(content).content
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $chatPost", "CHECK" )
        postChatService.sendChat(chatRoom_id, user_id, getChatPost(content))
    }

    override fun sendChatSuccess(result: Result) {
        Log.d("=============================== Message Post 성공!!!!!!!!!!!!!!!!!!!!", "==================================================" )
        // 현재 시간 구하기
        var sdf = SimpleDateFormat("MM월 dd일")
        var now = sdf.format(System.currentTimeMillis())
        var created_at = sdf.format(result.createdAt)

        if(now > created_at) // 현재 시간이 채팅 생성 시간보다 빠를 때 시간 띄워주기
            chatRVAdapter.addItem(Chat(0, chatRoom_id_get, 0, timestamp, getCurrentTime(), "time", "time", 3))

        println(result.createdAt)
        val data = JSONObject()
        data.put("chatId", result.chatId)
        data.put("chatRoomId", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("createdAt", result.createdAt)
        data.put("content", result.content)
        data.put("writer", result.writer)
        data.put("type", result.type)

        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        Log.d("Message Send", "내가 보낸 메세지 : " + result.content)

        edit_message.text = Editable.Factory.getInstance().newEditable("") // 채팅 입력창 다시 초기화시켜주기

        chatRVAdapter.addItem(Chat(result.chatId, result.chatRoomId, user_id_logined, result.createdAt, result.content, result.writer, result.type, 2))
        runOnUiThread {
            chatRVAdapter.notifyDataSetChanged()
        }
    }

    override fun sendChatFailure() {
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

        if(result.size == 0)
            // 채팅방 비어있을 때 시간 띄워주기
            chatRVAdapter.addItem(Chat(0, chatRoom_id_get, 0, timestamp, getCurrentTime(), "time", "time", 3))

        for(i in 0 until result.count()){
            if(result[i].user_id != user_id_logined) // 로그인 유저가 아니면 상대방 채팅
                chatRVAdapter.addItem(Chat(result[i].chat_id, result[i].chatRoom_id, result[i].user_id, result[i].created_at, result[i].content, result[i].writer, result[i].type, 1))
            else // 로그인 유저면 내 채팅
                chatRVAdapter.addItem(Chat(result[i].chat_id, result[i].chatRoom_id, result[i].user_id, result[i].created_at, result[i].content, result[i].writer, result[i].type, 2))
        }


        chatRVAdapter.notifyDataSetChanged()
    }

    override fun getChatAllFailure(code: Int, message: String) {
        Log.d("=============================== getChatAllFailure!!!!!!!!!!!!!!!!!!!!", "==================================================" )
    }

    fun getCurrentTime() :  String{
        val currentTime : Long = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        var current_time = dateFormat.format(currentTime)

        return current_time
    }
    val textWatcher = object : TextWatcher {

        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if(binding.editMessage.text == null)
                binding.btnSubmit.isClickable = false
            else
                binding.btnSubmit.isClickable = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stompClient.disconnect()
    }


}