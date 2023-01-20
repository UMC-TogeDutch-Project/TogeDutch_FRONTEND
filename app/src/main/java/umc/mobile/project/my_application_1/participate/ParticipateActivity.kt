package umc.mobile.project.my_application_1

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivityParticipateBinding

class ParticipateActivity : AppCompatActivity() {
    lateinit var binding: ActivityParticipateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateBinding.inflate(layoutInflater)
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
                    Log.d(ContentValues.TAG, "SearchVies Text is changed : $s")
                    return false
                }
            }

    }

    private fun initActionBar() {

        binding.mainActionbar.appbarPageNameLeftTv.text = "참여 내역"

        binding.mainActionbar.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

}