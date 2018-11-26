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

import dcc196.ufjf.br.semanac.Adapter.EventoAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Evento;

public class EventoActivity extends AppCompatActivity {
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
                Evento e = (Evento) DAO.getEventoInstance().get(position);
                intent.putExtra("evento", e);
                startActivity(intent);
            }
        });
        rclListaEventos.setAdapter(adapter);


        int total = getCursorEventos().getCount();

        txtTotalEventos.setText("Total de Eventos: " + total);


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

    }

    private Cursor getCursorEventos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] visao = {
                SemanaContract.Evento.COLUMN_NAME_TITULO
        };

        String sort = SemanaContract.Evento.COLUMN_NAME_TITULO + " DESC";
        return db.query(SemanaContract.Evento.TABLE_NAME, visao,null,null,null,null, sort);
    }
}
