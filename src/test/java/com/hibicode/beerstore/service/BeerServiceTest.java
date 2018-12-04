package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

public class BeerServiceTest {

    private BeerService beerService;

    @Mock
    private Beers beersMocked;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }


    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_creation_of_beer_that_exists(){

        Beer beerInDataBse = new Beer();
        beerInDataBse.setName("Corona");
        beerInDataBse.setType(BeerType.APA);
        beerInDataBse.setVolume(new BigDecimal("550"));

        when(beersMocked.findByNameAndAndType("Corona", BeerType.APA)).thenReturn(Optional.of(beerInDataBse));

        Beer newBeer = new Beer();
        newBeer.setName("Corona");
        newBeer.setType(BeerType.APA);
        newBeer.setVolume(new BigDecimal("550"));

        beerService.save(newBeer);
    }

    @Test
    public void should_create_new_beer(){

        Beer newBeer = new Beer();
        newBeer.setName("Corona");
        newBeer.setType(BeerType.APA);
        newBeer.setVolume(new BigDecimal("550"));

        Beer newBeerInDataBase = new Beer();
        newBeerInDataBase.setId(10L);
        newBeerInDataBase.setName("Corona");
        newBeerInDataBase.setVolume(new BigDecimal("550"));
        newBeerInDataBase.setType(BeerType.APA);

        when(beersMocked.save(newBeer)).thenReturn(newBeerInDataBase);
        Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Corona"));
        assertThat(beerSaved.getType(), equalTo(BeerType.APA));

    }
}
