package umc.mobile.project.ram.chat

import Post
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
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
import umc.mobile.project.ram.Auth.Post.GetPost.PostGetService
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.*
import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


var post_id_dialog: Int = 0
var user_id_dialog: Int = 0
var location_dialog = ""

class ChattingActivity : AppCompatActivity(), PostDetailGetResult, UserGetResult, PostGetAllResult,
    PostChatResult, ChatAllGetResult, PostPhotoResult,
    PostChatMeetTimeResult, ChatGetResult, PostLocationResult {
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

    /// stomp ??????
    private var url =
        "ws://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/stomp/chat/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postGetAllService = PostGetAllService() // ?????? ?????? ????????????
        postGetAllService.setPostGetResult(this)
        postGetAllService.getPostAll()

        initActionBar()

        // ?????? ??????
        chatRoom_id_get = intent.getIntExtra("chatRoom_id", -1)
        Log.d("ChattingActivity ????????? chatRoom_id: $chatRoom_id_get", "")

        if (chatRoom_id_get != -1) {
            try {
                runStomp(chatRoom_id_get, user_id_logined)
            } catch (e: Exception) {
                Log.d("ERROR", "stomp ????????? ??????")
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

        // ????????? ?????? ????????? ????????????
        val chatAllGetService = ChatAllGetService()
        chatAllGetService.setChatAllGetResult(this)
        chatAllGetService.getChatAll(chatRoom_id_get)


        /// ?????? ??????

        binding.itemProfileImg.setOnClickListener {
            val dlg = PostDetailPopupDialog(this)
            dlg.start()
        }

        binding.etcBtn.setOnClickListener {
            // ??????????????? ??????
//            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog_etc = BottomSheetDialog(this)
            dialog_etc.setContentView(R.layout.chat_bottom_dialog_content)

            val phone_btn = dialog_etc.findViewById<AppCompatButton>(R.id.phone_btn)
            phone_btn?.setOnClickListener {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show()
            }

            val declaration_btn = dialog_etc.findViewById<AppCompatButton>(R.id.declaration_btn)
            declaration_btn?.setOnClickListener {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show()

                val dlg = DeclarationPopupDialog(this)
                dlg.start()

            }

            val exit_btn = dialog_etc.findViewById<AppCompatButton>(R.id.exit_btn)
            exit_btn?.setOnClickListener {
                Toast.makeText(this, "????????? ??????", Toast.LENGTH_LONG).show()

            }
            dialog_etc.show()
        }

        binding.moreBtn.setOnClickListener {
            // ??????????????? ??????
//            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog_more = BottomSheetDialog(this)
            dialog_more.setContentView(R.layout.chat_bottom_dialog_more)
            picture_chat_iv = dialog_more.findViewById<AppCompatButton>(R.id.picture_chat_iv)!!
            place_chat_iv = dialog_more.findViewById<AppCompatButton>(R.id.place_chat_iv)!!

            val photo_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_photo)
            photo_btn?.setOnClickListener {
                Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show()


                val status = ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                if (status == PackageManager.PERMISSION_GRANTED) {
                    // Permission ??????
                    getImage()
                } else {
                    // Permission ??????

                    // ?????? ??????
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        100
                    )
                }
            }


            val camera_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_camera)
            camera_btn?.setOnClickListener {
                Toast.makeText(this, "????????? ??????", Toast.LENGTH_LONG).show()

            }

            val location_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_location)
            location_btn?.setOnClickListener {

//                val dlg = LocationPopupDialog(this)
//                dlg.start()
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
                        // ???????????? ??????????????? ?????????
                        Log.d("????????? time : ", text)
                        sendMeetTime(text)
                        promisePopupDialog.dismiss()
                    }
                })

            }

            dialog_more.findViewById<AppCompatButton>(R.id.btn_submit_dialog)!!.setOnClickListener {
                if (picture_chat_iv.visibility == View.VISIBLE) {
                    picture_chat_iv.visibility == View.GONE
                    edit_message.hint = "???????????? ???????????????"
                    sendPhoto(picture!!, chatRoom_id_get, user_id_logined)
                } else if (place_chat_iv.visibility == View.VISIBLE) {
                    place_chat_iv.visibility == View.GONE
                    edit_message.hint = "???????????? ???????????????"
                    // ?????? ??????
                    sendLocation(chatRoom_id_get, user_id_logined, latitude, longitude)
                }

            }

            dialog_more.show()
        }

    }


    @SuppressLint("CheckResult")
    private fun runStomp(chatRoom_id: Int, user_id: Int) { // user_id??? ?????? ???????????? ?????? ?????????!!!
        stompClient.connect()

        stompClient.topic("/sub/chat/room/${chatRoom_id}").subscribe { topicMessage ->
            Log.d("message Receive", topicMessage.payload)
            val sender =
                JSONObject(topicMessage.payload).getString("userId").toInt() // ????????? ?????? user_id ????????????

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
        Log.d("CHAT-GET ??????", "")
        val chat_id = result.chat_id
        val chatRoom_id = result.chatRoom_id
        val user_id = result.user_id

        val createAt: Timestamp = result.created_at
        Log.d("************* ?????? createAt :", createAt.toString())

        val content = result.content
        val writer = result.writer

        if (result.content.length >= 17 && result.content[4].toString()
                .equals("-") && result.content[7].toString()
                .equals("-") && result.content[13].toString()
                .equals(":") && result.content[16].toString().equals(":")
        ) {
            println("?????? ??????")
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
                .equals("????????????")
        ) {
            println("?????? ??????")
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
        Log.d("CHAT-GET ??????", "")
    }

    fun getChatPost(content: String): chatPost {

        return chatPost(content)
    }

    fun sendStomp(content: String, chatRoom_id: Int, user_id: Int) {
        // ????????? post?????? ???????????????????????? chat_id ????????? ????????? chat ????????? ????????????
        val postChatService = PostChatService() // chat ????????????
        postChatService.setChatResult(this)
        var chatPost = getChatPost(content).content
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $chatPost", "CHECK")
        postChatService.sendChat(chatRoom_id, user_id, getChatPost(content))

    }

    override fun sendChatSuccess(result: Result) {
        Log.d(
            "=============================== Message Post ??????!!!!!!!!!!!!!!!!!!!!",
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
        Log.d("Message Send", "?????? ?????? ????????? : " + result.content)

        edit_message.text = Editable.Factory.getInstance().newEditable("") // ?????? ????????? ?????? ?????????????????????


        if (result.content.length >= 17 && result.content[4].toString()
                .equals("-") && result.content[7].toString()
                .equals("-") && result.content[13].toString()
                .equals(":") && result.content[16].toString().equals(":")
        ) {
            println("?????? ??????")
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
                .equals("????????????")
        ) {
            println("?????? ??????")
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
        Log.d("Message Post ??????", "")
    }

    ///////////////////////////// ?????? ?????? //////////////
    private fun sendPhoto(picture: MultipartBody.Part, chatRoom_id: Int, user_id: Int) {
        // ????????? post?????? ???????????????????????? chat_id ????????? ????????? chat ????????? ????????????
        val postPhotoService = PostPhotoService() // chat ????????????
        postPhotoService.setPhotoResult(this)
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $picture", "CHECK")
        postPhotoService.sendPhoto(chatRoom_id, user_id, picture)
    }

    override fun sendPhotoSuccess(result: umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.Result) {
        Log.d(
            "=============================== Photo Post ??????!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
        val data = JSONObject()
        data.put("chatPhoto_id", result.chatId)
        data.put("chatRoom_id", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("created_at", result.createdAt)
        data.put("image", result.image)

        stompClient.send("/pub/chat/image", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // ?????? ????????? ?????? ?????????????????????

        sendStomp(result.image, result.chatRoomId, user_id_logined)
    }

    override fun sendPhotoFailure() {
        Log.d(
            "=============================== Photo Post ??????!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }


    /////////////////////////////////////////////////////////

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "??????"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    override fun getPostAllSuccess(code: Int, result: ArrayList<Post>) {


        var id = result.find { it.chatRoom_id == chatRoom_id_get }

        user_id_chatroom = id!!.user_id  // ??? post??? user_id ??????
        post_id_chatroom = id!!.post_id
        post_id_dialog = id!!.post_id
        user_id_dialog = id!!.user_id

        // ?????? user_id, post_id??? ?????? ????????? ????????????
        val postDetailGetService = PostDetailGetService()
        postDetailGetService.setPostDetailGetResult(this)
        Log.d("post_id $post_id_chatroom, user_id $user_id_chatroom", "")
        postDetailGetService.getPostDetail(post_id_chatroom, user_id_chatroom) // ????????? ??????
    }

    override fun getPostAllFailure(code: Int, message: String) {
        Log.d("getPostAllFailure ===============================================", code.toString())
    }


    override fun getPostUploadSuccess(code: Int, result: Post) {

        binding.itemContentTxt.text = result.title
        Glide.with(this).load(result.image).override(38, 38)
            .into(binding.itemProfileImg) // ????????? ????????????

        val geocoderLocation = Geocoder_location()
        location_dialog =
            geocoderLocation.calculate_location(this, result.latitude, result.longitude)

        val userGetService = UserGetService()
        userGetService.setUserGetResult(this)
        userGetService.getUser(result.user_id)
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("?????? ===============================================", code.toString())
    }

    override fun getUserSuccess(code: Int, result: UserGet) {
        binding.itemIdTxt.text = result.name
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
        // ????????? ???????????? ??? ?????? ????????????
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
            if (result[i].user_id != user_id_logined) { // ????????? ????????? ????????? ????????? ??????
                if (result[i].content.length >= 17 && result[i].content[4].toString()
                        .equals("-") && result[i].content[7].toString()
                        .equals("-") && result[i].content[13].toString()
                        .equals(":") && result[i].content[16].toString().equals(":")
                ) {
                    println("?????? ??????")
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
                        .equals("????????????")
                ) {
                    println("?????? ??????")
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
            } else {// ????????? ????????? ??? ??????
                if (result[i].content.length >= 17 && result[i].content[4].toString()
                        .equals("-") && result[i].content[7].toString()
                        .equals("-") && result[i].content[13].toString()
                        .equals(":") && result[i].content[16].toString().equals(":")
                ) {
                    println("?????? ??????")
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
                        .equals("????????????")
                ) {
                    println("?????? ??????")
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
        val dateFormat = SimpleDateFormat("yyyy??? MM??? dd???")
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

    //////////////////////// ?????? /////////////////////////

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // ???????????? resultCode??? ???????????? ??????
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SUBACTIITY_REQUEST_CODE) {
                Log.d("log: ", "log ??????")
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

            // ?????? ???????????? ??????
            if (requestCode == PICK_IMAGE) {
                val imagePath = data!!.data
                val file = File(absolutelyPath(imagePath, this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                Log.d("?????? ??????!! ======== ", file.name)

                picture_chat_iv.visibility = View.VISIBLE
                picture_chat_iv.setText(file.name)
                edit_message.hint = ""

                picture = body
                setAdjImgUri(imagePath!!)
                Toast.makeText(this, "?????? ??????", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            }
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
        intent.type = "image/*" // ?????? ?????????
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun setAdjImgUri(imgUri: Uri) {

        //2)Resizing ??? BitmapOption ?????????
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

        //3) Bitmap ?????? ??? ?????? (resized + rotated)
        contentResolver.openInputStream(imgUri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->
                val matrix = Matrix()
                matrix.preRotate(0f, 0f, 0f)
            }
        }
    }

    ////////////////// ???????????? ??????

    private fun sendMeetTime(time: String) {
        val postChatMeetTimeService = PostChatMeetTimeService()
        postChatMeetTimeService.setPhotoResult(this)
        postChatMeetTimeService.sendChatMeetTime(chatRoom_id_get, user_id_logined, time)
    }

    override fun sendChatMeetTimeSuccess(result: ChatMeetTime) {
        Log.d(
            "=============================== ChatMeetTime Post ??????!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
        val data = JSONObject()
        data.put("chatMeetTimeId", result.chatMeetTimeId)
        data.put("chatRoomId", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("meetTime", result.meetTime)
        data.put("createdAt", result.createdAt)

        stompClient.send("/pub/chat/meettime", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // ?????? ????????? ?????? ?????????????????????

        sendStomp(result.meetTime, result.chatRoomId, user_id_logined)
    }

    override fun sendChatMeetTimeFailure() {
        Log.d(
            "=============================== ChatMeetTime Post ??????!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }


    ////////////////////////////// ?????? ?????? ///////////
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
            "=============================== ChatPlace Post ??????!!!!!!!!!!!!!!!!!!!!",
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
        edit_message.text = Editable.Factory.getInstance().newEditable("") // ?????? ????????? ?????? ?????????????????????

        sendStomp("????????????" + place_basic + "&" + place_detail, result.chatRoomId, user_id_logined)
    }

    override fun sendLocationFailure() {
        Log.d(
            "=============================== ChatPlace Post ??????!!!!!!!!!!!!!!!!!!!!",
            "=================================================="
        )
    }


}