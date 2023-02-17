package com.pintluc.questionquizz;

import static com.pintluc.questionquizz.GameActivity.vitesseQuestion;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.android.material.slider.Slider;
import com.pintluc.questionquizz.Models.QuestionQuizzSQLite;

public class SettingsActivity extends AppCompatActivity {

    private Slider SL_vitesse;
    private Button BT_vitesse_valider;
    private Button BT_question_valider;
    private EditText ET_question;
    private CheckBox CB_reponse;
    private QuestionManager questionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SL_vitesse = findViewById(R.id.settings_vitesse_sl);
        BT_question_valider = findViewById(R.id.settings_valider_question_bt);
        BT_vitesse_valider = findViewById(R.id.settings_valider_vitesse_bt);
        ET_question = findViewById(R.id.settings_questions_et);
        CB_reponse = findViewById(R.id.settings_question_cb);

        questionManager = new QuestionManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * Lors du click sur le bouton, modifie la vitesse de défilement des questions
         * en fonction des paramètres choisis par l'utilisateur
         */
        BT_vitesse_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitesseQuestion = getValueSlider() * 1000;
            }
        });

        /**
         * Lors du click sur le bouton, ajoute la question dans la DB
         */
        BT_question_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question question = new Question(ET_question.getText().toString(), verifyCheckbox());
                addQuestion(question);
            }
        });
    }

    /**
     * Récupère la vitesse paramètré par l'utilisateur
     * @return la valeur du slider
     */
    private long getValueSlider() {
        String value = String.valueOf(SL_vitesse.getValue());
        return (long) Double.parseDouble(value);
    }

    /**
     * Ajoute une question dans la DB
     * @param question La question à ajouter
     */
    private void addQuestion(Question question) {
        QuestionQuizzSQLite helper = new QuestionQuizzSQLite(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        int index = questionManager.getQuestionList().size() + 1;
        int reponse = question.getReponse();

        db.execSQL("INSERT INTO quizz VALUES(" + index + ",\"" + question.getIntitule() + "\",\"" + reponse + "\")");

        db.close();
    }

    /**
     * Vérifie la valeur de la checkBox
     * @return 1 si checkBox cochées, sinon 0
     */
    private int verifyCheckbox() {
        if (CB_reponse.isChecked()) {
            return 1;
        }
        return 0;
    }
}