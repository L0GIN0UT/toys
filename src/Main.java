import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Toy {
    private int id;
    private String name;
    private int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

class ToyStore {
    private List<Toy> toys;

    public ToyStore() {
        this.toys = new ArrayList<>();
    }

    public void addToy(int id, String name, int weight) {
        Toy toy = new Toy(id, name, weight);
        toys.add(toy);
    }

    public void updateToyWeight(int id, int newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    public void playLottery() {
        List lottery = new ArrayList<>();
        for (Toy toy : toys) {
            int weight = toy.getWeight();
            for (int i = 0; i < weight; i++) {
                lottery.add(toy);
            }
        }

        Random random = new Random();
        Toy winningToy = (Toy) lottery.get(random.nextInt(lottery.size()));
        System.out.println("The winner is: " + winningToy.getName());
    }

    public void saveToFile(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        List lines = new ArrayList<>();
        for (Toy toy : toys) {
            String line = toy.getId() + "," + toy.getName() + "," + toy.getWeight();
            lines.add(line);
        }
        Files.write(filePath, lines);
    }

    public void loadFromFile(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        List<String> lines = Files.readAllLines(filePath);
        toys.clear();
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int weight = Integer.parseInt(parts[2]);
            toys.add(new Toy(id, name, weight));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.addToy(1, "Мишка", 20);
        store.addToy(2, "Лего", 30);
        store.addToy(3, "Кукла", 50);
        store.addToy(4, "Робот", 10);

        store.updateToyWeight(2, 25);

        try {
            store.saveToFile("toys.txt");
            store.loadFromFile("toys.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        store.playLottery();
    }
}