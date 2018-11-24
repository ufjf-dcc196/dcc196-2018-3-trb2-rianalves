package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class ParticipanteActivity extends AppCompatActivity {

    private static final int REQUEST_INSEREPARTICIPANTE = 1;

    private Button btnInserirParticipante;
    private RecyclerView rclListaParticipante;
    private TextView txtTotalP;

    private ParticipanteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante);

        btnInserirParticipante = (Button) findViewById(R.id.btn_inserirParticipante);
        txtTotalP =(TextView) findViewById(R.id.txt_totalP);

        rclListaParticipante = (RecyclerView) findViewById(R.id.rcl_Participantes);
        rclListaParticipante.setLayoutManager(new LinearLayoutManager(this));
        rclListaParticipante.setAdapter(new ParticipanteAdapter(DAO.getParticipanteInstance()));

        adapter = new ParticipanteAdapter(DAO.getParticipanteInstance());
        adapter.setOnClickListener(new ParticipanteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ParticipanteActivity.this, ParticipanteDetalhesActivity.class);
                Participante participante = (Participante) DAO.getParticipanteInstance().get(position);
                intent.putExtra("participante", participante);
                startActivity(intent);
            }
        });

        rclListaParticipante.setAdapter(adapter);

        int total = DAO.getParticipanteInstance().size();
        txtTotalP.setText("Total de Participantes: " + total);

        btnInserirParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteActivity.this, InserirParticipanteActivity.class);
                startActivityForResult(intent, ParticipanteActivity.REQUEST_INSEREPARTICIPANTE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ParticipanteActivity.REQUEST_INSEREPARTICIPANTE && resultCode == Activity.RESULT_OK){
            int total = DAO.getParticipanteInstance().size();
            txtTotalP.setText("Total de Participantes: " + total);
        }
        adapter.notifyDataSetChanged();
    }
}
