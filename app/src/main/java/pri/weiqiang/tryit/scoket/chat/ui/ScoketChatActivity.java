package pri.weiqiang.tryit.scoket.chat.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.scoket.chat.socket.Server;

public class ScoketChatActivity extends BaseActivity {

    public static int REQUEST_CODE_IMAGE = 0;
    private final String TAG = ScoketChatActivity.class.getSimpleName();
    public Server server;
    private ChatFragment mChatFragment = new ChatFragment();
    private ContactFragment mContactFragment = new ContactFragment();
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoket_chat);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ll_container, mChatFragment)
                .add(R.id.ll_container, mContactFragment)
                .hide(mChatFragment).show(mContactFragment).commit();
        mCurrentFragment = mContactFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mCurrentFragment instanceof ChatFragment) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mChatFragment).show(mContactFragment).commit();
                    mCurrentFragment = mContactFragment;
                    return false;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "requestCode:" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                    client.sendFile(path);
                    mChatFragment.sendFile(path);
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

    }

    public void showChat(String host) {
        getSupportFragmentManager().beginTransaction()
                .hide(mContactFragment).show(mChatFragment).commit();
        mCurrentFragment = mChatFragment;
        setTitle(host);
        ChatFragment.host = host;
    }

    public void startServer(int pite) {
        try {
            server = new Server(pite);
            /*socket服务端开始监听*/
            server.startListen();

        } catch (Exception e) {
            Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (mCurrentFragment instanceof ChatFragment) {
                getSupportFragmentManager().beginTransaction()
                        .hide(mChatFragment).show(mContactFragment).commit();
                mCurrentFragment = mContactFragment;
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
