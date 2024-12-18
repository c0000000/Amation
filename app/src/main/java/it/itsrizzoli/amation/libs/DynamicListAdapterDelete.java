package it.itsrizzoli.amation.libs;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;
public class DynamicListAdapterDelete<T> {
    private final List<T> items;
    private final ArrayAdapter<T> adapter;

    public DynamicListAdapterDelete(@NonNull List<T> items, @NonNull ArrayAdapter<T> adapter) {
        this.items = items;
        this.adapter = adapter;
    }

    // Aggiunge un elemento alla lista se non esiste già
    public void addItem(@NonNull T item) {
        if (!items.contains(item)) {
            items.add(item);
            adapter.notifyDataSetChanged();
        }
    }

    // Rimuove un elemento specificato dalla lista
    public void removeItem(@NonNull T item) {
        int position = items.indexOf(item);
        if (position >= 0) {
            items.remove(position);
            adapter.notifyDataSetInvalidated();
        }
    }

    // Rimuove un elemento dalla lista in base alla posizione
    public void removeItem(int position) {
        if (position >= 0) {
            items.remove(position);
            adapter.notifyDataSetInvalidated();
        }
    }

    // Aggiorna un elemento esistente nella lista
    public void updateItem(int position, @NonNull T item) {
        if (position >= 0 && position < items.size()) {
            items.set(position, item);
            adapter.notifyDataSetChanged();
        }
    }

    // Pulisce la lista di tutti gli elementi
    public void clearItems() {
        if (!items.isEmpty()) {
            items.clear();
            adapter.notifyDataSetChanged();
        }
    }

    // Restituisce l'elemento in una posizione specificata
    public T getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null; // o un valore di fallback appropriato
    }

    // Restituisce la dimensione della lista
    public int getItemCount() {
        return items.size();
    }
}
