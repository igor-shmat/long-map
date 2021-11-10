package com.longmap;

import com.longmap.service.LongMap;
import com.longmap.service.LongMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class LongMapApplicationTests {

    private LongMap<String> longMap = null;

    @BeforeEach
    void setUp() {
        longMap = new LongMapImpl<>(String.class);
    }

    // check Put method
    @Test
    public void testPutOneElement() {
        Long key = 1L;
        String testValue = "TestMap";
        String actualValue = longMap.put(key, testValue);
        // asset
        Assert.isTrue(1 == longMap.size(), "map size can be 1");
        Assert.isTrue(actualValue.equals(testValue), "testValue and actualValue can be equals");
    }

    @Test
    public void testPutOneElementWithNegativeKey() {
        // arrange
        String testValue = "TestMap";
        Long key = -15L;
        // act
        String actualValue = longMap.put(key, testValue);
        // asset
        Assert.isTrue(1 == longMap.size(), "map size can be 1");
        Assert.isTrue(actualValue.equals(testValue), "testValue and actualValue can be equals");
    }

    @Test
    public void testPutTwoSameElementAndSameKey() {
        Long key = 1L;
        String testValue = "TestMap";
        String actualValue1 = longMap.put(key, testValue);
        String actualValue2 = longMap.put(key, testValue);
        // asset
        Assert.isTrue(1 == longMap.size(), "map size can be 1");
        Assert.isTrue(actualValue1.equals(testValue), "actualValue1 and actualValue can be equals");
        Assert.isTrue(actualValue2.equals(testValue), "actualValue2 and actualValue can be equals");
    }

    @Test
    public void testPutTwoVariousElementAndSameKey() {
        Long key = 1L;
        String testValue1 = "TestMap";
        String testValue2 = "TestMapNot";

        // put Various element in map
        longMap.put(key, testValue1);
        longMap.put(key, testValue2);

        // assert
        Assert.isTrue(1 == longMap.size(), "map size can be 1");
        Assert.isTrue(longMap.get(key).equals(testValue2), "ongMap.get(key) and testValue2 can be equals");
    }

    @Test
    public void testPutKeyZero() {
        String testValue = "TestMap";
        Long keyZero = 0L;
        longMap.put(keyZero, testValue);

        // assert
        Assert.isTrue(1 == longMap.size(), "map size can be 1");
        Assert.isTrue(longMap.get(keyZero).equals(testValue), "ongMap.get(key) and testValue2 can be equals");
    }

    @Test
    public void testPutTwoEqualsElementAndTwoVariousKey() {
        String testValue = "TestMap";
        Long key1 = 1L;
        Long key2 = 2L;

        String actualValue1 = longMap.put(key1, testValue);
        String actualValue2 = longMap.put(key2, testValue);
        // asset
        Assert.isTrue(2 == longMap.size(), "map size can be 2");
        Assert.isTrue(actualValue1.equals(testValue), "actualValue1 and actualValue can be equals");
        Assert.isTrue(actualValue2.equals(testValue), "actualValue2 and actualValue can be equals");
    }

    // check Get method
    @Test
    public void testGetKey() {
        Long key = 1L;
        String testValue = "TestMap";

        longMap.put(key, testValue);
        String actualValue = longMap.get(key);
        // asset
        Assert.isTrue(testValue.equals(actualValue), "testValue and actualValue can be equals");
    }

    @Test
    public void testGetKeyNotExist() {
        Long keyThatNotExist = 1L;
        String actualValue = longMap.get(keyThatNotExist);
        // asset
        Assert.isNull(actualValue, "actualValue may by null");
    }

    // Remove
    @Test
    public void testRemoveKey() {
        String testValue = "TestMap";
        Long key = 1L;
        longMap.put(key, testValue);
        String actualValue = longMap.remove(key);
        // asset
        Assert.isTrue(0 == longMap.size(), "after remove longMap size can be '0'");
        Assert.isNull(longMap.get(key), "key may by null");
    }

    // empty
    @Test
    public void testIsEmptyTrue() {
        Boolean isEmpty = longMap.isEmpty();
        // asset
        Assert.isTrue(0 == longMap.size(), "longMap is empty");
        Assert.isTrue(isEmpty, "isEmpty is true");
    }

    @Test
    public void testIsEmptyFalse() {
        String testValue = "TestMap";
        Long key = 1L;
        longMap.put(key, testValue);
        Boolean isEmpty = longMap.isEmpty();
        // asset
        Assert.isTrue(1 == longMap.size(), "longMap == 1");
        Assert.isTrue(!isEmpty, "isEmpty is true");
    }

    @Test
    public void testContainsValue() {
        String testValue = "TestMap";
        Long key = 1L;
        longMap.put(key, testValue);
        Boolean isContains = longMap.containsValue(testValue);
        // asset
        Assert.isTrue(1 == longMap.size(), "longMap size == 1");
        Assert.isTrue(isContains, "isContains == true");
    }

    @Test
    public void testKeys() {
        // arrange
        String testValue1 = "TestMap1";
        Long key1 = 1L;
        String testValue2 = "TestMap2";
        Long key2 = 2L;
        String testValue3 = "TestMap3";
        Long key3 = 3L;
        longMap.put(key1, testValue1);
        longMap.put(key2, testValue2);
        longMap.put(key3, testValue3);
        long[] expected = {key1, key2, key3};

        long[] actual = longMap.keys();
        // asset
        Assert.isTrue(actual.length == expected.length, "actual.length == expected.length");
    }

    @Test
    public void testClearAll_GivenMapIsNotEmpty_whenInvokeTheMethod_thenMapIsCleared() {
        // arrange
        String testValue1 = "TestMap1";
        Long key1 = 1L;
        String testValue2 = "TestMap2";
        Long key2 = 2L;
        String testValue3 = "TestMap3";
        Long key3 = 3L;
        longMap.put(key1, testValue1);
        longMap.put(key2, testValue2);
        longMap.put(key3, testValue3);
        longMap.clear();
        // asset
        Assert.isTrue(0 == longMap.size(), "0 == longMap.size()");
        Assert.isTrue(0 == longMap.keys().length, "0 == longMap.keys().length");
    }
}
