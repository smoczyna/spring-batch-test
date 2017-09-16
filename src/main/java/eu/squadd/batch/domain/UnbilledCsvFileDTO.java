/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 * Unbilled Bookings source payload class. This class represents the input file for billed bookings file: cmdunld.csv
 * @author smorcja
 */
public class UnbilledCsvFileDTO extends BaseBookingDTO {
    
    private Long totalWholesaleUsage;

    public Long getTotalWholesaleUsage() {
        return totalWholesaleUsage;
    }

    public void setTotalWholesaleUsage(Long totalWholesaleUsage) {
        this.totalWholesaleUsage = totalWholesaleUsage;
    }
}
