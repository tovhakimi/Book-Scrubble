package test;

import java.security.MessageDigest;
import java.util.BitSet;
import java.math.BigInteger;


public class BloomFilter {
	private final BitSet bitSet;
    private final MessageDigest[] hashFunctions;
    private final int Size;

    public BloomFilter(int size, String... algs){
        bitSet = new BitSet(size);
        hashFunctions = new MessageDigest[algs.length];
        int i = 0;
        for(String hashFunction : algs){
            try{
                hashFunctions[i] = MessageDigest.getInstance(hashFunction);
                i++;
            }
            catch (Exception e){
                System.err.println("The hash name isn't valid.");
            }
        }
        Size = size;
    }

    public void add(String word){
        for(MessageDigest hashFunction : hashFunctions){
            bitSet.set(getWordBit(word,hashFunction));
        }
    }

    public int getWordBit(String word, MessageDigest MD){
        byte[] bts = MD.digest(word.getBytes());
        BigInteger holder = new BigInteger(bts);
        return Math.abs(holder.intValue() % Size);
    }

    public boolean contains(String word){
        for(MessageDigest hashFunc : hashFunctions){
            if(!bitSet.get(getWordBit(word, hashFunc))){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder bitsString = new StringBuilder();
        for(int i = 0 ; i < bitSet.length(); i++){
            bitsString.append(bitSet.get(i) ? "1" : "0");
        }
        return bitsString.toString();
    }
}
