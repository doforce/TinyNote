package com.example.vampire.tinynote.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vampire.tinynote.R;
import com.example.vampire.tinynote.data.NoteDB;

/**
 * Created by Vampire on 2016/4/10 0010.
 */
public class NoteDetailsAty extends AppCompatActivity implements View.OnClickListener{
    private EditText details_title,details_content;
    private TextView de_title;
    private ImageButton openCamera,openPiecture,save,back;
    private NoteDB noteDB;
    private SQLiteDatabase dbWriter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        initView();
    }

    /**
     * 初始化View
     */
    public void initView(){
        de_title = (TextView) findViewById(R.id.de_title);
        details_title= (EditText) findViewById(R.id.etTitle);
        details_content= (EditText) findViewById(R.id.etContent);
        openCamera = (ImageButton) findViewById(R.id.camera);
        openPiecture = (ImageButton) findViewById(R.id.picture);
        save = (ImageButton) findViewById(R.id.ib_save);
        back = (ImageButton) findViewById(R.id.ib_details_title_back);

        back.setOnClickListener(this);
        openPiecture.setOnClickListener(this);
        openCamera.setOnClickListener(this);
        save.setOnClickListener(this);
        noteDB=new NoteDB(this);
        dbWriter=noteDB.getWritableDatabase();

        //从数据库中还原数据
        details_title.setText(getIntent().getStringExtra(NoteDB.TITLE));
        details_content.setText(getIntent().getStringExtra(NoteDB.CONTENT));
        String title=getIntent().getStringExtra(NoteDB.TITLE);
        if (title.length() >= 4) {
            title = title.substring(0, 3)+"...";
        }
        de_title.setText(title);
    }

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
                updateDB();
                finish();
                break;
            case R.id.ib_details_title_back:
                finish();
                break;
        }
    }

    /**
     * 编辑后更新数据
     */
    private void updateDB(){
        ContentValues cv=new ContentValues();
        cv.put(NoteDB.CONTENT,details_content.getText().toString());
        cv.put(NoteDB.TITLE,details_title.getText().toString());
        cv.put(NoteDB.TIME,AddNoteAty.getCurrentTime());
        cv.put(NoteDB.CURRENT_TIME,AddNoteAty.gerCurrentSecond());
        int number=dbWriter.update(NoteDB.TABLE_NAME,cv,"_id="+getIntent().getIntExtra(NoteDB.ID,0),null);
        Log.e("numbers:", "" + number);
    }
}
