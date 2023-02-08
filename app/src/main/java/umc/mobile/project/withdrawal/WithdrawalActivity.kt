package umc.mobile.project.withdrawal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityWithdrawalBinding

class WithdrawalActivity : AppCompatActivity() {
    private val viewBinding: ActivityWithdrawalBinding by lazy {
        ActivityWithdrawalBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

    }

}