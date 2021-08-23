package com.steven.androidmediaplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

import java.util.ArrayList
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource


/**
 * 框架ExoPlayer制作简单的媒体播放器
 */
class ExoPlayerAudioActivity : AppCompatActivity(), Player.EventListener {

    private var mList: MutableList<String>? = null
    private var mExoPlayer: ExoPlayer? = null
    private var mPlayWhenReady: Boolean = false
    private var mPlayBackPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMusic()
        initializePlayer()
    }

    /**
     * 加载音频文件
     */
    private fun initMusic() {
        mList = ArrayList()
        mList!!.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/2e76a133b898a2f6c8fb62e963b87ce4_tongkuercanlandeyisheng.mp3")
        mList!!.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/e845a564e795c628c6bdd652ddbc24e3_fulidadeqingshaonianshidai.mp3")
        mList!!.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/e9b9abdcc3855586ec2c4651293c11e9_abuyuwulei.mp3")
        mList!!.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/ba2eaf007614fdf81c8d9e895ba88ff2_abuchuangzuodekaishi.mp3")
    }

    /**
     * 初始化媒体播放器
     */
    private fun initializePlayer() {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultRenderersFactory(this), DefaultTrackSelector(), DefaultLoadControl())
            mExoPlayer!!.addListener(this)
        }
        // 创建一个音频文件
        val uri = Uri.parse(mList!![mPlayBackPosition])
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri)
        mExoPlayer!!.prepare(mediaSource)
        mExoPlayer!!.playWhenReady = mPlayWhenReady
    }

    /**
     * 释放媒体资源
     */
    private fun releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer!!.stop()
            mExoPlayer!!.release()
            mExoPlayer = null
        }
    }

    /**
     * 上一首
     */
    fun lastMusic(view: View) {
        releasePlayer()
        mPlayWhenReady = true
        if (mPlayBackPosition == 0) {
            mPlayBackPosition = mList!!.size - 1
        } else {
            mPlayBackPosition--
        }
        initializePlayer()
    }


    /**
     * 播放
     */
    fun startMusic(view: View) {
        mPlayWhenReady = true
        mExoPlayer!!.playWhenReady = mPlayWhenReady
    }

    /**
     * 暂停
     */
    fun pauseMusic(view: View) {
        mPlayWhenReady = false
        mExoPlayer!!.playWhenReady = mPlayWhenReady
    }

    /**
     * 结束
     */
    fun stopMusic(view: View) {
        mPlayWhenReady = false
        mExoPlayer!!.stop()
        mExoPlayer!!.release()
    }


    /**
     * 下一首
     */
    fun nextMusic(view: View) {
        releasePlayer()
        mPlayWhenReady = true
        if (mPlayBackPosition == mList!!.size - 1) {
            mPlayBackPosition = 0
        } else {
            mPlayBackPosition++
        }
        initializePlayer()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        var stateString: String? = null
        if (playWhenReady && playbackState === Player.STATE_READY) {
            Log.d("zhoufan", "onPlayerStateChanged: actually playing media")
        }
        stateString = when (playbackState) {
            Player.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
            Player.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
            Player.STATE_READY -> {
                "ExoPlayer.STATE_READY     -"
            }
            Player.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
            else -> "UNKNOWN_STATE             -"
        }
        Log.d("zhoufan", "changed state to $stateString playWhenReady: $playWhenReady")
    }
}
