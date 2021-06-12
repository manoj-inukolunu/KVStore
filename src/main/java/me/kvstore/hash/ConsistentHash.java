package me.kvstore.hash;

import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import me.kvstore.Node;

public class ConsistentHash {

  private final List<Node> nodes = Lists.newArrayList();
  private final TreeMap<Integer, Node> map = new TreeMap<>();
  private final HashFunction function = new Murmur3HashFunction();

  public void printStats() {
    for (Node node : nodes) {
      System.out.println(node.getId() + " - " + node.getName() + " : " + node.count());
    }
  }

  public void addNode(Node node) {
    nodes.add(node);
    for (Node _node : nodes) {
      for (int i = 0; i < _node.getPartitions(); i++) {
        String id = _node.getId() + ":" + i;
        map.put(function.hash(id.getBytes(StandardCharsets.UTF_8)), _node);
      }
    }
  }

  public void put(String key, String value) {
    int hash = function.hash(key.getBytes(StandardCharsets.UTF_8));
    Integer higher = map.higherKey(hash);
    if (higher == null) {
      //insert to first node
      map.firstEntry().getValue().put(key, value);
    } else {
      map.get(higher).put(key, value);
    }
  }

  public String get(String key) {
    int hash = function.hash(key.getBytes(StandardCharsets.UTF_8));
    Integer higher = map.higherKey(hash);
    if (higher == null) {
      //insert to first node
      return map.firstEntry().getValue().get(key);
    } else {
      return map.get(higher).get(key);
    }
  }


  public static void main(String args[]) {

    List<Node> nodes = Lists.newArrayList(new Node(UUID.randomUUID().toString(), "NodeA", 100),
        new Node(UUID.randomUUID().toString(), "NodeB", 200), new Node(UUID.randomUUID().toString(), "NodeC", 100),
        new Node(UUID.randomUUID().toString(), "NodeD", 400));

    ConsistentHash hash = new ConsistentHash();

    for (Node node : nodes) {
      hash.addNode(node);
    }

    HashMap<String, String> map = new HashMap<>();
    for (int i = 0; i < 10000; i++) {
      String key = UUID.randomUUID().toString();
      String value = UUID.randomUUID().toString();
      map.put(key, value);
      hash.put(key, value);
    }
    for (String key : map.keySet()) {
      if (!hash.get(key).equals(map.get(key))) {
        System.out.println(key);
      }
    }

    hash.printStats();
  }
}
