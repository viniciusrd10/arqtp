package br.com.original.rest.presenter;

/**
 * @author Henrique Romão
 */
public class MessagePresenter {

    private String message;

    public MessagePresenter() {
    }

    public MessagePresenter(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
