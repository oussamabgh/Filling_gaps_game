package esi.tp5;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  
  public MyBaseManager myBaseManager = new MyBaseManager(this);
  public ArrayList<String> allWords = new ArrayList<>();
   public int currentWordIndex = -1;
   public String currentWord ="";
    TextView letter1 = null;
    EditText letter2 = null;
    EditText letter3 = null;
    TextView letter4 = null;
    ImageView correct = null;
    ImageView incorrect = null;
    Animation animation = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillTheDB();

        letter1 = (TextView) findViewById(R.id.letter1);
        letter2 = (EditText) findViewById(R.id.letter2);
        letter3 = (EditText) findViewById(R.id.letter3);
        letter4 = (TextView) findViewById(R.id.letter4);

        correct = (ImageView) findViewById(R.id.correct);
        incorrect = (ImageView) findViewById(R.id.incorrect);

        Cursor cursor = myBaseManager.getAllWords();

       
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            allWords.add(cursor.getString(1));
        }

        wordGenerator();

        setWordView(letter1,letter4);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = currentWord.charAt(0)+letter2.getText().toString()+
                        letter3.getText().toString()+currentWord.charAt(3);
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
        letter2.setText("");
        letter3.setText("");
        l4.setText(currentWord.charAt(3)+"");
    }

   
    public boolean checkAnswer(String answer){
  animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        if (answer.equalsIgnoreCase(currentWord)) {
            correct.startAnimation(animation);
            wordGenerator();
            setWordView(letter1,letter4);
            return true;
        }
        else {
            incorrect.startAnimation(animation);
            return false;
        }
    }

    //the database
    public void fillTheDB (){
        ArrayList<String> wordsToAdd = new ArrayList<>();

        wordsToAdd.add("slow");
        wordsToAdd.add("fast");
        wordsToAdd.add("easy");
        wordsToAdd.add("hard");
        wordsToAdd.add("good");
        wordsToAdd.add("look");
        wordsToAdd.add("wolf");
        wordsToAdd.add("lion");
        wordsToAdd.add("fear");
        wordsToAdd.add("user");
        wordsToAdd.add("feel");
   

        myBaseManager.addWords(wordsToAdd);

    }


}
