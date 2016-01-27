package com.example.wu_.jishiqi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.net.URLConnection;

public class ImageTsk extends Activity {
    private ImageView imageView;
    private ProgressBar mprogressBar;
    private String URL="http://img.67.com/news/2011/11/15/7_3096f0fc-0a53-483a-91db-8414f29782de_3.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.myimagelayout);
        imageView= (ImageView) findViewById(R.id.myimage);
        mprogressBar= (ProgressBar) findViewById(R.id.progress);
        new MysyncTask1().execute(URL);//开启一个异步处理进程，设置传递进去的参数。
    }

    class MysyncTask1 extends AsyncTask<String,Void,Bitmap >{
        @Override
        protected void onPreExecute() {//初始化操作。
            super.onPreExecute();
            mprogressBar.setVisibility(View.VISIBLE);//显示进度条
        }

        @Override
        protected Bitmap doInBackground(String... params) {//真正的异步处理操作
            String url=params[0];//参数中的params是一个可变长度的数组，此处为获取传递进来参数：图片的网址
            Bitmap bitmap=null;
            //下面是访问网络的操作，耗时操作
            URLConnection urlConnection;
            InputStream is;
            try {
                urlConnection=new URL(url).openConnection();
                is=urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(is);
                bitmap= BitmapFactory.decodeStream(bufferedInputStream);//解析输入流，从而转化为一张BITMAP
                is.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;//将Bitmap返回。
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);//此处的BITmap就是doINbACK..方法返回结果bitmap，作用在主线程。
            imageView.setImageBitmap(bitmap);
            mprogressBar.setVisibility(View.GONE);
        }
    }

}
