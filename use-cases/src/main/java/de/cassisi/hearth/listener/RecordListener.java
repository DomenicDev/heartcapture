package de.cassisi.hearth.listener;

public interface RecordListener<T> {

    void onDataReceived(T data);

}
