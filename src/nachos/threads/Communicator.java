package nachos.threads;

import nachos.machine.*;

/**
 * A communicator allows threads to synchronously exchange 32-bit messages.
 */
public class Communicator {

    private boolean spoken = false;
    private int word;

    private Lock lock;
    private Condition2 conditon2;

    public Communicator() {
        lock = new Lock();
        conditon2 = new Condition2(lock);
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * word to the listener.
     *
     * @param word the integer to transfer.
     */
    public void speak(int word) {
        lock.acquire();

        while (spoken) {
        	conditon2.sleep(); 
        }

        this.word = word;
        spoken = true;
        conditon2.wake(); 

        lock.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the word that thread passed to speak().
     *
     * @return the integer transferred.
     */
    public int listen() {
        lock.acquire();

        while (!spoken) {
        	conditon2.sleep(); 
        }

        int listenedWord = this.word;
        spoken = false;
        conditon2.wake(); 

        lock.release();

        return listenedWord;
    }
}
