package it.itsrizzoli.amation.libs;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class ArrayAdapterUtils<T, V extends View> {

    private final Context context;
    private final List<T> items;
    private int layoutRes;
    private ItemBinder<T, V> binder;
    @Nullable
    private AdapterView.OnItemClickListener onItemClickListener;
    @Nullable
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    @Nullable
    private AdapterView.OnItemSelectedListener onItemSelectedListener;

    private ArrayAdapterUtils(@NonNull Context context, @NonNull List<T> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    public static <T, V extends View> AdapterConfigurator<T, V> with(@NonNull Context context, @NonNull List<T> items) {
        return new AdapterConfigurator<>(context, items);
    }

    @NonNull
    public static <T, V extends View> AdapterConfigurator<T, V> with(@NonNull Context context, @NonNull T[] items) {
        return new AdapterConfigurator<>(context, Arrays.asList(items));
    }

    @NonNull
    private ArrayAdapter<T> createAdapter() {
        return new ViewHolderArrayAdapter<>(context, layoutRes, items, binder);
    }

    private void configureListView(@NonNull ListView listView) {
        ArrayAdapter<T> adapter = createAdapter();
        listView.setAdapter(adapter);

        if (onItemClickListener != null) {
            listView.setOnItemClickListener(onItemClickListener);
        }

        if (onItemLongClickListener != null) {
            listView.setOnItemLongClickListener(onItemLongClickListener);
        }

        if (onItemSelectedListener != null) {
            listView.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    private void configureGridView(@NonNull GridView gridView) {
        ArrayAdapter<T> adapter = createAdapter();
        gridView.setAdapter(adapter);

        if (onItemClickListener != null) {
            gridView.setOnItemClickListener(onItemClickListener);
        }

        if (onItemLongClickListener != null) {
            gridView.setOnItemLongClickListener(onItemLongClickListener);
        }

        if (onItemSelectedListener != null) {
            gridView.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    @SuppressWarnings("unchecked")
    private static class ViewHolderArrayAdapter<T, V extends View> extends ArrayAdapter<T> {

        private final int layoutRes;
        private final ItemBinder<T, V> binder;

        public ViewHolderArrayAdapter(@NonNull Context context, int layoutRes, @NonNull List<T> items, @NonNull ItemBinder<T, V> binder) {
            super(context, layoutRes, items);
            this.layoutRes = layoutRes;
            this.binder = binder;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder<V> viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layoutRes, parent, false);
                viewHolder = new ViewHolder<>(convertView);
                convertView.setTag(viewHolder);
            } else {
                // Safe unchecked cast ensured by class design
                viewHolder = (ViewHolder<V>) convertView.getTag();

            }

            T item = getItem(position);
            if (binder != null) {
                binder.bind(viewHolder, item, position);
            }

            return convertView;
        }
    }

    public interface ItemBinder<T, V extends View> {
        void bind(@NonNull ViewHolder<V> viewHolder, @Nullable T item, int position);
    }

    @SuppressWarnings("unchecked")
    public static class ViewHolder<V extends View> {
        private final WeakReference<View> rootViewRef;
        private final SparseArray<WeakReference<V>> viewCache;

        public ViewHolder(@NonNull View rootView) {
            this.rootViewRef = new WeakReference<>(rootView);
            this.viewCache = new SparseArray<>();
        }

        @NonNull
        public <T extends V> T findViewById(int viewId) {
            WeakReference<V> childViewRef = viewCache.get(viewId);
            V childView = childViewRef != null ? childViewRef.get() : null;
            if (childView == null) {
                View rootView = rootViewRef.get();
                if (rootView == null) {
                    throw new IllegalStateException("Root view has been garbage collected");
                }
                childView = rootView.findViewById(viewId);
                if (childView == null) {
                    throw new IllegalArgumentException("View with ID " + viewId + " not found in root view");
                }
                viewCache.put(viewId, new WeakReference<>(childView));
            }
            // Safe unchecked cast ensured by design
            return (T) childView;
        }

        @NonNull
        public V getViewCasting(int viewId) {
            WeakReference<V> childViewRef = viewCache.get(viewId);
            V childView = childViewRef != null ? childViewRef.get() : null;
            if (childView == null) {
                View rootView = rootViewRef.get();
                if (rootView == null) {
                    throw new IllegalStateException("Root view has been garbage collected");
                }
                childView = rootView.findViewById(viewId);
                if (childView == null) {
                    throw new IllegalArgumentException("View with ID " + viewId + " not found in root view");
                }
                viewCache.put(viewId, new WeakReference<>(childView));
            }
            // Safe unchecked cast ensured by design
            return childView;
        }

        @NonNull
        public View getRootView() {
            View rootView = rootViewRef.get();
            if (rootView == null) {
                throw new IllegalStateException("Root view has been garbage collected");
            }
            return rootView;
        }
    }


    public static class AdapterConfigurator<T, V extends View> {

        private final Context context;
        private final List<T> items;
        private int layoutRes;
        private ItemBinder<T, V> binder;
        private ArrayAdapter<T> adapter;

        @Nullable
        private AdapterView.OnItemClickListener onItemClickListener;
        @Nullable
        private AdapterView.OnItemLongClickListener onItemLongClickListener;
        @Nullable
        private AdapterView.OnItemSelectedListener onItemSelectedListener;

        private AdapterConfigurator(@NonNull Context context, @NonNull List<T> items) {
            this.context = context;
            this.items = items;
        }

        @NonNull
        public AdapterConfigurator<T, V> setLayoutRes(@LayoutRes int layoutRes) {
            this.layoutRes = layoutRes;
            return this;
        }

        @NonNull
        public AdapterConfigurator<T, V> setBinder(@NonNull ItemBinder<T, V> binder) {
            this.binder = binder;
            return this;
        }

        @NonNull
        public AdapterConfigurator<T, V> setOnItemClick(@Nullable OnItemClick onItemClick) {
            this.onItemClickListener = onItemClick;
            return this;
        }

        @NonNull
        public AdapterConfigurator<T, V> setOnItemLongClick(@Nullable OnItemLongClick onItemLongClick) {
            this.onItemLongClickListener = onItemLongClick;
            return this;
        }

        @NonNull
        public AdapterConfigurator<T, V> setOnItemSelected(@Nullable OnItemSelected onItemSelected) {
            this.onItemSelectedListener = onItemSelected;
            return this;
        }

        public interface OnClick extends AdapterView.OnItemClickListener {
            void onItemClick(AdapterView<?> parent, View clickedView, int index, long itemId);
        }

        // Per onClick
        public interface OnItemClick extends AdapterView.OnItemClickListener {
            void onItemClick(AdapterView<?> parent, View clickedView, int pos, long itemId);
        }

        // Per onLongClick
        public interface OnItemLongClick extends AdapterView.OnItemLongClickListener {
            boolean onItemLongClick(AdapterView<?> parent, View clickedView, int pos, long itemId);
        }

        // Per onSelected
        public interface OnItemSelected extends AdapterView.OnItemSelectedListener {
            void onItemSelected(AdapterView<?> parent, View clickedView, int pos, long itemId);

            void onNothingSelected(AdapterView<?> parent);
        }

        public DynamicListAdapter<T> applyTo(@IdRes int listViewId) {

            if (!(context instanceof Activity activity)) {
                throw new IllegalArgumentException("Context must be an instance of Activity.");
            }


            if (binder == null) {
                throw new IllegalArgumentException("Binder cannot be null.");
            }

            ArrayAdapterUtils<T, V> utils = new ArrayAdapterUtils<>(context, items);
            utils.layoutRes = this.layoutRes;
            utils.binder = this.binder;

            utils.onItemClickListener = this.onItemClickListener;
            utils.onItemLongClickListener = this.onItemLongClickListener;
            utils.onItemSelectedListener = this.onItemSelectedListener;

            View view = activity.findViewById(listViewId);

            if (view instanceof ListView listView) {
                utils.configureListView(listView);
            } else if (view instanceof GridView gridView) {
                utils.configureGridView(gridView);
            } else {
                throw new IllegalArgumentException("View with the given ID is not a ListView or GridView.");
            }
            this.adapter = utils.createAdapter();

            return new DynamicListAdapter<>(items, adapter);
        }

        public DynamicListAdapter<T> applyTo(@IdRes int listViewId, @NonNull View view) {
            if (!(context instanceof Activity activity)) {
                throw new IllegalArgumentException("Context must be an instance of Activity.");
            }


            if (binder == null) {
                throw new IllegalArgumentException("Binder cannot be null.");
            }

            ArrayAdapterUtils<T, V> utils = new ArrayAdapterUtils<>(context, items);
            utils.layoutRes = this.layoutRes;
            utils.binder = this.binder;

            utils.onItemClickListener = this.onItemClickListener;
            utils.onItemLongClickListener = this.onItemLongClickListener;
            utils.onItemSelectedListener = this.onItemSelectedListener;

            view = view.findViewById(listViewId);

            if (view instanceof ListView listView) {
                utils.configureListView(listView);
            } else if (view instanceof GridView gridView) {
                utils.configureGridView(gridView);
            } else {
                throw new IllegalArgumentException("View with the given ID is not a ListView or GridView.");
            }
            this.adapter = utils.createAdapter();
            return  new DynamicListAdapter<>(items, adapter);
        }

        public void applyTo(@IdRes int listViewId, @NonNull Activity activity) {
            View view = activity.findViewById(listViewId);
            if (view != null) {
                applyToListView(view, activity);
            } else {
                throw new IllegalArgumentException("View with the given ID not found.");
            }
        }

        private void applyToListView(View view, Context context) {
            if (view instanceof ListView listView) {
                if (binder == null) {
                    throw new IllegalArgumentException("Binder cannot be null.");
                }
                ArrayAdapterUtils<T, V> utils = new ArrayAdapterUtils<>(context, items);
                utils.layoutRes = this.layoutRes;
                utils.binder = this.binder;
                utils.configureListView(listView);
                this.adapter = utils.createAdapter();
            } else {
                throw new IllegalArgumentException("View with the given ID is not a ListView.");
            }
        }

        @NonNull
        public ArrayAdapter<T> buildAdapter() {
            ArrayAdapterUtils<T, V> utils = new ArrayAdapterUtils<>(context, items);
            utils.layoutRes = this.layoutRes;
            utils.binder = this.binder;
            this.adapter = utils.createAdapter();
            return adapter;
        }


    }
}
