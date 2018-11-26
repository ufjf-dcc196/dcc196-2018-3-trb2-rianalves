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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class InserirEventoActivity extends AppCompatActivity {
    private Button btnConfirmaCadEvento;
    private EditText txtTitulo;
    private EditText txtLocal;
    private EditText txtData;
    private EditText txtNumMaximoInscritos;
    private EditText txtFacilitador;
    private EditText txtDescicao;
    private SemanaDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        dbHelper = new SemanaDBHelper(getApplicationContext());

        btnConfirmaCadEvento = findViewById(R.id.btn_confirmaCadEvento);
        txtTitulo = findViewById(R.id.txt_titulo);
        txtLocal = findViewById(R.id.txt_local);
        txtData = findViewById(R.id.txt_dataHora);
        txtNumMaximoInscritos = findViewById(R.id.txt_NumMaxInscritos);
        txtFacilitador = findViewById(R.id.txt_facilitador);
        txtDescicao = findViewById(R.id.txt_descricao);

        btnConfirmaCadEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = txtData.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(sdf.parse(data));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(SemanaContract.Evento.COLUMN_NAME_TITULO,txtTitulo.getText().toString());
                valores.put(SemanaContract.Evento.COLUMN_NAME_DESCRICAO,txtDescicao.getText().toString());
                valores.put(SemanaContract.Evento.COLUMN_NAME_DATA, String.valueOf(cal));
                valores.put(SemanaContract.Evento.COLUMN_NAME_FACILITADOR,txtFacilitador.getText().toString());
                valores.put(SemanaContract.Evento.COLUMN_NAME_LOCAL,txtLocal.getText().toString());
                valores.put(SemanaContract.Evento.COLUMN_NAME_LOTACAO,txtNumMaximoInscritos.getText().toString());
                long id = db.insert(SemanaContract.Evento.TABLE_NAME,null, valores);
                Log.i("DBINFO", "registro criado com id: "+id);
                setResult(Activity.RESULT_OK);
                finish();


            }
        });
    }
}
