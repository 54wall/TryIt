package pri.weiqiang.tryit.handler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class HandlerDemoActivity extends BaseActivity {
    public static AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        Button mBtnWeakreferenceHandler =
                findViewById(R.id.btn_weakreference_handler);
        Button mBtnWeakReference = findViewById(R.id.btn_weakreference);
        Button mBtnHandler = findViewById(R.id.btn_handler);
        mBtnWeakreferenceHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(HandlerDemoActivity.this, WeakReferenceHandlerActivity.class);
                startActivity(i);
            }
        });
        mBtnWeakReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(HandlerDemoActivity.this, WeakReferenceActivity.class);
                startActivity(i);
            }
        });
        mBtnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(HandlerDemoActivity.this, HandlerActivity.class);
                startActivity(i);
            }
        });
        Button mBtnOne = findViewById(R.id.btn_one);
        mBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent().setClass(HandlerDemoActivity.this, OneActivity.class);
                startActivity(i);
            }
        });
        Button mBtnTwo = findViewById(R.id.btn_two);
        mBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent().setClass(HandlerDemoActivity.this, TwoActivity.class);
                startActivity(i);
            }
        });
    }
}
