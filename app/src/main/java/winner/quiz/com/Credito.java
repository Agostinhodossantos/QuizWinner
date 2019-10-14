package winner.quiz.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import winner.quiz.com.model.CreditoModel;
import winner.quiz.com.model.CreditoModelDb;
import winner.quiz.com.model.Jogador;
import winner.quiz.com.rv.Rv_credito;

public class Credito extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btn_compartilhar, btn_sacar;
    private TextView tv_credito, tv_pontos;
    private RecyclerView rv_credito;
    private static int credito = 0;
    private View mView;
    private AlertDialog dialog;
    private String rede;
    private int position;
    private String strRenferencia;
    private List<Jogador> jogadorList = new ArrayList<>();
    private List<CreditoModelDb> creditoListDb = new ArrayList<>();
    private List<CreditoModel> creditoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito);
        inicializarUi();
        pegarDados();
        inicializarFirebase();
        position =0;
        buscarDados();
        buscarCredito();
        atualizarRecargar();


        btn_sacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacar();
            }
        });

        btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnviarApp();
            }
        });
    }

    private void sacar() {
        rede = "";
        verificarSaldo();
        if (verificarSaldo()) {

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            mView = getLayoutInflater().inflate(R.layout.dialog_escolher_rede, null);

            mBuilder.setView(mView);
            dialog = mBuilder.create();
            dialog.setCancelable(true);
            dialog.show();

            ImageView btn_vodacom = mView.findViewById(R.id.btn_vodacom);
            ImageView btn_movitel = mView.findViewById(R.id.btn_movitel);
            ImageView btn_tmecl = mView.findViewById(R.id.btn_tmcel);

            btn_vodacom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rede = "vodacom";
                    dialog.dismiss();
                    enviarCodigo();
                }
            });

            btn_movitel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rede = "movitel";
                    dialog.dismiss();
                    enviarCodigo();
                }
            });

            btn_tmecl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rede = "tmcel";
                    dialog.dismiss();
                    enviarCodigo();
                }
            });


        } else {

            Toast.makeText(Credito.this, R.string.falha_tente_novamente, Toast.LENGTH_SHORT).show();

        }
    }

    private void enviarCodigo() {
        buscarCreditoNaoEnviados();


        //todo// //envirar codigo //


    }

    private void atualizarRecargar() {

       // buscarCredito();
       rv_credito = findViewById(R.id.rv_credito);
       rv_credito.setLayoutManager(new LinearLayoutManager(this));
       Rv_credito adapter = new Rv_credito(Credito.this , creditoList);
       rv_credito.setAdapter(adapter);

        Toast.makeText(this, ""+creditoList.size(), Toast.LENGTH_SHORT).show();

    }

    private void buscarCredito() {

        position = 0;
        databaseReference.child("Jogador").child(strRenferencia).child("Recarga").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                creditoList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    position++;
                    CreditoModel credito = objSnapshot.getValue(CreditoModel.class);
                    creditoList.add(credito);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
 atualizarRecargar();


    }

    private void buscarCreditoNaoEnviados() {

        position = 0;
        databaseReference.child("Credito").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    position++;
                    CreditoModelDb credito = objSnapshot.getValue(CreditoModelDb.class);
                    creditoListDb.add(credito);

                    if (true){
                        String codigoDarecarga = credito.getRecarga();
                        //todo// pegar recarga mostrar na tela//

                        final CreditoModel model = new CreditoModel();
                        model.setUid(UUID.randomUUID().toString());
                        model.setRede(rede);
                        model.setRecarga(codigoDarecarga);
                        databaseReference.child("Jogador").child(strRenferencia).child("Recarga").child(model.getUid()).setValue(model);
                        databaseReference.child("Jogador").child(strRenferencia).child("Recarga").child(model.getUid()).removeValue();
                        ///todo
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean verificarSaldo() {
        if (credito <= 10) {
            return true;
        } else {
            return false;
        }
    }

    private void inicializarUi() {
        btn_compartilhar = findViewById(R.id.btn_partilhar);
        btn_sacar = findViewById(R.id.btn_sacar);
        tv_credito = findViewById(R.id.tv_credito);
        tv_pontos = findViewById(R.id.tv_pontos);
        atualizarRecargar();
    }

    private void inicializarFirebase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void buscarDados() {
        //todo//

        databaseReference.child("Jogador").addValueEventListener(new ValueEventListener() {
             int  position_1 = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    position_1++;
                    Jogador jogador = objSnapshot.getValue(Jogador.class);
                    jogadorList.add(jogador);

                    if (jogador.getUid().trim().equals(strRenferencia)) {
                        tv_credito.setText(jogadorList.get(position_1 - 1).getCredito());
                        tv_pontos.setText(jogadorList.get(position_1 - 1).getPontos());
                        credito = Integer.parseInt(jogadorList.get(position_1 - 1).getPontos());
                        break;
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void pegarDados() {
        Intent intent  =  getIntent();
        if (intent != null){
            strRenferencia = intent.getStringExtra("Jogador");
        }else {
            Intent intent1 =  new Intent(this , Home.class);
            startActivity(intent1);
            Toast.makeText(this, "falha", Toast.LENGTH_SHORT).show();
        }

    }

    private void EnviarApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        String message = "\nLet me recommend you this application *Your app link* \n\n";

        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "choose one"));
    }
}