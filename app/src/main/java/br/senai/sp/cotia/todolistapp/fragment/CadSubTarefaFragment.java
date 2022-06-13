package br.senai.sp.cotia.todolistapp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.databinding.FragmentCadSubTarefaBinding;
import br.senai.sp.cotia.todolistapp.databinding.FragmentCadTarefaBinding;


public class CadSubTarefaFragment extends Fragment {
    private FragmentCadSubTarefaBinding binding;
    // varaivel para o dialog da foto
    private AlertDialog dialogFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCadSubTarefaBinding.inflate(getLayoutInflater(), container, false);

        setHasOptionsMenu(true);

        //instancia o dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.origem_imagem);
        String[] opcoes = {getString(R.string.camera), getString(R.string.galeria)};
        builder.setItems(opcoes, listenerDialog);
        dialogFoto = builder.create();

        //listener de click d imagem
        binding.imageSub.setOnClickListener(v -> {
            dialogFoto.show();
        });

        return binding.getRoot();
    }

    // listener disparado ao escolher uma opção no dialog
    private DialogInterface.OnClickListener listenerDialog = (dialog, i) -> {

    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sub_tarefa, menu);
    }
}