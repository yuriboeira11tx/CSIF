package sgif.com.br.sgif_setgameif;

public class Usuario implements Comparable<Usuario> {
    private String nome;
    private int score;

    public Usuario() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Usuario outroAluno) {
        return Integer.compare(outroAluno.getScore(), this.score);
    }
}
