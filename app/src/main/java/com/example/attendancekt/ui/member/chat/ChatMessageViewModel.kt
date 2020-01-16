package com.example.attendancekt.ui.member.chat

import android.app.Application
import androidx.databinding.DataBinderMapper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.example.attendancekt.AttendaceApplication
import com.example.attendancekt.R
import com.example.attendancekt.model.entity.ChatMessage
import com.example.attendancekt.model.entity.MessageType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.reactivex.disposables.CompositeDisposable
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class ChatMessageViewModel(application: Application): AndroidViewModel(application) {

    private var stompClient: StompClient
    private var objectMapper: ObjectMapper
    private val compositeDisposable = CompositeDisposable()

    private val list = mutableListOf<ChatMessage>()

    val messages = MutableLiveData<List<ChatMessage>>()
    val error = MutableLiveData<String>()
    val connectionResult = MutableLiveData<Boolean>()

    init {
        val baseUrl = application.getString(R.string.server_url)
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, baseUrl)
        objectMapper = jacksonObjectMapper()
    }

    fun connect(user: String) {
        AttendaceApplication.currentUser = user
        stompClient.connect()

        val dispo = stompClient.lifecycle().subscribe {event ->
            when(event.type) {

                LifecycleEvent.Type.OPENED -> {

                    stompClient.topic("/msg/public").subscribe {
                        val message = objectMapper.readValue(it.payload, ChatMessage::class.java)
                        list.add(message)

                        val data = mutableListOf<ChatMessage>()
                        data.addAll(list)
                        messages.postValue(data)
                    }

                    val joinMsg = ChatMessage(MessageType.JOIN, sender = user)
                    stompClient.send("/app/chat.addUser", objectMapper.writeValueAsString(joinMsg)).subscribe()

                    connectionResult.postValue(true)
                }

                LifecycleEvent.Type.ERROR -> {
                    error.postValue(event.exception.message)

                    connectionResult.postValue(false)
                }

                else -> {}
            }

        }

        compositeDisposable.add(dispo)
    }

    fun send(text: String) {
        val msg = ChatMessage(MessageType.CHAT, text, AttendaceApplication.currentUser)

        stompClient.send("/app/chat.sendMessage", objectMapper.writeValueAsString(msg)).subscribe()
    }

    fun disconnect() {
        stompClient.disconnect()
        compositeDisposable.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}