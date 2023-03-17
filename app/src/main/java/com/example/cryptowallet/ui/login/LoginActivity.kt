package com.example.cryptowallet.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.example.cryptowallet.databinding.ActivityLoginBinding
import com.example.cryptowallet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.materialButton.setOnClickListener {
            if (inputCheck(binding.editTextTextPersonName.text.toString(),
                    binding.editTextTextPersonPassword.text.toString(),
                    binding.editTextTextPersonPhoneNumber.text.toString()
                )
            ) {
                viewModel.createUser(binding.editTextTextPersonName.text.toString(),
                    binding.editTextTextPersonPhoneNumber.text.toString(),
                    binding.editTextTextPersonPassword.text.toString().toInt(),
                )
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
                goToMain()
            } else {
                Toast.makeText(this, "empty fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun inputCheck(title: String, subTitle: String, thirdTitle: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(subTitle) && TextUtils.isEmpty(
            thirdTitle))
    }

    private fun goToMain() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}