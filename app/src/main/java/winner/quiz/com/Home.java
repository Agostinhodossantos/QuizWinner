package winner.quiz.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import winner.quiz.com.model.Jogador;

public class Home extends AppCompatActivity {

    Button btn_comecar, btn_sobre , btn_meu_credito;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences prefs;
    final static String APP_PREFS = "app_prefs";
    final static String USERNAME_KEY = "username";
    private String username;
    private View mView;
    private AlertDialog dialog;
    private int clicouMen = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefs = getSharedPreferences(APP_PREFS , MODE_PRIVATE);
        username = prefs.getString(USERNAME_KEY , null);


        btn_comecar = findViewById(R.id.start);
        btn_meu_credito = findViewById(R.id.credit);
        btn_sobre = findViewById(R.id.about);

        inicializarFirebase();

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);


        btn_comecar.setAnimation(animation);
        btn_comecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMen = 1;
               verificaConexao();


            }
        });

        btn_sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this , MainManager.class);
                startActivity(intent);
            }
        });
        btn_meu_credito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMen = 2;
               verificaConexao();
            }
        });

    }

    private void verificarUserLogin() {

        if (username != null) {

            if (clicouMen == 1){
                Intent intent = new Intent(Home.this , Jogo.class);
                intent.putExtra("Jogador" , username);
                startActivity(intent);
            }else if (clicouMen == 2){
                Intent intent = new Intent(Home.this , Credito.class);
                intent.putExtra("Jogador" , username);
                startActivity(intent);
            }


        } else {

            final Jogador jogador = new Jogador();
            jogador.setCredito("0");
            jogador.setPontos("0");
            jogador.setUid(UUID.randomUUID().toString());

            databaseReference.child("Jogador").child(jogador.getUid()).setValue(jogador);
            Toast.makeText(Home.this, "logado", Toast.LENGTH_SHORT).show();
            username = jogador.getUid();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(USERNAME_KEY , username);
            editor.commit();

           if (clicouMen == 1){
               Intent intent = new Intent(Home.this , Jogo.class);
               intent.putExtra("Jogador" , username);
               startActivity(intent);
           }else if (clicouMen == 2){
               Intent intent = new Intent(Home.this , Credito.class);
               intent.putExtra("Jogador" , username);
               startActivity(intent);
           }

        }

    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


   public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable() &&
                conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
            verificarUserLogin();
        } else {
            conectado = false;
            tentarConectar();
        }
        return conectado;
    }

    private void tentarConectar() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Home.this);
        mView = getLayoutInflater().inflate(R.layout.dialognointernet, null);

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        ImageView btn_cancelar = mView.findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btn_retentar = mView.findViewById(R.id.btn_id_reiniciar);
        btn_retentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verificaConexao();
                dialog.dismiss();

            }
        });

    }


}
