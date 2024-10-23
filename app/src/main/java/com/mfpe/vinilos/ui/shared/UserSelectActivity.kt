package com.mfpe.vinilos.ui.shared

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mfpe.vinilos.R
import com.mfpe.vinilos.databinding.ActivityUserSelectBinding
import com.mfpe.vinilos.utils.PrefsManager

class UserSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCollectors.setOnClickListener {
            goToHome("collector")
        }

        binding.buttonVisitors.setOnClickListener {
            goToHome("visitor")
        }

        if (PrefsManager.getInstance(this).getUserType!!.isNotEmpty()) {
            goToHome(PrefsManager.getInstance(this).getUserType!!)
        }
    }

    private fun goToHome(userType: String) {
        PrefsManager.getInstance(this).saveUserType(userType)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}