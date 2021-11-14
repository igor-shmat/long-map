package com.longmap.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Bucket<V> {
    private List<KeyValueNode<V>> nodes;

    public Bucket() {
        nodes = new ArrayList<>();
    }

    public void addItem(KeyValueNode<V> item) {
        addItemToEmptyCell(item);
    }

    public void addItemToEmptyCell(KeyValueNode<V> item) {
        nodes.add(item);
    }

   public boolean isBucketContainsItemByValue(V value) {
        return searchItemByValue(value) != null;
    }

    boolean isBucketContainsItemByKey(long key){
        return searchItemByKey(key) != null;
    }

    public KeyValueNode<V> searchItemByValue(V value) {
        Optional<KeyValueNode<V>> node = nodes.stream()
                .filter(item -> item.getValue() != null && item.getValue().equals(value))
                .findFirst();
        return node.orElse(null);
    }

    public KeyValueNode<V> searchItemByValue(long key){
        Optional<KeyValueNode<V>> node = nodes.stream()
                .filter(item -> item.getKey() == key)
                .findFirst();
        return node.orElse(null);
    }

    public KeyValueNode<V> searchItemByKey(long key){
        Optional<KeyValueNode<V>> node = nodes.stream()
                .filter(item -> item.getKey() == key)
                .findFirst();
        return node.orElse(null);
    }

    boolean isBucketContainsKey(long key){
        return searchItemByValue(key) != null;
    }

    public KeyValueNode<V> getItem(long key) {
        KeyValueNode<V> item = searchItemByValue(key);
        return item;
    }

    public List<KeyValueNode<V>> getAllNodes(){
        return nodes;
    }

    public long[] getAllKeys(){
        return nodes
                .stream()
                .mapToLong(KeyValueNode::getKey).toArray();
    }

    public List<V> getAllValue(){
        return nodes.stream()
                .map(KeyValueNode:: getValue)
                .collect(Collectors.toList());
    }

    public KeyValueNode<V> removeItem(long key) {
        KeyValueNode<V> itemToRemove = searchItemByKey(key);
        if(itemToRemove == null) return null;
        nodes.remove(itemToRemove);
        return itemToRemove;
    }
}
