package pri.weiqiang.tryit.scrollview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;

public class SimpleListViewActivity extends BaseActivity {
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SimpleListViewActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(adapter);
    }
}
