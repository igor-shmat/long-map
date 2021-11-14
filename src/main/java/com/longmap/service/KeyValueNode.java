package com.longmap.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class KeyValueNode<V> {
    private long key;
    private V value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValueNode<?> keyValueNode = (KeyValueNode<?>) o;
        return key == keyValueNode.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}