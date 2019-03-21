# N-Queens Solver

**Talor Anderson**

**CS 4200**

## Execution Instructions

`javac *.java && java Tester`



**Expected Output:** Genetic and Simulated Annealing will each generate 3 random puzzles and print to console. 



## Part 1: Simulated Annealing

Simulated Annealing, based on the real-world application of annealing metal, is a general gradient descent algorithm where moves are selected randomly, with a probability based on how good the move is and how far into the annealing process we are. the probability is equal to $e^{-f \times t}$, where $f$ is a fitness function and t is a temperature that decreases with time. For this scenario, a negative $f$ corresponds to a 'good' move and a positive $f$ to a 'bad' one. 

### Approach

The algorithm was run with these criteria:

max iterations: 34000

initial temperature ($t$): 9000

schedule: $T(x) = t \times (0.9999)^x ​$

To get these criteria, I first settled on the fitness function with base 0.999. I found that values of 0.9 and 0.99 decreased too quickly to be useful. With this function and a starting temperature of 4000, the value of $t​$ decayed to a small value (<.01) in approximately 15000 iterations, so I set this as the max iteration count before the algorithm gives up. 

Later, I tried tweaking all the parameters to maximize the number of solved puzzles, and was able to achieve 999/1000 with the parameters in trial 2.


### Analysis

The data gathered from a trial of 1000 problems of n=25 generated the following results:

#### Trial 1 Criteria:

iteration limit: 15000

initial temperature ($t$): 4000

schedule: $T(x) = t \times (0.999)^x $

#### Trial 1 Results:

**Problems solved:** 648 / 1000

**Avg Search Cost (solved)**: 3709 iterations

**Avg runtime (solved):** 1.608 ms

**Max Iterations (solved):** 7380



#### Trial 2 Criteria:

iteration limit: 34000

initial temperature ($t​$): 9000

schedule: $T(x) = t \times (0.9999)^x $

#### Trial 2 Results:

**Problems solved:** 999 / 1000

**Avg Search Cost (solved)**: 6515 iterations

**Avg runtime (solved):** 2.665 ms

**Max Iterations (solved):** 31625

### Findings

From these two trials, we can see that a tradeoff is made, with a higher solve rate in trial 2 and a much lower average cost and runtime in trial 1. 



## Part 2: Genetic Algorithm

### Approach

for a puzzle of size N, the max number of collisions is $m = { n(n-1) \over 2 }$. 

From this, we can develop a score for each node:

$\textit{fitness} = m - \textit{collisions}$

this gives us a relative fitness from $m \to 0​$ that decreases linearly. I later changed my fitness function to one that decreases asymptotically:

$\textit{fitness} = { 1.0 \over ( 1.0 + \textit{collisions} )}​$

This function has a range from $1 \to 0​$ and gives a stronger bias to better solutions, so it performed much better. 



### Analysis

#### Trial 1 Criteria:

Generation limit: 50000

Population Size: 100

Mutation rate: 0.05

#### Trial 1 Results:

**Problems solved:** 896 / 1000

**Avg Search Cost (solved)**: 1519550 mating pairs

**Avg runtime (solved):** 1422 ms



#### Trial 2 Criteria:

Generation limit: 50000

Population Size: 60

Mutation rate: 0.05

#### Trial 2 Results:

**Problems solved:** 850 / 1000

**Avg Search Cost (solved)**: 1078440 mating pairs

**Avg runtime (solved):** 712.3 ms



### Findings

Like the Simulated Annealing trials, there is a tradeoff visible in the genetic algorithm parameters. I was able to find a greater amount of solutions by using a larger population (100), but a smaller population size (60) found only marginally fewer solutions and also found solutions in exactly half the time. 



## Conclusion

With both these algorithms, tuning the function parameters turned out to be the most important part of the process. Without a properly fitted population size, schedule function, etc, these algorithms would be essentially useless. However I was able to achieve acceptable results (87% solved) with the Genetic Algorithm approach and very good results (99.9% solved) with Simulated Annealing. However, the better approach is clearly simulated annealing because it ran several orders of magnitude faster. I think 