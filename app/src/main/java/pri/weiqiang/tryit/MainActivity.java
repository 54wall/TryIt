package pri.weiqiang.tryit;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pri.weiqiang.tryit.adb.AdbActivity;
import pri.weiqiang.tryit.aop.AopActivity;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.bigtext.BigTxtReaderActivity;
import pri.weiqiang.tryit.broadcast.BroadcastActivity;
import pri.weiqiang.tryit.camera.Camera1Activity;
import pri.weiqiang.tryit.camerahub.TextureViewSysCameraActivity;
import pri.weiqiang.tryit.camerax.CameraXActivity;
import pri.weiqiang.tryit.clock.ClockActivity;
import pri.weiqiang.tryit.component.ComponentActivity;
import pri.weiqiang.tryit.countdown.CountDownActivity;
import pri.weiqiang.tryit.customview.CustomViewActivity;
import pri.weiqiang.tryit.dagger2.Dagger2Activity;
import pri.weiqiang.tryit.eventbus.EventFirstActivity;
import pri.weiqiang.tryit.fronts.FrontActivity;
import pri.weiqiang.tryit.glide.GlideActivity;
import pri.weiqiang.tryit.handler.HandlerDemoActivity;
import pri.weiqiang.tryit.install.InstallActivity;
import pri.weiqiang.tryit.instance.SaveInstanceActivity;
import pri.weiqiang.tryit.jni.JNIActivity;
import pri.weiqiang.tryit.lifecycle.AActivity;
import pri.weiqiang.tryit.longclick.LongClickActivity;
import pri.weiqiang.tryit.looper.LooperActivity;
import pri.weiqiang.tryit.main.ModelMainItem;
import pri.weiqiang.tryit.main.OnItemClickLitener;
import pri.weiqiang.tryit.main.StaggeredGridAdapter;
import pri.weiqiang.tryit.permission.PermissionActivity;
import pri.weiqiang.tryit.print.PrintActivity;
import pri.weiqiang.tryit.progressbar.ProgressBarActivity;
import pri.weiqiang.tryit.reader.ui.ReadActivity;
import pri.weiqiang.tryit.scoket.chat.ui.ScoketChatActivity;
import pri.weiqiang.tryit.screensaver.ScreenSaveActivity;
import pri.weiqiang.tryit.scrollview.ScrollMainActivity;
import pri.weiqiang.tryit.seekbar.SeekRecycleviewActivity;
import pri.weiqiang.tryit.seekbar.SeekScrollActivity;
import pri.weiqiang.tryit.shell.ShellActivity;
import pri.weiqiang.tryit.shutdown.ShutDownActivity;
import pri.weiqiang.tryit.sidebar.SideBarActivity;
import pri.weiqiang.tryit.thread.KillThreadActivity;
import pri.weiqiang.tryit.webview.WebViewCorrectActivity;
import pri.weiqiang.tryit.webview.WebviewActivity;

public class MainActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    StaggeredGridAdapter staggeredGridAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    private String path = Environment.getExternalStorageDirectory() + File.separator + "aihub" + File.separator;
    private String name = "huanye.txt";
    private List<ModelMainItem> mData;
    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

        /**
         * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            //设置侧滑方向为从左到右和从右到左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            //将方向参数设置进去
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * @param recyclerView recyclerView
         * @param viewHolder   拖动的ViewHolder
         * @param target       目标位置的ViewHolder
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mData, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mData, i, i - 1);
                }
            }
            staggeredGridAdapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            staggeredGridAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();

        mData.add(new ModelMainItem("屏幕保护", ScreenSaveActivity.class));
        mData.add(new ModelMainItem("时钟 实例", ClockActivity.class));
        mData.add(new ModelMainItem("倒计时付款 实例", CountDownActivity.class));
        mData.add(new ModelMainItem("Camera1Activity", Camera1Activity.class));
        mData.add(new ModelMainItem("TextureViewSysCamera", TextureViewSysCameraActivity.class));
        mData.add(new ModelMainItem("CameraX简单实用（Java实现）", CameraXActivity.class));
        mData.add(new ModelMainItem("通过AOP实现Log打印", AopActivity.class));
        mData.add(new ModelMainItem("权限工具类", PermissionActivity.class));
        mData.add(new ModelMainItem("RecycleView添加SeekBar", SeekRecycleviewActivity.class));
        mData.add(new ModelMainItem("WebView添加SeekBar快速滑动侧边栏", SeekScrollActivity.class));
        mData.add(new ModelMainItem("正确读取WebView加载完全部内容高度", WebViewCorrectActivity.class));
        mData.add(new ModelMainItem("安卓关机重启", ShutDownActivity.class));
        mData.add(new ModelMainItem("Scoket实现局域网聊天，两台同时为服务器", ScoketChatActivity.class));
        mData.add(new ModelMainItem("全局字体替换(Activity主题限制为android:Theme.Light)", FrontActivity.class));
        mData.add(new ModelMainItem("电子书Reader模块提取（未完成）", ReadActivity.class));
        mData.add(new ModelMainItem("打印服务", PrintActivity.class));
        mData.add(new ModelMainItem("WebView显示SD卡上巨型文本", WebviewActivity.class));
        mData.add(new ModelMainItem("静默与普通安装", InstallActivity.class));
        mData.add(new ModelMainItem("Shell实现监听是否允许wifi调试ADB", ShellActivity.class));
        mData.add(new ModelMainItem("TextView显示大文本（未完成）", BigTxtReaderActivity.class));
        mData.add(new ModelMainItem("Adb命令", AdbActivity.class));
        mData.add(new ModelMainItem("发出静默安装指令", BroadcastActivity.class));
        mData.add(new ModelMainItem("通讯录侧边栏及文字头像", SideBarActivity.class));
        mData.add(new ModelMainItem("SaveInstanceActivity", SaveInstanceActivity.class));
        mData.add(new ModelMainItem("进度条演示", ProgressBarActivity.class));
        mData.add(new ModelMainItem("自定义View", CustomViewActivity.class));
        mData.add(new ModelMainItem("Dagger2试用", Dagger2Activity.class));
        mData.add(new ModelMainItem("JNI", JNIActivity.class));
        mData.add(new ModelMainItem("Scroll嵌套", ScrollMainActivity.class));
        mData.add(new ModelMainItem("Looper与Handler", LooperActivity.class));
        mData.add(new ModelMainItem("试用Handler实现长按", LongClickActivity.class));
        mData.add(new ModelMainItem("Glide试用", GlideActivity.class));
        mData.add(new ModelMainItem("EventBus试用", EventFirstActivity.class));
        mData.add(new ModelMainItem("Handler内存泄露", HandlerDemoActivity.class));
        mData.add(new ModelMainItem("Activity生命周期", AActivity.class));
        mData.add(new ModelMainItem("Kill线程", KillThreadActivity.class));
        mData.add(new ModelMainItem("调用静默安装", ComponentActivity.class));

    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.rv_activity);
        staggeredGridAdapter = new StaggeredGridAdapter(MainActivity.this, mData);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(staggeredGridAdapter);
        staggeredGridAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, staggeredGridAdapter.getItemLessonId(position));
                startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, final int position) {

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}
