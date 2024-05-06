/*
package com.spms.sports.manager;
import java.util.concurrent.*;

public class InventoryManager {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();

    public InventoryManager() {
        // Initialize inventory with sample data
        inventory.put("Book1", 100);
        inventory.put("Book2", 150);
    }

    public CompletableFuture<Void> processSale(String bookId, int quantity) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Processing sale for " + bookId);
            inventory.computeIfPresent(bookId, (key, val) -> val - quantity);
        }, executorService);
    }

    public CompletableFuture<Void> restock(String bookId, int quantity) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Restocking " + bookId);
            inventory.compute(bookId, (key, val) -> (val == null ? 0 : val) + quantity);
        }, executorService);
    }

    public CompletableFuture<Integer> checkAvailability(String bookId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Checking availability for " + bookId);
            return inventory.getOrDefault(bookId, 0);
        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        InventoryManager manager = new InventoryManager();
        manager.processSale("Book1", 1).thenRun(() -> 
            System.out.println("Sale processed"));
        manager.restock("Book1", 5).thenRun(() -> 
            System.out.println("Restocked"));
        manager.checkAvailability("Book1").thenAccept(stock -> 
            System.out.println("Availability: " + stock));

        manager.shutdown();
    }
}
*/