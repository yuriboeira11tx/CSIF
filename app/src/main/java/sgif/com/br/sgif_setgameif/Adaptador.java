package sgif.com.br.sgif_setgameif;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {
    private List<Usuario> listaUsuarios;

    public Adaptador(List<Usuario> lista) {
        this.listaUsuarios = lista;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeUsuario;
        TextView score;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomeUsuario = itemView.findViewById(R.id.nomeUsuario);
            score = itemView.findViewById(R.id.score);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adaptador, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.nomeUsuario.setText(usuario.getNome());
        holder.score.setText(String.valueOf(usuario.getScore()));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }
}
