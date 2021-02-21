package com.example.jogomemoria.modelo;

public class Score {
    private String name;
    private Double time;
    private Long erros;

    public Score(){
        this.name = "";
        this.time = 0D;
        this.erros = 0L;
    }

    public Score(String name, Double time, Long erros){
        this.name = name;
        this.time = time != null ? time/1000 : 0D;
        this.erros = erros != null ? erros : 0L;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Long getErros() {
        return erros;
    }

    public void setErros(Long erros) {
        this.erros = erros;
    }
}
