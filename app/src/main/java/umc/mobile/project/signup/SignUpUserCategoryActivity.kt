package umc.mobile.project.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import umc.mobile.project.R
import umc.mobile.project.databinding.ActivitySignUpUserCategoryBinding

class SignUpUserCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpUserCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpUserCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupSpinnerYear()
        setupSpinnerHandler()

        viewBinding.btnNext.setOnClickListener {
            val intent = Intent(this, SignUpUserPersonalInfoActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnBack.setOnClickListener {
            val intent = Intent(this, SignUpRegionActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun setupSpinnerYear() {
        val years = resources.getStringArray(R.array.category_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        viewBinding.spinnerCategory.adapter = adapter
    }
    private fun setupSpinnerHandler() {
        viewBinding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}