package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dcc196.ufjf.br.semanac.Adapter.EventoAdapter;
import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.Adapter.ParticipanteDetalhesAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Evento;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class ParticipanteDetalhesActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT = 1;

    private Participante participante;

    private Button btnEditar;
    private RecyclerView rclEventosInscritos;
    private RecyclerView rclEventosNaoInscritos;
    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtCPF;
    private TextView txtEventoCadastrados;
    private TextView txtEventosNaoCadastrados;
    private String participanteEscolhido;
   private Cursor cursor;
   private SemanaDBHelper dbHelper;
    private EventoAdapter adapter;
    private EventoAdapter adapter2;
    private List<Evento> eventosInscritos;
    private List<Evento> eventosTodos;
    private List<Evento> eventosDisponiveis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante_detalhes);

        dbHelper = new SemanaDBHelper(getApplicationContext());

        btnEditar = (Button) findViewById(R.id.btn_EditarParticipante);
        txtNome = findViewById(R.id.txt_NomeView);
        txtCPF = findViewById(R.id.txt_CPFView);
        txtEmail = findViewById(R.id.txt_EmailView);

        rclEventosInscritos = (RecyclerView) findViewById(R.id.rcl_EventosInscritos);
        rclEventosNaoInscritos = (RecyclerView) findViewById(R.id.rcl_Eventos);
        rclEventosNaoInscritos.setLayoutManager(new LinearLayoutManager(this));

        Bundle extra = getIntent().getExtras();
        participante = retornarDadosParticipante((Integer) extra.getSerializable( "participanteescolhido" ));


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

        adapter = new EventoAdapter(getCursorEventos());
        adapter.setOnEventoLongClickListener( new EventoAdapter.OnEventoLongClickListener() {
            @Override
            public void onEventoLongClick(View view, int position) {
                //rclEventosNaoInscritos.getAdapter()
                   InserirEvento(position);
            }
        } );
        rclEventosNaoInscritos.setAdapter( adapter );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ParticipanteDetalhesActivity.REQUEST_EDIT && resultCode == Activity.RESULT_OK){
            DAO.retornarDadosParticipante(participanteEscolhido);
        }
    }
    public  Participante retornarDadosParticipante(Integer participanteEscolhido)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String []visao = {
                SemanaContract.Participante.COLUMN_NAME_ID,
                SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE,
                SemanaContract.Participante.COLUMN_NAME_CPF,
                SemanaContract.Participante.COLUMN_NAME_EMAIL,
        };

        String select = SemanaContract.Participante.COLUMN_NAME_ID +" = ?";
        String [] selectArgs = {String.valueOf( participanteEscolhido)};

        cursor = db.query(SemanaContract.Participante.TABLE_NAME, visao,select,selectArgs,null,null, null);

        int idxNome = cursor.getColumnIndexOrThrow(SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE);
        int idxCPF = cursor.getColumnIndexOrThrow(SemanaContract.Participante.COLUMN_NAME_CPF);
        int idxEmail = cursor.getColumnIndexOrThrow(SemanaContract.Participante.COLUMN_NAME_EMAIL);

        cursor.moveToFirst();

        Participante participante = new Participante(cursor.getString(idxNome), cursor.getString(idxEmail) ,cursor.getString(idxCPF), null);
        return participante;
    }

    private Cursor getCursorEventos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] visualiza = {
                SemanaContract.Evento.COLUMN_NAME_ID,
                SemanaContract.Evento.COLUMN_NAME_TITULO
        };

        String sort = SemanaContract.Evento.COLUMN_NAME_TITULO + " ASC ";
        return db.query(SemanaContract.Evento.TABLE_NAME, visualiza,null,null,null,null, sort);
    }


    private void InserirEvento(int c)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(SemanaContract.EventoParticipante.COLUMN_NAME_IDEVENTO,"");
        valores.put( SemanaContract.EventoParticipante.COLUMN_NAME_IDPARTICIPANTE,"");
        long id = db.insert(SemanaContract.Evento.TABLE_NAME,null, valores);
        Log.i("DBINFO", "registro criado com id: "+id);
        setResult(Activity.RESULT_OK);
        finish();


    }

}
