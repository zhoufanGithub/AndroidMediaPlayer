package com.steven.androidmediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_media_controller_video_view.*

class MediaControllerVideoViewActivity : AppCompatActivity() {

    private var video_view: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_controller_video_view)

    }

    override fun onResume() {
        super.onResume()
        mediaControllerVideoView()
    }

    /**
     * 通过MediaController控制界面中的快进、快退、暂停、播放等
     * 控制器是由系统提供的，无法进行自定义
     */
    private fun mediaControllerVideoView() {
        if (video_view_container != null) {
            video_view_container.removeAllViews()
        }
        video_view = VideoView(applicationContext)
        video_view_container.addView(video_view)
        val path = "https://video-qn.51miz.com/preview/video/00/00/12/30/V-123077-95B77BE6.mp4"
        video_view!!.setVideoPath(path)
        val mediaController = MediaController(this)
        video_view!!.setMediaController(mediaController)
        video_view!!.start()
        video_view!!.requestFocus()
    }


    override fun onDestroy() {
        super.onDestroy()
        video_view!!.stopPlayback()
        video_view_container.removeAllViews()
        video_view = null
    }
}
