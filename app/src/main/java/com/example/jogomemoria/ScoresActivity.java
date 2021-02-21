package com.example.jogomemoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jogomemoria.database.ScoreDatabase;
import com.example.jogomemoria.modelo.Score;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    private ListView timeScores;
    private ListView errosScores;

    private Button btnTempo;
    private Button btnErros;
    private Button btnClearDb;

    private LinearLayout containerTempo;
    private LinearLayout containerErros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        this.containerTempo = (LinearLayout) findViewById(R.id.container_score_time);
        this.containerErros = (LinearLayout) findViewById(R.id.container_score_erros);

        this.btnTempo = (Button) findViewById(R.id.button_time_score);
        this.btnErros = (Button) findViewById(R.id.button_erros_score);
        this.btnClearDb = (Button) findViewById(R.id.button_clear_db);

        this.timeScores = (ListView) findViewById(R.id.time_score);
        this.errosScores = (ListView) findViewById(R.id.erros_score);

        this.populateTimeScore();
        this.populateErrosScore();
    }


    public void onButtonTimeClick(View v){
        this.btnTempo.setEnabled(false);
        this.btnErros.setEnabled(true);
        this.containerTempo.setVisibility(View.VISIBLE);
        this.containerErros.setVisibility(View.INVISIBLE);
    }

    public void onButtonErrosClick(View v){
        this.btnErros.setEnabled(false);
        this.btnTempo.setEnabled(true);
        this.containerErros.setVisibility(View.VISIBLE);
        this.containerTempo.setVisibility(View.INVISIBLE);
    }


    private void doClearScore(){
        this.clearDatabase();
        this.populateTimeScore();
        this.populateErrosScore();
        Toast.makeText(this, "Os placares foram apagados!", Toast.LENGTH_LONG).show();
    }

    private void cancelClearScore(){
        Toast.makeText(this, "Os placares não foram apagados!", Toast.LENGTH_LONG).show();
    }

    public void onClearScoreClick(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Excluir Placares?");
        alertBuilder.setMessage("Deseja realmente excluír os placares?");

        class OnClickPositive implements DialogInterface.OnClickListener{
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doClearScore();
            }
        }
        alertBuilder.setPositiveButton("Sim", new OnClickPositive());

        class OnClickNegative implements DialogInterface.OnClickListener{

            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelClearScore();
            }
        }
        alertBuilder.setNegativeButton("Não", new OnClickNegative());

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

    }


    private void populateTimeScore(){
        ArrayList<Score> scores = this.getTimeScore();
        ScoresAdapter scoreAdapter = new ScoresAdapter(this, scores, ScoresAdapter.SHOW_TIME);
        timeScores.setAdapter(scoreAdapter);
    }

    private ArrayList<Score> getTimeScore(){
        ScoreDatabase db = new ScoreDatabase(this);
        ArrayList<Score> scores = new ArrayList<Score>(db.allByTime());
        db.close();
        return scores;
    }

    private void populateErrosScore(){
        ArrayList<Score> scores = this.getErrosScore();
        ScoresAdapter scoreAdapter = new ScoresAdapter(this, scores, ScoresAdapter.SHOW_ERROS);
        errosScores.setAdapter(scoreAdapter);
    }

    private ArrayList<Score> getErrosScore(){
        ScoreDatabase db = new ScoreDatabase(this);
        ArrayList<Score> scores = new ArrayList<Score>(db.allByErros());
        db.close();
        return scores;
    }

    private void clearDatabase(){
        ScoreDatabase db = new ScoreDatabase(this);
        db.clear();
        db.close();
    }
}