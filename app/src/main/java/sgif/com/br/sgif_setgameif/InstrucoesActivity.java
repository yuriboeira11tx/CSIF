package sgif.com.br.sgif_setgameif;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class InstrucoesActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_instrucoes);

        viewPager = findViewById(R.id.viewPager);
        adapter = new SlideAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
