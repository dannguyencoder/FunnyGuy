package com.vinhcrazyyyy.funnyguy;

import java.util.List;

public interface FunnyContract {

    interface View {
        void showFunnyTextSuggestions(List<FunnyTextVM> results);
    }

    interface Presenter {
        void getFunnyTextSuggestions();
    }
}
