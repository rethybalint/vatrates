package com.example.vatrates.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CountryRates {
    private String countryName;
    private BigDecimal standardRate;
    private BigDecimal reducedRate;
    private BigDecimal reducedRateAlt;
    private BigDecimal superReducedRate;
    private BigDecimal parkingRate;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(BigDecimal reducedRate) {
        this.reducedRate = reducedRate;
    }

    public BigDecimal getReducedRateAlt() {
        return reducedRateAlt;
    }

    public void setReducedRateAlt(BigDecimal reducedRateAlt) {
        this.reducedRateAlt = reducedRateAlt;
    }

    public BigDecimal getSuperReducedRate() {
        return superReducedRate;
    }

    public void setSuperReducedRate(BigDecimal superReducedRate) {
        this.superReducedRate = superReducedRate;
    }

    public BigDecimal getParkingRate() {
        return parkingRate;
    }

    public void setParkingRate(BigDecimal parkingRate) {
        this.parkingRate = parkingRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryRates that = (CountryRates) o;
        return Objects.equals(countryName, that.countryName) && Objects.equals(standardRate, that.standardRate) && Objects.equals(reducedRate, that.reducedRate) &&
                Objects.equals(reducedRateAlt, that.reducedRateAlt) && Objects.equals(superReducedRate, that.superReducedRate) && Objects.equals(parkingRate, that.parkingRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, standardRate, reducedRate, reducedRateAlt, superReducedRate, parkingRate);
    }
}
