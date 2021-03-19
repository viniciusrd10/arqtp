package br.com.original.models;

import br.com.original.utils.JsonUtil;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private Header header;
    private String data;

    public Message(Object object) {
        this.data = JsonUtil.toJson(object);
        this.header = new Header();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Header getHeader() {
        return header;
    }
}
