package com.pintluc.questionquizz;

import android.database.Cursor;

public class Question {
    private String intitule;
    private int reponse;

    public Question(String intitule, int reponse) {
        setIntitule(intitule);
        setReponse(reponse);
    }

    public Question(Cursor cursor) {
        intitule = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponse = cursor.getInt(cursor.getColumnIndexOrThrow("reponse"));
    }

    public String getIntitule() {
        return intitule;
    }

    public int getReponse() {
        return reponse;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setReponse(int reponse) {
        this.reponse = reponse;
    }
}
