package umc.mobile.project.chat

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import umc.mobile.project.MainActivity
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityChattingBinding


class ChattingActivity: AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        binding.etcBtn.setOnClickListener {
            // 다이얼로그 부분분
            val dialog:BottomSheetDialog = BottomSheetDialog(this)
            dialog.setContentView(R.layout.chat_bottom_dialog_content)

            val phone_btn = dialog.findViewById<AppCompatButton>(R.id.phone_btn)
            phone_btn?.setOnClickListener {
                Toast.makeText(this, "전화 걸기 클릭", Toast.LENGTH_LONG).show()
            }

            val declaration_btn = dialog.findViewById<AppCompatButton>(R.id.declaration_btn)
            declaration_btn?.setOnClickListener {
                Toast.makeText(this, "신고 버튼 클릭", Toast.LENGTH_LONG).show()

                val intent = Intent(this, PopUpActivity::class.java)
                startActivity(intent)
            }

            val exit_btn = dialog.findViewById<AppCompatButton>(R.id.exit_btn)
            exit_btn?.setOnClickListener {
                Toast.makeText(this, "나가기 클릭", Toast.LENGTH_LONG).show()

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
}