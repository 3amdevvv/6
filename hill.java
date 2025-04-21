import java.util.Random;
import java.util.Scanner;

public class HillClimbing {

    static Random rand = new Random();

    // Fitness function: f(x) = x^2
    static double fitnessFunction(double x) {
        return x * x;
    }

    // Hill Climbing Algorithm
    static double[] hillClimbing(double start, double stepSize, int maxIterations) {
        double currentSolution = start;
        double currentFitness = fitnessFunction(currentSolution);

        for (int i = 0; i < maxIterations; i++) {
            double neighbor1 = currentSolution + stepSize;
            double neighbor2 = currentSolution - stepSize;

            double fitness1 = fitnessFunction(neighbor1);
            double fitness2 = fitnessFunction(neighbor2);

            if (fitness1 > currentFitness || fitness2 > currentFitness) {
                if (fitness1 > fitness2) {
                    currentSolution = neighbor1;
                    currentFitness = fitness1;
                } else {
                    currentSolution = neighbor2;
                    currentFitness = fitness2;
                }
            } else {
                break;
            }
        }

        return new double[]{currentSolution, currentFitness};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter starting point (between -10 and 10): ");
        double start = scanner.nextDouble();

        System.out.print("Enter step size: ");
        double stepSize = scanner.nextDouble();

        System.out.print("Enter maximum number of iterations: ");
        int maxIterations = scanner.nextInt();

        double[] result = hillClimbing(start, stepSize, maxIterations);
        System.out.println("Hill Climbing Result:");
        System.out.printf("Best Solution: %.4f, Fitness: %.4f\n", result[0], result[1]);

        scanner.close();
    }
} 
