package dcc196.ufjf.br.semanac.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dcc196.ufjf.br.semanac.Modelo.Participante;
import dcc196.ufjf.br.semanac.R;

public class ParticipanteAdapter extends RecyclerView.Adapter<ParticipanteAdapter.ViewHolder> {

    private List<Participante> lstParticipante;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public ParticipanteAdapter (List<Participante> participantes)
    {
        this.lstParticipante = participantes;
    }


    @NonNull
    @Override
    public ParticipanteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View participanteView = inflater.inflate(R.layout.layout_participante, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(participanteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemParticipante.setText(lstParticipante.get(i).getNome());
    }

       @Override
    public int getItemCount() {
        return lstParticipante.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView itemParticipante;

        public ViewHolder (final View participanteView)
        {
            super(participanteView);
            itemParticipante = (TextView)participanteView.findViewById(R.id.txt_Nome_View);
            participanteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(participanteView, position);
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
