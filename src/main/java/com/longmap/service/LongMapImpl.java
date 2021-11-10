package com.longmap.service;


import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {

    private static final float REHASH = 0.7f;
    private Bucket<V>[] buckets;
    private int numberItems;
    final Class<V> clazz;

    public LongMapImpl(Class<V> clazzOfValue) {
        this(16, clazzOfValue);
    }

    public LongMapImpl(int bucketsSize, Class<V> clazz) {
        this.clazz = clazz;
        buckets = createNewBucketsArray(bucketsSize);
    }

    public V put(long key, V value) {
        if (currentCapacity() >= REHASH) {
            rehash();
        }
        Bucket<V> bucket = getBucket(key);
        if (!bucket.isBucketContainsKey(key)) {
            KeyValue<V> keyValueNode = new KeyValue<>(key, value);
            bucket.addItem(keyValueNode);
            numberItems++;
        } else {
            KeyValue<V> node = bucket.getItem(key);
            node.setValue(value);
        }
        return value;
    }

    public V get(long key) {
        Bucket<V> bucket = getBucket(key);
        KeyValue<V> keyValue = bucket.getItem(key);
        if (keyValue == null) return null;
        return keyValue.getValue();
    }

    public V remove(long key) {
        Bucket<V> bucket = getBucket(key);
        KeyValue<V> item = bucket.removeItem(key);
        if (item != null) {
            numberItems--;
            return item.getValue();
        }
        return null;
    }

    public boolean isEmpty() {
        return numberItems == 0;
    }

    public boolean containsKey(long key) {
        Bucket<V> bucket = getBucket(key);
        return bucket.isBucketContainsItemByKey(key);
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < buckets.length; i++) {
            Bucket<V> bucket = buckets[i];
            if (bucket == null) {
                continue;
            }
            ;
            return bucket.isBucketContainsItemByValue(value);
        }
        return false;
    }

    public long[] keys() {
        long[] allKeys = new long[numberItems];
        int addedKeys = 0;
        for (int i = 0; i < buckets.length; i++) {
            Bucket<V> bucket = buckets[i];
            if (bucket == null) {
                continue;
            }
            ;
            long[] bucketKeys = bucket.getAllKeys();
            for (int j = addedKeys, k = 0; k < bucketKeys.length; j++, k++) {
                allKeys[j] = bucketKeys[k];
            }
            addedKeys += bucketKeys.length;
        }
        return allKeys;
    }

    public V[] values() {
        List<V> all = new ArrayList<>();
        for (int i = 0; i < buckets.length; i++) {
            Bucket<V> bucket = buckets[i];
            if (bucket == null) continue;
            all.addAll(bucket.getAllValue());
        }
        V[] result = (V[]) Array.newInstance(clazz, all.size());
        all.toArray(result);
        return result;
    }

    public long size() {
        return numberItems;
    }

    public void clear() {
        if (0 == size())
            return;
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        numberItems = 0;
    }

    private void rehash() {
        int newCapacity = buckets.length * 2;
        Bucket<V>[] newBuckets = createNewBucketsArray(newCapacity);
        for (int i = 0; i < buckets.length; i++) {
            Bucket<V> bucket = buckets[i];
            if (bucket == null) continue;
            bucket.getAllNodes().forEach(item -> moveKeyValuePairToNewBuckets(newBuckets, item));
        }
        buckets = newBuckets;
    }

    ;

    private void moveKeyValuePairToNewBuckets(Bucket<V>[] newBucketsArray, KeyValue<V> pair) {
        Bucket<V> bucket = getBucket(pair.getKey(), newBucketsArray);
        bucket.addItem(pair);
    }

    private float currentCapacity() {
        return (float) numberItems / buckets.length;
    }

    private Bucket<V> getBucket(long key, Bucket<V>[] bucketsArray) {
        Bucket<V> bucket = bucketsArray[calculateBucketAddress(key, bucketsArray.length)];
        if (bucket == null) {
            bucket = new Bucket<>();
            bucketsArray[calculateBucketAddress(key, bucketsArray.length)] = bucket;
        }
        return bucket;
    }

    private int calculateBucketAddress(long key, int bucketsLength) {
        return (int) Math.abs(key) % bucketsLength;
    }

    private Bucket<V> getBucket(long key) {
        return getBucket(key, buckets);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Bucket<V>[] createNewBucketsArray(int size) {
        return (Bucket<V>[]) new Bucket[size];
    }
}