public class BSTSet<K extends Comparable<? super K>> implements Set<K> {
    private BSTNode<K, Boolean> root;
    private int size;

    public BSTSet() {
        root = null;
        size = 0;
    }

    public int size() { return size; }
    public boolean empty() { return size == 0; }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean contains(K k) {
        return searchNode(root, k) != null;
    }

    public int nbKeyComp(K k) {
        return countComparisons(root, k);
    }

    public boolean insert(K k) {
        if (contains(k)) return false;
        root = insertNode(root, k);
        size++;
        return true;
    }

    public boolean remove(K k) {
        if (!contains(k)) return false;
        root = removeNode(root, k);
        size--;
        return true;
    }

    public List<K> getKeys() {
        List<K> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }

    private BSTNode<K, Boolean> searchNode(BSTNode<K, Boolean> node, K k) {
        if (node == null) return null;
        int cmp = k.compareTo(node.key);
        if (cmp < 0) return searchNode(node.left, k);
        if (cmp > 0) return searchNode(node.right, k);
        return node;
    }

    private int countComparisons(BSTNode<K, Boolean> node, K k) {
        if (node == null) return 0;
        int cmp = k.compareTo(node.key);
        if (cmp < 0) return 1 + countComparisons(node.left, k);
        if (cmp > 0) return 1 + countComparisons(node.right, k);
        return 1;
    }

    private BSTNode<K, Boolean> insertNode(BSTNode<K, Boolean> node, K k) {
        if (node == null) return new BSTNode<>(k, true);
        int cmp = k.compareTo(node.key);
        if (cmp < 0) node.left = insertNode(node.left, k);
        else node.right = insertNode(node.right, k);
        return node;
    }

    private BSTNode<K, Boolean> removeNode(BSTNode<K, Boolean> node, K k) {
        if (node == null) return null;
        int cmp = k.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeNode(node.left, k);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, k);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            BSTNode<K, Boolean> minNode = findMin(node.right);
            node.key = minNode.key;
            node.right = removeNode(node.right, minNode.key);
        }
        return node;
    }

    private BSTNode<K, Boolean> findMin(BSTNode<K, Boolean> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private void inorder(BSTNode<K, Boolean> node, List<K> list) {
        if (node == null) return;
        inorder(node.left, list);
        list.insert(node.key);
        inorder(node.right, list);
    }
}