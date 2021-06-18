package com.example.ui.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.databinding.FragmentLoginBinding
import com.example.net.ApiRetrofit
import com.example.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/21/21
 */
class LoginFragment : Fragment() {
    lateinit var dataBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentLoginBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.etUsername.setText("admin")
        dataBinding.etPassword.setText("123456")
        dataBinding.btnLogin.setOnClickListener {
            val userName = dataBinding.etUsername.text.trim().toString()
            val password = dataBinding.etPassword.text.trim().toString()
            GlobalScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        val response = ApiRetrofit.login(LoginRequest(userName, password))
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT)
                                .show()
                            if (response.status == 200) {
                                findNavController().navigateUp()
                                findNavController().navigate(R.id.action_global_pageFragment)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "exception:${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}