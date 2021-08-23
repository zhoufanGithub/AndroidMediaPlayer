package com.steven.androidmediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_media_player_controller_surface_view.*
import java.lang.Exception

/**
 * 视频播放
 * 实现方式三：MediaPlayer + SurfaceView + MediaController
 */
class MediaPlayerControllerSurfaceViewActivity : AppCompatActivity(), SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener, MediaController.MediaPlayerControl {

    private var mMediaPlayer: MediaPlayer? = null
    private var mMediaController: MediaController? = null
    private var bufferPercentage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_controller_surface_view)
        init()
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        mMediaController = MediaController(this)
        mMediaController?.setAnchorView(root_all)
        surfaceView.setZOrderOnTop(false)
        surfaceView.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        surfaceView.holder.addCallback(this)
    }

    override fun onResume() {
        super.onResume()
        try {
            val path = "https://video-qn.51miz.com/preview/video/00/00/12/37/V-123783-D16F674D.mp4"
            mMediaPlayer?.setDataSource(path)
            mMediaPlayer?.setOnBufferingUpdateListener(this)
            mMediaController?.setMediaPlayer(this)
            mMediaController?.isEnabled = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer!!.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mMediaController?.show()
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mMediaPlayer?.setDisplay(holder)
        mMediaPlayer?.prepareAsync()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        bufferPercentage = percent
    }

    override fun start() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.start()
        }
    }

    override fun pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.pause()
        }
    }

    override fun getDuration() = mMediaPlayer!!.duration


    override fun getCurrentPosition() = mMediaPlayer!!.currentPosition


    override fun seekTo(pos: Int) {
        mMediaPlayer!!.seekTo(pos)
    }

    override fun isPlaying(): Boolean {
        if (mMediaPlayer!!.isPlaying) {
            return true
        }
        return false
    }

    override fun getBufferPercentage(): Int {
        return bufferPercentage
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getAudioSessionId(): Int {
        return 0
    }
}
