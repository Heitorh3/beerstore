package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

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

        when(beersMocked.findByNameAndType("Corona", BeerType.APA)).thenReturn(Optional.of(beerInDataBse));

        Beer newBeer = new Beer();
        newBeer.setName("Corona");
        newBeer.setType(BeerType.APA);
        newBeer.setVolume(new BigDecimal("550"));

        beerService.save(newBeer);
    }

    @Test
    public void should_create_new_beer(){

        Beer newBeer = new Beer();
        newBeer.setName("Templaria");
        newBeer.setType(BeerType.APA);
        newBeer.setVolume(new BigDecimal("350"));

        Beer newBeerInDataBase = new Beer();
        newBeerInDataBase.setId(5L);
        newBeerInDataBase.setName("Templaria");
        newBeerInDataBase.setVolume(new BigDecimal("350"));
        newBeerInDataBase.setType(BeerType.APA);

        when(beersMocked.save(newBeer)).thenReturn(newBeerInDataBase);
        Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(5L));
        assertThat(beerSaved.getName(), equalTo("Templaria"));
        assertThat(beerSaved.getType(), equalTo(BeerType.APA));

    }

    @Test
    public void should_update_beer_volume() {
        final Beer beerInDatabase = new Beer();
        beerInDatabase.setId(10L);
        beerInDatabase.setName("Devassa");
        beerInDatabase.setType(BeerType.PILSEN);
        beerInDatabase.setVolume(new BigDecimal("300"));

        when(beersMocked.findByNameAndType("Devassa",
                BeerType.PILSEN)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer();
        beerToUpdate.setId(10L);
        beerToUpdate.setName("Devassa");
        beerToUpdate.setType(BeerType.PILSEN);
        beerToUpdate.setVolume(new BigDecimal("200"));

        final Beer beerMocked = new Beer();
        beerMocked.setId(10L);
        beerMocked.setName("Devassa");
        beerMocked.setType(BeerType.PILSEN);
        beerMocked.setVolume(new BigDecimal("200"));

        when(beersMocked.save(beerToUpdate)).thenReturn(beerMocked);

        final Beer beerUpdated = beerService.save(beerToUpdate);
        assertThat(beerUpdated.getId(), equalTo(10L));
        assertThat(beerUpdated.getName(), equalTo("Devassa"));
        assertThat(beerUpdated.getType(), equalTo(BeerType.PILSEN));
        assertThat(beerUpdated.getVolume(), equalTo(new BigDecimal
                ("200")));
    }

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_update_of_an_existing_beer_that_already_exists() {
        final Beer beerInDatabase = new Beer();
        beerInDatabase.setId(10L);
        beerInDatabase.setName("Heineken");
        beerInDatabase.setType(BeerType.LAGER);
        beerInDatabase.setVolume(new BigDecimal("355"));

        when(beersMocked.findByNameAndType("Heineken", BeerType
                .LAGER)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer();
        beerToUpdate.setId(5L);
        beerToUpdate.setName("Heineken");
        beerToUpdate.setType(BeerType.LAGER);
        beerToUpdate.setVolume(new BigDecimal("355"));

        beerService.save(beerToUpdate);
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete_when_beer_not_exist() {

        final Beer beerToUpdate = new Beer();
        beerToUpdate.setId(5L);
        beerToUpdate.setName("Heineken");
        beerToUpdate.setType(BeerType.LAGER);
        beerToUpdate.setVolume(new BigDecimal("355"));

        when(beersMocked.findById(5L))
                .thenReturn(Optional.empty());


        beerService.delete(beerToUpdate.getId());

    }
    @Test
    public void delete_beer_that_already_exist(){

        final Beer beerInDatabase = new Beer();
        beerInDatabase.setId(5L);
        beerInDatabase.setName("Heineken");
        beerInDatabase.setType(BeerType.LAGER);
        beerInDatabase.setVolume(new BigDecimal("355"));

        when(beersMocked.findById(5L)).thenReturn(Optional.of(beerInDatabase));

        beerService.delete(beerInDatabase.getId());
    }
}
