package winner.quiz.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import winner.quiz.com.model.CreditoModel;

public class MainManager extends AppCompatActivity {

    Button btn_enviar;
    EditText ed_code;
    CheckBox cb_movitel;
    CheckBox cb_vodacom;
    CheckBox cb_tmcel;
    String code;
    String rede;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        inicializarUi();

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               code = ed_code.getText().toString().trim();
               rede  = "";

                if(cb_movitel.isChecked()){
                    rede = "movitel";
                }else if(cb_vodacom.isChecked()){
                    rede = "vodacom";
                }else if(cb_tmcel.isChecked()){
                    rede = "tmcel";
                }

                if (code != null && rede != null){
                    inserirDados();
                }
            }

            private void inserirDados() {

                CreditoModel creditoModel = new CreditoModel();
                creditoModel.setRecarga(code);
                creditoModel.setRede(rede);
                creditoModel.setUid(UUID.randomUUID().toString());

                databaseReference.child("Credito").child(creditoModel.getUid()).setValue(creditoModel);

            }
        });
    }

    private void inicializarUi() {
        btn_enviar = findViewById(R.id.btn_enviar);
        ed_code = findViewById(R.id.ed_code);
        cb_movitel = findViewById(R.id.cb_movitel);
        cb_vodacom = findViewById(R.id.cb_vodacom);
        cb_tmcel = findViewById(R.id.cb_tmcel);
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
