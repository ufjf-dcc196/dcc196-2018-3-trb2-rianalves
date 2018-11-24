package dcc196.ufjf.br.semanac.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dcc196.ufjf.br.semanac.Modelo.Evento;
import dcc196.ufjf.br.semanac.R;

public class ParticipanteDetalhesAdapter extends RecyclerView.Adapter<ParticipanteDetalhesAdapter.ViewHolder> {
    private List<Evento> eventos;
    private OnParticipanteClickListener listener;
    private OnParticipanteLongClickListener longListener;

    public interface OnParticipanteClickListener {
        void onParticipanteClick(View participanteView, int position);
    }
    public interface OnParticipanteLongClickListener {
        void onParticipanteLongClick(View participanteView, int position);
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void setOnParticipanteClickListener(OnParticipanteClickListener listener){
        this.listener = listener;
    }
    public void setOnParticipanteLongClickListener(OnParticipanteLongClickListener listener){
        this.longListener = listener;
    }

    public ParticipanteDetalhesAdapter(List<Evento> eventos){
        this.eventos = eventos;
    }
    @NonNull
    @Override
    public ParticipanteDetalhesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View livroView = inflater.inflate(R.layout.layout_eventos, parent, false);
        ViewHolder holder = new ViewHolder(livroView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipanteDetalhesAdapter.ViewHolder holder, int position) {
        holder.txtItemEvento.setText(eventos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txtItemEvento;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtItemEvento = itemView.findViewById(R.id.txtEventoNome);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onParticipanteClick(itemView, position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            longListener.onParticipanteLongClick(itemView, position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onParticipanteClick(v, position);
                }
            }
        }
        @Override
        public boolean onLongClick(View v) {
            if(longListener!=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    longListener.onParticipanteLongClick(v, position);
                    return true;
                }
            }
            return false;
        }
    }
}
