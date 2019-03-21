/**
 * Population
 */
import java.util.ArrayList;

public class Population {
  ArrayList<Node> population;
  double probabilitySum;

  Population(int size) {
    this.population = new ArrayList<>(size);
    this.probabilitySum = 0.0;
  }
  public void add(Node n) {
    this.population.add(n);
    this.probabilitySum += n.getGAFitness();
  }

  public Node random() {
    double stop = Math.random()*probabilitySum;
    for(int i = 0; i < this.population.size(); i++) {
      Node test = this.population.get(i);
      if(test.getGAFitness() > stop) {
        return test;
      } else {
        stop -= test.getGAFitness();
      }
    }
    return null;
  }
}