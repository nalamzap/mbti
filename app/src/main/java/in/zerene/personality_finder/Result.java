package in.zerene.personality_finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class Result extends AppCompatActivity {

    ProgressBar progressBarI,progressBarN,progressBarF,progressBarJ;
    TextView textViewIntrovert,textViewExtrovert,textViewIntuitive,textViewObservant,textViewFeeling,
            textViewThinking,textViewJudging,textViewProspecting,

            pCode,pName;

    int[] vals;
    int pos;
    String personalityCode,personalityName;

    RelativeLayout shareRel;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        vals = new int[4];
        vals[0] = getIntent().getIntExtra("introvert",50);
        vals[1] = getIntent().getIntExtra("intuitive",50);
        vals[2] = getIntent().getIntExtra("feeling",50);
        vals[3] = getIntent().getIntExtra("judging",50);

        progressBarI = findViewById(R.id.pbarIntrovert);
        progressBarN = findViewById(R.id.pbarIntuitive);
        progressBarF = findViewById(R.id.pbarFeeling);
        progressBarJ = findViewById(R.id.pbarJudging);

        textViewIntrovert = findViewById(R.id.textViewIntrovert);
        textViewExtrovert = findViewById(R.id.textViewExtrovert);
        textViewIntuitive = findViewById(R.id.textViewIntuitive);
        textViewObservant = findViewById(R.id.textViewObservant);
        textViewFeeling = findViewById(R.id.textViewFeeling);
        textViewThinking = findViewById(R.id.textViewThinking);
        textViewJudging = findViewById(R.id.textViewJudging);
        textViewProspecting = findViewById(R.id.textViewProspecting);

        pCode = findViewById(R.id.personalityCode);
        pName = findViewById(R.id.personalityName);

        personalityCode = "";
        personalityName = "";

        if(vals[0]==50) vals[0] = 51;
        if(vals[0]>50){
            progressBarI.setProgress(vals[0]);
            personalityCode = personalityCode.concat("I");
        }
        else {
            progressBarI.setProgress(100-vals[0]);
            progressBarI.setRotation(180.0f);
            personalityCode = personalityCode.concat("E");
        }
        textViewIntrovert.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_introvert), vals[0],"%"));
        textViewExtrovert.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_extrovert), 100-vals[0],"%"));
        if(vals[1]==50) vals[1] = 51;
        if(vals[1]>50){
            progressBarN.setProgress(vals[1]);
            personalityCode = personalityCode.concat("N");
        }
        else {
            progressBarN.setProgress(100-vals[1]);
            progressBarN.setRotation(180.0f);
            personalityCode = personalityCode.concat("S");
        }
        textViewIntuitive.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_intuitive), vals[1],"%"));
        textViewObservant.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_observant), 100-vals[1],"%"));
        if(vals[2]==50) vals[2] = 51;
        if(vals[2]>50){
            progressBarF.setProgress(vals[2]);
            personalityCode = personalityCode.concat("F");
        }
        else {
            progressBarF.setProgress(100-vals[2]);
            progressBarF.setRotation(180.0f);
            personalityCode = personalityCode.concat("T");
        }
        textViewFeeling.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_feeling), vals[2],"%"));
        textViewThinking.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_thinking), 100-vals[2],"%"));
        if(vals[3]==50) vals[3] = 51;
        if(vals[3]>50){
            progressBarJ.setProgress(vals[3]);
            personalityCode = personalityCode.concat("J");
        }
        else {
            progressBarJ.setProgress(100-vals[3]);
            progressBarJ.setRotation(180.0f);
            personalityCode = personalityCode.concat("P");
        }
        textViewJudging.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_judging), vals[3],"%"));
        textViewProspecting.setText(MessageFormat.format("{0}{1}{2}", getString(R.string.text_prospecting), 100-vals[3],"%"));

        pCode.setText(personalityCode);

        switch(personalityCode){
            case "INFJ":
                personalityName = "Advocate";
                pos = 4;
                break;
            case "INFP":
                personalityName = "Mediator";
                pos = 5;
                break;
            case "INTJ":
                personalityName = "Architect";
                pos = 0;
                break;
            case "INTP":
                personalityName = "Logician";
                pos = 1;
                break;
            case "ISFJ":
                personalityName = "Defender";
                pos = 9;
                break;
            case "ISFP":
                personalityName = "Adventurer";
                pos = 13;
                break;
            case "ISTJ":
                personalityName = "Logistician";
                pos = 8;
                break;
            case "ISTP":
                personalityName = "Virtuoso";
                pos = 12;
                break;
            case "ENFJ":
                personalityName = "Protagonist";
                pos = 6;
                break;
            case "ENFP":
                personalityName = "Campaigner";
                pos = 7;
                break;
            case "ENTJ":
                personalityName = "Commander";
                pos = 2;
                break;
            case "ENTP":
                personalityName = "Debater";
                pos = 3;
                break;
            case "ESFJ":
                personalityName = "Consul";
                pos = 11;
                break;
            case "ESFP":
                personalityName = "Entertainer";
                pos = 15;
                break;
            case "ESTJ":
                personalityName = "Executive";
                pos = 10;
                break;
            case "ESTP":
                personalityName = "Entrepreneur";
                pos = 14;
                break;
        }
        pName.setText(personalityName);

        shareRel = findViewById(R.id.resultRel);
    }


    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.share) {
            shareRel.setVisibility(View.VISIBLE);
        } else if (id == R.id.resultRel) {
            shareRel.setVisibility(View.GONE);
        } else if (id == R.id.squareSaveBtn) {
            deviceSave(false);
        } else if (id == R.id.storySaveBtn) {
            deviceSave(true);
        } else if (id == R.id.save) {
            findViewById(R.id.saveRel).setVisibility(View.VISIBLE);
        } else if (id == R.id.onlineSave) {
            onlineSave();
        } else if (id == R.id.saveRel) {
            view.setVisibility(View.GONE);
        } else if (id == R.id.kayp) {
            kayp();
        }
    }

    private void onlineSave() {
        String email = ((TextView)findViewById(R.id.onlineSaveEmail)).getText().toString();
        String password = ((TextView)findViewById(R.id.onlineSavePassword)).getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email = email.replace(".","dot");
            if(password.length()>4){
                db = FirebaseDatabase.getInstance().getReference("mbti").child("users").child(email+password);
                db.child("personalityCode").setValue(personalityCode);
                db.child("ie").setValue(vals[0]);
                db.child("ns").setValue(vals[1]);
                db.child("ft").setValue(vals[2]);
                db.child("jp").setValue(vals[3]).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Result.this, "Saved!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
            else Toast.makeText(this, "Password should be more than 4 characters long", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Enter a valid E-mail address", Toast.LENGTH_SHORT).show();

    }

    private void kayp(){
        Intent intent = new Intent(this,Personalities.class);
        intent.putExtra("main",false);
        intent.putExtra("pos",pos);
        startActivity(intent);
    }

    private void deviceSave(boolean flag) {
        Intent intent = new Intent(this, ShareResult.class);
        intent.putExtra("code",personalityCode);
        intent.putExtra("name",personalityName);
        if(flag) intent.putExtra("story", true);
        intent.putExtra("ie", Math.max(vals[0], 100 - vals[0]));
        intent.putExtra("ns", Math.max(vals[1], 100 - vals[1]));
        intent.putExtra("ft", Math.max(vals[2], 100 - vals[2]));
        intent.putExtra("jp", Math.max(vals[3], 100 - vals[3]));
        startActivity(intent);
    }
}
