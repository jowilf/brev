package com.brev.kgsservice.generator;

import com.brev.kgsservice.config.AppConfig;
import com.brev.kgsservice.encoder.Encoder;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code Generator} class is responsible for generating and managing short keys
 * used in the URL shortening process. It maintains an internal queue of encoded keys
 * and acquires new key ranges when necessary.
 */
@Component
@Log
public class Generator {

    private final SharedCount sharedCount;

    private final BigInteger rangeSize;

    private final Encoder encoder;

    // Internal thread-safe queue to store generated keys
    private final Stack<String> queue = new Stack<>();

    @Autowired
    public Generator(SharedCount sharedCount, AppConfig config, Encoder encoder) {
        this.sharedCount = sharedCount;
        this.rangeSize = BigInteger.valueOf(config.getRangeSize());
        this.encoder = encoder;
    }


    @PostConstruct
    private void requestNewRange() {
        log.info("Requesting new range....");
        BigInteger availableRange = BigInteger.valueOf(getNextAvailableRange());
        BigInteger startValue = availableRange.multiply(rangeSize);
        BigInteger endValue = startValue.add(rangeSize).subtract(BigInteger.ONE);
        log.info("Acquired new range: [" + startValue + " ; " + endValue + "]");
        fillInternalQueue(startValue);
    }

    /**
     * Fills the internal queue with encoded keys starting from the given {@code startValue}.
     * This method clears the existing queue, generates a list of keys within the range,
     * shuffles them, and then pushes them onto the queue.
     *
     * @param startValue The starting value of the key range.
     */
    private void fillInternalQueue(BigInteger startValue) {
        queue.clear();
        Stream.iterate(startValue, n -> n.add(BigInteger.ONE))
                .limit(rangeSize.intValueExact())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.shuffle(list);
                    return list;
                })).stream().forEach((value) -> {
                    queue.push(encoder.encode(value));
                });
    }

    /**
     * Acquires the next available key range by incrementing the shared count.
     */
    private int getNextAvailableRange() {
        try {
            int count = sharedCount.getCount() + 1;
            sharedCount.setCount(count);
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public String nextKey() {
        if (queue.isEmpty()) requestNewRange();
        return queue.pop();
    }

    public void clear(){
        queue.clear();
    }

}
