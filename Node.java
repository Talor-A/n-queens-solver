import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Node implements Comparable<Node> {

  private int n;
  private int[] board;
  private int h;
  private int cost;

  private Random random;

  public Node() {
    //dummy constructor
  }
  public Node(int n) {
    this(Node.randomBoard(n));
  }

  public Node(int[] board) {
    this.random = new Random();
    this.board = board;
    this.n = board.length;
    this.calculateH();
  }

  public int getH() {
    return this.h;
  }
  public double getGAFitness() {
    return 1.0/(1.0+this.getH());
  }
  public int[] copyBoard() {
    return Arrays.copyOf(this.board, this.board.length);
  }
  public Node reproduce(Node other) {
    int[] childBoard = other.copyBoard();
    int c = random.nextInt(this.n);

    for(int i = 0; i<c; i++) {
      childBoard[i] = this.board[i];
    }
    return new Node(childBoard);
  }
  public int mutate() {
    int mutateIndex = random.nextInt(this.n);
    int previous = this.board[mutateIndex];
    int next = random.nextInt(this.n);
    while(previous == next) {
      // ensure we do not change to the same value
      next = random.nextInt(this.n);
    }
    this.board[mutateIndex] = next;
    this.calculateH();
    return mutateIndex;
  }
 
  private void calculateH() {
    int collisions = 0;
    /* by keeping track of our queen positions in arrays,
    we can change calculateH() to O(n) instead of O(n^2)  */


    int[] column_counts = new int[this.n]; // number of queens in a given column
    int[] diag_r_counts = new int[(this.n * 2) - 1]; // going down and right
    int[] diag_l_counts = new int[(this.n * 2) - 1]; // doing down and left

    for (int row = 0; row < n; row++) {
      int column = board[row];
      int delta_r = (n - 1) + (column - row);
      int delta_l = column + row;
      int queensInColumn = column_counts[column];
      int queensInDiagR = diag_r_counts[delta_r];
      int queensInDiagL = diag_l_counts[delta_l];

      collisions += queensInColumn;
      column_counts[column] = queensInColumn + 1;

      collisions += queensInDiagL;
      diag_l_counts[delta_l] = queensInDiagL + 1;

      collisions += queensInDiagR;
      diag_r_counts[delta_r] = queensInDiagR + 1;
    }

    this.h = collisions;
  }

  public ArrayList<Node> getChildren() {
    ArrayList<Node> children = new ArrayList<>();

    return children;
  }

  public void print() {
    for (int col : board) {
      String o = "|";
      for (int i = 0; i < n; i++) {
        if (i == col) {
          o += "Q|";
        } else {
          o += " |";
        }
      }
      System.out.println(o);
    }
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  @Override
  public int compareTo(Node n) {
    return Integer.valueOf(getH()).compareTo(Integer.valueOf(n.getH()));
  }

  @Override
  public String toString() {
    return Arrays.toString(board);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(board);
  }

  // Overriding equals() to compare two Complex objects
  @Override
  public boolean equals(Object o) {

    // If the object is compared with itself then return true
    if (o == this) {
      return true;
    }

    /*
     * Check if o is an instance of Complex or not "null instanceof [type]" also
     * returns false
     */
    if (!(o instanceof Node)) {
      return false;
    }

    // typecast o to Complex so that we can compare data members
    Node other = (Node) o;

    // Compare the data members and return accordingly
    return Arrays.equals(other.board, board);
  }

  public static int[] randomBoard(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = i;
    }
    Node.shuffleArray(arr);
    return arr;
  }

  private static void shuffleArray(int[] array) {
    int index, temp;
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
      index = random.nextInt(i + 1);
      temp = array[index];
      array[index] = array[i];
      array[i] = temp;
    }
  }

}