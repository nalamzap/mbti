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
        switch(view.getId()){
            case R.id.aboutMBTI :
                activityAboutMBTI();
                break;

            case R.id.takeTestCard :
                activityPersonalityTest();
                break;
            case R.id.personalities:
                _16personalities();
                break;
            case R.id.getSaved:
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
