package umc.mobile.project.mypage.notice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityNoticeBinding
import umc.mobile.project.mypage.notice.all.GetAllNoticeResult
import umc.mobile.project.mypage.notice.all.GetAllNoticeService

class NoticeActivity : AppCompatActivity(), GetAllNoticeResult {
    lateinit var viewBinding: ActivityNoticeBinding
    var noticeTitleList = ArrayList<NoticeGet>()

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

    }

    override fun onResume() {
        super.onResume()
        initActionBar()
        getNotice()
    }

    private fun initRecycler(result : ArrayList<NoticeGet>) {
        val noticeRVAdapter = NoticeRVAdapter(result)
        viewBinding.rvNotice.adapter = noticeRVAdapter
        viewBinding.rvNotice.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewBinding.rvNotice.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }

    //Get Title
    private fun getNotice() {
        val getAllNoticeService = GetAllNoticeService()
        getAllNoticeService.setGetAllNoticeResult(this)
        getAllNoticeService.getAllNotices()
    }

    override fun getAllNoticesSuccess(code: Int, result: ArrayList<NoticeGet>) {
        initRecycler(result)

        for (i in 0 .. result.size - 1) {
            Log.d(TAG, "notice id: ${result[i].noticeId}")
            Log.d(TAG, "notice title: ${result[i].title}")
        }

        //Toast.makeText(this, "공지사항 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getAllNoticesFailure(code: Int, message: String) {
        Toast.makeText(this, "공지사항 불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    private fun initActionBar() {
        viewBinding.include.appbarPageNameLeftTv.text = "공지사항"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}