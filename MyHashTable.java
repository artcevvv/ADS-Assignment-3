import java.lang.FdLibm.Pow;

public class MyHashTable<K, V> {
    private class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11; // default number of buckets
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        chainArray = new HashNode[M];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int M) {
        this.M = M;
        chainArray = new HashNode[M];
        size = 0;
    }

    private int hash(K key) {
        if (key instanceof String) {
            return customStringHash((String) key);
        } else if (key instanceof Integer) {
            return customIntegerHash((Integer) key);
        } else {
            String keyString = key.toString();
            return customStringHash((String) key);
        }
    }

    private int customStringHash(String str) {
        int hash = 0;
        int prime = 31;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash * prime + str.charAt(i)) % M;
        }

        return hash;
    }

    private long customIntegerHash(Integer num) {
        int value = num.intValue();
        // knuth's multiplecative method
        return ((value * 2654435761L) % (1L << 32)) % M;
    }

    private boolean valuesEqual(V value1, V value2) {
        if (value1 == value2)
            return true;
        if (value1 == null || value2 == null)
            return false;

        return value1.toString().equals(value2.toString());
    }

    private boolean keysEqual(K key1, K key2) {
        if (key1 == key2)
            return true;
        if (key1 == null || key2 == null)
            return false;

        if (key1 instanceof String && key2 instanceof String) {
            String str1 = (String) key1;
            String str2 = (String) key2;

            if (str1.length() != str2.length())
                return false;

            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i))
                    return false;
            }
            return true;
        } else {
            return key1.toString().equals(key2.toString());
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        int index = hash(key);
        HashNode<K, V> head = chainArray[index];

        while (head != null) {
            if (keysEqual(head.key, key)) {
                head.value = value;
                return;
            }

            head = head.next;
        }

        head = chainArray[index];
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = head;
        chainArray[index] = newNode;
        size++;

        double loadFactor = (1.0 * size) / M;
        if (loadFactor > 0.75) {
            resize(2 * M);
        }
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        int index = hash(key);
        HashNode<K, V> head = chainArray[index];

        while (head != null) {
            if (keysEqual(head.key, key)) {
                return head.value;
            }

            head = head.next;
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        int index = hash(key);
        HashNode<K, V> head = chainArray[index];
        HashNode<K, V> prev = null;

        while (head != null) {
            if (keysEqual(head.key, key)) {
                if (prev == null) {
                    chainArray[index] = head.next;
                } else {
                    prev.next = head.next;
                }

                size--;
                return head.value;
            }
            prev = head;
            head = head.next;
        }

        return null;
    }

    public boolean contains(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> head = chainArray[i];
            while (head != null) {
                if (valuesEqual(head.value, value)) {
                    return true;
                }
                head = head.next;
            }
        }

        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> head = chainArray[i];
            while (head != null) {
                if (valuesEqual(head.value, value)) {
                    return head.key;
                }
                head = head.next;
            }
        }
        return null;
    }

    public void display() {
        for (int i = 0; i < M; i++) {
            System.out.print("Chain " + i + ": ");
            HashNode<K, V> head = chainArray[i];
            while (head != null) {
                System.out.print(head + " -> ");
                head = head.next;
            }
            System.out.println("null");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        HashNode<K, V>[] oldChainArray = chainArray;
        int oldM = M;

        M = newSize;
        size = 0;
        chainArray = (HashNode<K, V>[]) new HashNode[M];

        for (int i = 0; i < oldM; i++) {
            HashNode<K, V> head = oldChainArray[i];
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}
