/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author smorcja
 */
public class WholesaleReportFixedLengthFileWriter extends FixedLengthGenericFileWriter<AggregateWholesaleReportDTO> {
    private static final Map<String, Integer> FIELDS;
    static {
        FIELDS = new LinkedHashMap();
        FIELDS.put("cycleMonthYear", 6);
        FIELDS.put("startDate", 10);
        FIELDS.put("endDate", 10);
        FIELDS.put("homeLegalEntity", 5);
        FIELDS.put("servingLegalEntity", 5);
        FIELDS.put("homeFinancialMarketId", 3);
        FIELDS.put("servingFinancialMarketId", 3);
        FIELDS.put("productDiscountOfferId", 10);
        FIELDS.put("contractTermId", 10);
        FIELDS.put("peakDollarAmt", 12);
        FIELDS.put("offpeakDollarAmt", 12);
        FIELDS.put("voiceMinutes", 10);
        FIELDS.put("tollDollarsAmt", 12);
        FIELDS.put("tollMinutes", 10);
        FIELDS.put("dollarAmt3G", 12);
        FIELDS.put("usage3G", 12);
        FIELDS.put("dollarAmt4G", 12);
        FIELDS.put("usage4G", 12);
        FIELDS.put("dollarAmtOther", 12);
        FIELDS.put("dbCrInd", 2);
        FIELDS.put("billedInd", 1);
    }    

    public WholesaleReportFixedLengthFileWriter(String filePath) {
        super(AggregateWholesaleReportDTO.class, filePath, FIELDS);
    }
}