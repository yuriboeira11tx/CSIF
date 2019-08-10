package sgif.com.br.sgif_setgameif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

public class CartaAdaptador extends RecyclerView.Adapter<CartaAdaptador.CartaViewHolder> {
    public ArrayList<Carta> cartaLista;
    public List<String> cartasSelecionadas = new ArrayList<>();
    public int[] LISTA_IMAGENS = {R.drawable.a1111, R.drawable.a1112, R.drawable.a1113, R.drawable.a1121, R.drawable.a1122, R.drawable.a1123, R.drawable.a1131, R.drawable.a1132, R.drawable.a1133, R.drawable.a1211, R.drawable.a1212, R.drawable.a1213, R.drawable.a1221, R.drawable.a1222, R.drawable.a1223, R.drawable.a1231, R.drawable.a1232, R.drawable.a1233, R.drawable.a1311, R.drawable.a1312, R.drawable.a1313, R.drawable.a1321, R.drawable.a1322, R.drawable.a1323, R.drawable.a1331, R.drawable.a1332, R.drawable.a1333, R.drawable.a2111, R.drawable.a2112, R.drawable.a2113, R.drawable.a2121, R.drawable.a2122, R.drawable.a2123, R.drawable.a2131, R.drawable.a2132, R.drawable.a2133, R.drawable.a2211, R.drawable.a2212, R.drawable.a2213, R.drawable.a2221, R.drawable.a2222, R.drawable.a2223, R.drawable.a2231, R.drawable.a2232, R.drawable.a2233, R.drawable.a2311, R.drawable.a2312, R.drawable.a2313, R.drawable.a2321, R.drawable.a2322, R.drawable.a2323, R.drawable.a2331, R.drawable.a2332, R.drawable.a2333, R.drawable.a3111, R.drawable.a3112, R.drawable.a3113, R.drawable.a3121, R.drawable.a3122, R.drawable.a3123, R.drawable.a3131, R.drawable.a3132, R.drawable.a3133, R.drawable.a3211, R.drawable.a3212, R.drawable.a3213, R.drawable.a3221, R.drawable.a3222, R.drawable.a3223, R.drawable.a3231, R.drawable.a3232, R.drawable.a3233, R.drawable.a3311, R.drawable.a3312, R.drawable.a3313, R.drawable.a3321, R.drawable.a3322, R.drawable.a3323, R.drawable.a3331, R.drawable.a3332, R.drawable.a3333};

    public static class CartaViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView score;
        public Context contexto;

        public CartaViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.figuraView);
            score = itemView.findViewById(R.id.scoreView);
            contexto = itemView.getContext();
        }
    }

    public CartaAdaptador(ArrayList<Carta> cartaArrayList) {
        cartaLista = cartaArrayList;
    }

    @Override
    public CartaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.figura, parent, false);
        return new CartaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartaViewHolder holder, int position) {
        final Carta cartaAtual = cartaLista.get(position);
        final SQLiteDatabase banco = holder.contexto.openOrCreateDatabase("csif", MODE_PRIVATE, null);
        banco.execSQL("CREATE TABLE IF NOT EXISTS score (id INTEGER PRIMARY KEY AUTOINCREMENT, valor INTEGER)");

        holder.imageView.setImageResource(cartaAtual.getmImageResource());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (cartasSelecionadas.size() < 3) {
                    Animation animation = AnimationUtils.loadAnimation(holder.contexto, R.anim.bounce);
                    holder.imageView.startAnimation(animation);

                    String nomeCarta = cartaAtual.getmCartaNome();
                    if (holder.imageView.getImageAlpha() == 255) {
                        holder.imageView.setImageAlpha(100);
                        cartasSelecionadas.add(nomeCarta);
                    } else {
                        holder.imageView.setImageAlpha(255);
                        cartasSelecionadas.remove(nomeCarta);
                    }

                    if (cartasSelecionadas.size() == 3) {
                        String card1 = cartasSelecionadas.get(0);
                        String card11 = card1.substring(1, 2);
                        String card12 = card1.substring(2, 3);
                        String card13 = card1.substring(3, 4);
                        String card14 = card1.substring(4, 5);

                        String card2 = cartasSelecionadas.get(1);
                        String card21 = card2.substring(1, 2);
                        String card22 = card2.substring(2, 3);
                        String card23 = card2.substring(3, 4);
                        String card24 = card2.substring(4, 5);

                        String card3 = cartasSelecionadas.get(2);
                        String card31 = card3.substring(1, 2);
                        String card32 = card3.substring(2, 3);
                        String card33 = card3.substring(3, 4);
                        String card34 = card3.substring(4, 5);

                        if (
                                ((card11.equals(card21) & card21.equals(card31)) & card11.equals(card31) | (!card11.equals(card21) & !card21.equals(card31) & !card11.equals(card31))) &
                                ((card12.equals(card22) & card22.equals(card32)) & card12.equals(card32) | (!card12.equals(card22) & !card22.equals(card32) & !card12.equals(card32))) &
                                ((card13.equals(card23) & card23.equals(card33)) & card13.equals(card33) | (!card13.equals(card23) & !card23.equals(card33) & !card13.equals(card33))) &
                                ((card14.equals(card24) & card24.equals(card34)) & card14.equals(card34) | (!card14.equals(card24) & !card24.equals(card34) & !card14.equals(card34)))
                        ) {
                            Toast.makeText(holder.imageView.getContext(), "SET", Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences = holder.contexto.getSharedPreferences("SCORE", MODE_PRIVATE);
                            int scoreDb = preferences.getInt("score", 0);
                            SharedPreferences.Editor editor = holder.contexto.getSharedPreferences("SCORE", MODE_PRIVATE).edit();

                            banco.execSQL("INSERT INTO score (valor) VALUES ('"+(scoreDb+10)+"')");
                            banco.close();
                            editor.putInt("score", scoreDb+10);
                            editor.putInt("melhorScore", scoreDb+10);
                            editor.apply();

                            Intent intent = new Intent(holder.contexto, MainActivity.class);
                            holder.contexto.startActivity(intent);
                        } else {
                            Toast.makeText(holder.imageView.getContext(), "OPS - Tente novamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(holder.contexto, MainActivity.class);
                            SharedPreferences preferences = holder.contexto.getSharedPreferences("SCORE", MODE_PRIVATE);

                            int scoreDb = preferences.getInt("score", 0);
                            SharedPreferences.Editor editor = holder.contexto.getSharedPreferences("SCORE", MODE_PRIVATE).edit();

                            if (preferences.getInt("score", 0) <= 0) {
                                editor.putInt("score", 0);
                            } else {
                                banco.execSQL("INSERT INTO score (valor) VALUES ('"+(scoreDb-10)+"')");
                                banco.close();
                                editor.putInt("score", scoreDb-10);
                            }

                            editor.apply();

                            holder.contexto.startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartaLista.size();
    }
}
