package umc.mobile.project.mypage.notice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.R
import umc.mobile.project.databinding.ItemNoticeBinding
import umc.mobile.project.mypage.notice.detail.GetNoticeResult
import umc.mobile.project.mypage.notice.detail.GetNoticeService


class NoticeRVAdapter(private val noticeTitleList: ArrayList<NoticeGet>): RecyclerView.Adapter<NoticeRVAdapter.ViewHolder>(),
    GetNoticeResult {
    val TAG: String = "로그"

    lateinit var context : Context

    var noticeActivity: NoticeActivity? = null

    var noticeId : Int = -1

    lateinit var bindingItemNoticeBinding: ItemNoticeBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NoticeRVAdapter.ViewHolder {
        val binding: ItemNoticeBinding = ItemNoticeBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        context = viewGroup.context

        noticeActivity = context as NoticeActivity

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = noticeTitleList.size

    override fun onBindViewHolder(holder: NoticeRVAdapter.ViewHolder, position: Int) {
        holder.bind(noticeTitleList[position])
    }

    inner class ViewHolder(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noticeGet: NoticeGet) {
            var selected_notice_btn : Int = 0
            var isSelected = false

            val noticeTitle : String = noticeGet.title
            val create_time = noticeGet.created_at
            val create_time_string = create_time.toString()

            Log.d("noticeTitle: ", noticeGet.title)
            Log.d("create_time: ", noticeGet.created_at.toString())
            Log.d("create_time_string: ", create_time.toString())

            var create_year = create_time_string.substring(0 until 4)
            var create_month = create_time_string.substring(5 until 7)
            var create_day = create_time_string.substring(8 until 10)
            var create_time_substring = create_year+"년 " + create_month + "월 " + create_day + "일"

            Log.d("create_year: ", create_year)
            Log.d("create_month: ", create_month)
            Log.d("create_day: ", create_day)
            Log.d("create_time_substring: ", create_time_substring)

            binding.noticeTitle.text = noticeTitle
            binding.noticeTime.text = create_time_substring

            Log.d("binding.noticeTitle.text: ", binding.noticeTitle.toString())
            Log.d("binding.noticeTime.text: ", binding.noticeTime.toString())

            noticeId = noticeGet.noticeId
            Log.d("noticeId값: ", noticeId.toString())
            Log.d("notice_id값: ", noticeGet.noticeId.toString())

            binding.seeNoticeDetail.setOnClickListener {
                isSelected = !isSelected
                if(isSelected) {
                    noticeId = noticeGet.noticeId
                    selected_notice_btn++

                    bindingItemNoticeBinding = binding

                    // notice 상세
                    getNoticeDetail()

                } else {
                    selected_notice_btn--
                    binding.seeNoticeDetail.setImageResource(R.drawable.back)
                    binding.noticeFramelayout.visibility = View.GONE
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(noticeGet: NoticeGet)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    // 공지사항 내용 조회
    fun getNoticeDetail() {
        val getNoticeService = GetNoticeService()
        getNoticeService.setGetNoticeResult(this)
        getNoticeService.getNoticeById(noticeId)
    }

    override fun getNoticeSuccess(code: Int, result: NoticeGet) {
        bindingItemNoticeBinding.seeNoticeDetail.setImageResource(R.drawable.clickback)

        Log.d("noticeId 값 : ", result.noticeId.toString())
        Log.d("title 값 : ", result.title)
        Log.d("공지사항 내용 : ", result.title)

        bindingItemNoticeBinding.randomMatching.noticeContent.text = result.content

        Log.d("bindingItemNoticeBinding.randomMatching.noticeContent.text 값 : ", bindingItemNoticeBinding.randomMatching.noticeContent.toString())

        bindingItemNoticeBinding.noticeFramelayout.visibility = View.VISIBLE

    }

    override fun getNoticeFailure(code: Int, message: String) {
        Toast.makeText(context, "공지사항 세부 내용 로딩 실패", Toast.LENGTH_SHORT).show()
        Log.d("fail noticeId 값 : ", noticeId.toString())
    }
}