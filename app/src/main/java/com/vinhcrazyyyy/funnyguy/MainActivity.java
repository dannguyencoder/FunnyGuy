package com.vinhcrazyyyy.funnyguy;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vinhcrazyyyy.funnyguy.clipboard.CBWatcherService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FunnyContract.View {

    FunnyContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, CBWatcherService.class));

        final ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                String a = clipboard.getText().toString();
                Toast.makeText(getBaseContext(), "Copy:\n" + a, Toast.LENGTH_LONG).show();
            }
        });

        presenter = new FunnyPresenter(new FunnyRemoteDatasource(new OkHttpApi()), this);
        presenter.getFunnyTextSuggestions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, CBWatcherService.class));
    }

    @Override
    public void showFunnyTextSuggestions(List<FunnyTextVM> results) {

    }
}
