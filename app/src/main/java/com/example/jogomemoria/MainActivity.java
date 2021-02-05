package com.example.jogomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Integer numPossibilities = 6;
    private List<String>  possibilities;
    private List<String> numericList;
    private List<Button> buttonList;
    private ConstraintLayout mainBody;
    private ProgressBar progressBar;
    private LinearLayout congratulationScreen;
    private Integer hits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this. mainBody =  (ConstraintLayout) findViewById(R.id.main_body);

        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        this.congratulationScreen = (LinearLayout) findViewById(R.id.congratulation_screen);

        this.buttonList = new ArrayList<>();
        this.buttonList.add( (Button) findViewById(R.id.button1));
        this.buttonList.add( (Button) findViewById(R.id.button2));
        this.buttonList.add( (Button) findViewById(R.id.button3));
        this.buttonList.add( (Button) findViewById(R.id.button4));
        this.buttonList.add( (Button) findViewById(R.id.button5));
        this.buttonList.add( (Button) findViewById(R.id.button6));

        this.start();

    }

    private void resetSession(){
        this.mainBody.setBackgroundColor(getResources().getColor(R.color.white));
        this.setAllButtonsVisible();
        this.hits = 0;
        this.progressBar.setProgress(0);
    }

    private void createRandomNumberList(){

        this.possibilities = new ArrayList<>();
        for(int i = 0 ; i < this.numPossibilities ; i++){
            this.possibilities.add(String.valueOf(i+1));
        }

        this.numericList = new ArrayList<>();
        for(int i = 0; i < this.numPossibilities; i++){
            Integer index = Math.round((float)Math.random()*(this.possibilities.size()-1));
            String result = this.possibilities.get(index);
            this.numericList.add(result);
            this.possibilities.remove(result);
        }

        Log.d("Conferir-> ", "Lista gerada: " + this.numericList.toString());
    }

    private void start(){

        this.congratulationScreen.setVisibility(View.INVISIBLE);

        this.mainBody.setBackgroundColor(getResources().getColor(R.color.white));

        this.setAllButtonsVisible();

        this.createRandomNumberList();

        this.progressBar.setProgress(0);

        this.hits = 0;

    }

    private void setAllButtonsVisible(){
        for(Button btn: this.buttonList){
            btn.setVisibility(View.VISIBLE);
        }
    }


    private Integer calcProgress(Integer hits){
        return Math.round(hits*100/numPossibilities);
    }

    public void btnRestart(View view){
        this.start();
    }

    public void onButtonClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        Log.d("Conferir-> ", "Botão clicado: " + btnText);
        if(btnText.equals(this.numericList.get(this.hits))){
            btn.setVisibility(View.INVISIBLE);
            this.mainBody.setBackground(btn.getBackground());
            this.hits++;
            this.progressBar.setProgress(this.calcProgress(this.hits));
            if(this.hits == this.numPossibilities){
                this.congratulationScreen.setVisibility(View.VISIBLE);
            }
        }else{
            this.resetSession();
        }
    }

}