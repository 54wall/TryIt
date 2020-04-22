package pri.weiqiang.tryit.scoket.chat.bean;

import java.io.Serializable;

import pri.weiqiang.tryit.scoket.chat.config.Constants;

public class MsgBean implements Serializable {

    //文件名称
    public String fileName;
    //文件长度
    public long fileLength;
    //传输类型
    public int transmissionType;
    //传输内容
    public String content;
    //传输的长度
    public long transLength;
    //发送还是接受类型
    public int itemType = Constants.CHAT_SEND;
    //0 文本  1  图片
    public int showType;

    private MsgBean(Builder builder) {
        fileName = builder.fileName;
        fileLength = builder.fileLength;
        transmissionType = builder.transmissionType;
        content = builder.content;
        transLength = builder.transLength;
        itemType = builder.itemType;
        showType = builder.showType;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public int getTransmissionType() {
        return transmissionType;
    }

    public String getContent() {
        return content;
    }

    public long getTransLength() {
        return transLength;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getShowType() {
        return showType;
    }

    public static class Builder {
        //文件名称
        private String fileName;
        //文件长度
        private long fileLength;
        //传输类型
        private int transmissionType;
        //传输内容
        private String content;
        //传输的长度
        private long transLength;
        //发送还是接受类型
        private int itemType = Constants.CHAT_SEND;
        //0 文本  1  图片
        private int showType;

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder fileLength(long fileLength) {
            this.fileLength = fileLength;
            return this;
        }

        public Builder transmissionType(int transmissionType) {
            this.transmissionType = transmissionType;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder transLength(long transLength) {
            this.transLength = transLength;
            return this;
        }

        public Builder itemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder showType(int showType) {
            this.showType = showType;
            return this;
        }

        public MsgBean build() {
            return new MsgBean(this);
        }
    }
}
