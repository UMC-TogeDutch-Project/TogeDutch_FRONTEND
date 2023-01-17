package umc.mobile.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityNoticePostBinding


class NoticePostActivity : AppCompatActivity() {
    private val viewBinding: ActivityNoticePostBinding by lazy {
        ActivityNoticePostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.backBtn.setOnClickListener{
            finish()
        }

    }
}