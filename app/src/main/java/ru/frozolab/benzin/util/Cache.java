package ru.frozolab.benzin.util;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import ru.frozolab.benzin.model.MainListItem;
import ru.frozolab.benzin.model.ViewListItem;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Cache {
    enum Key {MAIN_ITEMS}

    private static LoadingCache<Key, Object> PERSISTENCE_STORAGE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .maximumSize(10)
            .build(new CacheLoader<Key, Object>() {
                public Object load(Key key) throws Exception {
                    Object obj = null;
                    switch (key) {
                        case MAIN_ITEMS:
                            obj = MainListItem.loadItems();
                            break;
                        default:
                            throw new Exception("unknown persistence cache key");
                    }
                    return obj;
                }
            });

    private static LoadingCache<Integer, List<ViewListItem>> TYPE_COMPANIES = CacheBuilder
            .newBuilder()
            .maximumSize(10)
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(new CacheLoader<Integer, List<ViewListItem>>() {
                public List<ViewListItem> load(Integer key) {
                    return ViewListItem.loadItems(key);
                }
            });

    private static Object getFromPersistence(Key key) {
        Object obj = null;
        try {
            obj = PERSISTENCE_STORAGE.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key.name(), e.getLocalizedMessage());
        }
        return obj;
    }

    private static Object getFromTypeCompanies(int key) {
        Object obj = null;
        try {
            obj = TYPE_COMPANIES.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        }
        return obj;
    }

    public static List<MainListItem> getMainItems() {
        return (List<MainListItem>) getFromPersistence(Key.MAIN_ITEMS);
    }

    public static List<ViewListItem> getViewItems(int key) {
        return (List<ViewListItem>) getFromTypeCompanies(key);
    }
}
