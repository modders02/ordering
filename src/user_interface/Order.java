package user_interface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Singleton Pattern: Only one instance of OrderQueueManager can exist
class OrderQueueManager {
    private static OrderQueueManager instance;
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final List<OrderObserver> observers = new ArrayList<>();

    private OrderQueueManager() {}

    public static OrderQueueManager getInstance() {
        if (instance == null) {
            instance = new OrderQueueManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orderQueue.offer(order);
        notifyObservers(order); // Notify all kitchen staff
    }

    public Order getNextOrder() {
        return orderQueue.poll(); // Get and remove the next order
    }

    public void registerObserver(OrderObserver observer) {
        observers.add(observer); // Add kitchen staff as an observer
    }

    private void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderReceived(order);
        }
    }
}

// Observer interface
interface OrderObserver {
    void onOrderReceived(Order order);
}

// A kitchen staff that listens for new orders
class KitchenStaff implements OrderObserver {
    private final String name;

    public KitchenStaff(String name) {
        this.name = name;
    }

    @Override
    public void onOrderReceived(Order order) {
        System.out.println(name + " received order: " + order);
    }
}

// Base class for menu items
abstract class MenuItem {
    protected String name;
    protected double price;

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " - Php " + price;
    }
}

// Subclasses for different types of menu items
class MainDish extends MenuItem {
    public MainDish(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Beverage extends MenuItem {
    public Beverage(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Dessert extends MenuItem {
    public Dessert(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

// Represents a customer's order
class Order {
    private final List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return items.toString();
    }
}

class MenuItemFactory {
    public static MenuItem createMenuItem(String type, String name, double price) {
        if (type == null) {
            throw new IllegalArgumentException("Menu item type cannot be null.");
        }

        switch (type.toLowerCase()) {
            case "main":
                return new MainDish(name, price);
            case "beverage":
                return new Beverage(name, price);
            case "dessert":
                return new Dessert(name, price);
            default:
                throw new IllegalArgumentException("Unknown menu item type: " + type);
        }
    }
}
