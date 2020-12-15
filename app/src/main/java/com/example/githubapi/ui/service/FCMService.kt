package com.example.githubapi.ui.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    companion object {
        const val TAG = "FCMService"
    }
}