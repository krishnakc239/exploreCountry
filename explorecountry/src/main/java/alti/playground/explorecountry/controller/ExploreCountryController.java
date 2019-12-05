package alti.playground.explorecountry.controller;

import alti.playground.explorecountry.domain.Country;
import alti.playground.explorecountry.service.ExploreCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8085"}, maxAge = 6000)
public class ExploreCountryController {

    @Autowired
    private ExploreCountryService exploreCountryService;

    @GetMapping(value = "/api/countries/{iso2Code}", produces = "application/json")
    public List<Country> findSimilarCountries(@PathVariable String iso2Code,
                                               @RequestParam boolean matchRegion,
                                               @RequestParam boolean matchIncomeLevel,
                                               @RequestParam boolean matchLendingType) {
        return exploreCountryService.findSimilarCountries(iso2Code, matchRegion, matchIncomeLevel, matchLendingType);
    }

    @GetMapping(value = "/api/getcountry/{iso2Code}", produces = "application/json")
    public Country getCountry(@PathVariable String iso2Code)
    {
        return exploreCountryService.getCountry(iso2Code);
    }



}
