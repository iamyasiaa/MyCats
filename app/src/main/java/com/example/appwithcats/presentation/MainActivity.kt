package com.example.appwithcats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appwithcats.R
import com.example.appwithcats.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*val currentFragment = supportFragmentManager.findFragmentById(R.id.conteinerCats)

        if(currentFragment == null) {
            val fragment = Authorization.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.conteinerCats, fragment)
                .commit()
        }
    }

    override fun onCatSelected(url: String) {
        val fragment = CatFragment.newInstance(url)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.conteinerCats,fragment)
            .addToBackStack(null)
            .commit()
    }*/
    }
}