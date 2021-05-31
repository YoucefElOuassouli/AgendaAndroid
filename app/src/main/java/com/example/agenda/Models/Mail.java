package com.example.agenda.Models;

public class Mail {


    int id,client_id_from;
    String from,To,content;


    public Mail() {}

    public int getId() {
        return id;
    }

    public int getClient_id_from() {
        return client_id_from;
    }

    public void setClient_id_from(int client_id_from) {
        this.client_id_from = client_id_from;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
