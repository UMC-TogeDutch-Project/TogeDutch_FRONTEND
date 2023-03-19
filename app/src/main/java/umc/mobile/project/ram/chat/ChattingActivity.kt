package umc.mobile.project.ram.chat

import Post
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import umc.mobile.project.R
import umc.mobile.project.announcement.PlaceSearchActivity
import umc.mobile.project.databinding.ActivityChattingBinding
import umc.mobile.project.ram.Auth.Application.GetUser.UserGet
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetResult
import umc.mobile.project.ram.Auth.Application.GetUser.UserGetService
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetResult
import umc.mobile.project.ram.Auth.Chat.ChatAllGet.ChatAllGetService
import umc.mobile.project.ram.Auth.Chat.ChatGet.ChatGetResult
import umc.mobile.project.ram.Auth.Chat.ChatGet.ChatGetService
import umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost.ChatMeetTime
import umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost.PostChatMeetTimeResult
import umc.mobile.project.ram.Auth.Chat.ChatMeetTimePost.PostChatMeetTimeService
import umc.mobile.project.ram.Auth.Chat.ChatPost.PostChatResult
import umc.mobile.project.ram.Auth.Chat.ChatPost.PostChatService
import umc.mobile.project.ram.Auth.Chat.ChatPost.Result
import umc.mobile.project.ram.Auth.Chat.ChatPost.chatPost
import umc.mobile.project.ram.Auth.ChatLocation.PostLocationResult
import umc.mobile.project.ram.Auth.ChatLocation.PostLocationService
import umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.PostPhotoResult
import umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.PostPhotoService
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.PostDeclarationResult
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.PostDeclarationService
import umc.mobile.project.ram.Auth.Declaration.DeclarationPost.declarationPost
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.*
import java.io.File
import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


var post_id_dialog: Int = 0
var user_id_dialog: Int = 0
var location_dialog = ""

