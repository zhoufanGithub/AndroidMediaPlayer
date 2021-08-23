package com.steven.androidmediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.SurfaceHolder
import android.widget.MediaController
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_media_video.*

/**
 * 视频播放
 * 实现方式一：MediaController + VideoView
 * 实现方式二：MediaPlayer + SurfaceView
 * 实现方式三：MediaPlayer + SurfaceView + MediaController
 */
class MediaVideoActivity : AppCompatActivity(), SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnBufferingUpdateListener {

    private var mMediaPlayer: MediaPlayer? = null

    companion object {
        const val UPDATE_TIME = 0x0001
    }

    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                UPDATE_TIME -> {
                    updateTime()
                    sendEmptyMessageDelayed(UPDATE_TIME, 500)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_video)

    }

    override fun onResume() {
        super.onResume()
        mediaPlayerSurfaceView()
    }


    /**
     * MediaPlayer+SurfaceView+自定义控制器
     * 所有的界面控制器由自己定义
     */
    private fun mediaPlayerSurfaceView() {
        val path = "https://video-qn.51miz.com/preview/video/00/00/12/37/V-123783-D16F674D.mp4"
        // 设置SurfaceView
        surfaceView.setZOrderOnTop(false)
        surfaceView.holder.addCallback(this)
        // 设置MediaPlayer
        mMediaPlayer = MediaPlayer()
        mMediaPlayer?.setOnCompletionListener(this)
        mMediaPlayer?.setOnErrorListener(this)
        mMediaPlayer?.setOnInfoListener(this)
        mMediaPlayer?.setOnPreparedListener(this)
        mMediaPlayer?.setOnSeekCompleteListener(this)
        mMediaPlayer?.setOnVideoSizeChangedListener(this)
        mMediaPlayer?.setOnBufferingUpdateListener(this)
        try {
            //使用手机本地视频
            mMediaPlayer?.setDataSource(path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        seek_bar.setOnSeekBarChangeListener(this)
        media_play.setOnClickListener {
            play()
        }
    }

    /**
     * Surface被创建
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        mMediaPlayer?.setDisplay(holder)
        mMediaPlayer?.prepareAsync()
    }

    /**
     * Surface发生改变
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    /**
     * Surface销毁
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    /**
     * MediaPlayer装载完毕
     */
    override fun onPrepared(mp: MediaPlayer?) {
        media_book_start_time.text = TimeUtil.getTime(mp!!.currentPosition / 1000)
        media_book_total_time.text = TimeUtil.getTime(mp.duration / 1000)
        seek_bar.max = mp.duration
        seek_bar.progress = mp.currentPosition
    }

    /**
     * 播放结束
     */
    override fun onCompletion(mp: MediaPlayer?) {
        mHandler.removeMessages(UPDATE_TIME)
        seek_bar.progress = 0
        media_book_start_time.text = TimeUtil.getTime(0)
        media_song.setImageResource(R.drawable.media_pause_day)

    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }


    override fun onSeekComplete(mp: MediaPlayer?) {

    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {

    }

    private fun play() {
        if (mMediaPlayer == null) {
            return
        }
        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer!!.pause()
            mHandler.removeMessages(UPDATE_TIME)
            media_song.setImageResource(R.drawable.media_pause_day)
        } else {
            mMediaPlayer!!.start()
            mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500)
            media_song.setImageResource(R.drawable.media_play_day)
        }
    }

    /**
     * 更新播放时间
     */
    private fun updateTime() {
        media_book_start_time.text = TimeUtil.getTime(mMediaPlayer!!.currentPosition / 1000)
        seek_bar.progress = mMediaPlayer!!.currentPosition
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (mMediaPlayer != null && fromUser) {
            mMediaPlayer!!.seekTo(progress)
            media_book_start_time.text = TimeUtil.getTime(mMediaPlayer!!.currentPosition / 1000)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (!mMediaPlayer!!.isPlaying) {
            play()
        }
    }

    /**
     * 获取缓冲的信息
     */
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        Log.i("aaaaaaaaaaaaaaaaaaaaa", "当前缓冲$percent")
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeMessages(UPDATE_TIME)
        mMediaPlayer!!.stop()
        mMediaPlayer!!.release()
        mMediaPlayer = null
    }
}
