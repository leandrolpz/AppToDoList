package br.senai.sp.cotia.todolistapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.model.Tarefa;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {
    // variavel para listar as tarefas
    private List<Tarefa> tarefas;
    // variavel para o context
    private Context context;
    // variavel para o listener
    private OnTarefaClickListener listenerTarefa;

    // construtor que recebe os parametros para o Adapter
    public TarefaAdapter(List<Tarefa> lista, Context contexto, OnTarefaClickListener listenerTarefa) {
        this.tarefas = lista;
        this.context = contexto;
        this.listenerTarefa = listenerTarefa;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflar a view do viewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.tarefa, parent, false);
        // retorna uma viewHolder
        return new TarefaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        // obtem a tarefa atraves da position
        Tarefa t = tarefas.get(position);
        // transportar a info da tarefa para o holder
        holder.tvTitulo.setText(t.getTitulo());
        // formata a data e exibe no textView
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvData.setText(formatador.format(t.getDataPrevista()));
        holder.tvDescricao.setText(t.getDescricao());
        // verifica se esta concluida
        if (t.isConcluida()) {
            holder.tvStatus.setText(R.string.finalizada);
            holder.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.verde_finalizada));
        } else {
            holder.tvStatus.setText(R.string.aberta);
            holder.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.amarelo_andamento));
        }
        // implementa o click na tarefa
        holder.itemView.setOnClickListener(v -> {
            listenerTarefa.onClick(v, t);
        });
    }

    @Override
    public int getItemCount() {
        if (tarefas != null) {
            return tarefas.size();
        }
        return 0;
    }


    class TarefaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvData, tvStatus, tvDescricao;

        public TarefaViewHolder(View view) {
            super(view);
            // passar da view para os componentes
            tvTitulo = view.findViewById(R.id.tv_nome_tarefa);
            tvData = view.findViewById(R.id.tv_data_tarefa);
            tvStatus = view.findViewById(R.id.tv_status_tarefa);
            tvDescricao = view.findViewById(R.id.tv_part_descricao);
        }
    }

    // interface para o click na tarefa
    public interface OnTarefaClickListener {
        void onClick(View v, Tarefa t);
    }
}
