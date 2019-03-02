package sgif.com.br.sgif_setgameif;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public SlideAdapter(Context contexto) {
        this.context = contexto;
    }

    public String[] instrucoes = {
            "Cada carta do jogo possui quatro características: símbolo, cor, quantidade e preenchimento",
            "Identifique um conjunto de 3 cartas entre as 12 de forma que para cada uma das 4 características, as cartas sejam todas iguais ou todas diferentes"
    };

    public String[] titulos = {
            "Noções básicas",
            "Como fazer pontos?"
    };

    public int[] images = {
            R.drawable.ic_nocoes,
            R.drawable.pontos
    };

    @Override
    public int getCount() {
        return instrucoes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slide, container, false);
        RelativeLayout relativeLayout = view.findViewById(R.id.slideLayout);

        TextView textView = (TextView) view.findViewById(R.id.textInstrucao);
        TextView titulo = (TextView) view.findViewById(R.id.textTitulo);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageInstrucoes);

        textView.setText(instrucoes[position]);
        titulo.setText(titulos[position]);
        imageView.setImageResource(images[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
