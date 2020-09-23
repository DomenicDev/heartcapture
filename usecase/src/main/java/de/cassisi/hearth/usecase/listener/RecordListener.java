package de.cassisi.hearth.usecase.listener;

public interface RecordListener<T> {

    void onDataReceived(T data);

}
