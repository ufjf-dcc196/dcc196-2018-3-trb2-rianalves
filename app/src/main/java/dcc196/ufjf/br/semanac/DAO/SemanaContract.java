package dcc196.ufjf.br.semanac.DAO;

import android.provider.BaseColumns;


import java.util.Calendar;

public class SemanaContract {

    public final class Participante implements BaseColumns {
        public final static String TABLE_NAME = "Participante";
        public final static String COLUMN_NAME_PARTICIPANTE = "nome";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_CPF = "cpf";
        public final static String CREATE_PARTICIPANTE  = "CREATE TABLE "+Participante.TABLE_NAME+" ("
                + Participante._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Participante.COLUMN_NAME_PARTICIPANTE+ " TEXT, "
                + Participante.COLUMN_NAME_EMAIL+ " TEXT,"
                + Participante.COLUMN_NAME_CPF+ " TEXT"
                +")";
        public final static String DROP_PARTICIPANTE = "DROP TABLE IF EXISTS "+Participante.TABLE_NAME;
    }

    public final class Evento implements BaseColumns {
        public final static String TABLE_NAME = "Evento";
        public final static String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_LOCAL = "local";
        public static final String COLUMN_NAME_FACILITADOR= "facilitador";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_LOTACAO = "lotacao";
        public final static String CREATE_EVENTO = "CREATE TABLE "+Evento.TABLE_NAME+" ("
                + Evento._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Evento.COLUMN_NAME_TITULO+ " TEXT, "
                + Evento.COLUMN_NAME_DATA+ " TEXT, "
                + Evento.COLUMN_NAME_LOCAL+ " TEXT, "
                + Evento.COLUMN_NAME_FACILITADOR+ " TEXT, "
                + Evento.COLUMN_NAME_DESCRICAO+ " TEXT,"
                + Evento.COLUMN_NAME_LOTACAO+ " TEXT"
                +")";
        public final static String DROP_EVENTO = "DROP TABLE IF EXISTS "+Evento.TABLE_NAME;
    }

    public final class EventoParticipante implements BaseColumns {
        public final static String TABLE_NAME = "Participante";
        public final static String COLUMN_NAME_IDPARTICIPANTE = "idparticipante";
        public static final String COLUMN_NAME_IDEVENTO = "idevento";
        public final static String CREATE_EVENTOPARTICIPANTE  = "CREATE TABLE "+EventoParticipante.TABLE_NAME+" ("
                + EventoParticipante._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EventoParticipante.COLUMN_NAME_IDPARTICIPANTE+ " INTEGER, "
                + EventoParticipante.COLUMN_NAME_IDEVENTO+ " INTEGER,"
                + "PRIMARY KEY (idparticipante,idevento),"
                + "FOREIGN KEY(idevento) REFERENCES Evento(idevento), "
                + "FOREIGN KEY(idparticipante) REFERENCES Participante(idparticipante)"
                +")";
        public final static String DROP_EVENTOPARTICIPANTE = "DROP TABLE IF EXISTS "+EventoParticipante.TABLE_NAME;
    }
}
