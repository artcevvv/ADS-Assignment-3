import java.util.Random;

public class HashTableTest {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        Random random = new Random();
        
        // Add 10000 random elements
        for (int i = 0; i < 10000; i++) {
            MyTestingClass key = new MyTestingClass(random.nextInt(1000), "Name" + i);
            Student value = new Student("Student" + i, random.nextInt(100));
            table.put(key, value);
        }
        
        // Print distribution
        System.out.println("Hash Table Distribution:");
        for (int i = 0; i < table.getM(); i++) {
            System.out.println("Bucket " + i + ": " + table.getBucketSize(i) + " elements");
        }
    }
} 