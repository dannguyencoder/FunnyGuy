package com.vinhcrazyyyy.funnyguy.recyclerViewFunnyTexts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinhcrazyyyy.funnyguy.FunnyTextVM;
import com.vinhcrazyyyy.funnyguy.R;

import java.util.List;

import butterknife.BindView;

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

    class FunnyTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_funny_text)
        TextView tvFunnyText;

        public FunnyTextViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(FunnyTextVM data) {
            tvFunnyText.setText(data.getFunnyText());
        }
    }
}
