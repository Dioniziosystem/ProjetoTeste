package com.example.android.projetoteste;

/**
 * Created by emerson on 12/23/17.
 */

public class Tarefa {
    private String tarefa;
    private Boolean checked;

    public Tarefa(String tarefa, Boolean checked) {
        this.tarefa = tarefa;
        this.checked=checked;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}