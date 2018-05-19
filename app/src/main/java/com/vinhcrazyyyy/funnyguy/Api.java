package com.vinhcrazyyyy.funnyguy;

import java.util.List;

interface Api {
    void getFunnyTextSuggestions(FunnyTextListener listener);

    interface FunnyTextListener {
        void onResponse(List<FunnyTextVM> results);

        void onError(Exception e);
    }
}
