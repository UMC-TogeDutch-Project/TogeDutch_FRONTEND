package umc.mobile.project.my_application_1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityMyapplicationBinding

class MyApplicationActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyapplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, txt)
        binding.spinnerBtn.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spinnerBtn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

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
}