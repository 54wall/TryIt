package pri.weiqiang.tryit.dagger2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.dagger2.example1.DaggerE1Activity;
import pri.weiqiang.tryit.dagger2.example2.DaggerE2Activity;
import pri.weiqiang.tryit.dagger2.example3.DaggerE3Activity;
import pri.weiqiang.tryit.dagger2.example4.DaggerE4Activity;
import pri.weiqiang.tryit.dagger2.example5.DaggerE5Activity;
import pri.weiqiang.tryit.dagger2.example6.DaggerE6Activity;

/*https://www.jianshu.com/p/92f793e76654
 * Dagger2入门
 * 高永峰*/
public class Dagger2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        Button mBtnDraggerE1 = findViewById(R.id.btn_dagger_e1);
        mBtnDraggerE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE1Activity.class);
                startActivity(i);
            }
        });
        Button mBtnDraggerE2 = findViewById(R.id.btn_dagger_e2);
        mBtnDraggerE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE2Activity.class);
                startActivity(i);
            }
        });
        Button mBtnDraggerE3 = findViewById(R.id.btn_dagger_e3);
        mBtnDraggerE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE3Activity.class);
                startActivity(i);
            }
        });
        Button mBtnDragger4 = findViewById(R.id.btn_dagger_e4);
        mBtnDragger4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE4Activity.class);
                startActivity(i);
            }
        });
        Button mBtnDragger5 = findViewById(R.id.btn_dagger_e5);
        mBtnDragger5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE5Activity.class);
                startActivity(i);
            }
        });
        Button mBtnDragger6 = findViewById(R.id.btn_dagger_e6);
        mBtnDragger6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(Dagger2Activity.this, DaggerE6Activity.class);
                startActivity(i);
            }
        });
    }
}
