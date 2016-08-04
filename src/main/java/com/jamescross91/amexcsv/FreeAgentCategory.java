package com.jamescross91.amexcsv;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FreeAgentCategory {
    private static final String ACCOMODATION_AND_MEALS = "https://api.freeagent.com/v2/categories/285";
    private static final String TRAVEL = "https://api.freeagent.com/v2/categories/365";
    private static final String OFFICE_COSTS = "https://api.freeagent.com/v2/categories/250";
    private static final String COMPUTER_SOFTWARE = "https://api.freeagent.com/v2/categories/269";
    private static final String STAFF_TRAINING = "https://api.freeagent.com/v2/categories/282";

    private static final Map<String, String> descriptionCategoryMap = new HashMap<String, String>() {{
        put("TESCO", ACCOMODATION_AND_MEALS);
        put("TFL", TRAVEL);
        put("TRAINLINE", TRAVEL);
        put("ZIPCAR", TRAVEL);
        put("UBER", TRAVEL);
        put("STARBUCKS", ACCOMODATION_AND_MEALS);
        put("COSTA", ACCOMODATION_AND_MEALS);
        put("PRET", ACCOMODATION_AND_MEALS);
        put("NERO", ACCOMODATION_AND_MEALS);
        put("LEON", ACCOMODATION_AND_MEALS);
        put("NANDO'S", ACCOMODATION_AND_MEALS);
        put("WASABI", ACCOMODATION_AND_MEALS);
        put("CURATORS COFFEE GALLERY", ACCOMODATION_AND_MEALS);
        put("APPLE ITUNES STORE", COMPUTER_SOFTWARE);
        put("RAVE", OFFICE_COSTS);
        put("UDEMY", STAFF_TRAINING);
    }};

    public static String categoryForDescription(String desc) {
        Set<String> mapKeys = descriptionCategoryMap.keySet();

        for(String key : mapKeys) {
            if(desc.contains(key)) {
                return descriptionCategoryMap.get(key);
            }
        }

        throw new RuntimeException("Unable to find category for " + desc);
    }

}
