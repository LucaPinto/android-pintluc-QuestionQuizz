package com.pintluc.questionquizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button BT_start;
    private EditText ET_joueur1;
    private EditText ET_joueur2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        BT_start = findViewById(R.id.main_start_bt);
        ET_joueur1 = findViewById(R.id.main_joueur1_et);
        ET_joueur2 = findViewById(R.id.main_joueur2_et);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * Lors du click sur le bouton, récupère le nom des joueurs et lance le jeu
         */
        BT_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("joueur1", ET_joueur1.getText().toString());
                gameActivity.putExtra("joueur2", ET_joueur2.getText().toString());
                startActivity(gameActivity);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_about:
                Toast.makeText(MainActivity.this, R.string.about, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_settings:
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}