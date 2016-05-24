package com.example.vampire.tinynote.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vampire.tinynote.R;
import com.example.vampire.tinynote.adapter.RecyclerAdapter;
import com.example.vampire.tinynote.data.Mydata;
import com.example.vampire.tinynote.data.NoteDB;
import com.example.vampire.tinynote.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import openSource.recyclerDivider.DividerItemDecoration;

public class MainActivity extends AppCompatActivity  {
    private FloatingActionButton btnAddNote;
    private RecyclerView rv;
    private TextView tv_showNumber;
    private Intent intent;
    private NoteDB noteDB;
    private RecyclerAdapter adapter;
    private SQLiteDatabase dbReader, dbWriter;
    private Cursor cursor;
    private ArrayList<Mydata> allDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        btnAddNote = (FloatingActionButton) findViewById(R.id.fab_addNote);
        tv_showNumber = (TextView) findViewById(R.id.tv_showNumber);

        noteDB = new NoteDB(this);
        dbReader = noteDB.getReadableDatabase();
        dbWriter = noteDB.getWritableDatabase();

        rv= (RecyclerView) findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setLayoutManager(new GridLayoutManager(this,3));

        //绘制分割线
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        setItemClicked();

//                //删除事件
//                if (position == 1) {
//                    Mydata data = allDatas.get(contextPosition);
//                    int id=data.getId();
//                    try {
//                        dbWriter.delete(NoteDB.TABLE_NAME, NoteDB.ID+ "=" +id, null);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    sendDataAdapter();
//                }
    }

    /**
     * 在onResume()方法执行的时候读取数据库
     */
    @Override
    protected void onResume() {
        super.onResume();
        sendDataAdapter();

    }

    /**
     * 按钮点击事件
     *
     * @param v
     */
    public void add_onClick(View v) {
        intent = new Intent(this, AddNoteAty.class);
        if (v.getId() == R.id.fab_addNote) {
            startActivity(intent);
        }
    }

    /**
     * 从数据库中读取数据。
     stickDatas存储置顶数据，mydatas存储非置顶数据.alldatas列表存储非置顶+置顶
     */
    private void readDatatoArray() {
        String excesql = "select * from " + NoteDB.TABLE_NAME + " ORDER BY " + NoteDB.CURRENT_TIME + " DESC";
        cursor = dbReader.rawQuery(excesql, null);
        allDatas.clear();//清空列表
        ArrayList<String> stickTime = new ArrayList<>();
        ArrayList<String> stickTitle = new ArrayList<>();
        ArrayList<String> stickContent = new ArrayList<>();

        if (!allDatas.isEmpty()) {
            for (Mydata mydata : allDatas) {
                allDatas.add(mydata);
                stickTime.add(mydata.getTime());
                stickTitle.add(mydata.getTitle());
                stickContent.add(mydata.getContent());
            }
        }
        while (cursor.moveToNext()) {
            //如果不包含这个数据，我们就添加。
            if (!stickTime.contains(cursor.getString(cursor.getColumnIndex(NoteDB.TIME)))
                    && !stickTitle.contains(cursor.getString(cursor.getColumnIndex(NoteDB.TITLE)))
                    && !stickContent.contains(cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT)))) {

                allDatas.add(new Mydata(cursor.getInt(cursor.getColumnIndex(NoteDB.ID))
                        ,cursor.getString(cursor.getColumnIndex(NoteDB.TITLE))
                        , cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT))
                        , cursor.getString(cursor.getColumnIndex(NoteDB.TIME))
                        , cursor.getInt(cursor.getColumnIndex(NoteDB.CURRENT_TIME))
                ));
            }
        }
    }

    /**
     * 从大都小排列
     */
    private void sortDatas() {
        Collections.sort(allDatas, new Comparator<Mydata>() {
            @Override
            public int compare(Mydata lhs, Mydata rhs) {
                if (lhs.getCountTime() > rhs.getCountTime()) {
                    return -1;
                }
                return 1;
            }
        });

    }

    /**
     * 把数据发送至adpater
     */
    private void sendDataAdapter() {
        readDatatoArray();
        sortDatas();
        adapter = new RecyclerAdapter(this, allDatas);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        tv_showNumber.setText("["+String.valueOf(cursor.getCount())+"]");
    }

    /**
     * 设置点击事件
     */
    private void setItemClicked(){
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
            /**
             * 点击进入详情页面
             * @param view
             * @param position
             */
            @Override
            public void onItemClick(View view, int position) {
                Mydata data = allDatas.get(position);
                Intent i = new Intent(MainActivity.this, NoteDetailsAty.class);
                i.putExtra(NoteDB.ID, data.getId());
                i.putExtra(NoteDB.CONTENT, data.getContent());
                i.putExtra(NoteDB.TITLE, data.getTitle());
                startActivity(i);
            }

            /**
             * 长按显示置顶和删除操作
             * @param view
             * @param position
             */
            @Override
            public void onItemLongClick(View view, final int position) {
                Toast.makeText(MainActivity.this,"OnLongClicked",Toast.LENGTH_LONG).show();
            }
        }));
    }
}
