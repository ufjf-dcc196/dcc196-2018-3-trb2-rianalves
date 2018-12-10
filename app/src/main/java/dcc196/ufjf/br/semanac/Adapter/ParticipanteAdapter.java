package dcc196.ufjf.br.semanac.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dcc196.ufjf.br.semanac.DAO.SemanaContract;
import dcc196.ufjf.br.semanac.Modelo.Participante;
import dcc196.ufjf.br.semanac.R;

public class ParticipanteAdapter extends RecyclerView.Adapter<ParticipanteAdapter.ViewHolder> {

    private Cursor cursor;
    public ParticipanteAdapter(Cursor c){cursor = c;}
    private OnItemClickListener listener;

    public void setCursor(Cursor c){
        cursor = c;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ParticipanteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View participanteView = inflater.inflate(R.layout.layout_participante, viewGroup, false);
        ViewHolder holder = new ViewHolder(participanteView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int idxID = cursor.getColumnIndexOrThrow(SemanaContract.Participante.COLUMN_NAME_ID);
        int idxNome = cursor.getColumnIndexOrThrow(SemanaContract.Participante.COLUMN_NAME_PARTICIPANTE);
        cursor.moveToPosition(position);
        holder.txt_idParticipante.setText(String.valueOf(cursor.getInt(idxID)));
        holder.txt_nomeParticipante.setText(cursor.getString(idxNome));
    }

       @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txt_idParticipante;
        public TextView txt_nomeParticipante;

        public ViewHolder (final View participanteView)
        {
            super(participanteView);
            txt_idParticipante = (TextView)participanteView.findViewById(R.id.txt_id);
            txt_nomeParticipante = (TextView)participanteView.findViewById(R.id.txt_Nome_View);
            participanteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(participanteView,Integer.parseInt(txt_idParticipante.getText().toString()));
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                listener.onItemClick(v, position);
            }
        }
    }
}
