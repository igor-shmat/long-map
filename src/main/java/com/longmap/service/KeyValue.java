package com.longmap.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyValue<V> {
    private long key;
    private V value;
}
