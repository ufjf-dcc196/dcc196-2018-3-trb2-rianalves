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

import dcc196.ufjf.br.semanac.Adapter.ParticipanteAdapter;
import dcc196.ufjf.br.semanac.DAO.DAO;
import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.DAO.SemanaDBHelper;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class ParticipanteActivity extends AppCompatActivity {

    private static final int REQUEST_INSEREPARTICIPANTE = 1;

    private Button btnInserirParticipante;
    private RecyclerView rclListaParticipante;
    private TextView txtTotalP;

    private SemanaDBHelper dbHelper;
    private ParticipanteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante);
        dbHelper = new SemanaDBHelper(getApplicationContext());

        btnInserirParticipante = (Button) findViewById(R.id.btn_inserirParticipante);
        txtTotalP =(TextView) findViewById(R.id.txt_totalP);

        rclListaParticipante = (RecyclerView) findViewById(R.id.rcl_Participantes);
        rclListaParticipante.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ParticipanteAdapter(getCursorParticipantes());
        adapter.setOnClickListener(new ParticipanteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ParticipanteActivity.this,ParticipanteDetalhesActivity.class);
                intent.putExtra("participanteescolhido",position);
                startActivity(intent);
            }
        }
    );
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
            int total = getCursorParticipantes().getCount();
            txtTotalP.setText("Total de Participantes: " + total);
        }
        adapter.notifyDataSetChanged();
    }

    private Cursor getCursorParticipantes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] visao = {
                SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE
        };

        String sort = SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE + " DESC";
        return db.query(SemanaContract.Participante.TABLE_NAME, visao,null,null,null,null, sort);
    }
}
