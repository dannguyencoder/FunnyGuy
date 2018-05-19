package com.vinhcrazyyyy.funnyguy;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpApi implements Api {
    @Override
    public void getFunnyTextSuggestions(final FunnyTextListener listener) {
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://172.16.8.109:7777/")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();

                Log.d("response string", "onResponse: " + responseString);

                Type listType = new TypeToken<ArrayList<FunnyTextResponse>>(){}.getType();

                List<FunnyTextResponse> results = new Gson().fromJson(responseString, listType);

                Log.d("result response", "onResponse: " + results.size() + results.get(0).getFunnyText());

                final List<FunnyTextVM> resultsVM = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    FunnyTextResponse responseItem = results.get(i);

                    FunnyTextVM vm = new FunnyTextVM();
                    vm.setId(responseItem.getId());
                    vm.setFunnyText(responseItem.getFunnyText());
                    vm.setCategory(responseItem.getCategory());

                    resultsVM.add(vm);
                }

                Log.d("content result", "onResponse: " + resultsVM.get(0).getFunnyText() + resultsVM.size());

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onResponse(resultsVM);
                    }
                });

            }
        });
    }
}
