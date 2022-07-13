package com.example.vatrates.service;

import com.example.vatrates.model.CountryRates;

import java.util.Set;

public interface EuVatRatesHttpInputService {
    Set<CountryRates> getCountryRatesSet();
}
