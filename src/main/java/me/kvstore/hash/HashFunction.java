package me.kvstore.hash;

public interface HashFunction {

  int hash(byte[] data);

  int hash(int data);

}
