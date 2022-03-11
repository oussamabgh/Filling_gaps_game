package com.example.game;

import android.database.Cursor;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  
  public MyBaseManager myBaseManager = new MyBaseManager(this);
  public ArrayList<String> allWords = new ArrayList<>();
   public int currentWordIndex = -1;
   public String currentWord ="";
    TextView l1 ;
    EditText l2 ;
    EditText l3 ;
    TextView l4 ;
    ImageView correct;
    ImageView incorrect ;
    TextView nmbr;



   int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillTheDB();

        l1 = (TextView) findViewById(R.id.letter1);
        l2 = (EditText) findViewById(R.id.letter2);
        l3 = (EditText) findViewById(R.id.letter3);
        l4 = (TextView) findViewById(R.id.letter4);
        nmbr=(TextView)findViewById(R.id.nmbr);
        correct = (ImageView) findViewById(R.id.correct);
        incorrect = (ImageView) findViewById(R.id.incorrect);

        Cursor cursor = myBaseManager.getAllWords();

       
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            allWords.add(cursor.getString(1));
        }

        wordGenerator();

        setWordView(l1,l4);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = currentWord.charAt(0)+l2.getText().toString()+
                        l3.getText().toString()+currentWord.charAt(3);
                checkAnswer(answer);
            }
        });
    }

   
    public void wordGenerator(){
        currentWordIndex = currentWordIndex +1;
        if(currentWordIndex >= allWords.size()){
            currentWordIndex = 0;
        }
        currentWord = allWords.get(currentWordIndex);
    }

    
    public void setWordView(TextView l1, TextView l4){
        l1.setText(currentWord.charAt(0)+"");
        l2.setText("");
        l3.setText("");

        l4.setText(currentWord.charAt(3)+"");
    }

   
    public boolean checkAnswer(String answer){

        if (answer.equalsIgnoreCase(currentWord)) {


            correct.setVisibility(View.VISIBLE);
            incorrect.setVisibility(View.INVISIBLE);
       i++;
           if(i<5){
            nmbr.setText(String.valueOf(i)+"/5");
           } else {

               i=0;
               nmbr.setText("ollyy ");

           }

            wordGenerator();
            setWordView(l1,l4);

            return true;
        }
        else {
            incorrect.setVisibility(View.VISIBLE);
           correct.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    //the database
    public void fillTheDB (){
        ArrayList<String> wordsToAdd = new ArrayList<>();

        wordsToAdd.add("able");
        wordsToAdd.add("beat");
        wordsToAdd.add("fast");
        wordsToAdd.add("love");
        wordsToAdd.add("pink");
        wordsToAdd.add("gray");
        wordsToAdd.add("wife");
        wordsToAdd.add("game");
        wordsToAdd.add("text");
        wordsToAdd.add("line");
        wordsToAdd.add("food");
   

        myBaseManager.addWords(wordsToAdd);

    }


}
