package dcc196.ufjf.br.semanac;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import dcc196.ufjf.br.semanac.Adapter.EventoAdapter;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;

public class EventoActivity extends AppCompatActivity  {
    private static final int REQUEST_CADEVENTO = 1;

    private Button btnCadastrarEvento;
    private RecyclerView rclListaEventos;
    private TextView txtTotalEventos;
    private SemanaDBHelper dbHelper;
    private EventoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        dbHelper = new SemanaDBHelper(getApplicationContext());
       // ApagarTabela();
        btnCadastrarEvento = findViewById(R.id.btn_cadastrarEvento);
        rclListaEventos = findViewById(R.id.rv_listaEventos);
        txtTotalEventos = findViewById(R.id.txt_totalEventos);

        rclListaEventos = (RecyclerView) findViewById(R.id.rv_listaEventos);
        rclListaEventos.setLayoutManager(new LinearLayoutManager(this));


        adapter = new EventoAdapter(getCursorEventos());
        adapter.setOnClickListener(new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(EventoActivity.this, EventoDetlhesActivity.class);
                intent.putExtra("evento", position);
                startActivity(intent);
            }
        });
        rclListaEventos.setAdapter(adapter);


        txtTotalEventos.setText("Total de Eventos: " + getCursorEventos().getCount());


        btnCadastrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventoActivity.this, InserirEventoActivity.class);
                startActivityForResult(intent, EventoActivity.REQUEST_CADEVENTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EventoActivity.REQUEST_CADEVENTO && resultCode == Activity.RESULT_OK){
            int total = getCursorEventos().getCount();
            txtTotalEventos.setText("Total de Eventos: " + total);
        }
        adapter.setCursor(getCursorEventos());
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

    private void ApagarTabela()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //db.execSQL(SemanaContract.Participante.DROP_PARTICIPANTE);
        db.execSQL(SemanaContract.Evento.DROP_EVENTO);
    }
}
