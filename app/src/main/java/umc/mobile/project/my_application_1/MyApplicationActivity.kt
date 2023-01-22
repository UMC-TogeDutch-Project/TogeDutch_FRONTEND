package umc.mobile.project.my_application_1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import umc.mobile.project.R
import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.ActivityMyapplicationBinding
import umc.mobile.project.my_application_1.current_application.CurrentData
import umc.mobile.project.my_application_1.current_application.CurrentRVAdapter

class MyApplicationActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyapplicationBinding
    lateinit var myApplicationRVAdapter: MyApplicationRVAdapter
    val applicationList = ArrayList<Application>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        initRecycler()

        // 검색창
        var searchViewListener : SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    // phoneBookListAdapter.getFilter().filter(s)
                    Log.d(TAG, "SearchVies Text is changed : $s")
                    return false
                }
            }

        // 스피너
        setupSpinnerText()
        setupSpinnerHandler()

    }

    private fun setupSpinnerText() {
        val txt = resources.getStringArray(R.array.spinner_txt)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_custom, txt)
        binding.spinnerBtn.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerBtn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0) { // 업로드 기준일 때
                    initActionBar()
                    binding.searchView.queryHint = "나의 공고를 검색해보세요"
                    binding.frameLayoutParticipate.visibility = View.INVISIBLE // 참여 화면 없애기
                    binding.frameLayoutDefault.visibility = View.VISIBLE // 업로드 화면 보이기
                }

                else{ // 참여 일 때
                    binding.mainActionbar.appbarPageNameLeftTv.text = "참여"
                    binding.searchView.queryHint = "나의 참여내역을 검색해보세요"
                    binding.frameLayoutParticipate.visibility = View.VISIBLE // 참여 화면 없애기
                    binding.frameLayoutDefault.visibility = View.INVISIBLE // 업로드 화면 보이기
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "나의 공고"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    private fun initRecycler() {
        applicationList.apply {
            add(Application(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345))
            add(Application(8, "엽떡 2인으로 같이 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 1000, 130000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 2, 67.1234567, 127.3012345))


            myApplicationRVAdapter = MyApplicationRVAdapter(applicationList)
            binding.rvApplication.adapter = myApplicationRVAdapter

            myApplicationRVAdapter.setItemClickListener(object: MyApplicationRVAdapter.OnItemClickListener{
                override fun onItemClick(application: Application) {
                    val intent = Intent(this@MyApplicationActivity, MyApplicationDetailActivity::class.java)
                    startActivity(intent)
                }
            })

            myApplicationRVAdapter.notifyDataSetChanged()

        }
    }
}