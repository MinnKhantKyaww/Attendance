package com.example.attendancekt.ui.member.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancekt.R
import kotlinx.android.synthetic.main.fragment_chatting.*
import kotlinx.android.synthetic.main.fragment_member_attendance.*

class FragmentChatting: Fragment() {

    private lateinit var viewModel: ChatMessageViewModel
    private var adapter = ChatMessageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity())[ChatMessageViewModel::class.java]
        viewModel.messages.observe(this, Observer {
            adapter.submitList(it)
            chat_recyclerView.smoothScrollToPosition(it.size - 1)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chatting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManger = LinearLayoutManager(view.context)
        layoutManger.stackFromEnd = true

        chat_recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = this@FragmentChatting.adapter
        }

        btnSend.setOnClickListener{
            viewModel.send(edMessage.text.toString())
            edMessage.setText("")
        }
    }
}