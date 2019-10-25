/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.model.Sort.Order.Direction;

import java.util.List;
import java.util.Objects;

/**
 * An interface for objects that can be sorted. Sorted instances are immutable and all mutating operations on this interface return a new instance.
 *
 * @author graemerocher
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface SortQuery {

    /**
     * Constant for unsorted.
     */
    SortQuery UNSORTED = new DefaultSortQuery();

    /**
     * @return Is sorting applied
     */
    boolean isSorted();

    /**
     * Orders by the specified property name (defaults to ascending).
     *
     * @param propertyName The property name to order by
     * @return A new sort with the order applied
     */
    @NonNull
    SortQuery order(@NonNull String propertyName);

    /**
     * Adds an order object.
     *
     * @param order The order object
     * @return A new sort with the order applied
     */
    @NonNull SortQuery order(@NonNull SortQuery.Order order);

    /**
     * Orders by the specified property name and direction.
     *
     * @param propertyName The property name to order by
     * @param direction Either "asc" for ascending or "desc" for descending
     *
     * @return A new sort with the order applied
     */
    @NonNull SortQuery order(@NonNull String propertyName, @NonNull Direction direction);

    /**
     * @return The order definitions for this sort.
     */
    @NonNull List<Order> getSort();

    /**
     * @return Default unsorted sort instance.
     */
    static SortQuery unsorted() {
        return UNSORTED;
    }

    /**
     * Create a sort from the given list of orders.
     *
     * @param orderList The order list
     * @return The sort
     */
    @JsonCreator
    static @NonNull SortQuery of(
            @JsonProperty("orderBy") @Nullable List<Order> orderList) {
        if (CollectionUtils.isEmpty(orderList)) {
            return UNSORTED;
        }
        return new DefaultSortQuery(orderList);
    }

    /**
     * The ordering of results.
     */
    class Order extends Sort.Order {

        /**
         * Constructs an order for the given property in ascending order.
         * @param property The property
         */
        public Order(@NonNull String property) {
            this(property, Direction.ASC, false);
        }

        /**
         * Constructs an order for the given property with the given direction.
         * @param property The property
         * @param direction The direction
         * @param ignoreCase Whether to ignore case
         */
        @JsonCreator
        public Order(
                @JsonProperty("property") @NonNull String property,
                @JsonProperty("direction") @NonNull Direction direction,
                @JsonProperty("ignoreCase") boolean ignoreCase) {
           super(property, direction, ignoreCase);
        }

        /**
         * Creates a new order for the given property in descending order.
         *
         * @param property The property
         * @return The order instance
         */
        public static Order desc(String property) {
            return new Order(property, Direction.DESC, false);
        }

        /**
         * Creates a new order for the given property in ascending order.
         *
         * @param property The property
         * @return The order instance
         */
        public static Order asc(String property) {
            return new Order(property, Direction.ASC, false);
        }

        /**
         * Creates a new order for the given property in descending order.
         *
         * @param property The property
         * @param ignoreCase Whether to ignore case
         * @return The order instance
         */
        public static Order desc(String property, boolean ignoreCase) {
            return new Order(property, Direction.DESC, ignoreCase);
        }

        /**
         * Creates a new order for the given property in ascending order.
         *
         * @param property The property
         * @param ignoreCase  Whether to ignore case
         * @return The order instance
         */
        public static Order asc(String property, boolean ignoreCase) {
            return new Order(property, Direction.ASC, ignoreCase);
        }

        @Override
        public String toString() {
            return getProperty() + "," + getDirection();
        }

        public static Order from(Sort.Order order) {
            return new Order(order.getProperty(), order.getDirection(), false);
        }

    }
}
