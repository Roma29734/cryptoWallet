package com.example.cryptowallet.ui.start

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.cryptowallet.databinding.ActivityStartBinding
import com.example.cryptowallet.ui.login.LoginActivity
import com.example.cryptowallet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private var _binding: ActivityStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StarViewModel by viewModels()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            if(viewModel.getSizeUserTable() == 0) {
                goToLogin()
            } else {
                authSet()

                binding.materialButton.setOnClickListener {
                    viewModel.getUserData()
                    lifecycleScope.launch (Dispatchers.Main){
                        viewModel.userData.observe(this@StartActivity) {
                            if(binding.editTextNumberPassword.text.toString().toInt() == it.password) {
                                goToMain()
                            } else {
                                lifecycleScope.launch (Dispatchers.Main){
                                    Toast.makeText(this@StartActivity, "invalid password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                }
                binding.imageButton.setOnClickListener {
                    authSet()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun authSet() {
        checkDeviceHasBiometric()

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()

                    goToMain()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        biometricPrompt.authenticate(promptInfo)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(this)
        var info = ""
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                info = "App can authenticate using biometrics."

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
                info = "No biometric features available on this device."

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }

                startActivityForResult(enrollIntent, 100)
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                TODO()
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                TODO()
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                TODO()
            }
        }
    }

    private fun goToMain() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goToLogin() {
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}