package pri.weiqiang.tryit.videoview;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private FrameLayout mFlVideo;
    private VideoView adVideo;
    private String path = Environment.getExternalStorageDirectory().getPath()+"/aihub/ad.MP4";
    private Button mBtnPlay;
    private Button mBtnPause;
    private int mPlayingPos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlVideo = findViewById(R.id.fl_video);
        adVideo = findViewById(R.id.vv_ad);
        File file = new File(path);
        if(file.exists()){
            adVideo.setVideoPath(path);
            adVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVolume(0f, 0f);
                    mp.setLooping(true);
                }
            });

            //让VideoView获取焦点
            adVideo.requestFocus();
        }

        mBtnPlay = findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
        mBtnPause = findViewById(R.id.btn_pause);
        mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVideo();
            }
        });

    }

    public void playVideo() {
        try {
            if (adVideo != null) {
                adVideo.start();
                adVideo.seekTo(mPlayingPos);
            }
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage());
            adVideo = null;
        }
    }

    public void pauseVideo() {
        if (adVideo != null) {
            //adVideo在后台时第二次以后getCurrentPosition全部是0,所以仅对非0值进行保存
            int mPosTemp = adVideo.getCurrentPosition();
            if (mPosTemp != 0) {
                mPlayingPos = mPosTemp;
            }
            adVideo.pause();
        }
    }
}