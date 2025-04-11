
/** ****************************************************************
 *
 *   Jonah Goldberg / Section 002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ******************************************************************* */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked, so
     * ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures.
     * This allows the second parameter to be optional, and if not provided,
     * defaults to an ascending sort. If the second parameter is provided and is
     * false, a descending sort is performed.
     *
     * @param values - int[] array to be sorted.
     * @param ascending - if true,method performs an ascending sort, else
     * descending. There are two method signatures allowing this parameter to
     * not be passed and defaulting to 'true (or ascending sort).
     */
    public void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending) {
        //Length of values array
        int n = values.length;

        //First loop
        for (int i = 0; i < n - 1; i++) {
            //the current min index is i
            int min_index = i;
            //Start searching 1 in front of i (the end of the sorted region)
            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    if (values[j] < values[min_index]) {
                        //Then we've found a new min index
                        min_index = j;
                    }
                } else {
                    //Switch sign for descending values 
                    if (values[j] > values[min_index]) {
                        //Then we've found a new min index
                        min_index = j;
                    }
                }

            }

            //Swap the min to the correct position
            int temp = values[i];
            values[i] = values[min_index];
            values[min_index] = temp;

        }

    } // End class selectionSort

    /**
     * Method mergeSortDivisibleByKFirst
     *
     * This method will perform a merge sort algorithm. However, all numbers
     * that are divisible by the argument 'k', are returned first in the sorted
     * list. Example: values = { 10, 3, 25, 8, 6 }, k = 5 Sorted result should
     * be --> { 10, 25, 3, 6, 8 }
     *
     * values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6 Sorted result should be
     * --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     *
     * As shown above, this is a normal merge sort operation, except for the
     * numbers divisible by 'k' are first in the sequence.
     *
     * @param values - input array to sort per definition above
     * @param k - value k, such that all numbers divisible by this value are
     * first
     */
    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0) {
            return;
        }
        if (values.length <= 1) {
            return;
        }

        mergeSortDivisibleByKFirst(values, k, 0, values.length - 1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */
    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right) {
        //temp list which will help to keep it stable
        List<Integer> temp = new ArrayList<>();

        //init two pointers
        int i = left;
        int j = mid + 1;

        //Merge the two subarrays into the temp array list
        while (i <= mid && j <= right) {
            if (arr[i] % k == 0 && arr[j] % k != 0) {
                //if left is divisible by k while right is not then take left
                temp.add(arr[i++]);
            } else if (arr[i] % k != 0 && arr[j] % k == 0) {
                //if right is divisible by k but left isn't then take right
                temp.add(arr[j++]);
            } else if (arr[i] % k == 0 && arr[j] % k == 0) {
                //if both are divisible by k then take the left
                temp.add(arr[i++]);
            } else {
                //if both aren't divisible then keep the order the same
                if (arr[i] <= arr[j]) {
                    temp.add(arr[i]);
                    i++;
                } else {
                    temp.add(arr[j]);
                    j++;
                }
            }
        }

        //check the left for leftove elements
        while (i <= mid) {
            temp.add(arr[i]);
            //increment left ptr
            i++;
        }

        //check the right half for leftover elements
        while (j <= right) {
            temp.add(arr[j]);
            //increment right
            j++;
        }

        //add all the elements back into the array
        int curr = left;

        for (int num : temp) {
            //add the num to the current index in the array
            arr[curr] = num;
            //increment the urrent index
            curr++;
        }
    }

    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a
     * planet. You are further given an integer array 'asteroids', where
     * asteroids[i] is the mass of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any
     * arbitrary order. If the mass of the planet is greater than or equal to
     * the mass of the asteroid, the asteroid is destroyed and the planet gains
     * the mass of the asteroid. Otherwise, the planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise,
     * return false.
     *
     * Example 1: Input: mass = 10, asteroids = [3,9,19,5,21] Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]: - The
     * planet collides with the asteroid with a mass of 9. New planet mass: 10 +
     * 9 = 19 - The planet collides with the asteroid with a mass of 19. New
     * planet mass: 19 + 19 = 38 - The planet collides with the asteroid with a
     * mass of 5. New planet mass: 38 + 5 = 43 - The planet collides with the
     * asteroid with a mass of 3. New planet mass: 43 + 3 = 46 - The planet
     * collides with the asteroid with a mass of 21. New planet mass: 46 + 21 =
     * 67 All asteroids are destroyed.
     *
     * Example 2: Input: mass = 5, asteroids = [4,9,23,4] Output: false
     *
     * Explanation: The planet cannot ever gain enough mass to destroy the
     * asteroid with a mass of 23. After the planet destroys the other
     * asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22. This is less than
     * 23, so a collision would not destroy the last asteroid.
     *
     * Constraints: 1 <= mass <= 105 1 <= asteroids.length <= 105 1 <=
     * asteroids[i] <= 105
     *
     * @param mass - integer value representing the mass of the planet
     * @param asteroids - integer array of the mass of asteroids
     * @return - return true if all asteroids destroyed, else false.
     */
    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {

        // YOUR CODE GOES HERE, CONSIDER USING ARRAYS.SORT()
        //Sort the array of asteroids
        Arrays.sort(asteroids);

        for (int bigRock : asteroids) {
            //If the rock is smaller than our mass then add its weight to the mass
            if (bigRock <= mass) {
                mass += bigRock;
            } else {
                return false;
            }
        }

        //If we get here then all asteroids were able to be destroyed
        return true;
    }

    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith
     * person, and an infinite number of rescue sleds where each sled can carry
     * a maximum weight of limit. Each sled carries at most two people at the
     * same time, provided the sum of the weight of those people is at most
     * limit. Return the minimum number of rescue sleds to carry every given
     * person.
     *
     * Example 1: Input: people = [1,2], limit = 3 Output: 1 Explanation: 1 sled
     * (1, 2)
     *
     * Example 2: Input: people = [3,2,2,1], limit = 3 Output: 3 Explanation: 3
     * sleds (1, 2), (2) and (3)
     *
     * Example 3: Input: people = [3,5,3,4], limit = 5 Output: 4 Explanation: 4
     * sleds (3), (3), (4), (5)
     *
     * @param people - an array of weights for people that need to go in a sled
     * @param limit - the weight limit per sled
     * @return - the minimum number of rescue sleds required to hold all people
     */
    public static int numRescueSleds(int[] people, int limit) {

        // YOUR CODE GOES HERE, CONSIDER USING ARRAYS.SORT
        Arrays.sort(people);

        //Left pointer at the start index of the array (lighest people)
        int left = 0;

        //Int right is the end of the array (heaviest people)
        int right = people.length - 1;

        //Initialize sled count as 0
        int sledCount = 0;

        while (left <= right) {
            //Check if the lightest and heaviest person can share a sled
            if (people[left] + people[right] <= limit) {
                //move left ptr forward as the lightest person now has a sled
                left++;
            }
            //we need to always decrement the right pointer
            right--;

            //Add one ot the sled count since we know there's another person on the sled
            sledCount++;

        } // End Class ProblemSolutions
        return sledCount;

    }
}
