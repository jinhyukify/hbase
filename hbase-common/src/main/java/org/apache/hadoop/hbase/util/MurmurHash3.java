/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.util;

import org.apache.yetus.audience.InterfaceAudience;
import org.apache.yetus.audience.InterfaceStability;
import com.dynatrace.hash4j.hashing.Hasher32;
import com.dynatrace.hash4j.hashing.Hashing;

/**
 * This is a very fast, non-cryptographic hash suitable for general hash-based lookup. See
 * http://code.google.com/p/smhasher/wiki/MurmurHash3 for details.
 * <p>
 * MurmurHash3 is the successor to MurmurHash2. It comes in 3 variants, and the 32-bit version
 * targets low latency for hash table use.
 * </p>
 */
@InterfaceAudience.Private
@InterfaceStability.Stable
public class MurmurHash3 extends Hash {
  private static MurmurHash3 _instance = new MurmurHash3();

  public static Hash getInstance() {
    return _instance;
  }

  /** Returns the MurmurHash3_x86_32 hash. */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings("SF")
  @Override
  public <T> int hash(HashKey<T> hashKey, int seed) {
    Hasher32 hasher = seed == 0 ? Hashing.murmur3_32() : Hashing.murmur3_32(seed);
    return hasher.hashBytesToInt(hashKey, 0, hashKey.length(), HashKeyByteAccess.INSTANCE);
  }
}
