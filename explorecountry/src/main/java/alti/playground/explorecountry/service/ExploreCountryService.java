package alti.playground.explorecountry.service;

import alti.playground.explorecountry.domain.Country;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExploreCountryService {

    public Country getCountry(String iso2Code){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        ResponseEntity<List<Object>> rateResponse =
                restTemplate.exchange("http://api.worldbank.org/v2/country?format=json",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {});
//        List<Country> countries = (List<Country>) rateResponse.getBody().get(1);
//        System.out.println("unserialsed ");
//        System.out.println(countries.get(0));
        List<Country> countryList = mapper.convertValue(rateResponse.getBody().get(1), new TypeReference<List<Country>>() {
        });

//        System.out.println("serialsed ");
//        System.out.println(countryList.get(0));

        Optional<Country> found = countryList.stream().filter(country -> country.getIso2Code().equals(iso2Code)).findFirst();
        //System.out.println("Country = " + found);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public List<Country> findSimilarCountries(String iso2Code, boolean matchRegion, boolean matchIncomeLevel, boolean matchLendingType) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
      //  mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        ResponseEntity<List<Object>> rateResponse =
                restTemplate.exchange("http://api.worldbank.org/v2/country?format=json",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {
                        });
//        List<Country> countries = (List<Country>) rateResponse.getBody().get(1);
        List<Country> countryList = mapper.convertValue(rateResponse.getBody().get(1), new TypeReference<List<Country>>() {
        });

        Optional<Country> found = countryList.stream().filter(country -> country.getIso2Code().equals(iso2Code)).findFirst();
        System.out.println("Country found = " + found);
        if (found.isPresent()) {
            if (!matchRegion && !matchIncomeLevel & !matchLendingType) {
                List<Country> list = new ArrayList<>();
                list.add(found.get());
                System.out.println("Case 1");
                return list;
            } else if (!matchRegion && !matchIncomeLevel & matchLendingType) {
                System.out.println("Case 2");
                return countryList.stream().
                        filter(country -> country.getLendingType().getIso2code().equals(found.get().getLendingType().getIso2code())).
                        collect(Collectors.toList());
            } else if (!matchRegion && matchIncomeLevel & !matchLendingType) {
                System.out.println("Case 3");
                return countryList.stream().
                        filter(country -> country.getIncomeLevel().getIso2code().equals(found.get().getIncomeLevel().getIso2code())).
                        collect(Collectors.toList());
            } else if (!matchRegion && matchIncomeLevel & matchLendingType) {
                System.out.println("Case 4");
                return countryList.stream().
                        filter(country -> country.getIncomeLevel().getIso2code().equals(found.get().getIncomeLevel().getIso2code())).
                        filter(country -> country.getLendingType().getIso2code().equals(found.get().getLendingType().getIso2code())).
                        collect(Collectors.toList());
            } else if (matchRegion && !matchIncomeLevel & !matchLendingType) {
                System.out.println("Case 5");
                return countryList.stream().
                        filter(country -> country.getRegion().getIso2code().equals(found.get().getRegion().getIso2code())).
                        collect(Collectors.toList());
            } else if (matchRegion && !matchIncomeLevel & matchLendingType) {
                System.out.println("Case 6");
                return countryList.stream().
                        filter(country -> country.getRegion().getIso2code().equals(found.get().getRegion().getIso2code())).
                        filter(country -> country.getLendingType().getIso2code().equals(found.get().getLendingType().getIso2code())).
                        collect(Collectors.toList());
            } else if (matchRegion && matchIncomeLevel & !matchLendingType) {
                System.out.println("Case 7");
                return countryList.stream().
                        filter(country -> country.getRegion().getIso2code().equals(found.get().getRegion().getIso2code())).
                        filter(country -> country.getIncomeLevel().getIso2code().equals(found.get().getIncomeLevel().getIso2code())).
                        collect(Collectors.toList());
            } else if (matchRegion && matchIncomeLevel & matchLendingType) {
                System.out.println("Case 8");
                return countryList.stream().
                        filter(country -> country.getRegion().getIso2code().equals(found.get().getRegion().getIso2code())).
                        filter(country -> country.getIncomeLevel().getIso2code().equals(found.get().getIncomeLevel().getIso2code())).
                        filter(country -> country.getLendingType().getIso2code().equals(found.get().getLendingType().getIso2code())).
                        collect(Collectors.toList());
            }
        }

        return null;

    }

    public Integer getMatchCount(String iso2Code, boolean matchRegion, boolean matchIncomeLevel, boolean matchLendingType) {

        List<Country> list = findSimilarCountries(iso2Code, matchRegion, matchIncomeLevel, matchLendingType);
        if (list == null)
            return 0;
        else {
            return list.size();
        }
    }
}
