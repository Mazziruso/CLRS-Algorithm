package DataStructure;

public class BagMain {

    public static void main(String[] args) {

        BagArray<Integer> bagA = new BagArray<> (30);
        for(int i = 100; i>80; i--) {
            bagA.add(Integer.valueOf(i));
        }

        Object[] bagOut = bagA.toArray();
        printArrays(bagOut);
    }

    public static void printArrays(Object[] t) {
        for(int i=0; i<t.length; i++) {
            System.out.print(t[i] + " ");
        }
        System.out.println();
    }

}

final class BagArray<T> implements BagInterface<T> {

    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    private boolean initialized = false;
    private static final int MAX_CAPACITY = 100;

    public BagArray(int capacity) {
        if(capacity <= MAX_CAPACITY) {
            this.numberOfEntries = 0;
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[]) new Object[capacity];
            bag = tempBag;
            initialized = true;
        } else {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds allowed maximum.");
        }
    }

    public BagArray() {
        this(DEFAULT_CAPACITY);
    }

    private void checkInitialization() {
        if(!initialized) {
            throw new SecurityException("BagArray object is not initialized properly.");
        }
    }

    public boolean isArrayFull() {
        checkInitialization();
        return (this.numberOfEntries >= this.bag.length);
    }

    //return true if the addition is successful, or false if not
    public boolean add(T newEntry) {
        checkInitialization();
        boolean flag = true;
        if(isArrayFull()) {
            flag = false;
        } else {
            this.bag[this.numberOfEntries] = newEntry;
            this.numberOfEntries++;
        }

        return flag;
    }

    //return a newly allocated array of all the entries in the bag
    public T[] toArray() {
        checkInitialization();
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[this.numberOfEntries];
        for(int i=0; i<this.numberOfEntries; i++) {
            result[i] = this.bag[i];
        }
        return result;
    }

    public int getCurrentSize() {
        return this.numberOfEntries;
    }

    public boolean isEmpty() {
        return (this.numberOfEntries == 0);
    }

    public T remove() {
        checkInitialization();
        T result = null;
        if(this.numberOfEntries > 0) {
            result = this.bag[this.numberOfEntries-1];
            this.bag[this.numberOfEntries-1] = null;
            this.numberOfEntries--;
        }
        return result;
    }

    public boolean remove(T anEntry) {
        checkInitialization();
        boolean result = false;
        int index = 0;
        while((!result) && (index<this.numberOfEntries)) {
            if(anEntry.equals(this.bag[index])) {
                result = true;
            }
            index++;
        }
        this.bag[index-1] = this.bag[this.numberOfEntries-1];
        this.bag[this.numberOfEntries-1] = null;
        this.numberOfEntries--;
        return result;
    }

    public void clear() {
        while(!isEmpty()) {
            remove();
        }
    }

    public int getFrequencyOf(T anEntry) {
        checkInitialization();
        int count = 0;
        for(int i=0; i<this.numberOfEntries; i++) {
            if(anEntry.equals(this.bag[i])) {
                count++;
            }
        }

        return count;
    }

    public boolean contains(T anEntry) {
        checkInitialization();
        boolean found = false;
        int index = 0;

        while((!found) && (index<this.numberOfEntries)) {
            if(anEntry.equals(this.bag[index])) {
                found = true;
            }
            index++;
        }

        return found;
    }

}

interface BagInterface<T> {
    public int getCurrentSize();

    public boolean isEmpty();

    //return true if the addition is successful, or false if not
    public boolean add(T newEntry);

    //return Either the removed entry, if the removal was successful, or null
    public  T remove();

    public boolean remove(T anEntry);

    public void clear();

    //return the number of times anEntry appears in the bag
    public int getFrequencyOf(T anEntry);

    public boolean contains(T anEntry);

    //return a newly allocated array of all the entries in the bag
    //if the bag is empty, the returned array is empty
    public T[] toArray();

}