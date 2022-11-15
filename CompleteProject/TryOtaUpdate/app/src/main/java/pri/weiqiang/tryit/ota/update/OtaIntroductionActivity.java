package pri.weiqiang.tryit.ota.update;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtaIntroductionActivity extends Activity implements View.OnClickListener {
    private Button mcancle;
    private Button mconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota_introduction);
        mcancle = (Button) findViewById(R.id.cancle);
        mcancle.setOnClickListener(this);
        mconfirm = (Button) findViewById(R.id.confirm);
        mconfirm.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                finish();
                break;
            case R.id.confirm:
                Intent intent = new Intent();
                intent.setClass(this, OtaProcessActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}