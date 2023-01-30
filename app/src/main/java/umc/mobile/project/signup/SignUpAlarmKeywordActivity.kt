package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.databinding.ActivitySignUpAlarmKeywordBinding
import umc.mobile.project.signup.Auth.ApiService
import umc.mobile.project.signup.Auth.KeywordRequest
import umc.mobile.project.signup.Auth.KeywordResponse


class SignUpAlarmKeywordActivity : AppCompatActivity() {
    val TAG: String = "로그"
    private lateinit var viewBinding: ActivitySignUpAlarmKeywordBinding

    var keywordList = arrayListOf<DataVo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var key1 : String? = "word1"
        var key2 : String? = "word2"
        var key3 : String? = "word3"
        var key4 : String? = "word4"
        var key5 : String? = "word5"
        var key6 : String? = "word6"


        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        viewBinding = ActivitySignUpAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}")

        //////////////////////////////////////////////////////
        val mAdapter = CustomAdapter(this, keywordList)
        viewBinding.tableRecycleEdit.adapter = mAdapter
        val layout = LinearLayoutManager(this)
        viewBinding.tableRecycleEdit.layoutManager = layout
        viewBinding.tableRecycleEdit.setHasFixedSize(true)


        viewBinding.btnInputAlarm.setOnClickListener {
            mAdapter.addItem(DataVo(viewBinding.etInputAlarmKeyword.text.toString()))
        }


        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpRegionActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWord", viewBinding.etInputAlarmKeyword.text.toString())

            apiService.getUserKeywordId(KeywordRequest("${key1}", "${key2}", "${key3}", "${key4}","${key5}","${key6}"))
                .enqueue(object : Callback<KeywordResponse>{
                override fun onResponse(
                    call: Call<KeywordResponse>,
                    response: Response<KeywordResponse>
                ) {
                    if (response.isSuccessful){
                        val keywordResponseData = response.body()
                        if(keywordResponseData != null){
                            when(keywordResponseData.code){
                                1000 -> {Toast.makeText(this@SignUpAlarmKeywordActivity, "성공! ${keywordResponseData.message}", Toast.LENGTH_SHORT).show()
                                    var keywordIdx :Int = keywordResponseData.result.keyword_Id
                                    intent.putExtra("name", name)
                                    intent.putExtra("email", email)
                                    intent.putExtra("password", password)
                                    intent.putExtra("phoneNum", phoneNum)
                                    intent.putExtra("keyWordIdx", keywordIdx)

                                    startActivity(intent)
                                    overridePendingTransition(0, 0)
                                }

                                else -> Toast.makeText(this@SignUpAlarmKeywordActivity, "알수없는 애러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{

                    }
                }

                override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                }



            })

//            startActivity(intent)
//            overridePendingTransition(0, 0)
        }

        viewBinding.btnBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)

        }
    }


}