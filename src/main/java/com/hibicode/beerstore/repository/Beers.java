package com.hibicode.beerstore.repository;


import com.hibicode.beerstore.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface Beers extends JpaRepository<Beer, Long> {
}
