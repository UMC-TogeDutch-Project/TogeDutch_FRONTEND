package umc.mobile.project.commercial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
//import umc.mobile.project.announcement.AnnounceDetailActivity
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGetResult
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGetService
import umc.mobile.project.databinding.ActivityCommercialListBinding
import umc.mobile.project.ram.my_application_1.user_id_logined


class CommercialListActivity: AppCompatActivity(), CommercialGetResult {
    private lateinit var binding: ActivityCommercialListBinding
    private lateinit var commercialRVAdapter: CommercialRVAdapter
//    var mCommercialData = ArrayList<CommercialData>()
    private var commercialList = ArrayList<CommercialGet>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommercialListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initActionBar()

    }


    private fun initRecyclerView(){

        getCommercial()
//        mCommercialData.apply{
//            add(CommercialData("2023년 1월 17일 오후 6시 21분 ","가게이름","000님 광고 신청이 접수되었습니다."))
//
//        }
//        commercialRVAdapter = CommercialRVAdapter(mCommercialData)
//        binding.recyclerView.adapter=commercialRVAdapter //리사이클러뷰에 어댑터 연결
//        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
//        binding.recyclerView.addItemDecoration(AnnounceRVAdapterDecoration(20))

//        commercialRVAdapter.setItemClickListener(object: CommercialRVAdapter.OnItemClickListener{
//            override fun onItemClick(commercialData: CommercialData) {
//                val intent = Intent(this@CommercialListActivity, CommercialSignUpActivity::class.java)
//                startActivity(intent)
//            }
//        })
    }

    private fun initActionBar(){
        binding.comActionBar.appbarComSignUp.setOnClickListener{
            val intent = Intent(this, CommercialSignUpActivity::class.java)
            startActivity(intent)
        }
        binding.comActionBar.appbarBackBtn.setOnClickListener{
            finish()
        }
    }

    private fun getCommercial(){
        val commercialGetService = CommercialGetService()
        commercialGetService.setCommercialGetResult(this)
        commercialGetService.sendPost(user_id_logined, )
    }

    override fun commercialGetSuccess(result: ArrayList<CommercialGet>) {
        commercialList.addAll(result)
        commercialRVAdapter = CommercialRVAdapter(commercialList)
        binding.recyclerView.adapter = commercialRVAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        commercialRVAdapter.notifyDataSetChanged()
    }

    override fun commercialGetFailure() {

    }


}