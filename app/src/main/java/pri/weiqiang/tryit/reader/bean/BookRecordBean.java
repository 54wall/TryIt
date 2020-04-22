package pri.weiqiang.tryit.reader.bean;

/**
 * Created by newbiechen on 17-5-20.
 */

public class BookRecordBean {
    //所属的书的id

    private String bookId;
    //阅读到了第几章
    private int chapter;
    //当前的页码
    private int pagePos;


    public BookRecordBean(String bookId, int chapter, int pagePos) {
        this.bookId = bookId;
        this.chapter = chapter;
        this.pagePos = pagePos;
    }

    public BookRecordBean() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPagePos() {
        return pagePos;
    }

    public void setPagePos(int pagePos) {
        this.pagePos = pagePos;
    }
}
