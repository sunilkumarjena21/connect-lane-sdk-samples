package com.example.myapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.connect.lane.sdk.ConnectLane
import org.connect.lane.sdk.ConnectLaneActivity
import org.connect.lane.sdk.ConnectLaneConferenceOptions
import org.connect.lane.sdk.ConnectLaneUserInfo
import java.net.MalformedURLException
import java.net.URL
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun initialise(){

        // Initialize default options for  Meet conferences.
        val serverURL: URL
        serverURL = try {
            URL("https://webrtc.centralindia.cloudapp.azure.com")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val userName = findViewById<EditText>(R.id.userName)
        val text = userName.text.toString()
        android.util.Log.e("textUser",""+text)
        var bundle: Bundle = Bundle()
      //  bundle.putString("displayName", text)

        val defaultOptions = ConnectLaneConferenceOptions.Builder()
            .setServerURL(serverURL)
            .setWelcomePageEnabled(false)
            .setAudioMuted(true)
            .setVideoMuted(true)
            .setUserInfo(ConnectLaneUserInfo(bundle))
            .setFeatureFlag("invite.enabled",true)
            .setFeatureFlag("recording.enabled",true)
            .setFeatureFlag("video-share.enabled",true)
            .setFeatureFlag("live-streaming.enabled",false)
            .build()
        ConnectLane.setDefaultConferenceOptions(defaultOptions)
    }

    fun onButtonClick(v: View?) {
        initialise()
        val editConfernceName = findViewById<EditText>(R.id.conferenceName)
        val text = editConfernceName.text.toString()
        if (text.length > 0) {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = ConnectLaneConferenceOptions.Builder()
                .setRoom(text)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            ConnectLaneActivity.launch(this, options)
        }
    }
}
