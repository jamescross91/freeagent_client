package com.jamescross91.amexcsv;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FreeAgentCategory {
    private static final String ACCOMMODATION_AND_MEALS = "https://api.freeagent.com/v2/categories/285";
    private static final String TRAVEL = "https://api.freeagent.com/v2/categories/365";
    private static final String OFFICE_COSTS = "https://api.freeagent.com/v2/categories/250";
    private static final String COMPUTER_SOFTWARE = "https://api.freeagent.com/v2/categories/269";
    private static final String COMPUTER_HARDWARE = "https://api.freeagent.com/v2/categories/270";
    private static final String STAFF_TRAINING = "https://api.freeagent.com/v2/categories/282";
    private static final String INTERNET_AND_TELEPHONE = "https://api.freeagent.com/v2/categories/273";
    private static final String POSTAGE = "https://api.freeagent.com/v2/categories/358";
    private static final String STATIONARY = "https://api.freeagent.com/v2/categories/359";
    private static final String STAFF_ENTERTAINMENT = "https://api.freeagent.com/v2/categories/289";
    private static final String SUBCONTRACTOR_COSTS = "https://api.freeagent.com/v2/categories/150";
    private static final String WEB_HOSTING = "https://api.freeagent.com/v2/categories/268";
    private static final String ACCOUNTING_FEES = "https://api.freeagent.com/v2/categories/292";

    private static final Map<String, String> descriptionCategoryMap = new HashMap<String, String>() {{
        put("UPWORK", SUBCONTRACTOR_COSTS);
        put("LINKEDIN", SUBCONTRACTOR_COSTS);

        put("TFL", TRAVEL);
        put("TRAINLINE", TRAVEL);
        put("TL RAILWAY TICKETS ST ALBANS", TRAVEL);
        put("ZIPCAR", TRAVEL);
        put("UBER", TRAVEL);
        put("Uber", TRAVEL);
        put("GETT", TRAVEL);
        put("APCOA PARKING - HAL CIT HOUNSLOW", TRAVEL);
        put("LOOKINGFORCARPARK", TRAVEL);
        put("DFW UNITED AIRLINES DFW MEDLEY", TRAVEL);
        put("PAYPAL*PAYPAL *TRAINLIN LONDON", TRAVEL);
        put("LUL TICKET MACHINE", TRAVEL);
        put("BA.COM", TRAVEL);
        put("BRITISH AIRWAYS DIRECT USA", TRAVEL);
        put("HERTZ", TRAVEL);
        put("PATHTVM*WORLD", TRAVEL);
        put("LUTON AIRPORT LUTON", TRAVEL);
        put("ST PANCRAS STATION LONDON", TRAVEL);
        put("HOLIDAY INN MUNICH - CI MUENCHEN", TRAVEL);
        put("HEATHROW AIRPORT - PARK HOUNSLOW", TRAVEL);
        put("GN RAILWAY TICKETS WELWYN GARDEN", TRAVEL);
        put("KINGS CROSS LONDON LONDON", TRAVEL);
        put("AIR CANADA", TRAVEL);
        put("INDIGO UK-GTR", TRAVEL);
        put("ENTERPRISE RENT A CAR", TRAVEL);
        put("NJT NWK-INT AIR", TRAVEL);
        put("SUNOCO", TRAVEL);
        put("EAST COAST - KINGS CROS LONDON", TRAVEL);
        put("MYDRIVER.COM", TRAVEL);
        put("LNE RAILWAY KGX STN LONDON", TRAVEL);
        put("NCP", TRAVEL);
        put("GTR RAIL TICKET", TRAVEL);
        put("Saba UK-GTR Hertfordshire", TRAVEL);
        put("NODE WAY FILLING STAT", TRAVEL);
        put("GATWICK AIRPORT-SHORTST", TRAVEL);

        put("STARBUCKS", ACCOMMODATION_AND_MEALS);
        put("COSTA", ACCOMMODATION_AND_MEALS);
        put("PRET", ACCOMMODATION_AND_MEALS);
        put("PIZZA HUT", ACCOMMODATION_AND_MEALS);
        put("RED DOG SALOON", ACCOMMODATION_AND_MEALS);
        put("NERO", ACCOMMODATION_AND_MEALS);
        put("LEON", ACCOMMODATION_AND_MEALS);
        put("NANDO'S", ACCOMMODATION_AND_MEALS);
        put("WASABI", ACCOMMODATION_AND_MEALS);
        put("MCDONALD", ACCOMMODATION_AND_MEALS);
        put("CURATORS COFFEE GALLERY", ACCOMMODATION_AND_MEALS);
        put("COCO DI MAMA LONDON", ACCOMMODATION_AND_MEALS);
        put("YOYOWALLET.COM", ACCOMMODATION_AND_MEALS);
        put("YOYO WALLET LTD", ACCOMMODATION_AND_MEALS);
        put("THE COFFEE RUN LIMI", ACCOMMODATION_AND_MEALS);
        put("BENITOS HAT", ACCOMMODATION_AND_MEALS);
        put("MASTERPIECE CATERERS NEW YORK", ACCOMMODATION_AND_MEALS);
        put("CANTEEN VENDING AVENEL", ACCOMMODATION_AND_MEALS);
        put("TESCO", ACCOMMODATION_AND_MEALS);
        put("KINGS CROSS PASTY SHOP", ACCOMMODATION_AND_MEALS);
        put("GIRAFFE", ACCOMMODATION_AND_MEALS);
        put("THE QUEENS HEAD LONDON", ACCOMMODATION_AND_MEALS);
        put("WATERLOO STATION BURGER", ACCOMMODATION_AND_MEALS);
        put("DES VINS CAFE AND WINE LONDON", ACCOMMODATION_AND_MEALS);
        put("PREZZO LONDON", ACCOMMODATION_AND_MEALS);
        put("YO SUSHI", ACCOMMODATION_AND_MEALS);
        put("WAITROSE 0767 314N POTTERS BAR", ACCOMMODATION_AND_MEALS);
        put("CARLUCCIO'S ST", ACCOMMODATION_AND_MEALS);
        put("GOURMET BURGER KITCHEN", ACCOMMODATION_AND_MEALS);
        put("BA HIGH LIFE SHOP HORLEY", ACCOMMODATION_AND_MEALS);
        put("HYATT JERSEY CITY", ACCOMMODATION_AND_MEALS);
        put("NEW LONDON THEATRE LONDON", ACCOMMODATION_AND_MEALS);
        put("THE REAL GREEK LONDON", ACCOMMODATION_AND_MEALS);
        put("EAT (PICCADILLY) LONDON", ACCOMMODATION_AND_MEALS);
        put("WH SMITH KINGS CROSS", ACCOMMODATION_AND_MEALS);
        put("KALPESH PATEL - MCDONAL", ACCOMMODATION_AND_MEALS);
        put("SOURCED MARKET LONDON", ACCOMMODATION_AND_MEALS);
        put("HOFBRAEUHAUS AM PLATZL MUENCHEN", ACCOMMODATION_AND_MEALS);
        put("CDM - 108 - BANKSIDE LONDON", ACCOMMODATION_AND_MEALS);
        put("KRISPY KREME UK LTD", ACCOMMODATION_AND_MEALS);
        put("TORTILLA BANKSIDE LONDON", ACCOMMODATION_AND_MEALS);
        put("IZ *GAIL_S LIMITED HENDON", ACCOMMODATION_AND_MEALS);
        put("SOUTHWARK - ANCHOR BANK LONDON", ACCOMMODATION_AND_MEALS);
        put("TATE MODERN LONDON", ACCOMMODATION_AND_MEALS);
        put("CAFE ROUGE", ACCOMMODATION_AND_MEALS);
        put("WAGAMAMA", ACCOMMODATION_AND_MEALS);
        put("TOSSED", ACCOMMODATION_AND_MEALS);
        put("IZ *THE COLOMBIAN COFFE", ACCOMMODATION_AND_MEALS);
        put("PAUL", ACCOMMODATION_AND_MEALS);
        put("VAPIANO", ACCOMMODATION_AND_MEALS);
        put("CAPRICCI", ACCOMMODATION_AND_MEALS);
        put("WH SMITH BANKSIDE LONDON", ACCOMMODATION_AND_MEALS);
        put("JUST EAT.CO.UK LTD LONDON", ACCOMMODATION_AND_MEALS);
        put("BELGO", ACCOMMODATION_AND_MEALS);
        put("ZIZZI", ACCOMMODATION_AND_MEALS);
        put("THECOFFEEFACTORY", ACCOMMODATION_AND_MEALS);
        put("THE RAILWAY TAVERN BARNET", ACCOMMODATION_AND_MEALS);
        put("EXPEDIA", ACCOMMODATION_AND_MEALS);
        put("IZ *CHICKEN WORLDWIDE L LONDON", ACCOMMODATION_AND_MEALS);
        put("CHICK-FIL-A", ACCOMMODATION_AND_MEALS);
        put("HILTON", ACCOMMODATION_AND_MEALS);
        put("AUNTIE ANNE'S", ACCOMMODATION_AND_MEALS);
        put("Burger King", ACCOMMODATION_AND_MEALS);
        put("VIGILS REAL BARBEQUE", ACCOMMODATION_AND_MEALS);
        put("SARATOGA CASINO HOTEL", ACCOMMODATION_AND_MEALS);
        put("SARATOGA GAMING AND RAC", ACCOMMODATION_AND_MEALS);
        put("CHIPOTLE", ACCOMMODATION_AND_MEALS);
        put("CVS/PHARMACY", ACCOMMODATION_AND_MEALS);
        put("HOTEL MELA", ACCOMMODATION_AND_MEALS);
        put("Tonys Di Napoli", ACCOMMODATION_AND_MEALS);
        put("LAGUARDIAAUBONPAIN72", ACCOMMODATION_AND_MEALS);
        put("OLIVE GARDEN", ACCOMMODATION_AND_MEALS);
        put("WHOLEFDS BPK", ACCOMMODATION_AND_MEALS);
        put("BOOTS THE CHEMIST", ACCOMMODATION_AND_MEALS);
        put("DUANE READE", ACCOMMODATION_AND_MEALS);
        put("UBS NYC G GO", ACCOMMODATION_AND_MEALS);
        put("WEWORK", ACCOMMODATION_AND_MEALS);
        put("AIRBNB", ACCOMMODATION_AND_MEALS);

        put("FERNSNPET", STAFF_ENTERTAINMENT);
        put("FNP New Delhi", STAFF_ENTERTAINMENT);
        put("PAY U FERN", STAFF_ENTERTAINMENT);

        put("APPLE ITUNES STORE", COMPUTER_SOFTWARE);
        put("GITHUB SAN FRANCISCO", COMPUTER_SOFTWARE);
        put("APPLE.COM/BILL", COMPUTER_SOFTWARE);
        put("ITUNES.COM", COMPUTER_SOFTWARE);
        put("PAYPAL*DIGITALRIVE", COMPUTER_SOFTWARE);
        put("POSTBOX", COMPUTER_SOFTWARE);
        put("PLEXINC", COMPUTER_SOFTWARE);
        put("ITUNES.COM/BILL LUXEMBOURG", COMPUTER_SOFTWARE);
        put("SPOTIFY", COMPUTER_SOFTWARE);
        put("RING", COMPUTER_SOFTWARE);
        put("FACEBK", COMPUTER_SOFTWARE);
        put("JETBRAINS", COMPUTER_SOFTWARE);
        put("DOCUSIGN", COMPUTER_SOFTWARE);

        put("RAVE", OFFICE_COSTS);
        put("DOMESTICO", OFFICE_COSTS);
        put("AMAZON UK MARKETPLACE 800-279-6620", OFFICE_COSTS);
        put("ARGOS", OFFICE_COSTS);
        put("PAYPAL*OPTIMALPRIN 35314369001", OFFICE_COSTS);
        put("LEDHUTUK", OFFICE_COSTS);
        put("THE HOXTON MIX LTD", OFFICE_COSTS);

        put("UDEMY", STAFF_TRAINING);
        put("KRYTERION INC PHOENIX", STAFF_TRAINING);
        put("THEPROPERTYHUB", STAFF_TRAINING);
        put("PSI SERVICES LLC", STAFF_TRAINING);

        put("NEWSGROUPDIRECT", INTERNET_AND_TELEPHONE);
        put("DROPBOX", INTERNET_AND_TELEPHONE);
        put("LINODE", INTERNET_AND_TELEPHONE);
        put("GIGANEWS", INTERNET_AND_TELEPHONE);
        put("SKYPE", INTERNET_AND_TELEPHONE);


        put("AMAZON WEB SERVICES", WEB_HOSTING);
        put("AWS.AMAZON.CO", WEB_HOSTING);
        put("HETZNER ONLINE", WEB_HOSTING);
        put("GODADDY", WEB_HOSTING);
        put("WP ENGINE AUSTIN", WEB_HOSTING);
        put("GOOGLE *SVCSAPPS", WEB_HOSTING);
        put("GOOGLE *GSUITE_JAMESCR", WEB_HOSTING);
        put("GOOGLE *ADS", WEB_HOSTING);
        put("AMAZON DIGITAL SVCS", WEB_HOSTING);
        put("CIRCLECI", WEB_HOSTING);
        put("GITHUB", WEB_HOSTING);
        put("MSFT", WEB_HOSTING);

        put("POST OFFICE", POSTAGE);
        put("ROYAL MAIL", POSTAGE);
        put("ROYALMAILGR", POSTAGE);
        put("DHL", POSTAGE);
        put("PARCELFORCE", POSTAGE);
        put("PARCEL2GO", POSTAGE);
        put("UPS_GB", POSTAGE);

        put("RYMANS", STATIONARY);
        put("MOO.COM", STATIONARY);

        put("CURRYS", COMPUTER_HARDWARE);
        put("APPLE DISTRIBUTION INTE CORK", COMPUTER_HARDWARE);
        put("Apple Store 6313914350 NewYork", COMPUTER_HARDWARE);
        put("PAYPAL*PAUL", COMPUTER_HARDWARE);
        put("RICHER SOUNDS", COMPUTER_HARDWARE);
        put("PAYPAL*PAYPAL*NATHANKIR", COMPUTER_HARDWARE);
        put("AMAZON UK - Airport Extreme Dock", COMPUTER_HARDWARE);
        put("AMAZON UK - Speakers for smart amp", COMPUTER_HARDWARE);
        put("AMAZON UK - IoT smart lighting for testing", COMPUTER_HARDWARE);
        put("AMAZON UK - Wiring for smart IoT amplifier", COMPUTER_HARDWARE);
        put("AMAZON UK - Smart audio amp for development work", COMPUTER_HARDWARE);
        put("APPLE ONLINE UK HOLLYHILL", COMPUTER_HARDWARE);
        put("AMAZON Snowball Ice", COMPUTER_HARDWARE);
        put("CYBER INFRASTRUCTURE SAN JOSE", COMPUTER_HARDWARE);
        put("GOOGLE STORE", COMPUTER_HARDWARE);
        put("AMAZON SELLER SERVICES BANGALORE", COMPUTER_HARDWARE);

        put("A K E TAX & ACCOUNTAN", ACCOUNTING_FEES);
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
