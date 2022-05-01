package demo.jdk;

public class HashMapDemo<K, V> {


    MapNode<K, V>[] table;
    int size;

    //map 的数据结构，单向链表
    public static class MapNode<K, V> {

        int hashValue;
        K key;
        V value;
        MapNode<K, V> nextNode;

        MapNode(K key, int hashValue, V value, MapNode<K, V> nextNode) {
            this.hashValue = hashValue;
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }
    }
}
