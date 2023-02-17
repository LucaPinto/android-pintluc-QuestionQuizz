package com.pintluc.questionquizz.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionQuizzSQLite extends SQLiteOpenHelper {

    static String DB_NAME = "QuestionQuizz.db";
    static int DB_VERSION = 1;

    public QuestionQuizzSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabase = "CREATE TABLE quizz(idQuizz INTEGER PRIMARY KEY, question TEXT, reponse INTEGER);";
        db.execSQL(sqlCreateDatabase);
        db.execSQL("INSERT INTO quizz VALUES(1, \"La capitale de l'Australie est Sidney\", 0)");
        db.execSQL("INSERT INTO quizz VALUES(2, \"La terre est-elle ronde ?\", 1)");
        db.execSQL("INSERT INTO quizz VALUES(3, \"Est-ce que Messi à plus de 40 ans ?\", 0)");
        db.execSQL("INSERT INTO quizz VALUES(4, \"Est-ce que Federer est Suisse ?\", 1)");
        db.execSQL("INSERT INTO quizz VALUES(5, \"Est-ce que l'Italie est en Europe ?\", 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
