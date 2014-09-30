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

import ru.frozolab.benzin.model.Item;
import ru.frozolab.benzin.model.ListItem;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Cache {
    enum Key {MAIN_ITEMS, ALL_ITEMS}

    private static LoadingCache<Key, Object> PERSISTENCE_STORAGE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .maximumSize(10)
            .build(new CacheLoader<Key, Object>() {
                public Object load(Key key) throws Exception {
                    Object obj;
                    switch (key) {
                        case MAIN_ITEMS:
                            obj = Item.getPreparedMainItems();
                            break;
                        case ALL_ITEMS:
                            obj = Item.loadItems();
                            break;
                        default:
                            throw new Exception("unknown persistence cache key");
                    }
                    return obj;
                }
            });

    private static LoadingCache<Integer, List<ListItem>> VIEW_ITEMS = CacheBuilder
            .newBuilder()
            .maximumSize(10)
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(new CacheLoader<Integer, List<ListItem>>() {
                public List<ListItem> load(Integer typeId) {
                    return Item.getPreparedViewItems(typeId);
                }
            });

    private static Object getFromPersistence(Key key) {
        Object obj = null;
        try {
            obj = PERSISTENCE_STORAGE.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key.name(), e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("error while getting from cache, key = " + key.name(), e.getLocalizedMessage());
        }
        return obj;
    }

    private static Object getViewItemsFrom(int key) {
        Object obj = null;
        try {
            obj = VIEW_ITEMS.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        }
        return obj;
    }

    public static List<Item> getItems() {
        return (List<Item>) getFromPersistence(Key.ALL_ITEMS);
    }

    public static List<ListItem> getMainItems() {
        return (List<ListItem>) getFromPersistence(Key.MAIN_ITEMS);
    }

    public static List<ListItem> getViewItems(int typeId) {
        return (List<ListItem>) getViewItemsFrom(typeId);
    }
}
