package umc.mobile.project.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.MainActivity
import umc.mobile.project.R
import umc.mobile.project.announcement.access_token
import umc.mobile.project.signup.SignUpActivity
import umc.mobile.project.databinding.ActivityLoginBinding
import umc.mobile.project.latitude_var
import umc.mobile.project.longtitude_var
import umc.mobile.project.ram.my_application_1.user_id_logined
import java.util.logging.Handler
import java.util.regex.Pattern
import kotlinx.coroutines.delay as delay1

class LoginActivity : AppCompatActivity(), MyCustomDialogInterface {
    private lateinit var viewBinding: ActivityLoginBinding

    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val TAG: String = "로그"

    val retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiLoginService = retrofit.create(ApiLoginService::class.java)

    var kakaoNickname: String? = null
    var kakaoEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //myCustomDialog.show()
        viewBinding.btnLogin.setOnClickListener {
            val myCustomDialog = MyCustomDialog(this, this)

            apiLoginService.userLogin(LoginRequest("${viewBinding.etEmailId.text.toString()!!}", "${viewBinding.etPassword.text.toString()!!}"))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        Log.d(TAG, "onResponse:login통신 성공")
                        val loginResponseData = response.body()
                        Log.d(TAG, "onResponse:${loginResponseData}, ${response.isSuccessful}")

                        when (loginResponseData?.code) {
                            1000 -> {
                                Log.d(TAG, "onResponse:login응답 성공 userIdx: ${loginResponseData.result!!.userIdx}, 상태: ${loginResponseData.result!!.status}")
                                user_id_logined = loginResponseData.result!!.userIdx
                                access_token = loginResponseData.result.jwt
                                latitude_var = loginResponseData.result.latitude
                                longtitude_var = loginResponseData.result.longitude
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
                            null -> Toast.makeText(this@LoginActivity, "잘못된 이메일입니다.", Toast.LENGTH_SHORT).show()
                        }


                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure:login통신 실패")
                    }

                })

        }

        // 23.02.06 제이 추가
        /** HashKey확인 */
        val keyHash = Utility.getKeyHash(this)
        Log.d(TAG, "키 해시 값 : " + keyHash)

        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakao_native_key))

        viewBinding.btnKakaoLogin.setOnClickListener {
            val myCustomDialog = MyCustomDialog(this, this)

            kakaoLogin() // 카카오 로그인
        }

        viewBinding.tbFindPassword.setOnClickListener {
            val email = viewBinding.etEmailId.text.toString()
            apiLoginService.findPwd(email).enqueue(object : Callback<FindPwdResponse> {
                override fun onResponse(
                    call: Call<FindPwdResponse>,
                    response: Response<FindPwdResponse>
                ) {
                    if (response.isSuccessful) {
                        val findPwdResponseData = response.body()
                        if (findPwdResponseData?.result != null) {
                            Log.d(TAG, "onResponse:응답 성공 ${findPwdResponseData?.result}")
                            Toast.makeText(this@LoginActivity, "회원님의 이메일로 비밀번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@LoginActivity, "잘못된 이메일을 입력하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {

                    }
                }

                override fun onFailure(call: Call<FindPwdResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure:비밀번호 찾기 통신 실패")
                }

            })
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

    // 23.02.06 제이 추가
    private fun setLogin(bool: Boolean) {
        viewBinding.btnKakaoLogin.visibility = if (!bool) View.GONE else View.VISIBLE
    }

    // 23.02.06 제이 추가
    private fun kakaoLogin() {
        val myCustomDialog = MyCustomDialog(this, this)

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(TAG, "카카오계정으로 로그인 실패 : ${error}")
            } else if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과

                UserApiClient.instance.me { user, error ->
                    Log.d(TAG, "카카오계정으로 로그인 성공 \n\n " +
                            "token: ${token.accessToken} \n\n " +
                            "me: ${user}")
                    kakaoNickname = "${user?.kakaoAccount?.profile?.nickname}"
                    kakaoEmail = "${user?.kakaoAccount?.email}"
                }

                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    //실행할 코드
                    apiLoginService.kakaoLogin(kakaoEmail)
                        .enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                Log.d(TAG, "onResponse:KakaoLogin 통신 성공")
                                val loginResponseData = response.body()
                                Log.d(TAG, "onResponse:${loginResponseData}, ${response.isSuccessful}")

                                if (loginResponseData != null) {
                                    if(!loginResponseData.isSuccess){
                                        Toast.makeText(this@LoginActivity, "카카오 로그인 실패 -> 일반 로그인 필요", Toast.LENGTH_SHORT).show()
                                        setLogin(false) // 카카오 로그인 버튼 없애기
                                    }

                                    when (loginResponseData?.code) {
                                        1000 -> {
                                            Log.d(TAG, "onResponse:login응답 성공 userIdx: ${loginResponseData.result!!.userIdx}, 상태: ${loginResponseData.result!!.status}")
                                            user_id_logined = loginResponseData.result!!.userIdx
                                            access_token = loginResponseData.result.jwt
                                            myCustomDialog.show()
                                        }
                                        2039 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                                        4000 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Log.d(TAG, "onFailure:KakaoLogin통신 실패")
                            }
                        })
                }, 3000)

            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d(TAG, "카카오톡으로 로그인 실패 : ${error}")
                    Toast.makeText(this@LoginActivity, "카카오 로그인 실패 -> 일반 로그인 필요", Toast.LENGTH_SHORT).show()
                    setLogin(false) // 카카오 로그인 버튼 없애기

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                        Log.d(TAG, "카카오톡으로 로그인 성공 \n\n " +
                                "token: ${token.accessToken} \n\n " +
                                "me: ${user}")
                    }

                    android.os.Handler(Looper.getMainLooper()).postDelayed({
                        //실행할 코드
                        apiLoginService.kakaoLogin(kakaoEmail)
                            .enqueue(object : Callback<LoginResponse> {
                                override fun onResponse(
                                    call: Call<LoginResponse>,
                                    response: Response<LoginResponse>
                                ) {
                                    Log.d(TAG, "onResponse:KakaoLogin 통신 성공")
                                    val loginResponseData = response.body()
                                    Log.d(TAG, "onResponse:${loginResponseData}, ${response.isSuccessful}")

                                    if (loginResponseData != null) {
                                        if(!loginResponseData.isSuccess){
                                            Toast.makeText(this@LoginActivity, "카카오 로그인 실패 -> 일반 로그인 필요", Toast.LENGTH_SHORT).show()
                                            setLogin(false) // 카카오 로그인 버튼 없애기
                                        }

                                        when (loginResponseData?.code) {
                                            1000 -> {
                                                Log.d(TAG, "onResponse:login응답 성공 userIdx: ${loginResponseData.result!!.userIdx}, 상태: ${loginResponseData.result!!.status}")
                                                user_id_logined = loginResponseData.result!!.userIdx
                                                access_token = loginResponseData.result.jwt
                                                myCustomDialog.show()
                                            }
                                            2039 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                                            4000 -> Toast.makeText(this@LoginActivity, "${loginResponseData.message}    오류코드:${loginResponseData.code}, ${loginResponseData.isSuccess}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Log.d(TAG, "onFailure:KakaoLogin통신 실패")
                                }
                            })
                    }, 3000)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    override fun onbtnGotoMainClicked() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun checkEmail(): Boolean {
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

    override fun onBackPressed() {
        // super.onBackPressed()
    }
}