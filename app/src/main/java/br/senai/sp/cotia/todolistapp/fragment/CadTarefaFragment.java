package br.senai.sp.cotia.todolistapp.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import br.senai.sp.cotia.todolistapp.R;
import br.senai.sp.cotia.todolistapp.database.AppDatabase;
import br.senai.sp.cotia.todolistapp.databinding.FragmentCadTarefaBinding;
import br.senai.sp.cotia.todolistapp.model.Tarefa;


public class CadTarefaFragment extends Fragment {
    private FragmentCadTarefaBinding binding;
    // variavel para o datePicker
    private DatePickerDialog datePicker;
    int year, month, day;
    //variavel para data atual
    Calendar dataAtual;
    // variavel para data formatada
    String dataFormatada = "";
    // variavel para a database
    AppDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instancia a database
        database = AppDatabase.getDatabase(getContext());

        binding = FragmentCadTarefaBinding.inflate(getLayoutInflater(), container, false);

        //insatncia data atual
        dataAtual = Calendar.getInstance();
        // obter ano, mês e dia atual
        year = dataAtual.get(Calendar.YEAR);
        month = dataAtual.get(Calendar.MONTH);
        day = dataAtual.get(Calendar.DAY_OF_MONTH);

        // instancia o datePicker
        datePicker = new DatePickerDialog(getContext(), (datePicker, ano, mes, dia)->{
            // ao escolher uma data no datePicker cai aqui
            // passar para as variaveis globais
            year = ano;
            month = mes;
            day = dia;
            // formata a data
            dataFormatada = String.format("%02d/%02d/%04d", day, month + 1, year);
            // aplica a data formatada no botão
            binding.btData.setText(dataFormatada);

        }, year, month, day);

        // ação do click do botao
        binding.btData.setOnClickListener(v -> {
            datePicker.show();
        });

        // listener do botão salvar
        binding.btSalvar.setOnClickListener(v -> {
            if (binding.etNomeTarefa.getText().toString().isEmpty()){
               Toast.makeText(getContext(), "Coloque um titulo", Toast.LENGTH_SHORT).show();
            } else if (binding.etDescricao.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Preencha a descrição !!!", Toast.LENGTH_SHORT).show();
            } else if (dataFormatada.isEmpty()){
                Toast.makeText(getContext(), "Informe a data !!!", Toast.LENGTH_SHORT).show();
            }else {
                // criar uma tarefa
                Tarefa tarefa = new Tarefa();
                // popular o objeto tarefa
                tarefa.setTitulo(binding.etNomeTarefa.getText().toString());
                tarefa.setDescricao(binding.etDescricao.getText().toString());
                tarefa.setDataCriacao(dataAtual.getTimeInMillis());
                // criar um calendar
                Calendar dataPrevista = Calendar.getInstance();
                // muda a data para a data escolhida no datePicker
                dataPrevista.set(year, month, day);
                // passa os milisegundos da data para a data prevista
                tarefa.setDataPrevista(dataPrevista.getTimeInMillis());
                // salvar a tarefa
                new insertTarefa().execute(tarefa);

            }

        });


        return binding.getRoot();
    }

    // AsyncTask para inserir tarefa
    private class insertTarefa extends AsyncTask<Tarefa, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Tarefa... tarefas) {
            // pegar a tarefa a partir do vetor
            Tarefa t =  tarefas[0];
            try {
            // chamar o metodo para salvar a tarefa
            database.getTarefaDao().insert(t);
            // retornar
            return "ok";
            }catch (Exception erro){
                erro.printStackTrace();
                //retorna a mensagem de erro
                return  erro.getMessage();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("ok")){
                Log.w("RESULT", "IUPIIIII");
                Toast.makeText(getContext(), "Tarefa inserida com sucesso", Toast.LENGTH_SHORT).show();
                // voltar para o fragment anterior
                getActivity().onBackPressed();
            }else {
                Log.w("RESULT", result);
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}