package dcc196.ufjf.br.semanac;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;

import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class EventoDetlhesActivity extends AppCompatActivity {

    private Evento recuperado;
    private RecyclerView rclListaParticipantesEvento;
    private TextView nomeEvento;
    private TextView localEvento;
    private TextView dataEvento;
    private TextView facilitadorEvento;
    private TextView vagasEvento;
    private TextView inscritosEvento;
    private TextView descricaoEvento;
    private Cursor cursor;
    private SemanaDBHelper dbHelper;

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
        rclListaParticipantesEvento = findViewById(R.id.rv_ListaDeInscritosEventoInformacao);

        dbHelper = new SemanaDBHelper(getApplicationContext());

        Bundle extra = getIntent().getExtras();
        Integer recuperado = extra.getInt("evento");
        retornarDadosEvento(recuperado);


         rclListaParticipantesEvento.setLayoutManager(new LinearLayoutManager(this));

    }

    public void retornarDadosEvento(Integer recuperado)
    {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String []visao = {
                SemanaContract.Evento.COLUMN_NAME_TITULO,
                SemanaContract.Evento.COLUMN_NAME_LOCAL,
                SemanaContract.Evento.COLUMN_NAME_DATA,
                SemanaContract.Evento.COLUMN_NAME_FACILITADOR,
                SemanaContract.Evento.COLUMN_NAME_DESCRICAO,
                SemanaContract.Evento.COLUMN_NAME_LOTACAO,

        };

        String select = SemanaContract.Evento._ID+" = ?";
        String [] selectArgs = {String.valueOf(recuperado)};

        cursor = db.query(SemanaContract.Evento.TABLE_NAME, visao,select,selectArgs,null,null, null);


        int idxNome = cursor.getColumnIndexOrThrow(SemanaContract.Evento.COLUMN_NAME_TITULO);
        int idxLocal = cursor.getColumnIndexOrThrow(SemanaContract.Evento.COLUMN_NAME_LOCAL);
        int idxData = cursor.getColumnIndexOrThrow(SemanaContract.Evento.COLUMN_NAME_DATA);
        int idxFacilitador = cursor.getColumnIndexOrThrow(SemanaContract.Evento.COLUMN_NAME_FACILITADOR);
        int idxDescricao = cursor.getColumnIndexOrThrow(SemanaContract.Evento.COLUMN_NAME_DESCRICAO);
        int idxLotacao = cursor.getColumnIndex(SemanaContract.Evento.COLUMN_NAME_LOTACAO);

        cursor.moveToPosition(0);

        nomeEvento.setText(cursor.getString(idxNome));
        localEvento.setText( cursor.getString(idxLocal));
        //dataEvento.setText(cursor.getString(idxData));
        vagasEvento.setText("Vagas: "+ cursor.getString(idxLotacao));
        facilitadorEvento.setText("Facilitador: "+cursor.getString(idxFacilitador));
        descricaoEvento.setText("Descrição" + cursor.getString(idxDescricao));
    }
}
