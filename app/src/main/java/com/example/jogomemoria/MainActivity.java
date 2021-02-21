package com.example.jogomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jogomemoria.database.ScoreDatabase;
import com.example.jogomemoria.modelo.Score;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private final Integer numPossibilities = 6;
    private List<String>  possibilities;
    private List<String> numericList;
    private List<Button> buttonList;
    private ConstraintLayout mainBody;
    private ProgressBar progressBar;
    private LinearLayout congratulationScreen;
    private TextView timeResult;
    private TextView errosResult;
    private Integer hits;
    private Integer mistakes;
    private Calendar startTime;
    private EditText inputName;
    private Button btnSaveScore;

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

        this.timeResult = (TextView) findViewById(R.id.time_result);
        this.errosResult = (TextView) findViewById(R.id.erros_result);
        this.inputName = (EditText) findViewById(R.id.input_name);
        this.btnSaveScore = (Button) findViewById(R.id.button_save_score);

        class ValidaNomeEControlaOBotaoSave implements TextWatcher{
            Button btn;
            ValidaNomeEControlaOBotaoSave(Button button){
                this.btn = button;
            }
            public void afterTextChanged(Editable s) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Conferir-> count depois", String.valueOf(count));
                if(count < 1){
                    this.btn.setEnabled(false);
                }else{
                    this.btn.setEnabled(true);
                }
            }
        }
        this.inputName.addTextChangedListener(new ValidaNomeEControlaOBotaoSave(this.btnSaveScore));

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

        this.startTime = Calendar.getInstance();
        this.mistakes = 0;

        if(this.btnSaveScore.getText().length() < 1){
            this.btnSaveScore.setEnabled(false);
        }else{
            this.btnSaveScore.setEnabled(true);
        }
    }

    private void setAllButtonsVisible(){
        for(Button btn: this.buttonList){
            btn.setVisibility(View.VISIBLE);
        }
    }


    private Integer calcProgress(Integer hits){
        return Math.round(hits*100/numPossibilities);
    }

    public void onRestartClick(View view){
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
                this.win();
            }
        }else{
            this.mistakes++;
            this.resetSession();
        }
    }

    private void win(){
        Calendar finishTime = Calendar.getInstance();
        Long timeRes = finishTime.getTimeInMillis() - startTime.getTimeInMillis();

        this.timeResult.setText(timeRes.toString());
        this.errosResult.setText(this.mistakes.toString());

        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.inputName.getWindowToken(), 0);

        this.congratulationScreen.setVisibility(View.VISIBLE);
    }

    public void goToScore(View view){
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    public void onBtnSaveClick(View view){

        ScoreDatabase bd = new ScoreDatabase(this);

        Score score = new Score();
        score.setName(this.inputName.getText().toString());
        score.setTime(Double.valueOf(this.timeResult.getText().toString()));
        score.setErros(Long.valueOf(this.errosResult.getText().toString()));

        bd.insert(score);
        bd.close();

        Toast.makeText(this, R.string.msg_score_saved, Toast.LENGTH_LONG).show();

        InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.inputName.getWindowToken(), 0);

        this.start();

    }

}