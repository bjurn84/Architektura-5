import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class User {
    private String username;
    private String password;
    private String email;

    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class UserService {
    private List<User> users;
    private final Pattern emailRegex = Pattern.compile("^(.+)@(.+)$");

    public UserService() {
        users = new ArrayList<>();
    }

    public boolean register(String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        Matcher matcher = emailRegex.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        if (password.length() < 6) {
            return false;
        }

        User newUser = new User(username, password, email);
        users.add(newUser);
        return true;
    }
}

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class CartService {
    private List<CartItem> cartItems;

    public CartService() {
        cartItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        CartItem newCartItem = new CartItem(product, quantity);
        cartItems.add(newCartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}

class Order {
    private List<CartItem> items;
    private double totalPrice;
    private String status;

    public Order(List<CartItem> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = "Pending";
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class OrderService {
    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public void placeOrder(User user, CartService cartService) {
        double totalPrice = 0;
        for (CartItem item : cartService.getCartItems()) {
            totalPrice += (item.getProduct().getPrice() * item.getQuantity());
        }

        Order newOrder = new Order(cartService.getCartItems(), totalPrice);
        orders.add(newOrder);

        cartService.clearCart();
    }
}

class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        boolean registrationResult = userService.register("user1", "password123", "user1@example.com");

        if (registrationResult) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed!");
        }

        Product product1 = new Product("Product 1", 100, 10);
        CartService cartService = new CartService();
        cartService.addItem(product1, 2);

        System.out.println("Вы добавили следующие товары в корзину:");
        List<CartItem> cartItems = cartService.getCartItems();
        for (CartItem item : cartItems) {
            System.out.printf("%s (Количество: %d)\n", item.getProduct().getName(), item.getQuantity());
        }

        User user1 = new User("user1", "password123", "user1@example.com");
        OrderService orderService = new OrderService();
        orderService.placeOrder(user1, cartService);

        System.out.println("Заказ размещен успешно!");
    }
}