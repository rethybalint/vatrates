package com.example.vatrates.service.impl;

import com.example.vatrates.model.CountryRates;
import com.example.vatrates.model.RateType;
import com.example.vatrates.service.EuVatRatesHttpInputService;
import com.example.vatrates.service.VatRatesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class VatRatesServiceImpl implements VatRatesService {

    private final EuVatRatesHttpInputService euVatRatesHttpInputService;

    public VatRatesServiceImpl(EuVatRatesHttpInputService euVatRatesHttpInputService) {
        this.euVatRatesHttpInputService = euVatRatesHttpInputService;
    }

    @Override
    public List<CountryRates> queryCountryRates(RateType sortBy, Boolean descending, int resultsNum) {
        Function<CountryRates, BigDecimal> parameterGetter = getParameterGetter(sortBy);
        return euVatRatesHttpInputService.getCountryRatesSet().stream()
                .filter(countryRates -> parameterGetter.apply(countryRates) != null)
                .sorted(getCountryRatesComparator(parameterGetter, descending))
                .limit(resultsNum)
                .collect(toList());
    }

    private Function<CountryRates, BigDecimal> getParameterGetter(RateType rateType) {
        switch (rateType) {
            case STANDARD_RATE:
                return CountryRates::getStandardRate;
            case REDUCED_RATE:
                return CountryRates::getReducedRate;
            case REDUCED_RATE_ALT:
                return CountryRates::getReducedRateAlt;
            case SUPER_REDUCED_RATE:
                return CountryRates::getSuperReducedRate;
            case PARKING_RATE:
                return CountryRates::getParkingRate;
            default:
                throw new IllegalArgumentException("Unsupported rate type: " + rateType);
        }
    }

    private Comparator<CountryRates> getCountryRatesComparator(Function<CountryRates, BigDecimal> getter, boolean descending) {
        if (descending) {
            return Comparator.comparing(getter).reversed();
        }
        return Comparator.comparing(getter);
    }
}
