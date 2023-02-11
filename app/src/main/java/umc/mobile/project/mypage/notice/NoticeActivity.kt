package umc.mobile.project.mypage.notice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    private val viewBinding: ActivityNoticeBinding by lazy {
        ActivityNoticeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initActionBar()
    }

    private fun initActionBar() {
        viewBinding.include.appbarPageNameLeftTv.text = "공지사항"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            finish()
        }
    }
}