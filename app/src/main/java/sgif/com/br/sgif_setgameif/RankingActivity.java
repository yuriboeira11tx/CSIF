package sgif.com.br.sgif_setgameif;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {
    private ListView listView;
    private RequestQueue requestQueue;
    private ArrayList<String> addPontos = new ArrayList<String>();
    private ArrayList<String> nomes = new ArrayList<String>();
    private ArrayList<Integer> arrayScore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ranking);

        listView = findViewById(R.id.listaScores);
        requestQueue = Volley.newRequestQueue(RankingActivity.this);

        checkUser();
        jsonParse();
    }

    public void jsonParse() {
        String url = "http://app.vacaria.ifrs.edu.br/api/score/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("score");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject lacos = jsonArray.getJSONObject(i);

                        String nome = lacos.getString("nome");
                        String ponto = lacos.getString("pontos");

                        addPontos.add("      "+ nome +"   -   "+ ponto);
                        nomes.add(nome);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RankingActivity.this, R.layout.item, addPontos);
                        listView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private void makeJsonObjReq() {
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

        SharedPreferences sharedPreferences = getSharedPreferences("SCORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("SCORE", MODE_PRIVATE).edit();

        String url = "http://app.vacaria.ifrs.edu.br/api/score/";

        if (!arrayScore.isEmpty()) {
            editor.putInt("melhorScore", Collections.max(arrayScore));
            editor.apply();
        }

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("id_usr", sharedPreferences.getString("id", "0"));
        postParam.put("nome", sharedPreferences.getString("nome_usr", "Erro"));
        postParam.put("pontos", String.valueOf(sharedPreferences.getInt("melhorScore", 0)));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AA", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("AA", "Error: " + error.getMessage());
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setTag("AA");
        requestQueue.add(jsonObjReq);
    }

    public void checkUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("SCORE", MODE_PRIVATE);

        if (sharedPreferences.getString("id", "0").equals("0")) {
            createUser();
        } else {
            makeJsonObjReq();
        }
    }

    public void createUser() {
        final SharedPreferences.Editor editor = getSharedPreferences("SCORE", MODE_PRIVATE).edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(RankingActivity.this);
        builder.setTitle("Lugar no Ranking");
        builder.setIcon(R.drawable.pontos);
        builder.setMessage("Para participar do ranking mundial forneça um nome para sua identificação");

        final String[] nomeUsuario = new String[1];
        final EditText input = new EditText(RankingActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nomeUsuario[0] = input.getText().toString();

                if (nomes.contains(nomeUsuario[0])) {
                    Toast.makeText(RankingActivity.this, "Nome já existente!", Toast.LENGTH_LONG).show();
                    createUser();
                } else {
                    editor.putString("id", String.valueOf(nomes.size() + 1));
                    editor.putString("nome_usr", nomeUsuario[0]);
                    editor.apply();

                    Toast.makeText(RankingActivity.this, "Bem vindo ao Ranking Mundial do CSIF!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
