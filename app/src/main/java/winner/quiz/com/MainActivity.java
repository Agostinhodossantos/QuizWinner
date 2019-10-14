package winner.quiz.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import winner.quiz.com.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText edtNome  ,edtEmail;
    ListView list_dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<Pessoa> pessoaList = new ArrayList<>();
    private ArrayAdapter<Pessoa> pessoaArrayAdapter ;

   // private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //admob
//
//        MobileAds.initialize(MainActivity.this, getString(R.string.code_admob));
////        mAdView = findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId(getString(R.string.code_admob));




        edtEmail = findViewById(R.id.edit_email);
        edtNome = findViewById(R.id.edit_nome);
        list_dados = findViewById(R.id.list);

        inicializarFirebase();

        enventoDataBase();
    }

    private void enventoDataBase() {
        databaseReference.child("Pessoa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pessoaList.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    pessoaList.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if(id == R.id.menu_novo){
             Pessoa pessoa = new Pessoa();
             pessoa.setUid(UUID.randomUUID().toString());
             pessoa.setNome(edtNome.getText().toString());
             pessoa.setEmail(edtEmail.getText().toString());

             databaseReference.child("Pessoa").child(pessoa.getUid()).setValue(pessoa);
             databaseReference.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     Toast.makeText(MainActivity.this, "inserido com sucesso", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });

             databaseReference.addChildEventListener(new ChildEventListener() {
                 @Override
                 public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     Toast.makeText(MainActivity.this, "added", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     Toast.makeText(MainActivity.this, "changed", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                     Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     Toast.makeText(MainActivity.this, "moved", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });


             limparCampos();
         }
        return true;
    }

    private void limparCampos() {
        edtNome.setText("");
        edtEmail.setText("");
    }
}
