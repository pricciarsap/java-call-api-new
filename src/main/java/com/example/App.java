package com.example;

import com.google.gson.GsonBuilder;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.example.vdm.namespaces.businesspartner.BusinessPartner;
import com.example.vdm.namespaces.businesspartner.BusinessPartnerFluentHelper;
import com.example.vdm.services.APIBUSINESSPARTNERService;
import com.example.vdm.services.DefaultAPIBUSINESSPARTNERService;

import java.util.List;

public class App {
    public static void main(String[] args) {

        // Create the destination class
        HttpDestination destination = DefaultDestination.builder()
                .property("Name", "SANDBOX")
                .property("URL", "https://sandbox.api.sap.com/s4hanacloud")
                .property("Type", "HTTP")
                .property("Authentication", "NoAuthentication")
                .build().asHttp();

        // Preparing the query to the Business Partner service
        final APIBUSINESSPARTNERService service =
                new DefaultAPIBUSINESSPARTNERService();
        BusinessPartnerFluentHelper helper =
                service.getAllBusinessPartner()
                        .select(
                                BusinessPartner.BUSINESS_PARTNER,
                                BusinessPartner.LAST_NAME,
                                BusinessPartner.FIRST_NAME)
                        .filter(
                                BusinessPartner.FIRST_NAME.ge("E")
                                        .and(BusinessPartner.FIRST_NAME.lt("F")));

        // Setting the API Key in the request header (required for the
        // SAP API Business Hub sandbox system, not required for
        // S/4HANA Cloud).
        helper.withHeader("apikey", "EDqbnClM7mfmgqbHKBFKRpT1dhpt8sHF");

        // Running the query
        List<BusinessPartner> list = helper.executeRequest(destination);

        // Print out the result in a pretty JSON format.
        System.out.println(new GsonBuilder().disableHtmlEscaping()
                .setPrettyPrinting().create().toJson(list));
    }
}