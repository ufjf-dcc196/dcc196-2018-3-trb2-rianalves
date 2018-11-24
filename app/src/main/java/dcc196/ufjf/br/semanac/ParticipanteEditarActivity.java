package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dcc196.ufjf.br.semanac.Modelo.Participante;

public class ParticipanteEditarActivity extends AppCompatActivity {
    private Participante participante;
    private Button cnfEdicao;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_editar);

        final Intent intent = getIntent();
        participante = (Participante) intent.getSerializableExtra("participante");

        cnfEdicao = findViewById(R.id.btnConfirmarParticipanteEdit);
        txtNome = findViewById(R.id.editTextNomeParticipanteEdit);
        txtEmail = findViewById(R.id.editTextEmailParticipanteEdit);
        txtCPF = findViewById(R.id.editTextCPFParticipanteEdit);
        txtNome.setText(participante.getNome());
        txtEmail.setText(participante.getEmail());
        txtCPF.setText(participante.getCpf());

        cnfEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultado = new Intent();
                Participante p = new Participante();
                p.setNome(txtNome.getText().toString());
                p.setEmail(txtEmail.getText().toString());
                p.setCpf(txtCPF.getText().toString());
                resultado.putExtra("participante", p);
                setResult(Activity.RESULT_OK, resultado);
                finish();
            }
        });
    }
}
