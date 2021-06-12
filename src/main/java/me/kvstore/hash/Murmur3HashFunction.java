package me.kvstore.hash;

import com.google.common.hash.Hashing;

public class Murmur3HashFunction implements HashFunction {

  @Override
  public int hash(byte[] data) {
    return Hashing.murmur3_32().hashBytes(data).asInt();
  }

  @Override
  public int hash(int data) {
    return Hashing.murmur3_32().hashInt(data).asInt();
  }
}
