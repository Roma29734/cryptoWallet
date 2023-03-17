package com.example.cryptowallet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptowallet.R
import com.example.cryptowallet.databinding.ActivityMainBinding
import com.example.cryptowallet.databinding.ActivityStartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            if (loadData() == null) {
                saveDate(1000)
            } else {
                saveDate(loadData()!!.toInt() + 50)
            }
        }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun saveDate(state: Int) {
        val sheared = getSharedPreferences("money", MODE_PRIVATE)

        sheared.edit().apply {
            putString("MONEY_KEY", state.toString())
        }.apply()
    }

    private fun loadData(): String? {
        val sheared = getSharedPreferences("money", MODE_PRIVATE)

        return sheared.getString("MONEY_KEY", null)
    }
}