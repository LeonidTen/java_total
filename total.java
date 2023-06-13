import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void decreaseQuantity() {
        quantity--;
    }
}

class ToyShop {
    private List<Toy> toys;

    public ToyShop() {
        toys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateToyWeight(int toyId, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                break;
            }
        }
    }

    public Toy getRandomPrizeToy() {
        double totalWeight = 0.0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        double random = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;

        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (random < cumulativeWeight) {
                toy.decreaseQuantity();
                return toy;
            }
        }

        return null; // No toy selected
    }
}

public class ToyRaffle {
    private static final String OUTPUT_FILE = "prize_toys.txt";

    public static void main(String[] args) {
        ToyShop toyShop = new ToyShop();
        toyShop.addToy(new Toy(1, "Robot", 10, 20.0));
        toyShop.addToy(new Toy(2, "Doll", 15, 30.0));
        toyShop.addToy(new Toy(3, "Car", 20, 50.0));

        toyShop.updateToyWeight(1, 10.0); // Updating weight of Robot toy
        toyShop.updateToyWeight(2, 20.0); // Updating weight of Doll toy
        toyShop.updateToyWeight(3, 30.0); // Updating weight of Car toy

        Toy prizeToy = toyShop.getRandomPrizeToy();
        if (prizeToy != null) {
            savePrizeToyToFile(prizeToy);
            System.out.println("Congratulations! You won a " + prizeToy.getName() + " toy!");
        } else {
            System.out.println("No toy selected as prize.");
        }
    }

    private static void savePrizeToyToFile(Toy toy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("Congratulations! You won a " + toy.getName() + " toy!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}