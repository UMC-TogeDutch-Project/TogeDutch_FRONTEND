package umc.mobile.project.ram.chat

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import umc.mobile.project.MainActivity
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityChattingBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ChattingActivity: AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding

//    lateinit var firebaseDatabase: DatabaseReference
    lateinit var recycler_talks: RecyclerView
    lateinit var chatRoom: ChatRoom
//    lateinit var opponentUser: User
    lateinit var chatRoomKey: String
    lateinit var myUid: String

    lateinit var content_txt : TextView
    lateinit var profile_img : ImageView
    lateinit var id_txt : TextView
    lateinit var edit_message : EditText
    lateinit var btn_submit : AppCompatButton
    lateinit var more_btn : AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

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

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "채팅"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    // 변수 초기화 함수
    private fun initProperty(){

//        myUid = FirebaseAuth.getInstance().currentUser?.uid!!              //현재 로그인한 유저 id
//        firebaseDatabase = FirebaseDatabase.getInstance().reference!!

        chatRoom = (intent.getSerializableExtra("ChatRoom")) as ChatRoom      //채팅방 정보
        chatRoomKey = intent.getStringExtra("ChatRoomKey")!!            //채팅방 키
//        opponentUser = (intent.getSerializableExtra("Opponent")) as User    //상대방 유저 정보
    }

    //뷰 초기화
    private fun initializeView() {
//        content_txt =
//        profile_img =
//        id_txt =
        edit_message = binding.editMessage
        btn_submit = binding.btnSubmit
        more_btn = binding.moreBtn

//        txt_title.text = opponentUser!!.name ?: ""
    }

    //버튼 클릭 시 리스너 초기화
    fun initializeListener() {
        btn_submit.setOnClickListener()
        {
            // putMessage()
        }
    }

    //채팅방 목록 초기화 및 표시

    //chatRoomKey 없을 경우 초기화 후 목록 초기화

    /*
    //메시지 전송
    fun putMessage() {
        try {
            var message = Message(myUid, getDateTimeString(), edt_message.text.toString())    //메시지 정보 초기화
            Log.i("ChatRoomKey", chatRoomKey)
            FirebaseDatabase.getInstance().getReference("ChatRoom").child("chatRooms")
                .child(chatRoomKey).child("messages")                   //현재 채팅방에 메시지 추가
                .push().setValue(message).addOnSuccessListener {
                    Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                    edt_message.text.clear()
                }.addOnCanceledListener {
                    Log.i("putMessage", "메시지 전송에 실패하였습니다")
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("putMessage", "메시지 전송 중 오류가 발생하였습니다.")
        }
    } */

    /*
    //메시지 보낸 시각 정보 반환
    fun getDateTimeString(): String {
        try {
            var localDateTime = LocalDateTime.now()
            localDateTime.atZone(TimeZone.getDefault().toZoneId())
            var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            return localDateTime.format(dateTimeFormatter).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("getTimeError")
        }
    } */

    fun setupRecycler() {            //목록 초기화 및 업데이트
        recycler_talks.layoutManager = LinearLayoutManager(this)
        // recycler_talks.adapter = RecyclerMessagesAdapter(this, chatRoomKey, opponentUser.uid)
    }

}