public class BSTTest {
    public static void main(String[] args) {
        MyBST<Integer, String> tree = new MyBST<>();
        
        tree.put(5, "five");
        tree.put(3, "three");
        tree.put(7, "seven");
        tree.put(1, "one");
        tree.put(9, "nine");
        
        System.out.println("Size of tree: " + tree.size());
        
        System.out.println("\nIterating through tree:");
        for (var elem : tree.iterator()) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
        
        System.out.println("\nDeleting key 3...");
        tree.delete(3);
        System.out.println("New size: " + tree.size());
        
        System.out.println("\nIterating after deletion:");
        for (var elem : tree.iterator()) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
    }
} 