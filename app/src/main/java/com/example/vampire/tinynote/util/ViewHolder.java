package com.example.vampire.tinynote.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vampire.tinynote.R;

/**
 * Created by X on 2016/5/7 0007.
 * 自定义ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public View root;

    public ViewHolder(View root) {
        super(root);
        this.root=root;
        time= (TextView) root.findViewById(R.id.time);
        title= (TextView) root.findViewById(R.id.title);
    }

    public TextView getTime() {
        return time;
    }

    public TextView getTitle() {
        return title;
    }
}

