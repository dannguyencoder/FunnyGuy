package com.vinhcrazyyyy.funnyguy.recyclerViewFunnyTexts;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinhcrazyyyy.funnyguy.FunnyTextVM;
import com.vinhcrazyyyy.funnyguy.R;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

public class FunnyTextAdapter extends RecyclerView.Adapter<FunnyTextAdapter.FunnyTextViewHolder> {

    private List<FunnyTextVM> data;

    public FunnyTextAdapter(List<FunnyTextVM> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public FunnyTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_funny_text, parent, false);
        return new FunnyTextViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FunnyTextViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FunnyTextViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {

        @BindView(R.id.tv_funny_text)
        TextView tvFunnyText;

        public FunnyTextViewHolder(View itemView) {
            super(itemView);

            tvFunnyText = itemView.findViewById(R.id.tv_funny_text);
        }

        public void bind(FunnyTextVM data) {
            tvFunnyText.setText(data.getFunnyText());
        }

        @Override
        public void preAnimateAddImpl(ViewHolder holder) {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void preAnimateRemoveImpl(ViewHolder holder) {

        }

        @Override
        public void animateAddImpl(ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }

        @Override
        public void animateRemoveImpl(ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }
    }
}
