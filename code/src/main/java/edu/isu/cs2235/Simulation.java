package edu.isu.cs2235;

import edu.isu.cs2235.structures.Queue;
import edu.isu.cs2235.structures.impl.LinkedQueue;

import java.util.Arrays;
import java.util.Random;

/**
 * Class representing a wait time simulation program.
 *
 * @author Isaac Griffith
 * @author
 */
public class Simulation {

    private int arrivalRate;
    private int maxNumQueues;
    private Random r;
    private int numIterations = 50;
    private Queue<Integer>[] queues;
    private int[] numberOfPeopleThroughQueue;
    private int[] totalMinutesWaited;

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the current time. This defaults to using 50 iterations.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     */
    public Simulation(int arrivalRate, int maxNumQueues) {
        this.arrivalRate = arrivalRate;

        this.maxNumQueues = maxNumQueues;
        numberOfPeopleThroughQueue = new int[maxNumQueues];
        totalMinutesWaited = new int[maxNumQueues];
        r = new Random();
    }

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the provided seed value, and the number of iterations is set to
     * the provided value.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     * @param numIterations the number of iterations used to improve data
     * @param seed the initial seed value for the random number generator
     */
    public Simulation(int arrivalRate, int maxNumQueues, int numIterations, int seed) {
        this(arrivalRate, maxNumQueues);
        r = new Random(seed);
        this.numIterations = numIterations;
    }

    /**
     * Executes the Simulation
     */
    public void runSimulation() {
        System.out.println("Arrival rate: " + arrivalRate);
        for(int numberOfQueues = 1; numberOfQueues <= maxNumQueues; numberOfQueues++) {
            totalMinutesWaited = new int[numberOfQueues];
            numberOfPeopleThroughQueue = new int[numberOfQueues];

            for(int iteration = 0; iteration < numIterations; iteration++) {
                queues = new Queue[numberOfQueues];
                Arrays.fill(queues, new LinkedQueue<Integer>());

                int minutes = 0;

                while (minutes < 720) {
                    int numPeopleThisMinute = getRandomNumPeople(arrivalRate);

                    for (int i = 0; i < numPeopleThisMinute; i++) {
                        getSmallestQueue(queues).offer(minutes);
                    }

                    for (int i = 0; i < numberOfQueues; i++) {
                        if (!queues[i].isEmpty()) {
                            totalMinutesWaited[i] += minutes - queues[i].poll();
                            numberOfPeopleThroughQueue[i] += 1;
                        }
                        if (!queues[i].isEmpty()) {
                            totalMinutesWaited[i] += minutes - queues[i].poll();
                            numberOfPeopleThroughQueue[i] += 1;
                        }
                    }

                    minutes++;
                }
            }

            double averageTimeWaited = 0;
            for(int i = 0; i < numberOfQueues; i++) {
                averageTimeWaited += totalMinutesWaited[i] != 0 ? (double) totalMinutesWaited[i] / numberOfPeopleThroughQueue[i] : 0;
            }
            averageTimeWaited /= numberOfQueues;

            System.out.println("Average time waited using " + numberOfQueues + " queue(s): " + (int) Math.ceil(averageTimeWaited));
        }
    }

    private Queue getSmallestQueue(Queue[] queues) {
        if(queues == null)
            return null;
        Queue smallestQueue = queues[0];

        for(Queue queue : queues) {
            if(queue.size() < smallestQueue.size())
                smallestQueue = queue;
        }

        return smallestQueue;
    }

    /**
     * returns a number of people based on the provided average
     *
     * @param avg The average number of people to generate
     * @return An integer representing the number of people generated this minute
     */
    //Don't change this method.
    private static int getRandomNumPeople(double avg) {
        Random r = new Random();
        double L = Math.exp(-avg);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
