package com.example.myapplication.art_dev_chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    private final Messenger messenger = new Messenger(new MessengerHandler(Looper.getMainLooper()));
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public static class MessengerHandler extends Handler{
        public MessengerHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Log.e(TAG,"哈哈我接收到了数据");
                    break;
                case 2:
                    Log.i(TAG,"receive msg from Client:"+msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null,2);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你。");
                    replyMessage.setData(bundle);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }
}
