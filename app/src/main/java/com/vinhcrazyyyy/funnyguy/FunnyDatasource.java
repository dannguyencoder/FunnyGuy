package com.vinhcrazyyyy.funnyguy;

import java.util.List;

public interface FunnyDatasource {
    void getTextSuggestions(TextCallback textCallback);

    interface TextCallback {
        void onResponse(List<FunnyTextVM> results);

        void onError(Exception e);
    }
}
