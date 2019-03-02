package sgif.com.br.sgif_setgameif;

public class Carta {
    private int mImageResource;
    private String mCartaNome;

    public Carta(int imageResource, String cartaNome) {
        this.mImageResource = imageResource;
        this.mCartaNome = cartaNome;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmCartaNome() {
        return mCartaNome;
    }

    public void setmCartaNome(String mCartaNome) {
        this.mCartaNome = mCartaNome;
    }
}
