package umc.mobile.project.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import umc.mobile.project.MainActivity
import umc.mobile.project.R
import umc.mobile.project.signup.SignUpActivity
import umc.mobile.project.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), MyCustomDialogInterface {
    private lateinit var viewBinding: ActivityLoginBinding

    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnLogin.setOnClickListener {
            val myCustomDialog = MyCustomDialog(this, this)
            myCustomDialog.show()
        }

        viewBinding.tbSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        viewBinding.etEmailId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // text가 변경된 후 호출
                // s에는 변경 후의 문자열이 담겨 있다.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // text가 변경되기 전 호출
                // s에는 변경 전 문자열이 담겨 있다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // text가 바뀔 때마다 호출된다.
                // 우린 이 함수를 사용한다.
                checkEmail()
            }
        })


    }

    override fun onbtnGotoMainClicked() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun checkEmail():Boolean{
        var email = viewBinding.etEmailId.text.toString().trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
        if (p) {
            //이메일 형태가 정상일 경우
            viewBinding.tvEmailCheck.setText(" ")
            return true
        } else {
            viewBinding.tvEmailCheck.setText("이메일을 입력하세요.")
            //또는 questionEmail.setTextColor(R.color.red.toInt())
            return false
        }
    }



}