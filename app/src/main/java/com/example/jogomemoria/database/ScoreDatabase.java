package com.example.jogomemoria.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jogomemoria.modelo.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreDatabase {
    private static final String DATABASE_NAME = "db1";

    private static final int DATABASE_ACCESS = 0;

    /* Consultas */
    private static final String SQL_STRUCT = "CREATE TABLE IF NOT EXISTS score (id_ INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, time DOUBLE NOT NULL, erros BIGINT NOT NULL);";
    private static final String SQL_INSERT = "INSERT INTO score (name, time, erros) VALUES ('%s', '%f', '%d');";
    private static final String SQL_SELECT_ALL = "SELECT * FROM score ORDER BY name";
    private static final String SQL_SELECT_ALL_ORDER_BY_TIME = "SELECT * FROM score ORDER BY time ASC;";
    private static final String SQL_SELECT_ALL_ORDER_BY_ERROS = "SELECT * FROM score ORDER BY erros ASC;";
    private static final String SQL_CLEAR = "DROP TABLE IF EXISTS score";

    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexID, indexName, indexTime, indexErros;

    //Construtor
    public ScoreDatabase(Context context){
        database = context.openOrCreateDatabase(DATABASE_NAME,
                                DATABASE_ACCESS, null);
        database.execSQL(SQL_STRUCT);
        Log.d("Conferir-> Database", "ok");
    }

    public void clear(){
        database.execSQL(SQL_CLEAR);
        Log.d("Conferir-> Database", "limpo");
    }

    public void close(){
        database.close();
    }

    public void insert(Score score){
        String query = String.format(SQL_INSERT,
                                    score.getName(),
                                    score.getTime(),
                                    score.getErros());
        database.execSQL(query);
    }

    public List<Score> all(){
        List<Score> scores = new ArrayList<Score>();

        cursor = database.rawQuery(SQL_SELECT_ALL, null);

        if(cursor.moveToFirst()){
            indexID = cursor.getColumnIndex("id_");
            indexName = cursor.getColumnIndex("name");
            indexTime = cursor.getColumnIndex("time");
            indexErros = cursor.getColumnIndex("erros");

            do{
                Score score = new Score(cursor.getString(indexName),
                        cursor.getDouble(indexTime),
                        cursor.getLong(indexErros));
                scores.add(score);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return scores;
    }

    public List<Score> allByTime(){
        List<Score> scores = new ArrayList<Score>();

        cursor = database.rawQuery(SQL_SELECT_ALL_ORDER_BY_TIME, null);

        if(cursor.moveToFirst()){
            indexID = cursor.getColumnIndex("id_");
            indexName = cursor.getColumnIndex("name");
            indexTime = cursor.getColumnIndex("time");
            indexErros = cursor.getColumnIndex("erros");

            do{
                Score score = new Score(cursor.getString(indexName),
                        cursor.getDouble(indexTime),
                        cursor.getLong(indexErros));
                scores.add(score);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return scores;
    }

    public List<Score> allByErros(){
        List<Score> scores = new ArrayList<Score>();

        cursor = database.rawQuery(SQL_SELECT_ALL_ORDER_BY_ERROS, null);

        if(cursor.moveToFirst()){
            indexID = cursor.getColumnIndex("id_");
            indexName = cursor.getColumnIndex("name");
            indexTime = cursor.getColumnIndex("time");
            indexErros = cursor.getColumnIndex("erros");

            do{
                Score score = new Score(cursor.getString(indexName),
                        cursor.getDouble(indexTime),
                        cursor.getLong(indexErros));
                scores.add(score);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return scores;
    }
}
