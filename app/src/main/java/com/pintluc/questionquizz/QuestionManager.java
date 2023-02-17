package com.pintluc.questionquizz;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.pintluc.questionquizz.Models.QuestionQuizzSQLite;

public class QuestionManager {
    private ArrayList<Question> questionList = new ArrayList<>();
    private int currentQuestion;

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public QuestionManager(Context context) {
        questionList = initQuestionList(context);
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    /**
     * Récupère la question de la liste
     * @return la question de la liste
     */
    public Question getQuestion() {
        return questionList.get(currentQuestion);
    }

    /**
     * Passe à la question suivante de la liste
     * @return la question suivante de la liste
     */
    public Question nextQuestion() {
        currentQuestion++;
        if (currentQuestion >= questionList.size()) {
            currentQuestion = 0;
        }
        return getQuestion();
    }

    /**
     * Charge une liste de question depuis la DB.
     * @param context Le context de l'application pour passer la query
     * @return une arrayList chargé de Question
     */
    private ArrayList<Question> initQuestionList(Context context) {
        currentQuestion = -1;
        ArrayList<Question> initQuestion = new ArrayList<>();
        QuestionQuizzSQLite helper = new QuestionQuizzSQLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(true, "quizz", new String[]{"idQuizz", "question", "reponse"}, null,null, null, null, "idQuizz", null);

        while(cursor.moveToNext()) {
            initQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();

        return initQuestion;
    }
}
