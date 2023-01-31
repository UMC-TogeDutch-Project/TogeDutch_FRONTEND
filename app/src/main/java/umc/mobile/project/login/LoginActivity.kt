package umc.mobile.project.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.MainActivity
import umc.mobile.project.R
import umc.mobile.project.signup.SignUpActivity
import umc.mobile.project.databinding.ActivityLoginBinding
import umc.mobile.project.signup.Auth.ApiService
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), MyCustomDialogInterface {
    private lateinit var viewBinding: ActivityLoginBinding

    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiLoginService = retrofit.create(ApiLoginService::class.java)



        //myCustomDialog.show()
        viewBinding.btnLogin.setOnClickListener {
            val myCustomDialog = MyCustomDialog(this, this)
            apiLoginService.userLogin(LoginRequest("${viewBinding.etEmailId.text.toString()!!}", "${viewBinding.etPassword.text.toString()!!}"))
                .enqueue(object: Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        Log.d(TAG, "onResponse:통신 성공")
                        val loginResponseData = response.body()
                        Log.d(TAG, "onResponse:${loginResponseData}, ${response.isSuccessful}")

                        when(loginResponseData?.code){
                            1000 -> {Log.d(TAG, "onResponse:응답 성공 userIdx: ${loginResponseData.result!!.userIdx}, 상태: ${loginResponseData.result!!.status}")
                                myCustomDialog.show()
                            }
                            2010 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2011 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2012 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2013 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2014 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2015 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2016 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2017 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            2018 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            3000 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            3013 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            3014 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                            3015 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure:통신 실패")
                    }

                })

        }

        viewBinding.btnKakaoLogin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kauth.kakao.com/oauth/authorize?client_id=8525a5ca4d8aa3d945c288e381163d5c&redirect_uri=http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/oauth/kakao&response_type=code"))
            startActivity(intent)
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