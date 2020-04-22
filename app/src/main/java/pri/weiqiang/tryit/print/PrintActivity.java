package pri.weiqiang.tryit.print;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.print.one.PrintOneActivity;
import pri.weiqiang.tryit.print.two.PrintTwoActivity;

public class PrintActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        Button mBtnPrintOne = findViewById(R.id.btn_print_one);
        mBtnPrintOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PrintActivity.this, PrintOneActivity.class);
                startActivity(i);
            }
        });
        Button mBtnPrintTwo = findViewById(R.id.btn_print_two);
        mBtnPrintTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PrintActivity.this, PrintTwoActivity.class);
                startActivity(i);

            }
        });
    }
}
