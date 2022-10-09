package com.yusufcancakmak.chattapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupieAdapter
import com.yusufcancakmak.chattapp.databinding.ActivityGroupieExampleBinding

class GroupieExampleActivity :AppCompatActivity() {
    private lateinit var binding :ActivityGroupieExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupieExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBinding()
    }

    private fun initBinding() {
        val adapter = GroupieAdapter()
        binding.recyclerViewGroupie.adapter = adapter
    }
}