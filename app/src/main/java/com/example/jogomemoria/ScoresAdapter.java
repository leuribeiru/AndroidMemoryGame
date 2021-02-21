package com.example.jogomemoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.jogomemoria.modelo.Score;

import java.util.ArrayList;

public class ScoresAdapter extends ArrayAdapter<Score> {

    private String valueToShow;

    public static String SHOW_TIME = "time";
    public static String SHOW_ERROS = "erros";

    public ScoresAdapter(Context context, ArrayList<Score> scores, String valueToShow){
        super(context, 0, scores);
        this.valueToShow = valueToShow;
    }

    public ScoresAdapter(Context context, ArrayList<Score> scores){
        super(context, 0, scores);
        this.valueToShow = this.SHOW_TIME;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Score score = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_score, parent, false);
        }

        TextView score_name = (TextView) convertView.findViewById(R.id.score_name);
        TextView score_value = (TextView) convertView.findViewById(R.id.score_value);

        score_name.setText(score.getName());
        if(this.valueToShow == this.SHOW_TIME){
            score_value.setText(score.getTime().toString() + " " + getContext().getString(R.string.string_seconds));
        }else{
            score_value.setText(score.getErros().toString() + " " + getContext().getString(R.string.string_mistakes));
        }

        return convertView;
    }
}
