package umc.mobile.project.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import umc.mobile.project.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySearchBinding.inflate(layoutInflater)



        setContentView(viewBinding.root)
    }
}