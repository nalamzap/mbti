package in.zerene.personality_finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.aboutMBTI) {
            activityAboutMBTI();
        } else if (id == R.id.takeTestCard) {
            activityPersonalityTest();
        } else if (id == R.id.personalities) {
            _16personalities();
        } else if (id == R.id.getSaved) {
            savedActivity();
        }
    }

    private void _16personalities() {
        Intent intent = new Intent(this,Personalities.class);
        startActivity(intent);
    }

    private void activityAboutMBTI() {
        Intent intent = new Intent(this,Mbti.class);
        startActivity(intent);
    }

    private void activityPersonalityTest() {
        Intent intent = new Intent(this,PersonalityTest.class);
        startActivity(intent);
    }

    private void savedActivity(){
        Intent intent = new Intent(this,ShowSaved.class);
        startActivity(intent);
    }
}
