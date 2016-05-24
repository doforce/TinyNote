package com.example.vampire.tinynote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vampire.tinynote.R;
import com.example.vampire.tinynote.data.Mydata;
import com.example.vampire.tinynote.util.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Vampire on 2016/4/9 0009.
 * 自定义的Adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Mydata> mydatas;
    public RecyclerAdapter(Context context, ArrayList mydatas){
        this.context=context;
        this.mydatas=mydatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh=new ViewHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.cell,null));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh= (ViewHolder) holder;
        Mydata data = mydatas.get(position);
        String tit=data.getTitle();
        String tim=data.getTime();
        //赋值给textView控件
        vh.title.setText(subString(tit));
        vh.time.setText(tim);
    }

    @Override
    public int getItemCount() {
        return mydatas.size();
    }

    protected String subString(String s) {
        if (s.length() >=7) {
            s = s.substring(0, 6);
            return s+"...";
        }
        return s;
    }
}

