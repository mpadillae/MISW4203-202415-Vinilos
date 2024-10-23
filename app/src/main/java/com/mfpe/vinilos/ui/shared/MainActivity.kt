package com.mfpe.vinilos.ui.shared

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mfpe.vinilos.R
import com.mfpe.vinilos.databinding.ActivityMainBinding
import com.mfpe.vinilos.utils.PrefsManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_account)
        }

        binding.exitButton.setOnClickListener {
            PrefsManager.getInstance(this).clear()
            Intent(this, UserSelectActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
                finish()
            }
        }
    }

}