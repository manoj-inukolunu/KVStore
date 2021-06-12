package me.kvstore.hash;

public class FnvHashFunction implements HashFunction {


  private static final long FNV_BASIS = 0x811c9dc5;
  private static final long FNV_PRIME = (1 << 24) + 0x193;
  public static final long FNV_BASIS_64 = 0xCBF29CE484222325L;
  public static final long FNV_PRIME_64 = 1099511628211L;

  private static int abs(int a) {
    if (a >= 0) {
      return a;
    } else if (a != Integer.MIN_VALUE) {
      return -a;
    }
    return Integer.MAX_VALUE;
  }

  @Override
  public int hash(byte[] key) {
    long hash = FNV_BASIS;
    for (int i = 0; i < key.length; i++) {
      hash ^= 0xFF & key[i];
      hash *= FNV_PRIME;
    }

    return abs((int) hash);
  }

  @Override
  public int hash(int data) {
    return 0;
  }

  public long hash64(long val) {
    long hashval = FNV_BASIS_64;

    for (int i = 0; i < 8; i++) {
      long octet = val & 0x00ff;
      val = val >> 8;

      hashval = hashval ^ octet;
      hashval = hashval * FNV_PRIME_64;
    }
    return Math.abs(hashval);
  }

}
