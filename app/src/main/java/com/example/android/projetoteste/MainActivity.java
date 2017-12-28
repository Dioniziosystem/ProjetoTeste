package com.example.android.projetoteste;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ExpandableListView listaTeste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // cria os clientes Pai
        List<String> lstClientes = new ArrayList<>();
        lstClientes.add("Cliente1");
        lstClientes.add("Cliente2");
        lstClientes.add("Cliente3");

        //cria os grupos Filho
        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Grupo 1");
        lstGrupos.add("Grupo 2");
        lstGrupos.add("Grupo 3");

        //cria os itens Neto
        List<Tarefa> lstCheckList1 = new ArrayList<>();
        lstCheckList1.add(new Tarefa("Requisito 1", false));
        lstCheckList1.add(new Tarefa("Requisito 2", false));
        lstCheckList1.add(new Tarefa("Requisito 3", false));

        List<Tarefa> lstCheckList2 = new ArrayList<>();
        lstCheckList2.add(new Tarefa("Requisito 3", false));
        lstCheckList2.add(new Tarefa("Requisito 4", false));


        // cria o "relacionamento" dos grupos com seus itens
        /**HashMap<String, String> lstNivel1 = new HashMap<>();
         lstNivel1.put(lstClientes.get(0), lstGrupos.get(0));
         lstNivel1.put(lstClientes.get(0), lstGrupos.get(1));
         lstNivel1.put(lstClientes.get(0), lstGrupos.get(2));
         lstNivel1.put(lstClientes.get(1), lstGrupos.get(0));
         lstNivel1.put(lstClientes.get(1), lstGrupos.get(1));
         lstNivel1.put(lstClientes.get(1), lstGrupos.get(2));
         lstNivel1.put(lstClientes.get(2), lstGrupos.get(0));
         lstNivel1.put(lstClientes.get(2), lstGrupos.get(1));
         lstNivel1.put(lstClientes.get(2), lstGrupos.get(2));**/

        HashMap<String, List<Tarefa>> lstNivel2 = new HashMap<>();
        lstNivel2.put(lstGrupos.get(0), lstCheckList1);
        lstNivel2.put(lstGrupos.get(1), lstCheckList1);
        lstNivel2.put(lstGrupos.get(2), lstCheckList1);
        lstNivel2.put(lstGrupos.get(0), lstCheckList2);
        lstNivel2.put(lstGrupos.get(1), lstCheckList2);
        lstNivel2.put(lstGrupos.get(2), lstCheckList2);


        listaTeste = findViewById(R.id.listaPai);
        listaTeste.setAdapter(new listaPai(this, lstClientes, lstGrupos, lstNivel2));

        Log.d("cliente", String.valueOf(lstClientes.size()));
        Log.d("grupo", String.valueOf(lstGrupos.size()));
        Log.d("checklist1", String.valueOf(lstCheckList1.size()));
        Log.d("checklist2", String.valueOf(lstCheckList2.size()));
        Log.d("Nivel 2 Pai", String.valueOf(lstNivel2.size()));


    }

    public class listaPai extends BaseExpandableListAdapter {
        private List<String> lstClientes;
        private List<String> lstGrupos;
        private HashMap<String, List<Tarefa>> lstTarefas;
        private Context context;


        public listaPai(Context context, List<String> clientes, List<String> grupos, HashMap<String, List<Tarefa>> tarefas) {
            // inicializa as variáveis da classe
            this.context = context;
            lstClientes = clientes;
            lstGrupos = grupos;
            lstTarefas = tarefas;
        }

        @Override
        public int getGroupCount() {
            //retornar quantidade de clientes
            return lstClientes.size();
        }

        @Override
        public int getChildrenCount(int i) {
            //retornar quantidade de grupos no cliente
            return 1;
        }

        @Override
        public Object getGroup(int i) {
            //retornar um cliente
            return lstClientes.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            //retornar um grupo do cliente
            return lstGrupos.get(i1);
        }

        @Override
        public long getGroupId(int i) {
            // retorna o id do cliente, porém como nesse exemplo
            // o cliente não possui um id específico, o retorno
            // será o próprio i
            return i;
        }


        @Override

        public long getChildId(int i, int i1) {
            // retorna o id do grupo do cliente, porém como nesse exemplo
            // o grupo do cliente não possui um id específico, o retorno
            // será o próprio i1
            return i1;
        }



        @Override
        public View getGroupView(int i, boolean b, View contextView, ViewGroup viewGroup) {

            if (contextView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                contextView = layoutInflater.inflate(R.layout.cliente, null);
            }

            TextView nomeCliente = contextView.findViewById(R.id.listaCliente);
            nomeCliente.setBackgroundColor(Color.RED);
            nomeCliente.setTextSize(20);
            nomeCliente.setText((String) getGroup(i));
            return contextView;


        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

            CustExpLista segundoLevel = new CustExpLista(MainActivity.this);
            segundoLevel.setAdapter(new SegundoLevelAdapter(MainActivity.this, lstGrupos, lstTarefas));
            segundoLevel.setGroupIndicator(null);
            return segundoLevel;


        }


        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }

    public class CustExpLista extends ExpandableListView {
        int vlrGrupo, vlrFilho, vlrGrupoId;

        public CustExpLista(Context context) {
            super(context);
        }

        protected void onMeasure(int laguraMedida, int alturaMedida) {
            laguraMedida = MeasureSpec.makeMeasureSpec(450, MeasureSpec.AT_MOST);
            alturaMedida = MeasureSpec.makeMeasureSpec(450, MeasureSpec.AT_MOST);
            super.onMeasure(laguraMedida, alturaMedida);

        }

    }

    public class SegundoLevelAdapter extends BaseExpandableListAdapter {

        private List<String> lstGrupos;
        private HashMap<String, List<Tarefa>> lstTarefas;
        private Context context;


        public SegundoLevelAdapter(Context context, List<String> grupos, HashMap<String, List<Tarefa>> tarefas) {
            // inicializa as variáveis da classe
            this.context = context;
            lstGrupos = grupos;
            lstTarefas = tarefas;
        }

        @Override
        public int getGroupCount() {
            //retornar quantidade de grupos no cliente
            return lstGrupos.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return lstTarefas.get(getGroup(i)).size();
        }

        @Override
        public Object getGroup(int i) {
            return lstGrupos.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return lstTarefas.get(getGroup(i)).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }


        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }


        @Override
        public View getGroupView(int i, boolean b, View contextView, ViewGroup viewGroup) {

            if (contextView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                contextView = layoutInflater.inflate(R.layout.grupo, null);
            }

            TextView nomeGrupo = contextView.findViewById(R.id.listaGrupo);
            nomeGrupo.setTextSize(15);
            nomeGrupo.setBackgroundColor(Color.BLUE);
            nomeGrupo.setText((String) getGroup(i));

            return contextView;
        }


        @Override
        public View getChildView(int i, int i1, boolean b, View contextView, ViewGroup viewGroup) {

            if (contextView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                contextView = layoutInflater.inflate(R.layout.tarefa, null);
            }

            TextView nomeTarefa = contextView.findViewById(R.id.listaTarefa);
            CheckBox nomeChBox = contextView.findViewById(R.id.listaChBox);

            Tarefa tarefa = (Tarefa) getChild(i, i1);

            nomeTarefa.setBackgroundColor(Color.BLACK);
            nomeTarefa.setTextSize(15);
            nomeTarefa.setText(tarefa.getTarefa());
            nomeChBox.setChecked(tarefa.getChecked());

            return contextView;

        }


        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }

}
