package br.com.original.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Header implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createTime;

    public Header() {
        this.createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public String getCreateTime() {
        return createTime;
    }
}
