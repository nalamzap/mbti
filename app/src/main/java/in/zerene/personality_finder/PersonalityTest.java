package in.zerene.personality_finder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Objects;

public class PersonalityTest extends AppCompatActivity {

    TextView textProgress, question;
    ProgressBar progressBar;
    ImageButton agreeMore,agreeLess,agreeLess2,neutral,disagreeLess,disagreeLess2,disagreeMore,
            prev,next;

    DatabaseReference database;
    String[] q;
    byte[][] ans;
    float[] ansMarks;
    int[] btnNumber;
    float i,n,f,j,t,prog;
    byte c,c1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test);

        q = new String[60];
        ans = new byte[60][2];
        database = FirebaseDatabase.getInstance().getReference("mbti");
        database.addListenerForSingleValueEvent(eventListener);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                textProgress = findViewById(R.id.textProgress);
                question = findViewById(R.id.textViewQuestion);
                progressBar = findViewById(R.id.progressBar);
                agreeMore = findViewById(R.id.agreeMore);
                agreeLess = findViewById(R.id.agreeLess);
                agreeLess2 = findViewById(R.id.agreeLess2);
                neutral = findViewById(R.id.neutral);
                disagreeLess2 = findViewById(R.id.disagreeLess2);
                disagreeLess = findViewById(R.id.disagreeLess);
                disagreeMore = findViewById(R.id.disagreeMore);
                prev=findViewById(R.id.prev);
                next=findViewById(R.id.next);

                ansMarks = new float[60];
                btnNumber= new int[60];
                i=0.0f;
                n=0.0f;
                f=0.0f;
                j=0.0f;
                t=0.0f;
                prog = 0.0f;
                c=c1=0;

                agreeMore.setOnClickListener(onAnswerClickListener);
                agreeLess.setOnClickListener(onAnswerClickListener);
                agreeLess2.setOnClickListener(onAnswerClickListener);
                neutral.setOnClickListener(onAnswerClickListener);
                disagreeLess2.setOnClickListener(onAnswerClickListener);
                disagreeLess.setOnClickListener(onAnswerClickListener);
                disagreeMore.setOnClickListener(onAnswerClickListener);

                next.setOnClickListener(onNavClickListener);
                prev.setOnClickListener(onNavClickListener);
            }
        });
        thread.start();
    }
    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()) {

                int i = 0;

                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : temp.getChildren()) {
                        switch (Objects.requireNonNull(snapshot.getKey())) {
                            case "question":
                                q[i] = Objects.requireNonNull(snapshot.getValue()).toString();
                                break;
                            case "trait":
                                ans[i][0] = Byte.parseByte(Objects.requireNonNull(snapshot.getValue()).toString());
                                break;
                            case "val":
                                ans[i][1] = Byte.parseByte(Objects.requireNonNull(snapshot.getValue()).toString());
                                break;
                        }
                    }
                    i++;
                }
                startTest();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void startTest() {
        progressBar.setProgress(Math.round(prog));
        question.setText(q[0]);
        findViewById(R.id.loading).setVisibility(View.GONE);

    }
    View.OnClickListener onAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float val = 0.0f;
            switch (v.getId()) {
                case R.id.agreeMore:
                    if (ans[c1][1] == 0) val = 8.333f;
                    else val = 0.0f;
                    btnNumber[c1] = 0;
                    break;
                case R.id.agreeLess:
                    if (ans[c1][1] == 0) val = (8.333f / 6.0f) * 5.0f;
                    else val = (8.333f / 6.0f);
                    btnNumber[c1] = 1;
                    break;
                case R.id.agreeLess2:
                    if (ans[c1][1] == 0) val = (8.333f / 6.0f) * 4.0f;
                    else val = (8.333f / 6.0f) * 2.0f;
                    btnNumber[c1] = 2;
                    break;
                case R.id.neutral:
                    val = (float) ((((int)(Math.random()*1000)) %2)==0?3.66:4.66);
                    btnNumber[c1] = 3;
                    break;
                case R.id.disagreeLess2:
                    if (ans[c1][1] == 1) val = (8.333f / 6.0f) * 4.0f;
                    else val = (8.333f / 6.0f) * 2.0f;
                    btnNumber[c1] = 4;
                    break;
                case R.id.disagreeLess:
                    if (ans[c1][1] == 1) val = (8.333f / 6.0f) * 5.0f;
                    else val = (8.333f / 6.0f);
                    btnNumber[c1] = 5;
                    break;
                case R.id.disagreeMore:
                    if (ans[c1][1] == 1) val = 8.333f;
                    else val = 0.0f;
                    btnNumber[c1] = 6;
            }
            switch (ans[c1][0]) {
                case 0:
                    if (c1 != c) i -= ansMarks[c1];
                    i += val;
                    break;
                case 1:
                    if (c1 != c) n -= ansMarks[c1];
                    n += val;
                    break;
                case 2:
                    if (c1 != c) f -= ansMarks[c1];
                    f += val;
                    break;
                case 3:
                    if (c1 != c) j -= ansMarks[c1];
                    j += val;
                    break;
                case 4:
                    if (c1 != c) t -= ansMarks[c1];
                    t += val;
                    break;
            }
            ansMarks[c1] = val;
            if (c == c1) {
                c++;
                c1 = c;
                prog += (100.0f / 60.0f);
                progressBar.setProgress(Math.round(prog));
                textProgress.setText("Progress " + progressBar.getProgress() + "%");
                next.setVisibility(View.INVISIBLE);
                prev.setVisibility(View.VISIBLE);
            } else {
                c1++;
                next.setVisibility(View.VISIBLE);
                if (c1 == c) next.setVisibility(View.INVISIBLE);
                prev.setVisibility(View.VISIBLE);
            }
            if (c == 60) showResult();
            else {
                question.setText(q[c1]);

                agreeMore.setImageTintList(null);
                agreeLess.setImageTintList(null);
                agreeLess2.setImageTintList(null);
                neutral.setImageTintList(null);
                disagreeLess2.setImageTintList(null);
                disagreeLess.setImageTintList(null);
                disagreeMore.setImageTintList(null);
                if (c != c1) {

                    switch (btnNumber[c1]) {
                        case 0:
                            agreeMore.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 1:
                            agreeLess.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 2:
                            agreeLess2.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 3:
                            neutral.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 4:
                            disagreeLess2.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 5:
                            disagreeLess.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                            break;
                        case 6:
                            disagreeMore.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                    }
                }
            }
        }
    };

    private void showResult() {
        Intent intent = new Intent(this,Result.class);
        intent.putExtra("introvert",Math.round(i));
        intent.putExtra("intuitive",Math.round(n));
        intent.putExtra("feeling",Math.round(f));
        intent.putExtra("judging",Math.round(j));
        intent.putExtra("turbulent",Math.round(t));
        startActivity(intent);
        finish();

    }

    View.OnClickListener onNavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            agreeMore.setImageTintList(null);
            agreeLess.setImageTintList(null);
            agreeLess2.setImageTintList(null);
            neutral.setImageTintList(null);
            disagreeLess2.setImageTintList(null);
            disagreeLess.setImageTintList(null);
            disagreeMore.setImageTintList(null);
            switch (v.getId()){
                case R.id.prev:
                    c1--;
                    next.setVisibility(View.VISIBLE);
                    question.setText(q[c1]);
                    break;
                case R.id.next:
                    c1++;
                    prev.setVisibility(View.VISIBLE);
                    question.setText(q[c1]);
            }
            if(c!=c1){
                prev.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                switch(btnNumber[c1]){
                    case 0:
                        agreeMore.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 1:
                        agreeLess.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 2:
                        agreeLess2.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 3:
                        neutral.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 4:
                        disagreeLess2.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 5:
                        disagreeLess.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                        break;
                    case 6:
                        disagreeMore.setImageTintList(ColorStateList.valueOf(getColor(R.color.colorAccent)));
                }
            }
            if(c1==0)prev.setVisibility(View.INVISIBLE);
            if(c1==c){
                next.setVisibility(View.INVISIBLE);
            }
        }
    };
}
