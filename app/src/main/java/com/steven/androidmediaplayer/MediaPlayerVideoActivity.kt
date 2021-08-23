package com.steven.androidmediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.ArrayList

/**
 * 原生MediaPlayer制作简单的媒体播放器
 */
class MediaPlayerVideoActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var mList: MutableList<String> = mutableListOf()
    private var mCurrentPlayPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMusic()
        initMediaPlayer()
    }

    /**
     * 加载音频文件
     */
    private fun initMusic() {
        mList.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/2e76a133b898a2f6c8fb62e963b87ce4_tongkuercanlandeyisheng.mp3")
        mList.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/e845a564e795c628c6bdd652ddbc24e3_fulidadeqingshaonianshidai.mp3")
        mList.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/e9b9abdcc3855586ec2c4651293c11e9_abuyuwulei.mp3")
        mList.add("https://xysx-voice.oss-cn-shanghai.aliyuncs.com/audio/ba2eaf007614fdf81c8d9e895ba88ff2_abuchuangzuodekaishi.mp3")
    }

    /**
     * 初始化MediaPlayer
     */
    private fun initMediaPlayer() {
        // 1.实例化MediaPlayer
        // 也可以使用create的方式，如：
        // MediaPlayer mp = MediaPlayer.create(this, R.raw.test);//这时就不用调用setDataSource了
        mediaPlayer = MediaPlayer()
        // 2.设置播放的音频文件
        // 设置的音频文件的来源有3种
        // a. 用户在应用中事先自带的resource资源
        // 例如：MediaPlayer.create(this, R.raw.test);
        // b. 存储在SD卡或其他文件路径下的媒体文件,传入的是本地音频文件的绝对路径
        // 例如：mp.setDataSource("/sdcard/test.mp3");
        // c. 网络上的媒体文件，传入的是网络地址
        // 例如：mp.setDataSource("http://www.citynorth.cn/music/confucius.mp3");
        mediaPlayer?.setDataSource(mList[mCurrentPlayPosition])
        // MediaPlayer的setDataSource一共四个方法：
        // setDataSource (String path)
        // setDataSource (FileDescriptor fd)
        // setDataSource (Context context, Uri uri)
        // setDataSource (FileDescriptor fd, long offset, long length)
        // 其中使用FileDescriptor时，需要将文件放到与res文件夹平级的assets文件夹里，然后使用：
        // AssetFileDescriptor fileDescriptor = getAssets().openFd("rain.mp3");
        // mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(), fileDescriptor.getLength());来设置datasource
        // 3.装载我们的音频文件
        mediaPlayer?.prepareAsync()
        // 4.播放我们的音频文件
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    /**
     * 上一首
     */
    fun lastMusic(view: View) {
        mediaPlayer?.reset()
        if (mCurrentPlayPosition == 0) {
            mCurrentPlayPosition = mList.size - 1
        } else {
            mCurrentPlayPosition--
        }
        mediaPlayer?.setDataSource(mList[mCurrentPlayPosition])
        mediaPlayer?.prepareAsync()
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    /**
     * 播放
     */
    fun startMusic(view: View) {
        mediaPlayer?.start()
    }

    /**
     * 暂停
     */
    fun pauseMusic(view: View) {
        mediaPlayer?.pause()
    }

    /**
     * 结束
     */
    fun stopMusic(view: View) {
        mediaPlayer?.stop()
    }

    /**
     * 下一首
     */
    fun nextMusic(view: View) {
        try {
            mediaPlayer?.reset()
            if (mCurrentPlayPosition == mList.size - 1) {
                mCurrentPlayPosition = 0
            } else {
                mCurrentPlayPosition++
            }
            mediaPlayer?.setDataSource(mList[mCurrentPlayPosition])
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
