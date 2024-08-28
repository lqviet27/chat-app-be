package vn.bt.spring.chatappbe.Payload.Response;

import java.time.LocalDateTime;

public class ErrorDetail {
    private int ec;
    private String em;
    private String message;
    private LocalDateTime timeStamp;

    public ErrorDetail() {
    }

    public ErrorDetail(int ec, String em, String message, LocalDateTime timeStamp) {
        this.ec = ec;
        this.em = em;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getEc() {
        return ec;
    }

    public void setEc(int ec) {
        this.ec = ec;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "ec=" + ec +
                ", em='" + em + '\'' +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }


}
