package com.example.vatrates.service;

import com.example.vatrates.model.CountryRates;
import com.example.vatrates.model.RateType;

import java.util.List;

public interface VatRatesService {
    List<CountryRates> queryCountryRates(RateType sortBy, Boolean descending, int limit);
}
