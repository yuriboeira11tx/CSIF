package sgif.com.br.sgif_setgameif;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class TelaInicial extends AppCompatActivity {
    private Button botaoJogar;
    private Button botaoScore;
    private Button botaoRanking;
    private Button botaoInstrucoes;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tela_inicial);

        botaoJogar = findViewById(R.id.botaoJogar);
        botaoScore = findViewById(R.id.botaoScore);
        botaoRanking = findViewById(R.id.botaoRanking);
        botaoInstrucoes = findViewById(R.id.botaoInstrucoes);
        logo = findViewById(R.id.logo);

        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TelaInicial.this, R.anim.bounce);
                botaoJogar.startAnimation(animation);
                Intent intent = new Intent(TelaInicial.this, MainActivity.class);
                startActivity(intent);
            }
        });

        botaoScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TelaInicial.this, R.anim.bounce);
                botaoScore.startAnimation(animation);
                Intent intent = new Intent(TelaInicial.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        botaoRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TelaInicial.this, R.anim.bounce);
                botaoRanking.startAnimation(animation);
                Intent intent = new Intent(TelaInicial.this, RankingActivity.class);
                startActivity(intent);
            }
        });

        botaoInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TelaInicial.this, R.anim.bounce);
                botaoInstrucoes.startAnimation(animation);
                Intent intent = new Intent(TelaInicial.this, InstrucoesActivity.class);
                startActivity(intent);
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TelaInicial.this, R.anim.bounce);
                logo.startAnimation(animation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
