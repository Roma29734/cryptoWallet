package com.example.cryptowallet.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.cryptowallet.R
import com.example.cryptowallet.base.BaseFragment
import com.example.cryptowallet.databinding.FragmentProfileBinding
import com.example.cryptowallet.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding>
        (FragmentProfileBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) {
            binding.apply {
                textName.text = it.name
                textPhoneNumber.text = it.phoneNumber
            }
        }
    }
}