package sgif.com.br.sgif_setgameif;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button botaoReiniciar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Chronometer cronometroView;
    private TextView scoreView;
    private int[] LISTA_IMAGENS = {R.drawable.a1111, R.drawable.a1112, R.drawable.a1113, R.drawable.a1121, R.drawable.a1122, R.drawable.a1123, R.drawable.a1131, R.drawable.a1132, R.drawable.a1133, R.drawable.a1211, R.drawable.a1212, R.drawable.a1213, R.drawable.a1221, R.drawable.a1222, R.drawable.a1223, R.drawable.a1231, R.drawable.a1232, R.drawable.a1233, R.drawable.a1311, R.drawable.a1312, R.drawable.a1313, R.drawable.a1321, R.drawable.a1322, R.drawable.a1323, R.drawable.a1331, R.drawable.a1332, R.drawable.a1333, R.drawable.a2111, R.drawable.a2112, R.drawable.a2113, R.drawable.a2121, R.drawable.a2122, R.drawable.a2123, R.drawable.a2131, R.drawable.a2132, R.drawable.a2133, R.drawable.a2211, R.drawable.a2212, R.drawable.a2213, R.drawable.a2221, R.drawable.a2222, R.drawable.a2223, R.drawable.a2231, R.drawable.a2232, R.drawable.a2233, R.drawable.a2311, R.drawable.a2312, R.drawable.a2313, R.drawable.a2321, R.drawable.a2322, R.drawable.a2323, R.drawable.a2331, R.drawable.a2332, R.drawable.a2333, R.drawable.a3111, R.drawable.a3112, R.drawable.a3113, R.drawable.a3121, R.drawable.a3122, R.drawable.a3123, R.drawable.a3131, R.drawable.a3132, R.drawable.a3133, R.drawable.a3211, R.drawable.a3212, R.drawable.a3213, R.drawable.a3221, R.drawable.a3222, R.drawable.a3223, R.drawable.a3231, R.drawable.a3232, R.drawable.a3233, R.drawable.a3311, R.drawable.a3312, R.drawable.a3313, R.drawable.a3321, R.drawable.a3322, R.drawable.a3323, R.drawable.a3331, R.drawable.a3332, R.drawable.a3333};
    public ArrayList<Integer> onTable = new ArrayList<>();
    public ArrayList<Carta> cartasListaStart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        botaoReiniciar = findViewById(R.id.botaoReiniciar);
        scoreView = findViewById(R.id.scoreView);
        recyclerView = findViewById(R.id.recyclerView);
        cronometroView = findViewById(R.id.cronometroView);
        cronometroView.setBase(SystemClock.elapsedRealtime());
        cronometroView.start();

        Animation animationRecycler = AnimationUtils.loadAnimation(MainActivity.this, R.anim.sample_anim);
        recyclerView.startAnimation(animationRecycler);

        SharedPreferences preferences = getSharedPreferences("SCORE", MODE_PRIVATE);
        int scoreDb = preferences.getInt("score", 0);

        if (scoreDb <= 0) {
            scoreView.setText(String.valueOf(0));
        } else {
            scoreView.setText(String.valueOf(scoreDb));
        }

        // INICIO DO JOGO
        final ArrayList<Carta> cartasLista = new ArrayList<>();

        for (int o = 0; o < 81; o++) {
            Random random = new Random();

            int numero_aleatorio = LISTA_IMAGENS[random.nextInt(LISTA_IMAGENS.length)];

            while (onTable.contains(numero_aleatorio)) {
                numero_aleatorio = LISTA_IMAGENS[random.nextInt(LISTA_IMAGENS.length)];
                Log.i("->", String.valueOf(onTable.contains(numero_aleatorio)));
            }

            Log.i("-->", String.valueOf(onTable.contains(numero_aleatorio)));
            onTable.add(numero_aleatorio);
            String nomeCarta = getResources().getResourceEntryName(numero_aleatorio);

            cartasLista.add(new Carta(numero_aleatorio, nomeCarta));
        }

        for (int i = 0; i < 12; i++) {
            int numero = cartasLista.get(i).getmImageResource();
            String nomeCarta = getResources().getResourceEntryName(numero);

            cartasListaStart.add(new Carta(numero, nomeCarta));

            adapter = new CartaAdaptador(cartasListaStart);

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            recyclerView.setAdapter(adapter);
        }

        botaoReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationButton = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                Animation animationRecycler = AnimationUtils.loadAnimation(MainActivity.this, R.anim.sample_anim);
                botaoReiniciar.startAnimation(animationButton);
                recyclerView.startAnimation(animationRecycler);

                cartasLista.clear();
                /*cronometroView.setBase(SystemClock.elapsedRealtime());
                cronometroView.start();*/
                int segundos = (int) (SystemClock.elapsedRealtime()-cronometroView.getBase())/1000;
                Log.i("CRONOMETRO", String.valueOf(segundos));

                onTable.clear();
                cartasLista.clear();
                cartasListaStart.clear();

                for (int o = 0; o < 81; o++) {
                    Random random = new Random();

                    int numero_aleatorio = LISTA_IMAGENS[random.nextInt(LISTA_IMAGENS.length)];

                    while (onTable.contains(numero_aleatorio)) {
                        numero_aleatorio = LISTA_IMAGENS[random.nextInt(LISTA_IMAGENS.length)];
                        Log.i("->", String.valueOf(onTable.contains(numero_aleatorio)));
                    }

                    Log.i("-->", String.valueOf(onTable.contains(numero_aleatorio)));
                    onTable.add(numero_aleatorio);
                    String nomeCarta = getResources().getResourceEntryName(numero_aleatorio);

                    cartasLista.add(new Carta(numero_aleatorio, nomeCarta));
                }

                for (int i = 0; i < 12; i++) {
                    int numero = cartasLista.get(i).getmImageResource();
                    String nomeCarta = getResources().getResourceEntryName(numero);

                    cartasListaStart.add(new Carta(numero, nomeCarta));

                    adapter = new CartaAdaptador(cartasListaStart);

                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }

        Intent intent = new Intent(MainActivity.this, TelaInicial.class);
        startActivity(intent);
    }
}
