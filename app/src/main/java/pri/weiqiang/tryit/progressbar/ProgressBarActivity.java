package pri.weiqiang.tryit.progressbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class ProgressBarActivity extends BaseActivity {

    private Button button;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBarAsyncTask task = new ProgressBarAsyncTask(textView, progressBar);
                task.execute(1000);
            }
        });
    }
}
