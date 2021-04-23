package com.example.ui.fragment.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.databinding.FragmentRegisterBinding
import com.example.net.ApiRetrofit
import com.example.request.RegisterUserRequest
import kotlinx.coroutines.*

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
class RegisterFragment : Fragment() {
    lateinit var dataBinding: FragmentRegisterBinding
    lateinit var job: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentRegisterBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.toolBar.setNavigationOnClickListener {
            findNavController().apply {
                popBackStack(graph.startDestination, false)
            }
        }
        dataBinding.toolBar.setTitle(R.string.tv_register)
        dataBinding.btnRegister.setOnClickListener {
            val userName = dataBinding.etUsername.text.trim().toString()
            val password = dataBinding.etPassword.text.trim().toString()
            val nickName = dataBinding.etNickname.text.trim().toString()
            job = GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val request =
                            RegisterUserRequest(userName, "100", userName, password, nickName)
                        val response = ApiRetrofit.registerUser(request)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "exception:${e.message}")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}