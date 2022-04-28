package com.github.krishchik.whowithme.repository.filter;

import java.util.ArrayList;
import java.util.List;

import com.github.krishchik.whowithme.model.Event;
import org.springframework.data.jpa.domain.Specification;



public  class EventSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public EventSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public EventSpecificationsBuilder with(String key, String operation, Object value, Boolean orPredicate) {
        params.add(new SearchCriteria(key, operation, value, orPredicate));
        return this;
    }

    public Specification<Event> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<?>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new EventSpecification(param));
        }

        Specification result = specs.get(0);


        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}