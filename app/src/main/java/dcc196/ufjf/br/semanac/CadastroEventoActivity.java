package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class CadastroEventoActivity extends AppCompatActivity {
    private Button btnConfirmaCadEvento;
    private EditText txtTitulo;
    private EditText txtLocal;
    private EditText txtData;
    private EditText txtNumMaximoInscritos;
    private EditText txtFacilitador;
    private EditText txtDescicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

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
                Evento evento = new Evento(txtTitulo.getText().toString(), txtLocal.getText().toString(), cal, txtFacilitador.getText().toString(), txtDescicao.getText().toString(), null, Integer.parseInt(txtNumMaximoInscritos.getText().toString()), 0);
                DAO.getEventoInstance().add(evento);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
