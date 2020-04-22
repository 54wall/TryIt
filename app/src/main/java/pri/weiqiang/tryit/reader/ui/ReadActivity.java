package pri.weiqiang.tryit.reader.ui;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.List;

import pri.weiqiang.tryit.R;
import pri.weiqiang.tryit.base.BaseActivity;
import pri.weiqiang.tryit.reader.bean.CollBookBean;
import pri.weiqiang.tryit.reader.utils.Constant;
import pri.weiqiang.tryit.reader.utils.MD5Utils;
import pri.weiqiang.tryit.reader.utils.StringUtils;
import pri.weiqiang.tryit.reader.widget.page.PageLoader;
import pri.weiqiang.tryit.reader.widget.page.PageView;
import pri.weiqiang.tryit.reader.widget.page.TxtChapter;

public class ReadActivity extends BaseActivity {
    private String TAG = ReadActivity.class.getSimpleName();
    private PageView mPvPage;
    private PageLoader mPageLoader;
    private CollBookBean mCollBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mPvPage = findViewById(R.id.read_pv_page);
//        mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
//        /storage/emulated/0/aihub/huanye.txt

        File file = new File("/storage/emulated/0/aihub/huanye.txt");
        mCollBook = new CollBookBean();
        mCollBook.set_id(MD5Utils.strToMd5By16(file.getAbsolutePath()));
        mCollBook.setTitle(file.getName().replace(".txt", ""));
        mCollBook.setAuthor("");
        mCollBook.setShortIntro("无");
        mCollBook.setCover(file.getAbsolutePath());
        mCollBook.setLocal(true);
        mCollBook.setLastChapter("开始阅读");
        mCollBook.setUpdated(StringUtils.dateConvert(file.lastModified(), Constant.FORMAT_BOOK_DATE));
        mCollBook.setLastRead(StringUtils.
                dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {


                    @Override
                    public void onChapterChange(int pos) {
                        Log.e(TAG, "mPageLoader OnPageChangeListener onChapterChange");
                    }

                    @Override
                    public void requestChapters(List<TxtChapter> requestChapters) {
                        Log.e(TAG, "mPageLoader OnPageChangeListener requestChapters");
                    }

                    @Override
                    public void onCategoryFinish(List<TxtChapter> chapters) {
                        Log.e(TAG, "mPageLoader OnPageChangeListener onCategoryFinish");
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        Log.e(TAG, "mPageLoader OnPageChangeListener onPageCountChange");
                    }

                    @Override
                    public void onPageChange(int pos) {
                        Log.e(TAG, "mPageLoader OnPageChangeListener onPageChange");
                    }
                }
        );
        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
//                return !hideReadMenu();
                return false;
            }

            @Override
            public void center() {
//                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        processLogic();


    }

    protected void processLogic() {
//        mPageLoader.refreshChapterList();
        // 设置 CollBook
//        mPageLoader.getCollBook().setBookChapters(bookChapterBeen);
        Log.e(TAG, "mPageLoader 设置 CollBook");
        // 刷新章节列表
        mPageLoader.refreshChapterList();
    }
}