class ChattingActivity : AppCompatActivity(), PostDetailGetResult, UserGetResult, PostGetAllResult,
    PostChatResult, ChatAllGetResult, PostPhotoResult,
    PostChatMeetTimeResult, ChatGetResult, PostLocationResult, PostDeclarationResult {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatRVAdapter: ChatRVAdapter
    var timestamp = Timestamp(Date().time)
    var chatList = ArrayList<Chat>()

    lateinit var edit_message: EditText
    lateinit var btn_submit: AppCompatButton
    lateinit var more_btn: AppCompatButton
    var chatRoom_id_get: Int = 0

    lateinit var dialog_more: BottomSheetDialog
    lateinit var dialog_etc: BottomSheetDialog

    private var PICK_IMAGE = 1
    private var CAMERA_IMAGE = 2
    var picture: MultipartBody.Part? = null

    var writer_me = ""
    var type_me = "TALK"

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val SUBACTIITY_REQUEST_CODE = 100

    var place_basic = ""
    var place_detail = ""

    lateinit var place_chat_iv: AppCompatButton
    lateinit var picture_chat_iv: AppCompatButton

    var phone_number : String = ""
    var phoneNum_send = "tel:"

    lateinit var currentPhotoPath : String

    /// stomp 연결
    private var url =
        "ws://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/stomp/chat/websocket"
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
        Log.d("ChattingActivity 넘어온 chatRoom_id: $chatRoom_id_get", "")

        if (chatRoom_id_get != -1) {
            try {
                runStomp(chatRoom_id_get, user_id_logined)
            } catch (e: Exception) {
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
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this).apply{
            this.stackFromEnd = true // 가장 최근의 대화를 표시하기 위해 맨 아래로 정렬.
        }

        chatRVAdapter.chatList = chatDatas
        chatRVAdapter.notifyDataSetChanged()

        // 이전에 했던 채팅들 불러오기
        val chatAllGetService = ChatAllGetService()
        chatAllGetService.setChatAllGetResult(this)
        chatAllGetService.getChatAll(chatRoom_id_get)


        /// 버튼 클릭

        binding.itemProfileImg.setOnClickListener {
            val dlg = PostDetailPopupDialog(this)
            dlg.start()
        }

        binding.etcBtn.setOnClickListener {
            // 다이얼로그 부분
            dialog_etc = BottomSheetDialog(this)
            dialog_etc.setContentView(R.layout.chat_bottom_dialog_content)

            val phone_btn = dialog_etc.findViewById<AppCompatButton>(R.id.phone_btn)
            phone_btn?.setOnClickListener {
                phoneNum_send += phone_number
                var intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(phoneNum_send)
                startActivity(intent)
                Toast.makeText(this, "전화 걸기 클릭", Toast.LENGTH_LONG).show()
            }

            val declaration_btn = dialog_etc.findViewById<AppCompatButton>(R.id.declaration_btn)
            declaration_btn?.setOnClickListener {
                Toast.makeText(this, "신고 버튼 클릭", Toast.LENGTH_LONG).show()

                val declarationPopupDialog = DeclarationPopupDialog(this)
                declarationPopupDialog.start()
                declarationPopupDialog.setOnClickListener(object :
                    DeclarationPopupDialog.ButtonClickListener {
                    override fun onClicked(text: String) {
                        // 약속시간 전송하는거 해주기
                        Log.d("가져온 text : ", text)
                        // 신고접수 처리
                        sendDeclaration(chatRoom_id_get, declarationPost(text))
                    }
                })

            }

            val exit_btn = dialog_etc.findViewById<AppCompatButton>(R.id.exit_btn)
            exit_btn?.setOnClickListener {
                Toast.makeText(this, "나가기 클릭", Toast.LENGTH_LONG).show()

            }
            dialog_etc.show()
        }

        binding.moreBtn.setOnClickListener {
            // 다이얼로그 부분
            dialog_more = BottomSheetDialog(this)
            dialog_more.setContentView(R.layout.chat_bottom_dialog_more)
            picture_chat_iv = dialog_more.findViewById<AppCompatButton>(R.id.picture_chat_iv)!!
            place_chat_iv = dialog_more.findViewById<AppCompatButton>(R.id.place_chat_iv)!!

            val photo_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_photo)
            photo_btn?.setOnClickListener {
                Toast.makeText(this, "사진 클릭", Toast.LENGTH_LONG).show()
                val status = ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                if (status == PackageManager.PERMISSION_GRANTED) {
                    // Permission 허용
                    getImage()
                } else {
                    // Permission 허용

                    // 허용 요청
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        100
                    )
                }
            }


            val camera_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_camera)
            camera_btn?.setOnClickListener {
                Toast.makeText(this, "카메라 클릭", Toast.LENGTH_LONG).show()

                if (checkPersmission()) {
                    // Permission 허용
                    dispatchTakePictureIntent()
                } else {
                    // 허용 요청
                    requestPermission()
                }

            }

            val location_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_location)
            location_btn?.setOnClickListener {
                val intent = Intent(this@ChattingActivity, PlaceSearchActivity::class.java)
                startActivityForResult(intent, SUBACTIITY_REQUEST_CODE)

            }

            val calendar_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_calendar)
            calendar_btn?.setOnClickListener {
                val promisePopupDialog = PromisePopupDialog(this)
                promisePopupDialog.show()
                promisePopupDialog.setOnClickListener(object :
                    PromisePopupDialog.ButtonClickListener {
                    override fun onClicked(text: String) {
                        // 약속시간 전송하는거 해주기
                        Log.d("가져온 time : ", text)
                        sendMeetTime(text)
                        promisePopupDialog.dismiss()
                    }
                })

            }

            dialog_more.findViewById<AppCompatButton>(R.id.btn_submit_dialog)!!.setOnClickListener {
                if (picture_chat_iv.visibility == View.VISIBLE) {
                    picture_chat_iv.visibility == View.GONE
                    edit_message.hint = "메시지를 입력하세요"
                    sendPhoto(picture!!, chatRoom_id_get, user_id_logined)
                } else if (place_chat_iv.visibility == View.VISIBLE) {
                    place_chat_iv.visibility == View.GONE
                    edit_message.hint = "메시지를 입력하세요"
                    // 장소 전송
                    sendLocation(chatRoom_id_get, user_id_logined, latitude, longitude)
                }

            }

            dialog_more.show()
        }

    }


    @SuppressLint("CheckResult")
    private fun runStomp(chatRoom_id: Int, user_id: Int) { // user_id는 현재 로그인한 유저 아이디!!!
        stompClient.connect()

        stompClient.topic("/sub/chat/room/${chatRoom_id}").subscribe { topicMessage ->
            Log.d("message Receive", topicMessage.payload)
            val sender =
                JSONObject(topicMessage.payload).getString("userId").toInt() // 메세지 보낸 user_id 가져오기

            if (sender != user_id) {
                val chat_id = JSONObject(topicMessage.payload).getInt("chatId")


                val chatGetService = ChatGetService()
                chatGetService.setChatGetResult(this)
                chatGetService.getChat(chatRoom_id_get, chat_id)
            }
        }

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
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

    override fun getChatSuccess(code: Int, result: Chat) {
        Log.d("CHAT-GET 성공", "")
        val chat_id = result.chat_id
        val chatRoom_id = result.chatRoom_id
        val user_id = result.user_id

        val createAt: Timestamp = result.created_at
        Log.d("************* 받은 createAt :", createAt.toString())

        val content = result.content
        val writer = result.writer

        if (result.content.length >= 17 && result.content[4].toString()
                .equals("-") && result.content[7].toString()
                .equals("-") && result.content[13].toString()
                .equals(":") && result.content[16].toString().equals(":")
        ) {
            println("시간 선택")
            chatRVAdapter.addItem(
                Chat(
                    chat_id,
                    chatRoom_id,
                    user_id,
                    createAt,
                    content,
                    writer,
                    "TALK",
                    4
                )
            )
        } else if (result.content.length >= 17 && result.content.substring(0 until 4)
                .equals("장소채팅")
        ) {
            println("장소 선택")
            var place_text = result.content.substring(4)
            chatRVAdapter.addItem(
                Chat(
                    chat_id,
                    chatRoom_id,
                    user_id,
                    createAt,
                    place_text,
                    writer,
                    "TALK",
                    5
                )
            )
        }else {
            chatRVAdapter.addItem(
                Chat(
                    chat_id,
                    chatRoom_id,
                    user_id,
                    createAt,
                    content,
                    writer,
                    "TALK",
                    1
                )
            )
        }

        runOnUiThread {
            chatRVAdapter.notifyDataSetChanged()
        }

    }

    override fun getChatFailure(code: Int, message: String) {
        Log.d("CHAT-GET 실패", "")
    }

    fun getChatPost(content: String): chatPost {

        return chatPost(content)
    }

    fun sendStomp(content: String, chatRoom_id: Int, user_id: Int) {
        // 서버에 post해서 데이터베이스에서 chat_id 값까지 생성된 chat 데이터 가져오기
        val postChatService = PostChatService() // chat 보내놓기
        postChatService.setChatResult(this)
        var chatPost = getChatPost(content).content
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $chatPost", "CHECK")
        postChatService.sendChat(chatRoom_id, user_id, getChatPost(content))

    }

    override fun sendChatSuccess(result: Result) {
        Log.d(
            "=============================== Message Post 성공!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
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


        if (result.content.length >= 17 && result.content[4].toString()
                .equals("-") && result.content[7].toString()
                .equals("-") && result.content[13].toString()
                .equals(":") && result.content[16].toString().equals(":")
        ) {
            println("시간 선택")
            chatRVAdapter.addItem(
                Chat(
                    result.chatId,
                    result.chatRoomId,
                    user_id_logined,
                    result.createdAt,
                    result.content,
                    result.writer,
                    result.type,
                    4
                )
            )
        } else if (result.content.length >= 17 && result.content.substring(0 until 4)
                .equals("장소채팅")
        ) {
            println("장소 선택")
            var place_text = result.content.substring(4)
            chatRVAdapter.addItem(
                Chat(
                    result.chatId,
                    result.chatRoomId,
                    user_id_logined,
                    result.createdAt,
                    place_text,
                    result.writer,
                    result.type,
                    5
                )
            )
        } else {
            chatRVAdapter.addItem(
                Chat(
                    result.chatId,
                    result.chatRoomId,
                    user_id_logined,
                    result.createdAt,
                    result.content,
                    result.writer,
                    result.type,
                    2
                )
            )
        }

        runOnUiThread {
            chatRVAdapter.notifyDataSetChanged()
        }
    }

    override fun sendChatFailure() {
        Log.d("Message Post 실패", "")
    }

    ///////////////////////////// 사진 전송 //////////////
    private fun sendPhoto(picture: MultipartBody.Part, chatRoom_id: Int, user_id: Int) {
        // 서버에 post해서 데이터베이스에서 chat_id 값까지 생성된 chat 데이터 가져오기
        val postPhotoService = PostPhotoService() // chat 보내놓기
        postPhotoService.setPhotoResult(this)
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $picture", "CHECK")
        postPhotoService.sendPhoto(chatRoom_id, user_id, picture)
    }

    override fun sendPhotoSuccess(result: umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.Result) {
        Log.d(
            "=============================== Photo Post 성공!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
        val data = JSONObject()
        data.put("chatPhoto_id", result.chatId)
        data.put("chatRoom_id", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("created_at", result.createdAt)
        data.put("image", result.image)

        stompClient.send("/pub/chat/image", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // 채팅 입력창 다시 초기화시켜주기

        sendStomp(result.image, result.chatRoomId, user_id_logined)

        dialog_more.dismiss()
    }

    override fun sendPhotoFailure() {
        Log.d(
            "=============================== Photo Post 실패!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }

    /////////////////////////////////////////////////////////

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
        post_id_dialog = id!!.post_id
        user_id_dialog = id!!.user_id

        // 찾은 user_id, post_id로 화면 데이터 불러오기
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        Log.d("post_id $post_id_chatroom, user_id $user_id_chatroom", "")
        postDetailGetService.getPostDetail(post_id_chatroom, user_id_chatroom) // 임의로 지정
    }

    override fun getPostAllFailure(code: Int, message: String) {
        Log.d("getPostAllFailure ===============================================", code.toString())
    }


    override fun getPostUploadSuccess(code: Int, result: Post) {

        binding.itemContentTxt.text = result.title
        Glide.with(this).load(result.image).override(38, 38)
            .into(binding.itemProfileImg) // 이미지 가져오기

        val geocoderLocation = Geocoder_location()
        location_dialog =
            geocoderLocation.calculate_location(this, result.latitude, result.longitude)

        val userGetService = UserGetService()
        userGetService.setUserGetResult(this)
        userGetService.getUser(result.user_id)
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("실패 ===============================================", code.toString())
    }

    override fun getUserSuccess(code: Int, result: UserGet) {
        binding.itemIdTxt.text = result.name
        phone_number = result.phone
    }

    override fun getUserFailure(code: Int, message: String) {
        Log.d("getUserFailure ===============================================", code.toString())
    }

    override fun getChatAllSuccess(code: Int, result: ArrayList<Chat>) {
        Log.d(
            "=============================== getChatAllSuccess!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )

        if (result.size == 0)
        // 채팅방 비어있을 때 시간 띄워주기
            chatRVAdapter.addItem(
                Chat(
                    0,
                    chatRoom_id_get,
                    0,
                    timestamp,
                    getCurrentTime(),
                    "time",
                    "time",
                    3
                )
            )

        for (i in 0 until result.count()) {
            if (result[i].user_id != user_id_logined) { // 로그인 유저가 아니면 상대방 채팅
                if (result[i].content.length >= 17 && result[i].content[4].toString()
                        .equals("-") && result[i].content[7].toString()
                        .equals("-") && result[i].content[13].toString()
                        .equals(":") && result[i].content[16].toString().equals(":")
                ) {
                    println("시간 선택")
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            result[i].user_id,
                            result[i].created_at,
                            result[i].content,
                            result[i].writer,
                            result[i].type,
                            4
                        )
                    )

                } else if (result[i].content.length >= 17 && result[i].content.substring(0 until 4)
                        .equals("장소채팅")
                ) {
                    println("장소 선택")
                    var place_text = result[i].content.substring(4)
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            user_id_logined,
                            result[i].created_at,
                            place_text,
                            result[i].writer,
                            result[i].type,
                            5
                        )
                    )
                } else {
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            result[i].user_id,
                            result[i].created_at,
                            result[i].content,
                            result[i].writer,
                            result[i].type,
                            1
                        )
                    )
                }
            } else {// 로그인 유저면 내 채팅
                if (result[i].content.length >= 17 && result[i].content[4].toString()
                        .equals("-") && result[i].content[7].toString()
                        .equals("-") && result[i].content[13].toString()
                        .equals(":") && result[i].content[16].toString().equals(":")
                ) {
                    println("시간 선택")
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            result[i].user_id,
                            result[i].created_at,
                            result[i].content,
                            result[i].writer,
                            result[i].type,
                            4
                        )
                    )

                } else if (result[i].content.length >= 17 && result[i].content.substring(0 until 4)
                        .equals("장소채팅")
                ) {
                    println("장소 선택")
                    var place_text = result[i].content.substring(4)
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            user_id_logined,
                            result[i].created_at,
                            place_text,
                            result[i].writer,
                            result[i].type,
                            5
                        )
                    )
                } else {
                    chatRVAdapter.addItem(
                        Chat(
                            result[i].chat_id,
                            result[i].chatRoom_id,
                            result[i].user_id,
                            result[i].created_at,
                            result[i].content,
                            result[i].writer,
                            result[i].type,
                            2
                        )
                    )
                }
            }
        }


        chatRVAdapter.notifyDataSetChanged()
    }

    override fun getChatAllFailure(code: Int, message: String) {
        Log.d(
            "=============================== getChatAllFailure!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }

    fun getCurrentTime(): String {
        val currentTime: Long = System.currentTimeMillis()
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
            if (binding.editMessage.text == null)
                binding.btnSubmit.isClickable = false
            else
                binding.btnSubmit.isClickable = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stompClient.disconnect()
    }

    //////////////////////// 사진 /////////////////////////

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SUBACTIITY_REQUEST_CODE) {
                Log.d("log: ", "log 찍힘")
                if (data != null) {

                    place_basic = data.getStringExtra("place_basic").toString()
                    place_detail = data.getStringExtra("place_detail").toString()

                    place_chat_iv.visibility = View.VISIBLE
                    place_chat_iv.setText(place_basic)
                    edit_message.hint = ""
//                    editTextAnnEtPlace = data.getStringExtra("address").toString()
//                    viewBinding.annEtPlace.setText(data.getStringExtra("address"))
                    latitude = data.getDoubleExtra("latitude", 0.0)
                    longitude = data.getDoubleExtra("longitude", 0.0)
                }
            }

            // 사진 가져오는 부분
            if (requestCode == PICK_IMAGE) {
                val imagePath = data!!.data
                val file = File(absolutelyPath(imagePath, this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                Log.d("파일 생성!! ======== ", file.name)

                picture_chat_iv.visibility = View.VISIBLE
                picture_chat_iv.setText(file.name)
                edit_message.hint = ""

                picture = body
                setAdjImgUri(imagePath!!)
                Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show()
            }

            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                // 카메라로부터 받은 데이터가 있을경우에만
                val file = File(currentPhotoPath)
                val imagePath = Uri.fromFile(file)
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                Log.d("파일 생성!! ======== ", file.name)

                picture_chat_iv.visibility = View.VISIBLE
                picture_chat_iv.setText(file.name)
                edit_message.hint = ""

                picture = body
                setAdjImgUri(imagePath!!)
                Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show()
            }
        }

        else {
            Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    fun getImage() {
        // val intent = Intent("android.intent.action.GET_CONTENT")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*" // 모든 이미지
        startActivityForResult(intent, PICK_IMAGE)
    }



    //////////////// 카메라 접근

    val REQUEST_IMAGE_CAPTURE = 2

    // 카메라 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            REQUEST_IMAGE_CAPTURE)
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가")
        }else{
            Log.d("TAG","카메라 불허가")
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                // 찍은 사진을 그림파일로 만들기
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "그림파일 만드는도중 에러생김")
                        null
                    }

                // 그림파일을 성공적으로 만들었다면 onActivityForResult로 보내기
                // z카메라 이미지 저장
                if (Build.VERSION.SDK_INT < 24) {
                    if(photoFile != null){
                        val photoURI = Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
                else{
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this, "umc.mobile.project.fileprovider", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    // 카메라로 촬영한 이미지를 파일로 저장
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



    private fun setAdjImgUri(imgUri: Uri) {

        //2)Resizing 할 BitmapOption 만들기
        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            contentResolver.openInputStream(imgUri)?.use { inputStream ->
                //get img dimension
                BitmapFactory.decodeStream(inputStream, null, this)
            }

            // Determine how much to scale down the image
            val targetW: Int = 1000 //in pixel
            val targetH: Int = 1000 //in pixel
            val scaleFactor: Int = Math.min(outWidth / targetW, outHeight / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        //3) Bitmap 생성 및 셋팅 (resized + rotated)
        contentResolver.openInputStream(imgUri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->
                val matrix = Matrix()
                matrix.preRotate(0f, 0f, 0f)
            }
        }
    }

    ////////////////// 약속시간 전송

    private fun sendMeetTime(time: String) {
        val postChatMeetTimeService = PostChatMeetTimeService()
        postChatMeetTimeService.setPhotoResult(this)
        postChatMeetTimeService.sendChatMeetTime(chatRoom_id_get, user_id_logined, time)
    }

    override fun sendChatMeetTimeSuccess(result: ChatMeetTime) {
        Log.d(
            "=============================== ChatMeetTime Post 성공!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
        val data = JSONObject()
        data.put("chatMeetTimeId", result.chatMeetTimeId)
        data.put("chatRoomId", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("meetTime", result.meetTime)
        data.put("createdAt", result.createdAt)

        stompClient.send("/pub/chat/meettime", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // 채팅 입력창 다시 초기화시켜주기

        sendStomp(result.meetTime, result.chatRoomId, user_id_logined)

        dialog_more.dismiss()
    }

    override fun sendChatMeetTimeFailure() {
        Log.d(
            "=============================== ChatMeetTime Post 실패!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }


    ////////////////////////////// 장소 전송 ///////////
    private fun sendLocation(chatRoom_id: Int, user_id: Int, latitude: Double, longitude: Double) {
        val postLocationService = PostLocationService()
        postLocationService.setLocationResult(this)
        postLocationService.sendLocation(
            chatRoom_id,
            user_id,
            latitude.toBigDecimal(),
            longitude.toBigDecimal()
        )
    }

    override fun sendLocationSuccess(result: umc.mobile.project.ram.Auth.ChatLocation.Result) {
        Log.d(
            "=============================== ChatPlace Post 성공!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
        val data = JSONObject()
        data.put("chatLocationIdx", result.chatLocationIdx)
        data.put("chatRoomId", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("latitude", result.latitude)
        data.put("longitude", result.longitude)
        data.put("createdAt", result.createdAt)

        stompClient.send("/pub/chat/meettime", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // 채팅 입력창 다시 초기화시켜주기

        sendStomp("장소채팅" + place_basic + "&" + place_detail, result.chatRoomId, user_id_logined)

        dialog_more.dismiss()
    }

    override fun sendLocationFailure() {
        Log.d(
            "=============================== ChatPlace Post 실패!!!!!!!!!!!!!!!!!!!!",
            ""
        )
    }

    private fun sendDeclaration(chatRoom_id: Int, text : declarationPost){
        val postDeclarationService = PostDeclarationService()
        postDeclarationService.setDeclarationResult(this)
        postDeclarationService.sendDeclaration(chatRoom_id, text)
    }
    override fun sendDeclarationSuccess(result: umc.mobile.project.ram.Auth.Declaration.DeclarationPost.Result) {
        Log.d("SEND-SUCCESS", "신고사유 전송 성공")
        Toast.makeText(this, "신고 접수가 완료되었습니다.", Toast.LENGTH_LONG)
    }

    override fun sendDeclarationFailure() {
        Log.d("SEND-FAILURE", "신고사유 전송 실패")
        Toast.makeText(this, "신고 접수가 실패하였습니다.", Toast.LENGTH_LONG)
    }




}