package com.chenchen.collections.http;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 带上传进度的RequestBody
 */
public class ProgressRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private OnUploadListener mProgressListener;
    private BufferedSink bufferedSink;


    public ProgressRequestBody(File file , OnUploadListener progressListener) {
        this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file) ;
        this.mProgressListener = progressListener ;
    }

    public ProgressRequestBody(RequestBody requestBody, OnUploadListener progressListener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = progressListener;
    }

    //返回了requestBody的类型，想什么form-data/MP3/MP4/png等等等格式
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                    mProgressListener.onStart(contentLength);
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调上传接口
                mProgressListener.onProgress(bytesWritten);
                if(bytesWritten == contentLength){
                    mProgressListener.onComplete();
                }
            }
        };
    }

    public interface OnUploadListener{
        /**
         * 上传开始
         * @param size 文件大小，单位：byte
         */
        void onStart(long size);

        /**
         * 上传进度
         * @param currentLength 上传的大小，单位：byte
         */
        void onProgress(long currentLength);

        /**
         * 文件读入完成
         */
        void onComplete();
    }
}
