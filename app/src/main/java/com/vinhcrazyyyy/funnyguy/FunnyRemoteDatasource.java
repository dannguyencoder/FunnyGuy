package com.vinhcrazyyyy.funnyguy;

import java.util.List;

public class FunnyRemoteDatasource implements FunnyDatasource {

    Api api;

    public FunnyRemoteDatasource(Api api) {
        this.api = api;
    }

    @Override
    public void getTextSuggestions(final TextCallback textCallback) {
        api.getFunnyTextSuggestions(new Api.FunnyTextListener() {
            @Override
            public void onResponse(List<FunnyTextVM> results) {
                textCallback.onResponse(results);
            }

            @Override
            public void onError(Exception e) {
                textCallback.onError(e);
            }
        });
    }
}
