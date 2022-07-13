package com.example.vatrates.rest;

import com.example.vatrates.model.CountryRates;
import com.example.vatrates.model.RateType;
import com.example.vatrates.service.VatRatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vat-rates")
public class VatRatesController {

    private final VatRatesService vatRatesService;

    public VatRatesController(VatRatesService vatRatesService) {
        this.vatRatesService = vatRatesService;
    }

    @GetMapping
    @Operation(summary = "Query countries with their VAT rates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Countries sorted by VAT rates",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryRates.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input parameter(s)") })
    public ResponseEntity<List<CountryRates>> queryCountryRates(@Parameter(description = "VAT rate type to sort by") @RequestParam(value = "sort", defaultValue = "STANDARD_RATE") RateType sortBy,
                                                           @Parameter(description = "Sort in descending order") @RequestParam(value = "desc", defaultValue = "false") boolean descending,
                                                           @Parameter(description = "Number of results to show") @RequestParam(value = "resultsnum", defaultValue = "3") Integer resultsNum) {
        return ResponseEntity.ok(vatRatesService.queryCountryRates(sortBy, descending, resultsNum));
    }

}
