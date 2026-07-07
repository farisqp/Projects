public class BSTMap<K extends Comparable<? super K>, T> implements Map<K, T> {
    private BSTNode<K, T> root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    public int size() { return size; }
    public boolean empty() { return size == 0; }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean insert(K key, T data) {
        if (get(key) != null) return false;
        root = insertNode(root, key, data);
        size++;
        return true;
    }

    public T get(K key) {
        BSTNode<K, T> node = searchNode(root, key);
        return node == null ? null : node.value;
    }

    public boolean update(K key, T e) {
        BSTNode<K, T> node = searchNode(root, key);
        if (node == null) return false;
        node.value = e;
        return true;
    }

    public boolean remove(K key) {
        if (get(key) == null) return false;
        root = removeNode(root, key);
        size--;
        return true;
    }

    public int nbKeyComp(K key) {
        return countComparisons(root, key);
    }

    public List<K> getKeys() {
        List<K> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }

    private BSTNode<K, T> searchNode(BSTNode<K, T> node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return searchNode(node.left, key);
        if (cmp > 0) return searchNode(node.right, key);
        return node;
    }

    private int countComparisons(BSTNode<K, T> node, K key) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return 1 + countComparisons(node.left, key);
        if (cmp > 0) return 1 + countComparisons(node.right, key);
        return 1;
    }

    private BSTNode<K, T> insertNode(BSTNode<K, T> node, K key, T data) {
        if (node == null) return new BSTNode<>(key, data);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insertNode(node.left, key, data);
        else node.right = insertNode(node.right, key, data);
        return node;
    }

    private BSTNode<K, T> removeNode(BSTNode<K, T> node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeNode(node.left, key);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            BSTNode<K, T> minNode = findMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = removeNode(node.right, minNode.key);
        }
        return node;
    }

    private BSTNode<K, T> findMin(BSTNode<K, T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void inorder(BSTNode<K, T> node, List<K> list) {
        if (node == null) return;
        inorder(node.left, list);
        list.insert(node.key);
        inorder(node.right, list);
    }
}