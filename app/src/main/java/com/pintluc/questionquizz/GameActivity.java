package com.pintluc.questionquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    private TextView TV_joueur1;
    private TextView TV_joueur2;
    private TextView TV_score1;
    private TextView TV_score2;
    private TextView TV_question_j1;
    private TextView TV_question_j2;
    private Button BT_rejouer;
    private Button BT_menu;
    private Button BT_joueur1;
    private Button BT_joueur2;
    private QuestionManager question;
    public static long vitesseQuestion = 2000;

    Runnable questionRunnable = null;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TV_joueur1 = findViewById(R.id.game_joueur1_tv);
        TV_joueur2 = findViewById(R.id.game_joueur2_tv);
        TV_score1 = findViewById(R.id.game_score1_tv);
        TV_score2 = findViewById(R.id.game_score2_tv);
        TV_question_j1 = findViewById(R.id.game_go1_tv);
        TV_question_j2 = findViewById(R.id.game_go2_tv);
        BT_rejouer = findViewById(R.id.game_rejouer_bt);
        BT_menu = findViewById(R.id.game_menu_bt);
        BT_joueur1 = findViewById(R.id.game_joueur1_bt);
        BT_joueur2 = findViewById(R.id.game_joueur2_bt);

        Intent gameActivity = getIntent();
        question = new QuestionManager(this);

        // Récupère et affiche le nom des joueurs dans la partie
        String joueur1 = String.valueOf(gameActivity.getStringExtra("joueur1"));
        String joueur2 = String.valueOf(gameActivity.getStringExtra("joueur2"));
        TV_joueur1.setText(joueur1);
        TV_joueur2.setText(joueur2);

        startCountDownTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * Lors du click sur le bouton, vérifie la réponse de la question et modifie le score
         */
        BT_joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyScore(TV_score1);
                setPlayerButton(false);
            }
        });

        /**
         * Lors du click sur le bouton, vérifie la réponse de la question et modifie le score
         */
        BT_joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyScore(TV_score2);
                setPlayerButton(false);
            }
        });

        /**
         * Lors du click sur le bouton, reset le jeu complet et le recommence
         */
        BT_rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
                startCountDownTimer();
            }
        });

        /**
         * Lors du click sur le bouton, redirige l'utilisateur au menu
         */
        BT_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(GameActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    /**
     * Affiche et change de questions toutes les 2 secondes
     */
    private void startQuestionIterative() {
        handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                   if (question.getCurrentQuestion() == question.getQuestionList().size() - 1) {
                       // Exécuter le code de fin de partie
                       handler.removeCallbacks(this);
                       setButtonVisibility(View.VISIBLE);
                   }else {
                       // Récupère et affiche les questions
                       displayQuestion();

                       setPlayerButton(true);

                       setButtonVisibility(View.INVISIBLE);

                       handler.postDelayed(this, vitesseQuestion);
                   }
            }
        };

        handler.postDelayed(questionRunnable, 1000);
    }

    /**
     * Compte à rebours avant le début de la partie
     */
    private void startCountDownTimer() {
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Afficher le compteur à l'utilisateur
                String temps = String.valueOf(millisUntilFinished / 1000);
                TV_question_j1.setText(temps);
                TV_question_j2.setText(temps);

                setPlayerButton(false);
                setButtonVisibility(View.INVISIBLE);
            }

            public void onFinish() {
                // Afficher le départ à l'utilisateur
                String partez = "Partez !";
                TV_question_j1.setText(partez);
                TV_question_j2.setText(partez);
                startQuestionIterative();
            }
        }.start();
    }

    /**
     * Vérifie la réponse de la question et modifie le score
     * @param TV_score Le score des joueurs affiché
     */
    private void modifyScore(TextView TV_score) {
        int score = Integer.parseInt(TV_score.getText().toString());
        if (question.getQuestion().getReponse() == 1) {
            score++;

        } else {
            score--;
        }

        TV_score.setText(String.valueOf(score));
    }

    /**
     * Rend les boutons "rejouer" et "menu" visible ou invisible
     * @param visible
     */
    private void setButtonVisibility(int visible) {
        BT_menu.setVisibility(visible);
        BT_rejouer.setVisibility(visible);
    }

    /**
     * Rend les boutons des joueurs enable ou disable
     * @param enable
     */
    private void setPlayerButton(boolean enable) {
        BT_joueur1.setEnabled(enable);
        BT_joueur2.setEnabled(enable);
    }

    /**
     * Reset l'interface graphique et l'index des questions
     */
    private void resetGame() {
        question.setCurrentQuestion(-1);
        TV_score1.setText("0");
        TV_score2.setText("0");
    }

    /**
     * Récupère et affiche les questions
     */
    private void displayQuestion() {
        String question_j1 = String.valueOf(question.nextQuestion().getIntitule());
        String question_j2 = String.valueOf(question.getQuestion().getIntitule());
        TV_question_j1.setText(question_j1);
        TV_question_j2.setText(question_j2);
    }
}