public class MyBST<K extends Comparable<K>, V> {
        private Node root;
        private int size;
    
        private class Node {
            private K key;
            private V val;
            private Node left, right;
    
            public Node(K key, V val) {
                this.key = key;
                this.val = val;
            }
        }
    
        public MyBST() {
            this.size = 0;
        }
    
        public void put(K key, V val) {
            root = put(root, key, val);
        }
    
        private Node put(Node x, K key, V val) {
            if (x == null) {
                size++;
                return new Node(key, val);
            }
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = put(x.left, key, val);
            } else if (cmp > 0) {
                x.right = put(x.right, key, val);
            } else {
                x.val = val;
            }
            return x;
        }
    
        public V get(K key) {
            Node x = get(root, key);
            if (x == null) return null;
            return x.val;
        }
    
        private Node get(Node x, K key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) return get(x.left, key);
            if (cmp > 0) return get(x.right, key);
            return x;
        }
    
        public void delete(K key) {
            root = delete(root, key);
        }
    
        private Node delete(Node x, K key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = delete(x.left, key);
            } else if (cmp > 0) {
                x.right = delete(x.right, key);
            } else {
                size--;
                if (x.right == null) return x.left;
                if (x.left == null) return x.right;
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            return x;
        }
    
        private Node min(Node x) {
            if (x.left == null) return x;
            return min(x.left);
        }
    
        private Node deleteMin(Node x) {
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            return x;
        }
    
        public int size() {
            return size;
        }
    
        public Iterable<KeyValuePair<K, V>> iterator() {
            return new BSTIterator();
        }
    
        private class BSTIterator implements Iterable<KeyValuePair<K, V>> {
            private java.util.Stack<Node> stack;
            private Node current;
    
            public BSTIterator() {
                stack = new java.util.Stack<>();
                current = root;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
    
            public java.util.Iterator<KeyValuePair<K, V>> iterator() {
                return new Iterator();
            }
    
            private class Iterator implements java.util.Iterator<KeyValuePair<K, V>> {
                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }
    
                @Override
                public KeyValuePair<K, V> next() {
                    if (!hasNext()) throw new java.util.NoSuchElementException();
                    Node node = stack.pop();
                    KeyValuePair<K, V> pair = new KeyValuePair<>(node.key, node.val);
                    current = node.right;
                    while (current != null) {
                        stack.push(current);
                        current = current.left;
                    }
                    return pair;
                }
            }
        }
    
        public static class KeyValuePair<K, V> {
            private K key;
            private V value;
    
            public KeyValuePair(K key, V value) {
                this.key = key;
                this.value = value;
            }
    
            public K getKey() {
                return key;
            }
    
            public V getValue() {
                return value;
            }
        }
}
