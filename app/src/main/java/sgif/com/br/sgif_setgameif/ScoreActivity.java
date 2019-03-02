package sgif.com.br.sgif_setgameif;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreList;
    private ImageView imageView;
    private ArrayList<Integer> arrayScore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);

        scoreList = findViewById(R.id.scoreList);
        imageView = findViewById(R.id.estrela);

        SQLiteDatabase banco = openOrCreateDatabase("csif", MODE_PRIVATE, null);
        banco.execSQL("CREATE TABLE IF NOT EXISTS score (id INTEGER PRIMARY KEY AUTOINCREMENT, valor INTEGER)");

        Cursor cursor = banco.rawQuery("SELECT valor FROM score", null);
        int indiceColunaValor = cursor.getColumnIndex("valor");

        cursor.moveToFirst();

        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                arrayScore.add(cursor.getInt(indiceColunaValor));
            }
        }

        cursor.close();
        banco.close();

        if (!arrayScore.isEmpty()) {
            scoreList.setText(Collections.max(arrayScore).toString());
            SharedPreferences.Editor editor = getSharedPreferences("SCORE", MODE_PRIVATE).edit();
            editor.putInt("melhorScore", Collections.max(arrayScore));
            editor.apply();
        } else {
            SharedPreferences preferences = getSharedPreferences("SCORE", MODE_PRIVATE);
            scoreList.setText(String.valueOf(preferences.getInt("score", 0)));
        }

        Animation animation = AnimationUtils.loadAnimation(ScoreActivity.this, R.anim.blink_anim);
        Animation animationStar = AnimationUtils.loadAnimation(ScoreActivity.this, R.anim.blink_anim);
        scoreList.setAnimation(animation);
        imageView.startAnimation(animationStar);
    }
}
