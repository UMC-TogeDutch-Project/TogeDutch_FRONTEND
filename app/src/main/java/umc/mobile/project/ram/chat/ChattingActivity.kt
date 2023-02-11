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
import umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.PostPhotoResult
import umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.PostPhotoService
import umc.mobile.project.ram.Auth.Post.GetPost.PostGetService
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllResult
import umc.mobile.project.ram.Auth.Post.GetPostAll.PostGetAllService
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetResult
import umc.mobile.project.ram.Auth.Post.GetPostDetail.PostDetailGetService
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.*
import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChattingActivity: AppCompatActivity(), PostDetailGetResult, UserGetResult, PostGetAllResult, PostChatResult, ChatAllGetResult,PostPhotoResult {
    lateinit var binding: ActivityChattingBinding
    lateinit var chatRVAdapter: ChatRVAdapter
    var timestamp = Timestamp(Date().time)
    var chatList = ArrayList<Chat>()

    lateinit var edit_message : EditText
    lateinit var btn_submit : AppCompatButton
    lateinit var more_btn : AppCompatButton
    var chatRoom_id_get : Int = 0

    lateinit var dialog_more : BottomSheetDialog
    lateinit var dialog_etc : BottomSheetDialog

    private var PICK_IMAGE = 1
    var picture : MultipartBody.Part? = null

    var writer_me = ""
    var type_me = "TALK"



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
//            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog_etc = BottomSheetDialog(this)
            dialog_etc.setContentView(R.layout.chat_bottom_dialog_content)

            val phone_btn = dialog_etc.findViewById<AppCompatButton>(R.id.phone_btn)
            phone_btn?.setOnClickListener {
                Toast.makeText(this, "전화 걸기 클릭", Toast.LENGTH_LONG).show()
            }

            val declaration_btn = dialog_etc.findViewById<AppCompatButton>(R.id.declaration_btn)
            declaration_btn?.setOnClickListener {
                Toast.makeText(this, "신고 버튼 클릭", Toast.LENGTH_LONG).show()

                val dlg = DeclarationPopupDialog(this)
                dlg.start()

            }

            val exit_btn = dialog_etc.findViewById<AppCompatButton>(R.id.exit_btn)
            exit_btn?.setOnClickListener {
                Toast.makeText(this, "나가기 클릭", Toast.LENGTH_LONG).show()

            }
            dialog_etc.show()
        }

        binding.moreBtn.setOnClickListener {
            // 다이얼로그 부분
//            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog_more = BottomSheetDialog(this)
            dialog_more.setContentView(R.layout.chat_bottom_dialog_more)

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

            }

            val location_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_location)
            location_btn?.setOnClickListener {

                val dlg = LocationPopupDialog(this)
                dlg.start()

            }

            val calendar_btn = dialog_more.findViewById<AppCompatButton>(R.id.btn_calendar)
            calendar_btn?.setOnClickListener {
                val dlg = PromisePopupDialog(this)
                dlg.start()

            }

            dialog_more.findViewById<AppCompatButton>(R.id.btn_submit_dialog)!!.setOnClickListener {
                sendPhoto(picture!!, chatRoom_id_get, user_id_logined)
            }

            dialog_more.show()
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
//        // 현재 시간 구하기
//        var sdf = SimpleDateFormat("MM월 dd일")
//        var now = sdf.format(System.currentTimeMillis())
//        var created_at = sdf.format(result.createdAt)
//
//        if(now > created_at) // 현재 시간이 채팅 생성 시간보다 빠를 때 시간 띄워주기
//            chatRVAdapter.addItem(Chat(0, chatRoom_id_get, 0, timestamp, getCurrentTime(), "time", "time", 3))

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

    ///////////////////////////// 사진 전송 //////////////
    private fun sendPhoto(picture: MultipartBody.Part, chatRoom_id: Int, user_id: Int){
        // 서버에 post해서 데이터베이스에서 chat_id 값까지 생성된 chat 데이터 가져오기
        val postPhotoService = PostPhotoService() // chat 보내놓기
        postPhotoService.setPhotoResult(this)
        Log.d("chatRoom_id : $chatRoom_id, user_id : $user_id, chatPost : $picture", "CHECK" )
        postPhotoService.sendPhoto(chatRoom_id, user_id, picture)
    }

    override fun sendPhotoSuccess(result: umc.mobile.project.ram.Auth.ChatPhoto.ChatPhotoPost.Result) {
        Log.d("=============================== Photo Post 성공!!!!!!!!!!!!!!!!!!!!", "==================================================" )

        val data = JSONObject()
        data.put("chatPhoto_id", result.chatId)
        data.put("chatRoom_id", result.chatRoomId)
        data.put("userId", user_id_logined)
        data.put("created_at", result.createdAt)
        data.put("image", result.image)

        stompClient.send("/pub/chat/image", data.toString()).subscribe()
        edit_message.text = Editable.Factory.getInstance().newEditable("") // 채팅 입력창 다시 초기화시켜주기

        chatRVAdapter.addItem(Chat(result.chatId, result.chatRoomId, user_id_logined, result.createdAt, result.image, writer_me, type_me, 2))
        runOnUiThread {
            chatRVAdapter.notifyDataSetChanged()
        }
    }

    override fun sendPhotoFailure() {
        Log.d("=============================== Photo Post 실패!!!!!!!!!!!!!!!!!!!!", "==================================================" )
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
//        var id = result.find { it.title.equals(chatRoom_selected_subject) } // chatRoom 목록에서 받아온 제목이랑 똑같은 공고 찾기

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

    //////////////////////// 사진 /////////////////////////

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 돌려받은 resultCode가 정상인지 체크
        if(resultCode == Activity.RESULT_OK){

            // 사진 가져오는 부분
            if (requestCode == PICK_IMAGE) {
                val imagePath = data!!.data

                val file = File(absolutelyPath(imagePath, this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

//                pictureNameList.addAll(listOf(file.name)) // 데이터 넣는 부분

                Log.d("파일 생성!! ======== ", file.name)
                picture = body

                setAdjImgUri(imagePath!!)


                Toast.makeText(this, "사진 첨부", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
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
        intent.type = "image/*" // 모든 이미지
        startActivityForResult(intent, PICK_IMAGE)
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
//                binding.m.setImageBitmap(
//                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
//                )
            }
        }
    }




}