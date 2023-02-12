package umc.mobile.project.news.mate

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.R
import umc.mobile.project.databinding.ItemNewsMateDataBinding
import umc.mobile.project.news.NewsApiService

class MateDataRVAdapter (private val MateDataList: ArrayList<MateData>) : RecyclerView.Adapter<MateDataRVAdapter.DataViewHolder>() {

    lateinit var context : Context
    private val checkboxStatus = SparseBooleanArray()
    val TAG: String = "로그"

    //ViewHolder객체
    inner class DataViewHolder(private val viewBinding: ItemNewsMateDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(mateData: MateData) {
            viewBinding.tvDate.text = mateData.status
            viewBinding.tvTitle.text = mateData.title
            viewBinding.tvUserId.text = mateData.applicant + "님이 메이트를 신청했습니다."
            val applicationIdx = mateData.application_id
            viewBinding.btnAcept.setOnClickListener {
                pushBtnAccept(applicationIdx)
                MateDataList.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
            viewBinding.btnReject.setOnClickListener {
                pushBtnReject(applicationIdx)
                MateDataList.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding =
            ItemNewsMateDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        context = viewGroup.context
        return DataViewHolder(viewBinding)
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(MateDataList[position])

    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = MateDataList.size

    private fun pushBtnAccept(applicationIdx: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        newsApiService.pushBtnAccept(applicationIdx).enqueue(object : Callback<MatePostAcceptResponse>{
            override fun onResponse(
                call: Call<MatePostAcceptResponse>,
                response: Response<MatePostAcceptResponse>
            ) {
                Log.d(TAG, "onResponse: 통신 성공")
                if(response.isSuccessful){
                    val matePostAcceptResponseData = response.body()!!
                    Log.d(TAG, "onResponse: ${matePostAcceptResponseData}")
                    when(matePostAcceptResponseData.code){
                        1000 -> Log.d(TAG, "onResponse: ${matePostAcceptResponseData.result}")
                        else -> Log.d(TAG, "onResponse: ${response.errorBody()}")
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<MatePostAcceptResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t}")
            }

        })
    }

    private fun pushBtnReject(applicationIdx: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        newsApiService.pushBtnDeny(applicationIdx).enqueue(object : Callback<MatePostAcceptResponse>{
            override fun onResponse(
                call: Call<MatePostAcceptResponse>,
                response: Response<MatePostAcceptResponse>
            ) {
                Log.d(TAG, "onResponse: 통신 성공")
                if(response.isSuccessful){
                    val matePostDenyResponseData = response.body()!!
                    Log.d(TAG, "onResponse: ${matePostDenyResponseData}")
                    when(matePostDenyResponseData.code){
                        1000 -> Log.d(TAG, "onResponse: ${matePostDenyResponseData.result}")
                        else -> Log.d(TAG, "onResponse: ${response.errorBody()}")
                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<MatePostAcceptResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t}")
            }

        })
    }

}