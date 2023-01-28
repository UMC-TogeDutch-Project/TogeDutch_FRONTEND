package umc.mobile.project.ram.chat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.ram.my_application_1.current_application.CurrentRVAdapter

//class ChatRVAdapter(
//    val context: Context,
//    var chatRoomKey: String?,
//    val opponentUid: String?
//) :
//    RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {
//    var messages: ArrayList<Chat> = arrayListOf()     //메시지 목록
//    var messageKeys: ArrayList<String> = arrayListOf()   //메시지 키 목록
////    val myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
//    val recyclerView = (context as ChattingActivity).recycler_talks   //목록이 표시될 리사이클러 뷰

//    init {
//        setupMessages()
//    }
//
//    fun setupMessages() {
//        getMessages()
//    }

//    fun getMessages() {
//        FirebaseDatabase.getInstance().getReference("ChatRoom")
//            .child("chatRooms").child(chatRoomKey!!).child("messages")   //전체 메시지 목록 가져오기
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {}
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    messages.clear()
//                    for (data in snapshot.children) {
//                        messages.add(data.getValue<Message>()!!)         //메시지 목록에 추가
//                        messageKeys.add(data.key!!)                        //메시지 키 목록에 추가
//                    }
//                    notifyDataSetChanged()          //화면 업데이트
//                    recyclerView.scrollToPosition(messages.size - 1)    //스크롤 최 하단으로 내리기
//                }
//            })
//    }

//    override fun getItemViewType(position: Int): Int {               //메시지의 id에 따라 내 메시지/상대 메시지 구분
//        return if (messages[position].senderUid.equals(myUid)) 1 else 0
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            1 -> {            //메시지가 내 메시지인 경우
//                val view =
//                    LayoutInflater.from(context)
//                        .inflate(R.layout.list_talk_item_mine, parent, false)   //내 메시지 레이아웃으로 초기화
//
//                MyMessageViewHolder(ListTalkItemMineBinding.bind(view))
//            }
//            else -> {      //메시지가 상대 메시지인 경우
//                val view =
//                    LayoutInflater.from(context)
//                        .inflate(R.layout.list_talk_item_others, parent, false)  //상대 메시지 레이아웃으로 초기화
//                OtherMessageViewHolder(ListTalkItemOthersBinding.bind(view))
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (messages[position].senderUid.equals(myUid)) {       //레이아웃 항목 초기화
//            (holder as MyMessageViewHolder).bind(position)
//        } else {
//            (holder as OtherMessageViewHolder).bind(position)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return messages.size
//    }
//
//    inner class OtherMessageViewHolder(itemView: ListTalkItemOthersBinding) :         //상대 메시지 뷰홀더
//        RecyclerView.ViewHolder(itemView.root) {
//        var background = itemView.background
//        var txtMessage = itemView.txtMessage
//        var txtDate = itemView.txtDate
//        var txtIsShown = itemView.txtIsShown
//
//        fun bind(position: Int) {           //메시지 UI 항목 초기화
//            var message = messages[position]
//            var sendDate = message.sended_date
//
//            txtMessage.text = message.content
//
//            txtDate.text = getDateText(sendDate)
//
//            if (message.confirmed.equals(true))           //확인 여부 표시
//                txtIsShown.visibility = View.GONE
//            else
//                txtIsShown.visibility = View.VISIBLE
//
//            setShown(position)             //해당 메시지 확인하여 서버로 전송
//        }

//        fun getDateText(sendDate: String): String {    //메시지 전송 시각 생성
//
//            var dateText = ""
//            var timeString = ""
//            if (sendDate.isNotBlank()) {
//                timeString = sendDate.substring(8, 12)
//                var hour = timeString.substring(0, 2)
//                var minute = timeString.substring(2, 4)
//
//                var timeformat = "%02d:%02d"
//
//                if (hour.toInt() > 11) {
//                    dateText += "오후 "
//                    dateText += timeformat.format(hour.toInt() - 12, minute.toInt())
//                } else {
//                    dateText += "오전 "
//                    dateText += timeformat.format(hour.toInt(), minute.toInt())
//                }
//            }
//            return dateText
//        }

//        fun setShown(position: Int) {          //메시지 확인하여 서버로 전송
//            FirebaseDatabase.getInstance().getReference("ChatRoom")
//                .child("chatRooms").child(chatRoomKey!!).child("messages")
//                .child(messageKeys[position]).child("confirmed").setValue(true)
//                .addOnSuccessListener {
//                    Log.i("checkShown", "성공")
//                }
//        }
//    }
//
//    inner class MyMessageViewHolder(itemView: ListTalkItemMineBinding) :       // 내 메시지용 ViewHolder
//        RecyclerView.ViewHolder(itemView.root) {
//        var background = itemView.background
//        var txtMessage = itemView.txtMessage
//        var txtDate = itemView.txtDate
//        var txtIsShown = itemView.txtIsShown
//
//        fun bind(position: Int) {            //메시지 UI 레이아웃 초기화
//            var message = messages[position]
//            var sendDate = message.sended_date
//            txtMessage.text = message.content
//
//            txtDate.text = getDateText(sendDate)
//
//            if (message.confirmed.equals(true))
//                txtIsShown.visibility = View.GONE
//            else
//                txtIsShown.visibility = View.VISIBLE
//        }
//
//        fun getDateText(sendDate: String): String {        //메시지 전송 시각 생성
//            var dateText = ""
//            var timeString = ""
//            if (sendDate.isNotBlank()) {
//                timeString = sendDate.substring(8, 12)
//                var hour = timeString.substring(0, 2)
//                var minute = timeString.substring(2, 4)
//
//                var timeformat = "%02d:%02d"
//
//                if (hour.toInt() > 11) {
//                    dateText += "오후 "
//                    dateText += timeformat.format(hour.toInt() - 12, minute.toInt())
//                } else {
//                    dateText += "오전 "
//                    dateText += timeformat.format(hour.toInt(), minute.toInt())
//                }
//            }
//            return dateText
//        }
//    }

//}