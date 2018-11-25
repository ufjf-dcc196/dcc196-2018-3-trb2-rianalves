package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class InserirParticipanteActivity extends AppCompatActivity {
    private Button btnInserirCadastro;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCPF;
    private SemanaDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_participante);

        dbHelper = new SemanaDBHelper(getApplicationContext());


        btnInserirCadastro = findViewById(R.id.btn_confirmaCadPart);
        txtNome = (EditText) findViewById(R.id.txt_nomeParticipante);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtCPF = (EditText) findViewById(R.id.txt_cpf);

        btnInserirCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE,txtNome.getText().toString());
                valores.put(SemanaContract.Participante.COLUMN_NAME_EMAIL,txtEmail.getText().toString());
                valores.put(SemanaContract.Participante.COLUMN_NAME_CPF,txtCPF.getText().toString());
                long id = db.insert(SemanaContract.Participante.TABLE_NAME,null, valores);
                Log.i("DBINFO", "registro criado com id: "+id);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
