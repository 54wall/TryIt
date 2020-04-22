package pri.weiqiang.tryit.print.one;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.pdf.PdfDocument.Page;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pri.weiqiang.tryit.R;

/**
 * Android中通用布局页面打印
 *
 * @author ldm
 * @description：
 * @date 2016-4-28 上午9:07:06
 */
public class PrintLyaoutActivity extends ListActivity {

    private static final int UNIT_IN_INCH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new MotoGpStatAdapter(loadMotoGpStats(),
                getLayoutInflater()));
        print();
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
        // 打印服务,访问打印队列，并提供PrintDocumentAdapter类支持
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        // PrintDocumentAdapter – 提供自定义打印文档的基础类。
        printManager.print("MotoGP stats", new PrintDocumentAdapter() {
            private int mRenderPageWidth;
            private int mRenderPageHeight;
            // PrintAttributes可以让你指定一种颜色模式，媒体尺寸，边距还有分辨率
            private PrintAttributes mPrintAttributes;
            // PrintDocumentInfo对象，用于描述所打印的内容
            private PrintDocumentInfo mDocumentInfo;
            private Context mPrintContext;

            // onLayout方法在PrintAttribute改变的时候就会调用，目的就是为了创建PrintDocumentInfo对象，描述所打印的内容
            @Override
            public void onLayout(final PrintAttributes oldAttributes,
                                 final PrintAttributes newAttributes,
                                 final CancellationSignal cancellationSignal,
                                 final LayoutResultCallback callback, final Bundle metadata) {

                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }

                boolean layoutNeeded = false;

                final int density = Math.max(newAttributes.getResolution()
                        .getHorizontalDpi(), newAttributes.getResolution()
                        .getVerticalDpi());

                final int marginLeft = (int) (density
                        * (float) newAttributes.getMinMargins().getLeftMils() / UNIT_IN_INCH);
                final int marginRight = (int) (density
                        * (float) newAttributes.getMinMargins().getRightMils() / UNIT_IN_INCH);
                final int contentWidth = (int) (density
                        * (float) newAttributes.getMediaSize().getWidthMils() / UNIT_IN_INCH)
                        - marginLeft - marginRight;
                if (mRenderPageWidth != contentWidth) {
                    mRenderPageWidth = contentWidth;
                    layoutNeeded = true;
                }

                final int marginTop = (int) (density
                        * (float) newAttributes.getMinMargins().getTopMils() / UNIT_IN_INCH);
                final int marginBottom = (int) (density
                        * (float) newAttributes.getMinMargins().getBottomMils() / UNIT_IN_INCH);
                final int contentHeight = (int) (density
                        * (float) newAttributes.getMediaSize().getHeightMils() / UNIT_IN_INCH)
                        - marginTop - marginBottom;
                if (mRenderPageHeight != contentHeight) {
                    mRenderPageHeight = contentHeight;
                    layoutNeeded = true;
                }

                if (mPrintContext == null
                        || mPrintContext.getResources().getConfiguration().densityDpi != density) {
                    Configuration configuration = new Configuration();
                    configuration.densityDpi = density;
                    mPrintContext = createConfigurationContext(configuration);
                    mPrintContext.setTheme(android.R.style.Theme_Holo_Light);
                }

                if (!layoutNeeded) {
                    callback.onLayoutFinished(mDocumentInfo, false);
                    return;
                }

                final List<MotoGpStatItem> items = ((MotoGpStatAdapter) getListAdapter())
                        .cloneItems();
                /**
                 * 耗时异步任务
                 */
                new AsyncTask<Void, Void, PrintDocumentInfo>() {
                    @Override
                    protected void onPreExecute() {
                        cancellationSignal
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        cancel(true);
                                    }
                                });
                        mPrintAttributes = newAttributes;
                    }

                    // 后台操作
                    @Override
                    protected PrintDocumentInfo doInBackground(Void... params) {
                        try {
                            MotoGpStatAdapter adapter = new MotoGpStatAdapter(
                                    items,
                                    (LayoutInflater) mPrintContext
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE));

                            int currentPage = 0;
                            int pageContentHeight = 0;
                            int viewType = -1;
                            View view = null;
                            LinearLayout dummyParent = new LinearLayout(
                                    mPrintContext);
                            dummyParent.setOrientation(LinearLayout.VERTICAL);

                            final int itemCount = adapter.getCount();
                            for (int i = 0; i < itemCount; i++) {
                                if (isCancelled()) {
                                    return null;
                                }

                                final int nextViewType = adapter
                                        .getItemViewType(i);
                                if (viewType == nextViewType) {
                                    view = adapter
                                            .getView(i, view, dummyParent);
                                } else {
                                    view = adapter
                                            .getView(i, null, dummyParent);
                                }
                                viewType = nextViewType;

                                measureView(view);

                                pageContentHeight += view.getMeasuredHeight();
                                if (pageContentHeight > mRenderPageHeight) {
                                    pageContentHeight = view
                                            .getMeasuredHeight();
                                    currentPage++;
                                }
                            }

                            PrintDocumentInfo info = new PrintDocumentInfo.Builder(
                                    "MotoGP_stats.pdf")
                                    .setContentType(
                                            PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                                    .setPageCount(currentPage + 1).build();

                            callback.onLayoutFinished(info, true);
                            return info;
                        } catch (Exception e) {
                            callback.onLayoutFailed(null);
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    protected void onPostExecute(PrintDocumentInfo result) {
                        mDocumentInfo = result;
                    }

                    @Override
                    protected void onCancelled(PrintDocumentInfo result) {
                        callback.onLayoutCancelled();
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        (Void[]) null);
            }

            /**
             * 这个方法在需要使用文件描述符修改PDF文件的时候，就会调用，
             * 特别要注意的是这个方法（PrintDocumentAdapter的方法也一样）是在主线程调用的；
             * 在这里提出，因为此方法最好是在后台运行，因为考虑到我们会做一些文件IO操作。
             * 这个方法的主要目的就是往PDF里面写内容，把PDF内容写到文件里，然后调用对应的回调方法。
             */
            @Override
            public void onWrite(final PageRange[] pages,
                                final ParcelFileDescriptor destination,
                                final CancellationSignal cancellationSignal,
                                final WriteResultCallback callback) {

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    return;
                }

                final List<MotoGpStatItem> items = ((MotoGpStatAdapter) getListAdapter())
                        .cloneItems();

                new AsyncTask<Void, Void, Void>() {
                    private final SparseIntArray mWrittenPages = new SparseIntArray();
                    // PrintedPdfDocument:基于特定PrintAttributeshelper创建PDF。
                    private final PrintedPdfDocument mPdfDocument = new PrintedPdfDocument(
                            PrintLyaoutActivity.this, mPrintAttributes);

                    @Override
                    protected void onPreExecute() {
                        cancellationSignal
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        cancel(true);
                                    }
                                });
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        MotoGpStatAdapter adapter = new MotoGpStatAdapter(
                                items,
                                (LayoutInflater) mPrintContext
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE));

                        int currentPage = -1;
                        int pageContentHeight = 0;
                        int viewType = -1;
                        View view = null;
                        Page page = null;
                        LinearLayout dummyParent = new LinearLayout(
                                mPrintContext);
                        dummyParent.setOrientation(LinearLayout.VERTICAL);

                        final float scale = Math.min((float) mPdfDocument
                                .getPageContentRect().width()
                                / mRenderPageWidth, (float) mPdfDocument
                                .getPageContentRect().height()
                                / mRenderPageHeight);

                        final int itemCount = adapter.getCount();
                        for (int i = 0; i < itemCount; i++) {
                            if (isCancelled()) {
                                return null;
                            }

                            final int nextViewType = adapter.getItemViewType(i);
                            if (viewType == nextViewType) {
                                view = adapter.getView(i, view, dummyParent);
                            } else {
                                view = adapter.getView(i, null, dummyParent);
                            }
                            viewType = nextViewType;
                            measureView(view);

                            pageContentHeight += view.getMeasuredHeight();
                            if (currentPage < 0
                                    || pageContentHeight > mRenderPageHeight) {
                                pageContentHeight = view.getMeasuredHeight();
                                currentPage++;
                                if (page != null) {
                                    mPdfDocument.finishPage(page);
                                }
                                if (containsPage(pages, currentPage)) {
                                    page = mPdfDocument.startPage(currentPage);
                                    page.getCanvas().scale(scale, scale);
                                    mWrittenPages.append(mWrittenPages.size(),
                                            currentPage);
                                } else {
                                    page = null;
                                }
                            }

                            if (page != null) {
                                view.layout(0, 0, view.getMeasuredWidth(),
                                        view.getMeasuredHeight());
                                view.draw(page.getCanvas());
                                page.getCanvas().translate(0, view.getHeight());
                            }
                        }

                        if (page != null) {
                            mPdfDocument.finishPage(page);
                        }

                        try {
                            mPdfDocument.writeTo(new FileOutputStream(
                                    destination.getFileDescriptor()));
                            PageRange[] pageRanges = computeWrittenPageRanges(mWrittenPages);
                            callback.onWriteFinished(pageRanges);
                        } catch (IOException ioe) {
                            callback.onWriteFailed(null);
                        } finally {
                            mPdfDocument.close();
                        }

                        return null;
                    }

                    @Override
                    protected void onCancelled(Void result) {
                        callback.onWriteCancelled();
                        mPdfDocument.close();
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        (Void[]) null);
            }

            private void measureView(View view) {
                final int widthMeasureSpec = ViewGroup.getChildMeasureSpec(
                        MeasureSpec.makeMeasureSpec(mRenderPageWidth,
                                MeasureSpec.EXACTLY), 0,
                        view.getLayoutParams().width);
                final int heightMeasureSpec = ViewGroup.getChildMeasureSpec(
                        MeasureSpec.makeMeasureSpec(mRenderPageHeight,
                                MeasureSpec.EXACTLY), 0,
                        view.getLayoutParams().height);
                view.measure(widthMeasureSpec, heightMeasureSpec);
            }

            private PageRange[] computeWrittenPageRanges(
                    SparseIntArray writtenPages) {
                List<PageRange> pageRanges = new ArrayList<PageRange>();

                int start = -1;
                int end = -1;
                final int writtenPageCount = writtenPages.size();
                for (int i = 0; i < writtenPageCount; i++) {
                    if (start < 0) {
                        start = writtenPages.valueAt(i);
                    }
                    int oldEnd = end = start;
                    while (i < writtenPageCount && (end - oldEnd) <= 1) {
                        oldEnd = end;
                        end = writtenPages.valueAt(i);
                        i++;
                    }
                    PageRange pageRange = new PageRange(start, end);
                    pageRanges.add(pageRange);
                    start = end = -1;
                }

                PageRange[] pageRangesArray = new PageRange[pageRanges.size()];
                pageRanges.toArray(pageRangesArray);
                return pageRangesArray;
            }

            private boolean containsPage(PageRange[] pageRanges, int page) {
                final int pageRangeCount = pageRanges.length;
                for (int i = 0; i < pageRangeCount; i++) {
                    if (pageRanges[i].getStart() <= page
                            && pageRanges[i].getEnd() >= page) {
                        return true;
                    }
                }
                return false;
            }
        }, null);
    }

    private List<MotoGpStatItem> loadMotoGpStats() {
        String[] years = getResources().getStringArray(R.array.motogp_years);
        String[] champions = getResources().getStringArray(
                R.array.motogp_champions);
        String[] constructors = getResources().getStringArray(
                R.array.motogp_constructors);

        List<MotoGpStatItem> items = new ArrayList<MotoGpStatItem>();

        final int itemCount = years.length;
        for (int i = 0; i < itemCount; i++) {
            MotoGpStatItem item = new MotoGpStatItem();
            item.year = years[i];
            item.champion = champions[i];
            item.constructor = constructors[i];
            items.add(item);
        }

        return items;
    }

    private static final class MotoGpStatItem {
        String year;
        String champion;
        String constructor;
    }

    private class MotoGpStatAdapter extends BaseAdapter {
        private final List<MotoGpStatItem> mItems;
        private final LayoutInflater mInflater;

        public MotoGpStatAdapter(List<MotoGpStatItem> items,
                                 LayoutInflater inflater) {
            mItems = items;
            mInflater = inflater;
        }

        public List<MotoGpStatItem> cloneItems() {
            return new ArrayList<MotoGpStatItem>(mItems);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_print_layout,
                        parent, false);
            }

            MotoGpStatItem item = (MotoGpStatItem) getItem(position);

            TextView yearView = convertView.findViewById(R.id.year);
            yearView.setText(item.year);

            TextView championView = convertView
                    .findViewById(R.id.champion);
            championView.setText(item.champion);

            TextView constructorView = convertView
                    .findViewById(R.id.constructor);
            constructorView.setText(item.constructor);

            return convertView;
        }
    }
}
