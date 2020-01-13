package com.example.attendancekt.ui.member.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.attendancekt.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment() {

    private lateinit var viewModel: ChatMessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity())[ChatMessageViewModel::class.java]
        viewModel.connectionResult.observe(requireActivity(), Observer {
            if(it) {
                findNavController().navigate(R.id.action_loginFragment_to_fragmentChatting)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnJoin.setOnClickListener{
            edUserName.text.toString().also {
                progressBar.visibility = View.VISIBLE
                viewModel.connect(it)
            }
        }
    }

}