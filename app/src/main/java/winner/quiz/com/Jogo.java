package winner.quiz.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import winner.quiz.com.model.Jogador;
import winner.quiz.com.model.Pergunta;

public class Jogo extends AppCompatActivity implements RewardedVideoAdListener {

    private Button btn_resposta1, btn_resposta2, btn_resposta3, btn_resposta4;
    private TextView tv_ponto, tv_credito, tv_pergunta;
    private ImageView img_ponto1, img_ponto2, img_ponto3;
    private List<Pergunta> perguntaList = new ArrayList<>();
    private int contar;
    private int clicouMe = 0;
    private AdView adView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String strRenferencia;
    private List<Jogador> jogadorList = new ArrayList<>();
    private int pontos, credito, pontoDb;
    private int position;
    private int contarErros = 0;
    private View mView;
    private AlertDialog dialog;
    private int realPosition;
    private CardView card_ad_video, card_ad_full, card_compartilhar;
    private static final int valorDeVideo = 2;
    private static final int valorCompartilhar = 1;
    private static final int valorAnucioFull = 1;
    private static String AD_UNIT_ID;
    private RewardedVideoAd rewardedVideoAd;
    // 1014 / 1000 //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        inicializarUI();
        pegarDados();
        setContar(0);
        MultiDex.install(this);
        inicializarFirebase();
        inicializarPerguntas();
        controlarAnucios();
        AD_UNIT_ID = getString(R.string.admob_reward_test);


