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

import ru.frozolab.benzin.model.currency.CurrencyItem;
import ru.frozolab.benzin.model.currency.CurrencyListItem;
import ru.frozolab.benzin.model.fuel.FuelItem;
import ru.frozolab.benzin.model.fuel.FuelListItem;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Cache {
    enum Key {
        FUEL_MAIN_ITEMS,
        FUEL_ALL_ITEMS,
        CURRENCY_MAIN_ITEMS,
        CURRENCY_ALL_ITEMS
    }

    private static LoadingCache<Key, Object> PERSISTENCE_STORAGE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .maximumSize(10)
            .build(new CacheLoader<Key, Object>() {
                public Object load(Key key) throws Exception {
                    Object obj;
                    switch (key) {
                        case FUEL_MAIN_ITEMS:
                            obj = FuelItem.getPreparedMainItems();
                            break;
                        case FUEL_ALL_ITEMS:
                            obj = FuelItem.loadItems();
                            break;
                        case CURRENCY_MAIN_ITEMS:
                            obj = CurrencyItem.getPreparedMainItems();
                            break;
                        case CURRENCY_ALL_ITEMS:
                            obj = CurrencyItem.loadItems();
                            break;
                        default:
                            throw new Exception("unknown persistence cache key");
                    }
                    return obj;
                }
            });

    private static LoadingCache<Integer, List<FuelListItem>> FUEL_VIEW_ITEMS = CacheBuilder
            .newBuilder()
            .maximumSize(10)
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(new CacheLoader<Integer, List<FuelListItem>>() {
                public List<FuelListItem> load(Integer typeId) {
                    return FuelItem.getPreparedViewItems(typeId);
                }
            });

    private static LoadingCache<Integer, List<CurrencyListItem>> CURRENCY_VIEW_ITEMS = CacheBuilder
            .newBuilder()
            .maximumSize(10)
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(new CacheLoader<Integer, List<CurrencyListItem>>() {
                public List<CurrencyListItem> load(Integer typeId) {
                    return CurrencyItem.getPreparedViewItems(typeId);
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

    private static Object getFuelViewItemsFrom(int key) {
        Object obj = null;
        try {
            obj = FUEL_VIEW_ITEMS.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        }
        return obj;
    }

    private static Object getCurrencyViewItemsFrom(int key) {
        Object obj = null;
        try {
            obj = CURRENCY_VIEW_ITEMS.get(key);
        } catch (ExecutionException e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("error while getting from cache, key = " + key, e.getLocalizedMessage());
        }
        return obj;
    }

    public static List<FuelItem> getFuelItems() {
        return (List<FuelItem>) getFromPersistence(Key.FUEL_ALL_ITEMS);
    }

    public static List<FuelListItem> getFuelMainItems() {
        return (List<FuelListItem>) getFromPersistence(Key.FUEL_MAIN_ITEMS);
    }

    public static List<FuelListItem> getFuelViewItems(int typeId) {
        return (List<FuelListItem>) getFuelViewItemsFrom(typeId);
    }

    public static List<CurrencyItem> getCurrencyItems() {
        return (List<CurrencyItem>) getFromPersistence(Key.CURRENCY_ALL_ITEMS);
    }

    public static List<CurrencyListItem> getCurrencyMainItems() {
        return (List<CurrencyListItem>) getFromPersistence(Key.CURRENCY_MAIN_ITEMS);
    }

    public static List<CurrencyListItem> getCurrencyViewItems(int typeId) {
        return (List<CurrencyListItem>) getCurrencyViewItemsFrom(typeId);
    }
}
