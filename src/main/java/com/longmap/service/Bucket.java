package com.longmap.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Bucket<V> {
    private List<KeyValue<V>> nodes;

    public Bucket() {
        nodes = new ArrayList<>();
    }

    public void addItem(KeyValue<V> item) {
        addItemToEmptyCell(item);
    }

    public void addItemToEmptyCell(KeyValue<V> item) {
        nodes.add(item);
    }

   public boolean isBucketContainsItemByValue(V value) {
        return searchItemByValue(value) != null;
    }

    boolean isBucketContainsItemByKey(long key){
        return searchItemByKey(key) != null;
    }

    public KeyValue<V> searchItemByValue(V value) {
        Optional<KeyValue<V>> node = nodes.stream()
                .filter(item -> item.getValue() != null && item.getValue().equals(value))
                .findFirst();
        return node.orElse(null);
    }

    public KeyValue<V> searchItemByValue(long key){
        Optional<KeyValue<V>> node = nodes.stream()
                .filter(item -> item.getKey() == key)
                .findFirst();
        return node.orElse(null);
    }

    public KeyValue<V> searchItemByKey(long key){
        Optional<KeyValue<V>> node = nodes.stream()
                .filter(item -> item.getKey() == key)
                .findFirst();
        return node.orElse(null);
    }

    boolean isBucketContainsKey(long key){
        return searchItemByValue(key) != null;
    }

    public KeyValue<V> getItem(long key) {
        KeyValue<V> item = searchItemByValue(key);
        return item;
    }

    public List<KeyValue<V>> getAllNodes(){
        return nodes;
    }

    public long[] getAllKeys(){
        return nodes
                .stream()
                .mapToLong(KeyValue::getKey).toArray();
    }

    public List<V> getAllValue(){
        return nodes.stream()
                .map(KeyValue:: getValue)
                .collect(Collectors.toList());
    }

    public KeyValue<V> removeItem(long key) {
        KeyValue<V> itemToRemove = searchItemByKey(key);
        if(itemToRemove == null) return null;
        nodes.remove(itemToRemove);
        return itemToRemove;
    }
}
