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

import dcc196.ufjf.br.semanac.Adapter.EventoAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class EventoActivity extends AppCompatActivity {
    private static final int REQUEST_CADEVENTO = 1;

    private Button btnCadastrarEvento;
    private RecyclerView rvListaEventos;
    private TextView txtTotalEventos;
    private EventoAdapter adapter;

    private int totalEventos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);btnCadastrarEvento = findViewById(R.id.btn_cadastrarEvento);
        rvListaEventos = findViewById(R.id.rv_listaEventos);
        txtTotalEventos = findViewById(R.id.txt_totalEventos);

        rvListaEventos = (RecyclerView) findViewById(R.id.rv_listaEventos);
        rvListaEventos.setLayoutManager(new LinearLayoutManager(this));
        rvListaEventos.setAdapter(new EventoAdapter(DAO.getEventoInstance()));

        adapter = new EventoAdapter(DAO.getEventoInstance());
        adapter.setOnClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(EventoActivity.this, EventoDetlhesActivity.class);
                Evento e = (Evento) DAO.getEventoInstance().get(position);
                intent.putExtra("evento", e);
                startActivity(intent);
            }
        });
        rvListaEventos.setAdapter(adapter);


        int total = DAO.getEventoInstance().size();

        txtTotalEventos.setText("Total de Eventos: " + total);


        btnCadastrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventoActivity.this, CadastroEventoActivity.class);
                startActivityForResult(intent, EventoActivity.REQUEST_CADEVENTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EventoActivity.REQUEST_CADEVENTO && resultCode == Activity.RESULT_OK){
            int total = DAO.getEventoInstance().size();
            txtTotalEventos.setText("Total de Eventos: " + total);
        }
        adapter.notifyDataSetChanged();
    }
}
