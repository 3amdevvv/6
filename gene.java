import java.util.*;

public class GAFunctionOptimization {

    static Random rand = new Random();

    // Fitness function: f(x) = x^2
    static double fitnessFunction(double x) {
        return x * x;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter population size: ");
        int popSize = sc.nextInt();

        System.out.print("Enter number of generations: ");
        int generations = sc.nextInt();

        System.out.print("Enter mutation rate (e.g. 0.01): ");
        double mutationRate = sc.nextDouble();

        sc.close();

        double bestSolution = 0;
        double bestFitness = Double.NEGATIVE_INFINITY;

        // Initialize population with random values in range [-10, 10]
        double[] population = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            population[i] = rand.nextDouble() * 20 - 10;
        }

        for (int gen = 0; gen < generations; gen++) {
            // Calculate fitness
            double[] fitnessValues = new double[popSize];
            double totalFitness = 0;
            for (int i = 0; i < popSize; i++) {
                fitnessValues[i] = fitnessFunction(population[i]);
                totalFitness += fitnessValues[i];
            }

            // Update best solution
            for (int i = 0; i < popSize; i++) {
                if (fitnessValues[i] > bestFitness) {
                    bestFitness = fitnessValues[i];
                    bestSolution = population[i];
                }
            }

            // Selection using roulette wheel
            double[] selectionProb = new double[popSize];
            for (int i = 0; i < popSize; i++) {
                selectionProb[i] = fitnessValues[i] / totalFitness;
            }

            double[] selectedParents = new double[popSize / 2];
            for (int i = 0; i < popSize / 2; i++) {
                selectedParents[i] = rouletteWheelSelect(population, selectionProb);
            }

            // Crossover and Mutation to form new population
            double[] newPopulation = new double[popSize];
            for (int i = 0; i < popSize / 2; i++) {
                double parent1 = selectedParents[i];
                double parent2 = selectedParents[(i + 1) % (popSize / 2)];

                double offspring1 = rand.nextBoolean() ? parent1 : parent2;
                double offspring2 = rand.nextBoolean() ? parent2 : parent1;

                // Mutation
                if (rand.nextDouble() < mutationRate)
                    offspring1 += rand.nextDouble() * 2 - 1;
                if (rand.nextDouble() < mutationRate)
                    offspring2 += rand.nextDouble() * 2 - 1;

                newPopulation[2 * i] = offspring1;
                newPopulation[2 * i + 1] = offspring2;
            }

            population = newPopulation;
        }

        System.out.printf("Best solution found: %.5f\n", bestSolution);
        System.out.printf("Best fitness: %.5f\n", bestFitness);
    }

    // Roulette wheel selection
    private static double rouletteWheelSelect(double[] population, double[] probabilities) {
        double r = rand.nextDouble();
        double sum = 0;
        for (int i = 0; i < population.length; i++) {
            sum += probabilities[i];
            if (sum >= r) {
                return population[i];
            }
        }
        return population[population.length - 1]; // fallback
    }
}


Enter population size: 10
Enter number of generations: 100
Enter mutation rate (e.g. 0.01): 0.05
