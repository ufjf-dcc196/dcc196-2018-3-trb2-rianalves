package dcc196.ufjf.br.semanac.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dcc196.ufjf.br.semanac.Modelo.Evento;
import dcc196.ufjf.br.semanac.Modelo.Participante;

public class DAO {

    public static List<Participante> lstParticipantes;
    private static List<Evento> lstEventos;

    public static List getParticipanteInstance()
    {
        if (lstParticipantes == null)
        {
            lstParticipantes = new ArrayList<Participante>();
            Participante p1 = new Participante("Rian Alves", "rianalves@gmail.com", "00000000000", null);
            Participante p2 = new Participante("João Dias", "jpdias@gmail.com", "111111111111", null);
            Participante p3 = new Participante("Pedro Fernandes ", "pfernandes@gmail.com", "2222222222", null);
            lstParticipantes.add(p1);
            lstParticipantes.add(p2);
            lstParticipantes.add(p3);
        }
        return lstParticipantes;
    }

    public static List getEventoInstance()
    {
        if (lstEventos == null)
        {
            String data = "31/10/2018 19:30";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(data));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lstEventos = new ArrayList<Evento>();
            Evento e1 = new Evento("Androi Studio", "LAB4", cal, "Igor", "Desenvolvimento de Mobile", null, 50, 0);
            Evento e2 = new Evento("Testes de Software", "SALA10", cal, "Vânia Oliveira", "Abordará o uso do ferramentas de Software", null, 50, 0);
            lstEventos.add(e1);
            lstEventos.add(e2);
        }
        return lstEventos;
    }
}
