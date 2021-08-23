package com.steven.androidmediaplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun mediaPlayerVideoClick(view: View) {
        startActivity(Intent(this@MainActivity, MediaPlayerVideoActivity::class.java))
    }

    fun exoPlayerVideoClick(view: View) {
        startActivity(Intent(this@MainActivity, ExoPlayerAudioActivity::class.java))
    }

    fun mediaControllerVideoView(view: View) {
        startActivity(Intent(this@MainActivity, MediaControllerVideoViewActivity::class.java))
    }

    fun mediaPlayerSurfaceView(view: View) {
        startActivity(Intent(this@MainActivity, MediaVideoActivity::class.java))
    }

    fun mediaPlayerSurfaceViewMediaController(view: View) {
        startActivity(Intent(this@MainActivity, MediaPlayerControllerSurfaceViewActivity::class.java))
    }

    fun exoPlayerClick(view: View) {
        startActivity(Intent(this@MainActivity, ExoPlayerVideoActivity::class.java))
    }


}
