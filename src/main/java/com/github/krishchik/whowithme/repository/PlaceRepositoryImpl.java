package com.github.krishchik.whowithme.repository;


import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.model.Place;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PlaceRepositoryImpl extends AbstractRepositoryImpl<Place, Long> implements PlaceRepository {

    @Override
    protected Class<Place> getEntityClass() {
        return Place.class;
    }


    @Override
    public List<Place> getPlacesSortedByCapacity() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Place> cq = cb.createQuery(getEntityClass());
        Root<Place> root = cq.from(getEntityClass());
        CriteriaQuery<Place> all = cq.select(root).orderBy(cb.asc(root.get("capacity")));
        TypedQuery<Place> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<Place> getThreeCheapestPlaces() {
        return entityManager.createQuery("from Place order by price").setMaxResults(3).getResultList();
    }

}
