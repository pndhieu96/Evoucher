package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentNotificationBinding

class NotificationFragment : DialogFragment() {

    private lateinit var binding: FragmentNotificationBinding
    private var title = ""
    private var des = ""
    private var callBack: CallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater)

        binding.tvTitle.text = title
        binding.tvDes.text = des
        binding.btnClose.setOnClickListener {
            callBack?.close()
        }

        return binding.root
    }

    interface CallBack{
        fun close()
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, des: String, callBack: CallBack) = NotificationFragment().apply {
            this.title = title
            this.des = des
            this.callBack = callBack
        }
    }
}