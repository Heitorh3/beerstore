package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeerService {

    private Beers beers;

    public BeerService(@Autowired Beers beers){
        this.beers = beers;
    }

    public Beer save(final Beer beer){
        Optional<Beer> beerByNameAndAndType = beers.findByNameAndAndType(beer.getName(), beer.getType());

        if(beerByNameAndAndType.isPresent()){
            throw new BeerAlreadyExistException();
        }

        return beers.save(beer);
    }


}
