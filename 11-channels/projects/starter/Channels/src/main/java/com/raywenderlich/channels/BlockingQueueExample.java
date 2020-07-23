package com.raywenderlich.channels;

import java.util.concurrent.SynchronousQueue;

public class BlockingQueueExample {

  public static void main(String[] args) {

    SynchronousQueue<String> queue = new SynchronousQueue<>();

    System.out.println("Beginning:");
    try {
      System.out.println("Let's put in basket: Apple");
      queue.put("Apple");
      System.out.println("Let's put in basket: Banana");
      queue.put("Banana");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Done!");
  }
}