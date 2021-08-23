package com.steven.androidmediaplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exo_player_video.*

/**
 * 使用ExoPlayer来播放视频
 */
class ExoPlayerVideoActivity : AppCompatActivity(), Player.EventListener {

    private var mSimpleExoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player_video)
        initExoPlayer()
    }

    private fun initExoPlayer() {
        // 创建一个播放器
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)
        // 将播放器附着在一个View上
        exo_play_show.player = mSimpleExoPlayer
        // 准备播放器资源
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, application.packageName))
        val videoSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("https://video-qn.51miz.com/preview/video/00/00/12/37/V-123783-D16F674D.mp4"))
        mSimpleExoPlayer!!.prepare(videoSource)
    }

    fun startExoPlayer(view: View) {
        mSimpleExoPlayer!!.playWhenReady = true
    }

    fun pauseExoPlayer(view: View) {
        mSimpleExoPlayer!!.playWhenReady = false
    }

    fun stopExoPlayer(view: View) {
        mSimpleExoPlayer!!.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleExoPlayer!!.stop()
        mSimpleExoPlayer!!.release()
        mSimpleExoPlayer = null
    }
}
