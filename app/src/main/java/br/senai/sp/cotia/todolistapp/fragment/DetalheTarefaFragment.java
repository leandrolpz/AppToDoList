package br.senai.sp.cotia.todolistapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.databinding.FragmentDetalheTarefaBinding;
import br.senai.sp.cotia.todolistapp.model.Tarefa;


public class DetalheTarefaFragment extends Fragment {
    private FragmentDetalheTarefaBinding binding;
    // variavel para tarefa
    Tarefa tarefa;
    private TextView tv_data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instancia o binding
        binding = FragmentDetalheTarefaBinding.inflate(getLayoutInflater(), container, false);
        binding.floatDetalhe.setOnClickListener(v -> {
            NavHostFragment.findNavController(DetalheTarefaFragment.this).navigate
                    (R.id.action_detalheTarefaFragment_to_cadSubTarefaFragment);
        });
        // verifica se foi passado algo no bundle
        if (getArguments() != null) {
            // recupera a tarefa do bundle
            tarefa = (Tarefa) getArguments().getSerializable("tarefa");
            // popular as informações da tarefa

        }
        // retorna a raiz(root)
        return binding.getRoot();
    }


}