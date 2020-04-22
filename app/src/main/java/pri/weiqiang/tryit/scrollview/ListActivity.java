package pri.weiqiang.tryit.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import pri.weiqiang.tryit.R;

public class ListActivity extends Activity {

    private MyListView listView;
    private MyScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        scrollView = findViewById(R.id.sv);
        listView = findViewById(R.id.lv);
        listView.setAdapter(new MyListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        scrollView.smoothScrollTo(0, 0);
    }

}
