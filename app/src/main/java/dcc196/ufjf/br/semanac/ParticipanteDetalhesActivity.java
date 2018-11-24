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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.Adapter.ParticipanteDetalhesAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.Modelo.Evento;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class ParticipanteDetalhesActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT = 1;

    private Participante participante;

    private Button btnEditar;
    private RecyclerView rclEventosInscritos;
    private RecyclerView rvListaEventosNaoInscrito;
    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtCPF;
    private TextView txtEventoCadastrados;
    private TextView txtEventosNaoCadastrados;
    private ParticipanteDetalhesAdapter adapter;
    private ParticipanteDetalhesAdapter adapter2;
    private List<Evento> eventosInscritos;
    private List<Evento> eventosTodos;
    private List<Evento> eventosDisponiveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_detalhes);

        btnEditar = (Button) findViewById(R.id.btn_EditarParticipante);
        txtNome = findViewById(R.id.txt_NomeView);
        txtCPF = findViewById(R.id.txt_CPFView);
        txtEmail = findViewById(R.id.txt_EmailView);

        rclEventosInscritos = (RecyclerView) findViewById(R.id.rcl_EventosInscritos);
        rvListaEventosNaoInscrito = (RecyclerView) findViewById(R.id.rcl_Eventos);

        Intent intent = getIntent();
        participante = (Participante) intent.getSerializableExtra("participante");

        txtNome.setText("Nome: " +participante.getNome());
        txtEmail.setText("Email: " + participante.getEmail());
        txtCPF.setText("CPF: " + participante.getCpf());

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipanteDetalhesActivity.this, ParticipanteEditarActivity.class);
                intent.putExtra("participante", participante);
                startActivityForResult(intent, ParticipanteDetalhesActivity.REQUEST_EDIT);
            }
        });

        for (int i = 0; i < DAO.getParticipanteInstance().size(); i++)
        {
            List<Participante> parts = DAO.getParticipanteInstance();
            if (parts.get(i).getCpf().equals(participante.getCpf()))
            {
                participante = parts.get(i);
                break;
            }
        }

        eventosInscritos = participante.getEventos();
        eventosTodos = DAO.getEventoInstance();

        rclEventosInscritos.setLayoutManager(new LinearLayoutManager(this));
        rvListaEventosNaoInscrito.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ParticipanteDetalhesAdapter(eventosInscritos);
        if (participante.getEventos().size() == 0)
        {
            adapter2 = new ParticipanteDetalhesAdapter(DAO.getEventoInstance());
            eventosDisponiveis = DAO.getEventoInstance();
        }
        else
        {
            List<Evento> eventos3 = new ArrayList<>();
            for (int i = 0; i < DAO.getEventoInstance().size(); i++)
            {
                Boolean inserir = true;
                for (int j = 0; j < participante.getEventos().size(); j++)
                {
                    if (eventosTodos.get(i).getNome().equals(participante.getEventos().get(j).getNome()))
                    {
                        inserir = false;
                    }
                }
                if (inserir)
                {
                    eventos3.add(eventosTodos.get(i));
                }
            }
            eventosDisponiveis = eventos3;
            adapter2 = new ParticipanteDetalhesAdapter(eventosDisponiveis);
        }

    //Clique longo
        adapter.setOnParticipanteLongClickListener(new ParticipanteDetalhesAdapter.OnParticipanteLongClickListener() {
            @Override
            public void onParticipanteLongClick(View participanteView, int position) {
                List<Participante> partis = new ArrayList<>();
                Evento e = eventosInscritos.get(position);
                e.setNumInscritos(e.getNumInscritos() - 1);
                for (int i = 0; i < e.getParticipanteList().size(); i++)
                {
                    if (!e.getParticipanteList().get(i).getCpf().equals(participante.getCpf()))
                    {
                        //não está. Pode ser adicionado
                        partis.add(e.getParticipanteList().get(i));
                    }
                }
                e.setParticipanteList(partis);//adicionamos o participante ao evento
                List <Evento> eventosInscritos2 = new ArrayList<>();
                for (int i = 0; i < eventosInscritos.size(); i++)
                {
                    if (i != position)
                    {
                        eventosInscritos2.add(eventosInscritos.get(i));
                    }
                }
                eventosInscritos = eventosInscritos2;
                participante.setEventos(eventosInscritos);
                adapter.setEventos(eventosInscritos);
                adapter.notifyDataSetChanged();
                eventosDisponiveis = new ArrayList<>();
                for (int i = 0; i < DAO.getEventoInstance().size(); i++)
                {
                    Boolean inserir = true;
                    for (int j = 0; j < participante.getEventos().size(); j++)
                    {
                        if (eventosTodos.get(i).getNome().equals(participante.getEventos().get(j).getNome()))
                        {
                            inserir = false;
                        }
                    }
                    if (inserir)
                    {
                        eventosDisponiveis.add(eventosTodos.get(i));
                    }
                }
                adapter2.setEventos(eventosDisponiveis);
                adapter2.notifyDataSetChanged();
                return;
            }
        });
        rclEventosInscritos.setAdapter(adapter);

  //Todos os eventos
        adapter2.setOnParticipanteLongClickListener(new ParticipanteDetalhesAdapter.OnParticipanteLongClickListener() {
            @Override
            public void onParticipanteLongClick(View participanteView, int position) {
                if (eventosDisponiveis.get(position).getNumInscritos()+1 <= eventosDisponiveis.get(position).getNumMaximoInscritos())
                {
                    eventosDisponiveis.get(position).setNumInscritos(eventosDisponiveis.get(position).getNumInscritos() + 1);
                    participante.getEventos().add(eventosDisponiveis.get(position));
                    eventosDisponiveis.get(position).getParticipanteList().add(participante);
                    eventosDisponiveis = new ArrayList<>();
                    for (int i = 0; i < DAO.getEventoInstance().size(); i++)
                    {
                        Boolean inserir = true;
                        for (int j = 0; j < participante.getEventos().size(); j++)
                        {
                            //Verificar se os participantes já tão inscritos no Evento
                            if (eventosTodos.get(i).getNome().equals(participante.getEventos().get(j).getNome()))
                            {
                                inserir = false;
                            }
                        }
                        if (inserir)
                        {
                            eventosDisponiveis.add(eventosTodos.get(i));
                        }
                    }
                    adapter2.setEventos(eventosDisponiveis);
                    adapter2.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Evento já está lotado", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
        rvListaEventosNaoInscrito.setAdapter(adapter2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ParticipanteDetalhesActivity.REQUEST_EDIT && resultCode == Activity.RESULT_OK){
            Participante p = (Participante) data.getSerializableExtra("participante");
            List<Participante> participanteList = DAO.getParticipanteInstance();
            for (Participante parts: participanteList) {
                if (parts.getNome().equals(participante.getNome()) && parts.getCpf().equals(participante.getCpf()) && parts.getEmail().equals(participante.getEmail()))
                {
                    parts.setNome(p.getNome());
                    parts.setCpf(p.getCpf());
                    parts.setEmail(p.getEmail());
                    txtNome.setText("Nome: " +p.getNome());
                    txtEmail.setText("Email: " + p.getEmail());
                    txtCPF.setText("CPF: " + p.getCpf());
                    break;
                }
            }
        }
    }
}
