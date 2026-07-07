public class BSTNode<K extends Comparable<? super K>, T> {
    public K key;
    public T value;
    public BSTNode<K, T> left;
    public BSTNode<K, T> right;
    public int height;

    public BSTNode(K key, T value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }
}