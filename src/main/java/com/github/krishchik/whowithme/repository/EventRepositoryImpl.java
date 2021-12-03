package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Repository;
import com.github.krishchik.whowithme.model.User_;
import com.github.krishchik.whowithme.model.Event_;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EventRepositoryImpl extends AbstractRepositoryImpl<Event, Long> implements EventRepository {



    @Override
    protected Class<Event> getEntityClass() {
        return Event.class;
    }

    @Override
    public List<Event> getEventsByPlace(Long placeId) {
        String queryString = "select e from Event e join fetch e.place pl where e.id = :id";
        TypedQuery<Event> query = entityManager.createQuery(queryString, Event.class);
        query.setParameter("id", placeId);
        return query.getResultList();
    }

    @Override
    public List<Event> getUsersEvents(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> q = cb.createQuery(Event.class);
        Root<Event> r = q.from(Event.class);
        r.fetch(Event_.USERS, JoinType.LEFT);
        q.where(cb.equal(r.get(User_.ID), user.getId()));
        return  entityManager.createQuery(q).getResultList();
    }
}
