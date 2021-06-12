package me.kvstore;


import java.util.HashMap;

public class Node {

  private String id;
  private String name;

  public String getName() {
    return name;
  }

  private Node parentNode;
  private int partitions;

  private HashMap<String, String> map = new HashMap<>();

  public String getId() {
    return id;
  }

  public Node(String id, String name, int partitions) {
    this.id = id;
    this.name = name;
    this.partitions = partitions;
  }

  public int getPartitions() {
    return this.partitions;
  }


  public void put(String key, String value) {
    map.put(key, value);
  }

  public String get(String key) {
    return map.get(key);
  }

  public int count() {
    return map.size();
  }
}
