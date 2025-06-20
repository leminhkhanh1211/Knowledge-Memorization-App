package com.example.knowledgememorizationapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.example.knowledgememorizationapp.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnForgotPasswordSubmit.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = binding.etForgotPasswordEmail.text.toString()
        if (validateEmail(email)) {
            showProgressBar()
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                hideProgressBar()
                if (task.isSuccessful) {
                    binding.tilEmailForgetPassword.visibility = View.GONE
                    binding.tvSubmitMsg.visibility = View.VISIBLE
                    binding.btnForgotPasswordSubmit.visibility = View.GONE
                } else {
                    showToast("Reset password failed, try again later")
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailForgetPassword.error = "Enter valid email address"
            false
        } else {
            binding.tilEmailForgetPassword.error = null
            true
        }
    }
}