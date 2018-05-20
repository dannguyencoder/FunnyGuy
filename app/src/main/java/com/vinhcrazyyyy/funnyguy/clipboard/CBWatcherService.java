package com.vinhcrazyyyy.funnyguy.clipboard;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.vinhcrazyyyy.funnyguy.FunnyDatasource;
import com.vinhcrazyyyy.funnyguy.FunnyRemoteDatasource;
import com.vinhcrazyyyy.funnyguy.FunnyTextVM;
import com.vinhcrazyyyy.funnyguy.OkHttpApi;
import com.vinhcrazyyyy.funnyguy.recyclerViewFunnyTexts.FunnyTextAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CBWatcherService extends Service {

    private final String tag = "[[ClipboardWatcherService]] ";
    List<FunnyTextVM> funnyTextSuggestions;

    //finally removed view by recyclerView
//    private View mView;

    RecyclerView recyclerView;
    FunnyTextAdapter funnyTextAdapter;

    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
//        Handler mServiceHandler = null;
//        Message msg = mServiceHandler.obtainMessage();
//        msg.arg1 = startId;
//        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            final ClipData cd = cb.getPrimaryClip();
            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String copiedValue = cd.getItemAt(0).getText().toString();
                        Toast.makeText(CBWatcherService.this, "You copied" + (cd.getItemAt(0).getText()).toString(), Toast.LENGTH_SHORT).show();

                        showSuggestions(copiedValue);
                    }
                });
            }
        }
    }

    private void showSuggestions(final String copiedValue) {
        //filter the results
        final FunnyRemoteDatasource funnyRemoteDatasource = new FunnyRemoteDatasource(new OkHttpApi());

        funnyRemoteDatasource.getTextSuggestions(new FunnyDatasource.TextCallback() {
            @Override
            public void onResponse(List<FunnyTextVM> results) {
                List<FunnyTextVM> funnyTextSuggestions = filterResults(copiedValue, results);

                showPopUpWithRecyclerView(funnyTextSuggestions);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void showPopUpWithRecyclerView(List<FunnyTextVM> funnyTextSuggestions) {
        //TODO: Make it like a window. Show just text using canvas as a demo
        //Add it on the first time
        if (recyclerView == null) {
            addRecyclerViewToWindow(funnyTextSuggestions);
        } else {
            //else recycle the list with new data
            funnyTextAdapter = new FunnyTextAdapter(funnyTextSuggestions);
            recyclerView.setAdapter(funnyTextAdapter);
        }

    }

    private void addRecyclerViewToWindow(List<FunnyTextVM> funnyTextSuggestions) {
        recyclerView = setupRecyclerView(funnyTextSuggestions);

        //Finally remove the view by recyclerView
//        mView = new MyLoadView(this, funnyTextSuggestions);

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 10, 10,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                android.graphics.PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.CENTER;
        mParams.setTitle("Window test");

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(recyclerView, mParams);
    }

    private RecyclerView setupRecyclerView(List<FunnyTextVM> funnyTextSuggestions) {
        recyclerView = new RecyclerView(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));

        recyclerView.setItemAnimator(animator);

        funnyTextAdapter = new FunnyTextAdapter(funnyTextSuggestions);

        recyclerView.setAdapter(new AlphaInAnimationAdapter(funnyTextAdapter));

        return recyclerView;
    }

    private List<FunnyTextVM> filterResults(String copiedValue, List<FunnyTextVM> data) {
        String[] listWords = copiedValue.split("\\P{L}+");

        List<FunnyTextVM> results = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getFunnyText().toLowerCase().trim().contains(copiedValue.toLowerCase().trim())) {
                results.add(data.get(i));
            }
        }
        Log.d("filter result", "filterResults: " + results.size());
        return results;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent intent = new Intent("com.android.vinhcrazyyyy.funnyguy.clipboard.CBWatcherService");
        sendBroadcast(intent);
    }

    //Finally removed view and replace by recyclerView
//    public class MyLoadView extends View {
//
//        private android.graphics.Paint mPaint;
//
//        private List<FunnyTextVM> funnyText;
//
//        public MyLoadView(android.content.Context context, List<FunnyTextVM> data) {
//            super(context);
//            mPaint = new android.graphics.Paint();
//            mPaint.setTextSize(50);
//            mPaint.setARGB(200, 200, 200, 200);
//            this.funnyText = data;
//        }
//
//        @Override
//        protected void onDraw(android.graphics.Canvas canvas) {
//            super.onDraw(canvas);
//            int nextLineHeight = 40;
//            int breakLineHeight = 50;
//            for (int i = 0; i < funnyText.size(); i++) {
//                canvas.drawText(funnyText.get(i).getFunnyText() + "\n", 0, nextLineHeight, mPaint);
//                nextLineHeight += breakLineHeight;
//            }
//
//        }
//
//        @Override
//        protected void onAttachedToWindow() {
//            super.onAttachedToWindow();
//        }
//
//        @Override
//        protected void onDetachedFromWindow() {
//            super.onDetachedFromWindow();
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
}
