package umc.mobile.project.mypage.withdrawal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.announcement.access_token
import umc.mobile.project.databinding.ActivityWithdrawalBinding
import umc.mobile.project.login.LoginActivity
import umc.mobile.project.ram.my_application_1.user_id_logined

class WithdrawalActivity : AppCompatActivity(), WithdrawalResult {

    val TAG: String = "로그"

    private val viewBinding: ActivityWithdrawalBinding by lazy {
        ActivityWithdrawalBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.btnWithdrawal.setOnClickListener{
            Log.d(TAG, "탈퇴하기 버튼 클릭")
            save()

            // 탈퇴 후에 로그인 화면으로 돌아가기
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun save() {
        val withdrawalService = WithdrawalService()
        withdrawalService.setWithdrawalResult(this)
        withdrawalService.changeUserStatus(access_token, user_id_logined)
    }

    override fun changeUserStatusSuccess(result: ChangeUserStatusResult) {
        Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
    }

    override fun changeUserStatusFailure() {
        Toast.makeText(this, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
    }

}