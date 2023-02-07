package umc.mobile.project.ram.my_application_1.current_application

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityCurrentapplicationBinding
import umc.mobile.project.ram.Auth.Application.ViewUpload.ApplicationGet
import umc.mobile.project.ram.Auth.Application.ViewUpload.ViewUploadGetResult
import umc.mobile.project.ram.Auth.Application.ViewUpload.ViewUploadGetService
import umc.mobile.project.ram.my_application_1.*

class CurrentApplicationActivity : AppCompatActivity(), ViewUploadGetResult {
    lateinit var binding: ActivityCurrentapplicationBinding
    lateinit var currentRVAdapter: CurrentRVAdapter
    val currentList = ArrayList<ApplicationGet>()
    var post_id_get = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        post_id_get = intent.getIntExtra("post_id", 0)

        initActionBar()
        initRecycler()
    }
    private fun initRecycler() {
        geApplicationUploadView()
    }

    private fun geApplicationUploadView(){
        val viewUploadGetService = ViewUploadGetService()
        viewUploadGetService.setViewUploadGetResult(this)
        viewUploadGetService.getViewUpload(user_id_logined) // 임의로 지정

        Log.d("받은 post_id ================= ", post_id_get.toString())

    }

    private fun initActionBar() {

//        binding.mainActionbar.appbarPageNameLeftTv.text = "공고 신청 현황"
//
//        binding.mainActionbar.appbarBackBtn.setOnClickListener {
//             finish()
//        }

    }

    override fun getPostUploadSuccess(code: Int, result: ArrayList<ApplicationGet>) {
        Log.d("받은 공고 : ========================= " , result[0].post_id.toString())
            currentList.addAll(result)
            currentRVAdapter = CurrentRVAdapter(currentList)
            binding.rvApplication.adapter = currentRVAdapter

            currentRVAdapter.setItemClickListener(object: CurrentRVAdapter.OnItemClickListener{
                override fun onItemClick(applicationGet: ApplicationGet) {

                }
            })

            currentRVAdapter.notifyDataSetChanged()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

}