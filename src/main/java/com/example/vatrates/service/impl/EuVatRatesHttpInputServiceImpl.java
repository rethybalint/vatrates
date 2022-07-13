package com.example.vatrates.service.impl;

import com.example.vatrates.model.CountryRates;
import com.example.vatrates.service.EuVatRatesHttpInputService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EuVatRatesHttpInputServiceImpl implements EuVatRatesHttpInputService {

    private static final String API_URL = "https://euvatrates.com/rates.json";

    @Override
    @Cacheable("rates")
    public Set<CountryRates> getCountryRatesSet() {
        return parseJsonResponse(queryVatRatesApi());
    }

    private Set<CountryRates> parseJsonResponse(String jsonResponse) {
        Set<CountryRates> countryRatesSet = new HashSet<>();
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(jsonResponse, JsonElement.class);
        jsonElement.getAsJsonObject().get("rates").getAsJsonObject().entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(countryRates -> {
                    CountryRates newCountryRates = new CountryRates();
                    newCountryRates.setCountryName(countryRates.getAsJsonObject().get("country").getAsString());
                    newCountryRates.setStandardRate(extractBigDecimalFromJson(countryRates, "standard_rate"));
                    newCountryRates.setReducedRate(extractBigDecimalFromJson(countryRates, "reduced_rate"));
                    newCountryRates.setReducedRateAlt(extractBigDecimalFromJson(countryRates, "reduced_rate_alt"));
                    newCountryRates.setSuperReducedRate(extractBigDecimalFromJson(countryRates, "super_reduced_rate"));
                    newCountryRates.setParkingRate(extractBigDecimalFromJson(countryRates, "parking_rate"));
                    countryRatesSet.add(newCountryRates);
                });
        return countryRatesSet;
    }

    private BigDecimal extractBigDecimalFromJson(JsonElement countryRates, String rateName) {
        JsonElement rate = countryRates.getAsJsonObject().get(rateName);
        if (rate.getAsJsonPrimitive().isBoolean()) {
            return null;
        }
        return rate.getAsBigDecimal();
    }

    private String queryVatRatesApi() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL, String.class);
    }
}
