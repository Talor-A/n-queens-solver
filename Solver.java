import java.util.Random;

public class Solver {
  public static Node solveGenetic(int n, int populationSize, double mutationChance, int maxGenerations) {
    Node child = new Node();
    Population population = new Population(populationSize);
    for(int i=0; i<populationSize; i++) {
      population.add(new Node(n));
    }
    for(int generation = 1; generation <= maxGenerations; generation++) {
      // System.out.print("G: "+generation);
      Population offspring = new Population(populationSize);
      for(int i=0; i < populationSize; i++) {
        Node parentA = population.random();
        Node parentB = population.random();
  
        child = parentA.reproduce(parentB);
        if(child.getH() == 0) {
          // System.out.println();
          child.setCost((generation-1)*populationSize + i);
          return child;
        }
  
        if(Math.random() < mutationChance) {
          child.mutate();
        }
        offspring.add(child);
      }
      // System.out.println(" Sum: "+population.probabilitySum);
      population = offspring;
    }
    child.setCost(-1);
    return child;
  }

  public static Node solveSimulatedAnnealing(Node node, int stop, double temp, double schedule) {
    Random random = new Random(1234);
    Node current = node;

    for (int iterations = 1; iterations <= stop; iterations++) {
      temp = temp * schedule;

      
      

      if (current.getH() == 0) {
        // System.out.printf("%nsolution found in %d iterations%n", iterations);
        current.setCost(iterations);
        return current;
      }

      Node child = new Node(current.copyBoard());
      child.mutate();

      double fitness = child.getH() - current.getH();
      double prob = Math.exp(-fitness * temp);
      
      // System.out.printf("I: %2d   ", iterations);
      // System.out.printf("H: %2d   ", current.getH());
      // System.out.printf("F: % 2d  ", (int)fitness);
      // System.out.printf("T: %5.4f   ", temp);
      // System.out.printf("P: %6.4f   ", Math.min(prob, 1.0));
      // System.out.println();

      if (fitness < 0) {
        current = child;
      } else if (prob > Math.random()) {
        current = child;
      }
      current.setCost(iterations);
    }
    return current;
  }
}