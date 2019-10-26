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

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.util.ArgumentUtils;
import io.micronaut.core.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of the sort interface.
 *
 * @author graemerocher
 * @since 1.0
 */
final class DefaultSortQuery implements SortQuery {
    private final List<Order> orderBy;

    /**
     * Constructor that takes an order.
     * @param orderBy The order by
     */
    DefaultSortQuery(List<Order> orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Default constructor.
     */
    DefaultSortQuery() {
        this.orderBy = Collections.emptyList();
    }

    /**
     * Specifies the order of results.
     *
     * @param order The order object
     * @return The Query instance
     */
    public @NonNull DefaultSortQuery order(@NonNull Order order) {
        ArgumentUtils.requireNonNull("order", order);
        List<Order> newOrderBy = new ArrayList<>(orderBy);
        newOrderBy.add(order);
        return new DefaultSortQuery(newOrderBy);
    }

    /**
     * Gets the Order entries for this query.
     *
     * @return The order entries
     */
    @Override
    public @NonNull List<Order> getSort() {
        return Collections.unmodifiableList(orderBy);
    }

    @Override
    public boolean isSorted() {
        return CollectionUtils.isNotEmpty(orderBy);
    }

    @NonNull
    @Override
    public DefaultSortQuery order(@NonNull String propertyName) {
        return order(new Order(propertyName));
    }

    @NonNull
    @Override
    public DefaultSortQuery order(@NonNull String propertyName, @NonNull Sort.Order.Direction direction) {
        return order(new Order(propertyName, direction, false));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultSortQuery that = (DefaultSortQuery) o;
        return orderBy.equals(that.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy);
    }
}
