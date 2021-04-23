package com.example.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.R
import com.example.databinding.FragmentMainBinding
import com.example.util.AndroidBarUtils
import com.qmuiteam.qmui.arch.QMUIFragment

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
class MainFragment : Fragment() {
    lateinit var dataBinding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentMainBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.cvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_global_loginFragment)
        }
        dataBinding.cvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_global_registerFragment)
        }
    }
}