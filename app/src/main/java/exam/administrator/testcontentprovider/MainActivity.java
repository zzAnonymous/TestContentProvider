package exam.administrator.testcontentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MyWordsTag";
    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = this.getContentResolver();

        Button buttonAll=(Button)findViewById(R.id.buttonAll);
        Button buttonInsert=(Button)findViewById(R.id.buttonInsert);
        Button buttonDelete=(Button)findViewById(R.id.buttonDelete);
        Button buttonDeleteAll=(Button)findViewById(R.id.buttonDeleteAll);
        Button buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        Button buttonQuery=(Button)findViewById(R.id.buttonQuery);

        //得到全部
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                        new String[] { Words.Word._ID, Words.Word.COLUMN_NAME_WORD,Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},
                        null, null, null);
                if (cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                if (cursor.moveToFirst()){
                    do{
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Words.Word._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))+ ",";
                        msg += "含义：" + cursor.getInt(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)) + ",";
                        msg += "示例" + cursor.getFloat(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)) + "\n";
                    }while(cursor.moveToNext());
                }

                Log.v(TAG,msg);


            }
        });

        //增加
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strWord="Banana";
                String strMeaning="banana";
                String strSample="This banana is very nice.";
                ContentValues values = new ContentValues();

                values.put(Words.Word.COLUMN_NAME_WORD, strWord);
                values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
                values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

                Uri newUri = resolver.insert(Words.Word.CONTENT_URI, values);
            }
        });

        //删除
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";//简单起见，这里指定ID，用户可在程序中设置id的实际值
                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
                int result = resolver.delete(uri, null, null);
            }
        });

        //删除全部
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolver.delete(Words.Word.CONTENT_URI, null, null);
            }
        });

        //修改
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";
                String strWord="Banana";
                String strMeaning="banana";
                String strSample="This banana is very nice.";
                ContentValues values = new ContentValues();

                values.put(Words.Word.COLUMN_NAME_WORD, strWord);
                values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
                values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
                int result = resolver.update(uri, values, null, null);

            }
        });

        //根据id查询
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";
                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
                Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                        new String[] { Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},
                        null, null, null);
                if (cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                if (cursor.moveToFirst()){
                    do{
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(Words.Word._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))+ ",";
                        msg += "含义：" + cursor.getInt(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)) + ",";
                        msg += "示例" + cursor.getFloat(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)) + "\n";
                    }while(cursor.moveToNext());
                }

                Log.v(TAG,msg);
            }
        });
    }

}

