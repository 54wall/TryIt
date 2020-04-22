package pri.weiqiang.tryit.print.one;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import pri.weiqiang.tryit.R;


/**
 * Android图片打印
 *
 * @author ldm
 * @description
 * @date 2016-4-27 5:42:51
 */
public class PrintBitmapActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_bitmap);
        mImageView = findViewById(R.id.image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.print_custom_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_print) {
            print();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void print() {
        // // bitmap打印工具类。
        PrintHelper printHelper = new PrintHelper(this);

        /**
         * PrintHelper通过setScaleMode()方法设置模式，现在有两种模式�?
         * SCALE_MODE_FIT：这个打印完整的图片，这样打印纸的边缘可能有空白
         * SCALE_MODE_FILL：这个填满所有的打印纸，因此图片的边缘可能打印不出来
         */
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        // 获取ImageView这个用于显示图片的控件里的图片,不能是矢量图
        Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        // 打印图片
        printHelper.printBitmap("Print Bitmap", bitmap);
    }
}
