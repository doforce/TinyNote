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
public class RecyclerViewAdapter extends RecyclerView.Adapter {
//    public OnItemClickedListener onItemClickedListener;

    private Context context;
    private ArrayList<Mydata> mydatas;
    public RecyclerViewAdapter(Context context, ArrayList mydatas){
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

//        if (onItemClickedListener!=null){
//            vh.root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos=vh.getLayoutPosition();
//                    onItemClickedListener.onClicked(vh.root,pos);
//                }
//            });
//
//            vh.root.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos=vh.getLayoutPosition();
//                    onItemClickedListener.onLongClicked(vh.root,pos);
//                    return false;
//                }
//            });
//        }


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

//    /**
//     * 点击事件接口
//     */
//    public interface OnItemClickedListener{
//        public void onClicked(View parent, int position);
//        public boolean onLongClicked(View parent,int position);
//    }
//
//    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
//        this.onItemClickedListener = onItemClickedListener;
//    }
}

