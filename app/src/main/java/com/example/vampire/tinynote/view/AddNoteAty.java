package com.example.vampire.tinynote.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.vampire.tinynote.R;
import com.example.vampire.tinynote.data.NoteDB;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vampire on 2016/4/9 0009.
 */
public class AddNoteAty extends AppCompatActivity implements View.OnClickListener {
    private EditText etTitle,etContent;
    private ImageButton openCamera, openPicture,save,back;

    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_content);

        initView();
    }

    /**
     * 初始化View控件
     */
    private void initView(){
        etTitle= (EditText) findViewById(R.id.etTitle);
        etContent= (EditText) findViewById(R.id.etContent);
        openCamera = (ImageButton) findViewById(R.id.camera);
        openPicture = (ImageButton) findViewById(R.id.picture);
        save = (ImageButton) findViewById(R.id.ib_save);
        back = (ImageButton) findViewById(R.id.ib_new_title_back);
        back.setOnClickListener(this);
        openPicture.setOnClickListener(this);
        openCamera.setOnClickListener(this);
        save.setOnClickListener(this);

        noteDB=new NoteDB(this);
        //获取数据库的写入权限
        dbWriter=noteDB.getWritableDatabase();
    }

    /**
     * 插入数据库
     */
    private void addDB(){
        ContentValues cv=new ContentValues();
        cv.put(NoteDB.CONTENT,etContent.getText().toString());
        cv.put(NoteDB.TITLE,etTitle.getText().toString());
        cv.put(NoteDB.TIME,getCurrentTime());
        cv.put(NoteDB.CURRENT_TIME,gerCurrentSecond());
        dbWriter.insert(NoteDB.TABLE_NAME,null,cv);
    }

    /**
     * 获取设备当前时间
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date();
        String str=format.format(date);
        return str;
    }

    public static long gerCurrentSecond() {
        long currentSecond=System.currentTimeMillis();
        return currentSecond;
    }

    /**
     * 四个按钮点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                break;
            case R.id.picture:
                Intent intent=new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivity(intent);
                break;
            case R.id.ib_save:
                if (etTitle.getText().toString().equals("") && etContent.getText().toString().equals("")) {
                    break;
                }
                addDB();
                finish();
                break;
            case R.id.ib_new_title_back:
                finish();
                break;
        }
    }
}
