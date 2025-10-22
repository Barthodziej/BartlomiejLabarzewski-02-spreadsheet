package uj.wmii.pwj.spreadsheet;
import java.util.LinkedList;

public class CellUpdateManager {
    public LinkedList<ICellUpdateListener> listeners;

    public CellUpdateManager() {
        listeners = new LinkedList<ICellUpdateListener>();
    }

    public void subscribe(ICellUpdateListener listener) {
        listeners.add(listener);
    }
    public void unsubscribe(ICellUpdateListener listener) {
        listeners.remove(listener);
    }
    public void notifyListeners() {
        for (ICellUpdateListener listener : listeners) {
            listener.update();
        }
    }
}
