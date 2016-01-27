package com.example.wu_.jishiqi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

public class MYhandler extends Activity {
        Button button;
        MyHandler myHandler;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.handlertest);

            button = (Button) findViewById(R.id.b5);
            myHandler = new MyHandler();
            MyThread m = new MyThread();
            new Thread(m).start();
        }

         // 接受消息，处理消息 ，此Handler会与当前主线程一块
        class MyHandler extends Handler {
            public MyHandler() {
            }

            public MyHandler(Looper L) {
                super(L);
            }

            // 子类必须重写此方法，接受数据
            @Override
            public void handleMessage(Message msg) {

                Log.d("MyHandler", "handleMessage。。。。。。");
                super.handleMessage(msg);
                // 此处可以更新UI
                Bundle b = msg.getData();
                String color = b.getString("color");
                MYhandler.this.button.append(color);

            }
        }

        class MyThread implements Runnable {
            public void run() {

                try {Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("thread。。。。。。。", "mThread。。。。。。。。");
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("color", "我的");
                msg.setData(b);

                MYhandler.this.myHandler.sendMessage(msg); // 向Handler发送消息，更新UI

            }
        }
    }

