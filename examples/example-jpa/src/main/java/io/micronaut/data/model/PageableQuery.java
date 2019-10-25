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
import io.micronaut.core.annotation.Introspected;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Models pageable data. The {@link #from(int, int)} method can be used to construct a new instance to pass to Micronaut Data methods.
 *
 * @author boros
 * @author graemerocher
 * @since 1.0.0
 */
@Introspected
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PageableQuery extends SortQuery {

    /**
     * Constant for no pagination.
     */
    PageableQuery UNPAGED = new PageableQuery() {
        @Override
        public int getPage() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }
    };

    /**
     * @return The page number.
     */
    int getPage();

    /**
     * Maximum size of the page to be returned. A value of -1 indicates no maximum.
     * @return size of the requested page of items
     */
    int getSize();

    /**
     * Offset in the requested collection. Defaults to zero.
     * @return offset in the requested collection
     */
    default long getOffset() {
        return (long) getPage() * (long) getSize();
    }

    /**
     * @return The sort definition to use.
     */
    @NonNull
    default SortQuery sort() {
        return SortQuery.unsorted();
    }

    /**
     * @return The next pageable.
     */
    default @NonNull PageableQuery next() {
        int size = getSize();
        int newNumber = getPage() + 1;
        // handle overflow
        if (newNumber < 0) {
            return PageableQuery.from(0, size, sort());
        } else {
            return PageableQuery.from(newNumber, size, sort());
        }
    }

    /**
     * @return The previous pageable
     */
    default @NonNull PageableQuery previous() {
        int size = getSize();
        int newNumber = getPage() - size;
        // handle overflow
        if (newNumber < 0) {
            return PageableQuery.from(0, size, sort());
        } else {
            return PageableQuery.from(newNumber, size, sort());
        }
    }

    @NonNull
    @Override
    default PageableQuery order(@NonNull String propertyName) {
        SortQuery newSort = sort().order(propertyName);
        return PageableQuery.from(getPage(), getSize(), newSort);
    }

    @Override
    default boolean isSorted() {
        return sort().isSorted();
    }

    @NonNull
    @Override
    default PageableQuery order(@NonNull Order order) {
        SortQuery newSort = sort().order(order);
        return PageableQuery.from(getPage(), getSize(), newSort);
    }

    @NonNull
    @Override
    default PageableQuery order(@NonNull String propertyName, @NonNull Sort.Order.Direction direction) {
        SortQuery newSort = sort().order(propertyName, direction);
        return PageableQuery.from(getPage(), getSize(), newSort);
    }

    @NonNull
    @Override
    default List<Order> getSort() {
        return sort().getSort();
    }

    /**
     * Creates a new {@link PageableQuery} at the given offset with a default size of 10.
     * @param page The page
     * @return The pageable
     */
    static @NonNull PageableQuery from(int page) {
        return new DefaultPageableQuery(page, 10, null);
    }

    /**
     * Creates a new {@link PageableQuery} at the given offset.
     * @param page The page
     * @param size the size
     * @return The pageable
     */
    static @NonNull PageableQuery from(int page, int size) {
        return new DefaultPageableQuery(page, size, null);
    }

    /**
     * Creates a new {@link PageableQuery} at the given offset.
     * @param page The page
     * @param size the size
     * @param sort the sort
     * @return The pageable
     */
    @JsonCreator
    static @NonNull PageableQuery from(
            @JsonProperty("page") int page,
            @JsonProperty("size") int size,
            @JsonProperty("sort") @Nullable SortQuery sort) {
        return new DefaultPageableQuery(page, size, sort);
    }

    static @NonNull PageableQuery from(Pageable pageable) {
        return new DefaultPageableQuery(pageable.getNumber(),
                pageable.getSize(),
                SortQuery.of(pageable.getOrderBy().stream().map(Order::from).collect(Collectors.toList())));
    }

    /**
     * @return A new instance without paging data.
     */
    static @NonNull PageableQuery unpaged() {
        return UNPAGED;
    }
}