        //admob//

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        MobileAds.initialize(this, getString(R.string.admob_app_id));


        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        btn_resposta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMe = 1;
                avaliarResposta();
                inserirPerguntasUi();
                realPosition = getContar();
                setContar(1);


            }
        });
        btn_resposta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMe = 2;
                avaliarResposta();
                inserirPerguntasUi();
                realPosition = getContar();
                setContar(1);
            }
        });
        btn_resposta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMe = 3;
                avaliarResposta();
                inserirPerguntasUi();
                realPosition = getContar();
                setContar(1);
            }
        });
        btn_resposta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicouMe = 4;
                avaliarResposta();
                inserirPerguntasUi();
                realPosition = getContar();
                setContar(1);
            }
        });

    }

    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }


    private void controlarAnucios() {


        if(pontoDb == 1){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 5){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 10){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 20){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 25){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 30){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 40){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 50){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 70){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 75){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if (pontoDb == 80){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 85){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 90){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb == 100){

            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }

        }else if(pontoDb > 100){
            if (rewardedVideoAd.isLoaded()) {
                card_ad_video.setVisibility(View.VISIBLE);
            }
        }




//        if (pontoDb == 5) {
//            card_compartilhar.setVisibility(View.VISIBLE);
//        } else if (pontoDb < 6) {
//            card_ad_video.setVisibility(View.VISIBLE);
//        } else if (pontoDb == 7) {
//            card_ad_full.setVisibility(View.VISIBLE);
//
//        } else {
//
//        }
//

        card_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo enviar app nas rede social//
                card_compartilhar.setVisibility(View.INVISIBLE);
                EnviarApp();
            }
        });

        card_ad_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_ad_full.setVisibility(View.INVISIBLE);
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });

        card_ad_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_ad_video.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void pegarDados() {
        Intent intent = getIntent();
        if (intent != null) {
            strRenferencia = intent.getStringExtra("Jogador");
        } else {
            Intent intent1 = new Intent(Jogo.this, Home.class);
            startActivity(intent1);
            Toast.makeText(this, "falha", Toast.LENGTH_SHORT).show();
        }

    }

    private void avaliarResposta() {

        switch (clicouMe) {
            case 1:

                if (btn_resposta1.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
                    pontos = pontos + 1;
                    respostaCerta();
                } else {
                    btn_resposta1.setBackgroundResource(R.drawable.btn_bg_erro);
                    respostaCerta();
                    contarErros++;
                }
                break;
            case 2:

                if (btn_resposta2.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
                    pontos = pontos + 1;
                    respostaCerta();

                } else {
                    btn_resposta2.setBackgroundResource(R.drawable.btn_bg_erro);
                    respostaCerta();
                    contarErros++;
                }
                break;
            case 3:
                Toast.makeText(this, "RespostaCerta" + perguntaList.get(realPosition).getRespostaCerta().trim(), Toast.LENGTH_SHORT).show();
                if (btn_resposta3.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
                    pontos = pontos + 1;
                    respostaCerta();
                } else {
                    btn_resposta3.setBackgroundResource(R.drawable.btn_bg_erro);
                    respostaCerta();
                    contarErros++;
                }
                break;
            case 4:

                if (btn_resposta4.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
                    pontos = pontos + 1;
                    respostaCerta();
                } else {
                    btn_resposta4.setBackgroundResource(R.drawable.btn_bg_erro);
                    contarErros++;
                    respostaCerta();

                }
                break;
            default:

                break;
        }
        redifinirBackground();

    }

    private void respostaCerta() {
        mostrarRespostaCerta();
        actualizarDados();
        buscarDados();
    }

    private void inicializarPerguntas() {


        perguntaList.add(new Pergunta("2 + 2", "2", " 4", "d", "0", "4"));
        perguntaList.add(new Pergunta("4 + 4", "8", " 6", "4", "10", "8"));
        perguntaList.add(new Pergunta("2 + 2", "2", " 4", "d", "0", "4"));
        perguntaList.add(new Pergunta("4 + 4", "8", " 6", "4", "10", "8"));
        perguntaList.add(new Pergunta("2 + 2", "2", " 4", "d", "0", "4"));
        perguntaList.add(new Pergunta("4 + 4", "8", " 6", "4", "10", "8"));
        perguntaList.add(new Pergunta("2 + 2", "2", " 4", "d", "0", "4"));
        perguntaList.add(new Pergunta("4 + 4", "8", " 6", "4", "10", "8"));

        inserirPerguntasUi();
    }

    private void inserirPerguntasUi() {

        if (perguntaList.size() - 1 == getContar()) {
            // fimDoJogo();
            Toast.makeText(this, "Fim", Toast.LENGTH_SHORT).show();
        } else {
            tv_pergunta.setText(perguntaList.get(getContar()).getPergunta());
            btn_resposta1.setText(perguntaList.get(getContar()).getResposta1());
            btn_resposta2.setText(perguntaList.get(getContar()).getResposta2());
            btn_resposta3.setText(perguntaList.get(getContar()).getResposta3());
            btn_resposta4.setText(perguntaList.get(getContar()).getResposta4());


        }

    }

    public int getContar() {
        return contar;
    }

    public void setContar(int contar) {
        if (getContar() - 1 == perguntaList.size() - 1) {
            // fimDoJogo();
        } else {
            actualizarDados();
            this.contar = getContar() + contar;
        }

    }

    private void fimDoJogo() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Jogo.this);
        mView = getLayoutInflater().inflate(R.layout.dialogfim, null);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button btn_reniciar = mView.findViewById(R.id.btn_id_reiniciar);
        btn_reniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reniciarOjogo();
            }
        });

    }

    private void reniciarOjogo() {

        setContar(0);
        contarErros = 0;
        respostaCerta();
        img_ponto3.setImageResource(R.drawable.btn_bg);
        img_ponto2.setImageResource(R.drawable.btn_bg);
        img_ponto1.setImageResource(R.drawable.btn_bg);

    }

    private void redifinirBackground() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                btn_resposta1.setBackgroundResource(R.drawable.btn_bg);
                btn_resposta2.setBackgroundResource(R.drawable.btn_bg);
                btn_resposta3.setBackgroundResource(R.drawable.btn_bg);
                btn_resposta4.setBackgroundResource(R.drawable.btn_bg);

            }
        }, 900);
    }

    private void inicializarUI() {
        btn_resposta1 = findViewById(R.id.btn_resposta1);
        btn_resposta2 = findViewById(R.id.btn_resposta2);
        btn_resposta3 = findViewById(R.id.btn_resposta3);
        btn_resposta4 = findViewById(R.id.btn_resposta4);
        tv_credito = findViewById(R.id.tv_credito);
        tv_pergunta = findViewById(R.id.tv_pergunta);
        tv_ponto = findViewById(R.id.tv_pontos);
        img_ponto1 = findViewById(R.id.img_ponto1);
        img_ponto2 = findViewById(R.id.img_ponto2);
        img_ponto3 = findViewById(R.id.img_ponto3);
        card_ad_video = findViewById(R.id.card_ad_video);
        card_ad_full = findViewById(R.id.ad_2);
        card_compartilhar = findViewById(R.id.card_compartilhar);
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        buscarDados();
    }

    private void buscarDados() {
        //todo//
        position = 0;
        databaseReference.child("Jogador").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    position++;
                    Jogador jogador = objSnapshot.getValue(Jogador.class);
                    jogadorList.add(jogador);

                    if (jogador.getUid().trim().equals(strRenferencia)) {
                        tv_credito.setText(jogadorList.get(position - 1).getCredito());
                        tv_ponto.setText(jogadorList.get(position - 1).getPontos());
                        pontoDb = Integer.parseInt(jogadorList.get(position - 1).getPontos());

                        break;
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    protected void mostrarRespostaCerta() {

        if (btn_resposta1.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
            btn_resposta1.setBackgroundResource(R.drawable.btn_bg_certo);
        }

        if (btn_resposta2.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
            btn_resposta1.setBackgroundResource(R.drawable.btn_bg_certo);
        }

        if (btn_resposta3.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
            btn_resposta1.setBackgroundResource(R.drawable.btn_bg_certo);
        }

        if (btn_resposta4.getText().toString().trim().equalsIgnoreCase(perguntaList.get(realPosition).getRespostaCerta().trim())) {
            btn_resposta1.setBackgroundResource(R.drawable.btn_bg_certo);
        }

    }

    private void actualizarDados() {

        buscarDados();
        vidas();
        //todo pegar intent id //
        Jogador jogador = new Jogador();
        jogador.setPontos(pontoDb + pontos + "");
        jogador.setCredito(credito + "");
        jogador.setUid(strRenferencia);

        databaseReference.child("Jogador").child(jogador.getUid()).setValue(jogador);


    }

    private void EnviarApp() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        String message = "\nLet me recommend you this application *Your app link* \n\n";

        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "choose one"));
    }

    private void vidas() {

        if (contarErros() == 1) {
            img_ponto1.setImageResource(R.drawable.btn_bg_erro);
        } else if (contarErros == 2) {
            img_ponto2.setImageResource(R.drawable.btn_bg_erro);
        } else if (contarErros() == 3) {
            img_ponto3.setImageResource(R.drawable.btn_bg_erro);
            fimDoJogo();
        }


    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private int contarErros() {

        return contarErros;

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //rewardedVideoAd.show();
        controlarAnucios();

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        credito = credito + 2;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {


    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}

