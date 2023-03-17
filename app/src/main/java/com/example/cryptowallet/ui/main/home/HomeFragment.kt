package com.example.cryptowallet.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cryptowallet.R
import com.example.cryptowallet.base.BaseFragment
import com.example.cryptowallet.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>
        (FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            if(loadData() != null) {
                binding.textMoney.text = "${loadData()}$"
            } else {
                binding.textMoney.text = "0$"
            }
        }
        binding.matButTopUpYourWallet.setOnClickListener {

        }
    }

    private fun loadData(): String? {
        val sheared = requireActivity().getSharedPreferences("money", AppCompatActivity.MODE_PRIVATE)

        return sheared.getString("MONEY_KEY", null)
    }
}