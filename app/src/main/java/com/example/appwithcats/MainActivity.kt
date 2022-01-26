package com.example.appwithcats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appwithcats.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
//    override fun onBackPressed() {
//        val count = supportFragmentManager.backStackEntryCount
//        if (count == 0) {
//            super.onBackPressed()
//            //additional code
//        } else {
//            supportFragmentManager.popBackStack()
//        }
//    }
//    override fun onBackPressed() {
//        AlertDialog.Builder(this).apply {
//            setTitle("accept")
//            setMessage("Are you sure, exit?")
//
//            setPositiveButton("Yes") { _, _ ->
//                super.onBackPressed()
//            }
//
//            setNegativeButton("No"){_, _ ->
//                Toast.makeText(this@MainActivity, "Thank you",
//                    Toast.LENGTH_LONG).show()
//            }
//            setCancelable(true)
//        }.create().show()
//    }
}