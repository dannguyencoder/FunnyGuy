package com.vinhcrazyyyy.funnyguy;

import java.util.List;

public class FunnyPresenter implements FunnyContract.Presenter {

    private FunnyDatasource funnyDatasource;
    private FunnyContract.View view;

    public FunnyPresenter(FunnyDatasource funnyDatasource, FunnyContract.View view) {
        this.funnyDatasource = funnyDatasource;
        this.view = view;
    }

    @Override
    public void getFunnyTextSuggestions() {
        funnyDatasource.getTextSuggestions(new FunnyDatasource.TextCallback() {
            @Override
            public void onResponse(List<FunnyTextVM> results) {
                view.showFunnyTextSuggestions(results);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
