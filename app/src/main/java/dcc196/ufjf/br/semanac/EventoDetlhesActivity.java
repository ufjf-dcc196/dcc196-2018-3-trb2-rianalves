package dcc196.ufjf.br.semanac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class EventoDetlhesActivity extends AppCompatActivity {

    private Evento recuperado;
    private RecyclerView rv_ListaParticipantesEvento;
    private TextView nomeEvento;
    private TextView localEvento;
    private TextView dataEvento;
    private TextView facilitadorEvento;
    private TextView vagasEvento;
    private TextView inscritosEvento;
    private TextView descricaoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detlhes);

        nomeEvento = findViewById(R.id.txtNomeEventoInformacao);
        localEvento = findViewById(R.id.txtLocalEventoInformacao);
        dataEvento = findViewById(R.id.txtDataEventoInformacao);
        facilitadorEvento = findViewById(R.id.txtFacilitadorEventoInformacao);
        vagasEvento = findViewById(R.id.txtVagasEventoInformacao);
        inscritosEvento = findViewById(R.id.txtInscritosEventoInformacao);
        descricaoEvento = findViewById(R.id.txtDescricaoEventoInformacao);
        rv_ListaParticipantesEvento = findViewById(R.id.rv_ListaDeInscritosEventoInformacao);


        Intent intent = getIntent();
        recuperado = (Evento) intent.getSerializableExtra("evento");
        preencheInfos(recuperado);

        rv_ListaParticipantesEvento.setLayoutManager(new LinearLayoutManager(this));
        rv_ListaParticipantesEvento.setAdapter(new ParticipanteAdapter(recuperado.getParticipanteList()));

    }

    public void preencheInfos(Evento evento)
    {
        nomeEvento.setText("Nome: " + evento.getNome());
        localEvento.setText("Local: " + evento.getLocal());
        Calendar c = evento.getDataEvento();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String data = sdf.format(c.getTime());
        dataEvento.setText("Data: " + data);
        facilitadorEvento.setText("Facilitador: " + evento.getFacilitador());
        vagasEvento.setText("Vagas: " + String.valueOf(evento.getNumMaximoInscritos()));
        inscritosEvento.setText("Inscritos: " + String.valueOf(evento.getNumInscritos()));
        descricaoEvento.setText("Descrição: " + evento.getDescricao());
    }
}
