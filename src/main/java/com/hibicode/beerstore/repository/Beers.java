package com.hibicode.beerstore.repository;


import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface Beers extends JpaRepository<Beer, Long> {

    Optional<Beer> findByNameAndAndType(String name, BeerType type);

}
