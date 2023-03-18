package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        var phoneNum = intent.getStringExtra("phoneNum")
        var image = intent.getStringExtra("image")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        viewBinding = ActivitySignUpAlarmKeywordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val keywordList : ArrayList<DataVo> = arrayListOf()
        val keywordListIt : MutableList<String?> = mutableListOf(null, null, null, null, null, null)

        Log.d(TAG, "onCreate: ${name}, ${email}, ${password}, ${phoneNum}")

        //////////////////////////////////////////////////////

        val customAdapter = CustomAdapter(keywordList)

        viewBinding.tableRecycleEdit.adapter = customAdapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        viewBinding.tableRecycleEdit.layoutManager = staggeredGridLayoutManager




        viewBinding.btnInputAlarm.setOnClickListener{
            val keyword = viewBinding.etInputAlarmKeyword.text.toString()
//            if(keywordList.size < 6){
            keywordList.apply { add(DataVo(keyword)) }
            Log.d(TAG, "onCreate:${keywordList.size}")
            customAdapter.notifyDataSetChanged()
            Log.d(TAG, "onCreate:${keywordList}")


            viewBinding.etInputAlarmKeyword.setText(null)
//            }
//            else{
//                Toast.makeText(this@SignUpAlarmKeywordActivity, "최대 키워드 갯수를 초과했습니다.", Toast.LENGTH_SHORT).show()
//            }

        }


        viewBinding.btnNext.setOnClickListener {
            val keywordListIt : MutableList<String?> = mutableListOf(null, null, null, null, null, null)

            val intent = Intent(this, SignUpRegionActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("phoneNum", phoneNum)
            intent.putExtra("keyWord", viewBinding.etInputAlarmKeyword.text.toString())
            intent.putExtra("image", image)

            for(i in 0..keywordList.size - 1){
                keywordListIt[i] = keywordList.get(i).toString().substring(22, keywordList.get(i).toString().lastIndex)
            }

            apiService.getUserKeywordId(KeywordRequest("${keywordListIt[0]}", "${keywordListIt[1]}", "${keywordListIt[2]}", "${keywordListIt[3]}","${keywordListIt[4]}","${keywordListIt[5]}"))
                .enqueue(object : Callback<KeywordResponse>{
                override fun onResponse(
                    call: Call<KeywordResponse>,
                    response: Response<KeywordResponse>
                ) {
                    if (response.isSuccessful){
                        val keywordResponseData = response.body()
                        if(keywordResponseData != null){
                            when(keywordResponseData.code){
                                1000 -> {//Toast.makeText(this@SignUpAlarmKeywordActivity, "성공! ${keywordResponseData.message}", Toast.LENGTH_SHORT).show()
                                    var keywordIdx :Int = keywordResponseData.result.keywordIdx

                                    intent.putExtra("name", name)
                                    intent.putExtra("email", email)
                                    intent.putExtra("password", password)
                                    intent.putExtra("phoneNum", phoneNum)
                                    intent.putExtra("keyWordIdx", keywordIdx)
                                    Log.d(TAG, "onResponse: ${keywordListIt[0]}, ${keywordListIt[1]}, ${keywordListIt[2]}, ${keywordListIt[3]}, ${keywordListIt[4]}, ${keywordListIt[5]}")
                                    Log.d(TAG, "onResponse: ${keywordResponseData}")
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