package dcc196.ufjf.br.semanac.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SemanaDBHelper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "Semana.db";

    public SemanaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SemanaContract.Participante.CREATE_PARTICIPANTE);
        db.execSQL(SemanaContract.Evento.CREATE_EVENTO);
        //db.execSQL(SemanaContract.EventoParticipante.CREATE_EVENTOPARTICIPANTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SemanaContract.Participante.DROP_PARTICIPANTE);
        db.execSQL(SemanaContract.Evento.DROP_EVENTO);
        db.execSQL(SemanaContract.EventoParticipante.DROP_EVENTOPARTICIPANTE);
        onCreate(db);
    }
}
