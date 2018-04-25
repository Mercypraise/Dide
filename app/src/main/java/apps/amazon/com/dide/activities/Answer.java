package apps.amazon.com.dide.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import apps.amazon.com.dide.R;

public class Answer extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        String question = getIntent().getStringExtra("QUESTION");
        String answer = getIntent().getStringExtra("ANSWER");

        ((TextView) findViewById(R.id.question)).setText(question);
        ((TextView) findViewById(R.id.answer)).setText(answer);

    }
}
