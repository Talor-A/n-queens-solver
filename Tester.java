import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Tester {
  public static void main(String[] args) {
    // demo();
    
    runGA();
    // runSA();
  }
  
  public static void demo() {
    int n = 25;

    // genetic parameters
    int populationSize = 50;
    double mutationChance = 0.07;
    int maxGenerations = 80000;

    // anneal parameters
    int stop = 34000;
    double temp = 9000;
    double schedule = 0.9999;

    for(int i = 1; i <= 3; i++) {
      Node genetic = Solver.solveGenetic(n, populationSize, mutationChance, maxGenerations);

      System.out.println("Genetic Algorithm Solution "+i);
      genetic.print();
      System.out.println("H: " + genetic.getH());


    }
    for(int i = 1; i <= 3; i++) {
      Node anneal = Solver.solveSimulatedAnnealing(new Node(n), stop, temp, schedule);

      System.out.println("Simulated Annealing Solution "+i);
      anneal.print();
      System.out.println("H: " + anneal.getH());
      

    }
  }

  public static void runGA() {
    int n = 25;
    int populationSize = 30;
    double mutationChance = 0.07;
    int maxGenerations = 80000;
    int numPuzzles = 1000;

    try (PrintWriter writer = new PrintWriter(new File("test_n" + n + "_" + numPuzzles + "_genetic.csv"))) {
      writer.write(String.format("h,cost,runtime%n"));
      for (int i = 1; i <= numPuzzles; i++) {
        System.out.printf("%5d/%d", i, numPuzzles);

        long startTime = System.currentTimeMillis();
        Node solution = Solver.solveGenetic(n, populationSize, mutationChance, maxGenerations);

        int runTime = (int) (System.currentTimeMillis() - startTime);
        int cost = solution.getCost();
        int h = solution.getH();

        System.out.println(cost == -1 ? "\u2717" : "\u2713");
        writer.write(String.format("%d,%d,%d%n", h, cost, runTime));

      }
      writer.close();
      System.out.println("evolution done!");
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  public static void runSA() {
    int numPuzzles = 1000;
    int n = 25;
    try (PrintWriter writer = new PrintWriter(new File("test_n" + n + "_" + numPuzzles + "_anneal.csv"))) {
      writer.write(String.format("h,cost,runtime%n"));
      for (int i = 1; i <= numPuzzles; i++) {
        System.out.printf("%5d/%d", i, numPuzzles);

        Node node = new Node(n);

        int stop = 34000;
        double temp = 9000;
        double schedule = 0.9999;
        long startTime = System.currentTimeMillis();
        Node solution = Solver.solveSimulatedAnnealing(node, stop, temp, schedule);
        int runTime = (int) (System.currentTimeMillis() - startTime);
        int cost = solution.getCost();
        int h = solution.getH();
        System.out.println(cost == -1 ? "\u2717" : "\u2713");

        writer.write(String.format("%d,%d,%d%n", h, cost, runTime));

      }
      writer.close();
      System.out.println("annealing done!");
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}