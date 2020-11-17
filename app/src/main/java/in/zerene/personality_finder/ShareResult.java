package in.zerene.personality_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

public class ShareResult extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    EditText nameText;
    TextView name,mbti,personality,desc,ie,iep,ns,nsp,ft,ftp,jp,jpp;
    Button save;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean flag = getIntent().getBooleanExtra("story",false);

        if(flag){requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_story_result);
        }
        else setContentView(R.layout.activity_square_result);
        name = findViewById(R.id.textViewNameShare);
        mbti = findViewById(R.id.textViewMbtiTypeSquare);
        personality = findViewById(R.id.textViewPersonalityNameSquare);
        desc = findViewById(R.id.textViewDescSquareResult);
        ie = findViewById(R.id.i_e);
        iep = findViewById(R.id.i_eP);
        ns = findViewById(R.id.n_s);
        nsp = findViewById(R.id.n_sP);
        ft = findViewById(R.id.f_t);
        ftp = findViewById(R.id.f_tP);
        jp = findViewById(R.id.j_p);
        jpp = findViewById(R.id.j_pP);

        mbti.setText(getIntent().getStringExtra("code"));
        personality.setText(getIntent().getStringExtra("name"));
        iep.setText(getIntent().getIntExtra("ie",0)+"%");
        nsp.setText(getIntent().getIntExtra("ns",0)+"%");
        ftp.setText(getIntent().getIntExtra("ft",0)+"%");
        jpp.setText(getIntent().getIntExtra("jp",0)+"%");

        String code = getIntent().getStringExtra("code");

        if (code.charAt(0) == 'I') ie.setText("Introvert");
        else ie.setText("Extrovert");
        if (code.charAt(1) == 'N') ns.setText("Intuition");
        else ns.setText("Sensing");
        if (code.charAt(2) == 'F') ft.setText("Feeling");
        else ft.setText("Thinking");
        if (code.charAt(3) == 'J') jp.setText("Judging");
        else jp.setText("Prospecting");

        String desc;

        switch(code){
            case "INFJ":
                desc = getString(R.string.infjDESC);
                break;
            case "INFP":
                desc = getString(R.string.infpDESC);
                break;
            case "INTJ":
                desc = getString(R.string.intjDESC);
                break;
            case "INTP":
                desc = getString(R.string.intpDESC);
                break;
            case "ISFJ":
                desc = getString(R.string.isfjDESC);
                break;
            case "ISFP":
                desc = getString(R.string.isfpDESC);
                break;
            case "ISTJ":
                desc = getString(R.string.istjDESC);
                break;
            case "ISTP":
                desc = getString(R.string.istpDESC);
                break;
            case "ENFJ":
                desc = getString(R.string.enfjDESC);
                break;
            case "ENFP":
                desc = getString(R.string.enfpDESC);
                break;
            case "ENTJ":
                desc = getString(R.string.entjDESC);
                break;
            case "ENTP":
                desc = getString(R.string.entpDESC);
                break;
            case "ESFJ":
                desc = getString(R.string.esfjDESC);
                break;
            case "ESFP":
                desc = getString(R.string.esfpDESC);
                break;
            case "ESTJ":
                desc = getString(R.string.estjDESC);
                break;
            case "ESTP":
                desc = getString(R.string.estpDESC);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + code);
        }
        
        this.desc.setText(desc);

        save = findViewById(R.id.save_square_result);
        nameText = findViewById(R.id.editName);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                save.setEnabled(s.length() != 0);
                name.setText(s.toString());
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkPermission()) {
                requestPermission();
            }
        }

        if(flag){
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.editNameStoryLayout).setVisibility(View.VISIBLE);
                }
            });
            findViewById(R.id.editNameStoryLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.editNameStoryLayout).setVisibility(View.GONE);
                }
            });
            findViewById(R.id.closeEditName).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.editNameStoryLayout).setVisibility(View.GONE);
                }
            });
        }


    }

    public void onClick(View view) throws IOException {
        Bitmap bitmap = screenShot(findViewById(R.id.linearLayoutSquare));

        String path = Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath().replace("Android/data/in.zerene.personality_finder/files/Pictures","Pictures/Personality (MBTI) Finder");

        File mFolder = new File(path);
        String name = Calendar.getInstance().getTime().toString().concat(".png");
        File imgFile = new File(mFolder.getPath() + "/"+name);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }

        if (imgFile.exists()) {
            imgFile.delete();
        }
        if(imgFile.createNewFile())
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{imgFile.getAbsolutePath()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });

    }

    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ShareResult.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ShareResult.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(ShareResult.this, "Write External Storage permission allows us to store images. Please allow this permission to continue using our app", Toast.LENGTH_LONG).show();
        }

        ActivityCompat.requestPermissions(ShareResult.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Write External Storage permission allows us to store images. Please allow this permission in app settings", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
