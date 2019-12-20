package com.codewars;


import java.util.Iterator;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Erastothenes {
    public static void main(String[] args) {
        Erastothenes e = new Erastothenes();
        System.out.println(e.erastothenes(100));
    }
    public Set<Integer> erastothenes(int n) {
        Set<Integer> primes = IntStream.rangeClosed(1, n)
                .filter(x -> x % 2 != 0 && x != 2)
                .boxed()
                .collect(Collectors.toSet());

        IntPredicate isNotPrime = x -> x % 2 == 0 && x != 2;
        Iterator<Integer> primesIter = primes.iterator();
        while(primesIter.hasNext()) {
            int x = primesIter.next();
            if (isNotPrime.test(x)) {
                primesIter.remove();
            } else {
                isNotPrime.and(y -> y % x == 0);
            }
        }
        return primes;
    }
}
