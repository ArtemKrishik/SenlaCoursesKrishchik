package com.github.krishchik.whowithme.repository.repositoryApi;

import com.github.krishchik.whowithme.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceCrudRepository extends JpaRepository<Place, Long> {

    Page<Place> findByOrderByPrice(Pageable pageable);

    Page<Place> findAll(Pageable pageable);








}
