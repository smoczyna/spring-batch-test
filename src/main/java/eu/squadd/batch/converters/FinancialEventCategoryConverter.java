/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.converters;

import eu.squadd.batch.domain.mongo.FinancialEventCategory;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author smoczyna
 */
public class FinancialEventCategoryConverter implements Converter<FinancialEventCategory, Document> {

    @Override
    public Document convert(FinancialEventCategory fec) {
        Document doc = new Document();
        doc.put("productid", fec.getProductid());
        doc.put("homesidequalsservingsidindicator", fec.getHomesidequalsservingsidindicator());
        doc.put("alternatebookingindicator", fec.getAlternatebookingindicator());
        doc.put("financialmarketid", fec.getFinancialmarketid());
        doc.put("interexchangecarriercode", fec.getInterexchangecarriercode());
        doc.put("adminfeereasoncode", fec.getAdminfeereasoncode());
        doc.put("bamsaffiliateindicator", fec.getBamsaffiliateindicator());
        doc.put("billingaccrualindicator", fec.getBillingaccrualindicator());
        doc.put("billtypecode", fec.getBilltypecode());
        doc.put("cashpostindicator", fec.getCashpostindicator());
        doc.put("cellularcarrierrslcode", fec.getCellularcarrierrslcode());
        doc.put("companycode", fec.getCompanycode());
        doc.put("contracttermsid", fec.getContracttermsid());
        doc.put("creditcardtypecode", fec.getCreditcardtypecode());
        doc.put("debitcreditindicator", fec.getDebitcreditindicator());
        doc.put("directindirectindicator", fec.getDirectindirectindicator());
        doc.put("exceptioncode", fec.getExceptioncode());
        doc.put("financialcategory", fec.getFinancialcategory());
        doc.put("financialcategorydescription", fec.getFinancialcategorydescription());
        doc.put("financialcategoryeffectivedate", fec.getFinancialcategoryeffectivedate());
        doc.put("financialcategoryenddate", fec.getFinancialcategoryenddate());
        doc.put("financialeventcategorycode", fec.getFinancialeventcategorycode());
        doc.put("financialeventcategorytype", fec.getFinancialeventcategorytype());
        doc.put("financialeventdescription", fec.getFinancialeventdescription());
        doc.put("financialeventdetails", fec.getFinancialeventdetails());
        doc.put("financialeventeffectivedate", fec.getFinancialeventeffectivedate());
        doc.put("financialeventenddate", fec.getFinancialeventenddate());
        doc.put("financialeventnormalsign", fec.getFinancialeventnormalsign());
        doc.put("financialeventnumber", fec.getFinancialeventnumber());
        doc.put("financialeventprocessingcode", fec.getFinancialeventprocessingcode());
        doc.put("financialgroupcode", fec.getFinancialgroupcode());
        doc.put("financialmappingeffectivedate", fec.getFinancialmappingeffectivedate());
        doc.put("financialmappingenddate", fec.getFinancialmappingenddate());
        doc.put("financialmarketsourcecode", fec.getFinancialmarketsourcecode());
        doc.put("foreignservedindicator", fec.getForeignservedindicator());
        doc.put("glaccountdescription", fec.getGlaccountdescription());
        doc.put("glaccountnumber", fec.getGlaccountnumber());
        doc.put("glcostcenternumber", fec.getGlcostcenternumber());
        doc.put("gleffectivedate", fec.getGleffectivedate());
        doc.put("glenddate", fec.getGlenddate());
        doc.put("glfinancialaccounttypecode", fec.getGlfinancialaccounttypecode());
        doc.put("gllocationid", fec.getGllocationid());
        doc.put("glproductid", fec.getGlproductid());
        doc.put("legalentityeqindicator", fec.getLegalentityeqindicator());
        doc.put("miscfinancialtransactionnumber", fec.getMiscfinancialtransactionnumber());
        doc.put("paymentmediacode", fec.getPaymentmediacode());
        doc.put("paymentsourcecode", fec.getPaymentsourcecode());
        doc.put("pricebandtypecode", fec.getPricebandtypecode());
        doc.put("processinggroupnumber", fec.getProcessinggroupnumber());
        doc.put("statecode", fec.getStatecode());
        doc.put("taxjurisdictionlevel", fec.getTaxjurisdictionlevel());
        doc.put("taxtypecode", fec.getTaxtypecode());
        doc.put("transactionitemtypecode", fec.getTransactionitemtypecode());
        doc.put("dummy", fec.getDummy());
        return doc;
    }

    public FinancialEventCategory convert(Document doc) {
        FinancialEventCategory fec = new FinancialEventCategory();
        fec.setProductid(doc.getInteger("productid"));
        fec.setHomesidequalsservingsidindicator(doc.getString("homesidequalsservingsidindicator"));
        fec.setAlternatebookingindicator(doc.getString("alternatebookingindicator"));
        fec.setFinancialmarketid(doc.getString("financialmarketid"));
        fec.setInterexchangecarriercode(doc.getInteger("interexchangecarriercode"));
        fec.setAdminfeereasoncode(doc.getString("adminfeereasoncode"));
        fec.setBamsaffiliateindicator(doc.getString("bamsaffiliateindicator"));
        fec.setBillingaccrualindicator(doc.getString("billingaccrualindicator"));
        fec.setBilltypecode(doc.getString("billtypecode"));
        fec.setCashpostindicator(doc.getString("cashpostindicator"));
        fec.setCellularcarrierrslcode(doc.getString("cellularcarrierrslcode"));
        fec.setCompanycode(doc.getString("companycode"));
        fec.setContracttermsid(doc.getInteger("contracttermsid"));
        fec.setCreditcardtypecode(doc.getString("creditcardtypecode"));
        fec.setDebitcreditindicator(doc.getString("debitcreditindicator"));
        fec.setDirectindirectindicator(doc.getString("directindirectindicator"));
        fec.setExceptioncode(doc.getString("exceptioncode"));
        fec.setFinancialcategory(doc.getInteger("financialcategory"));
        fec.setFinancialcategorydescription(doc.getString("financialcategorydescription"));
        fec.setFinancialcategoryeffectivedate(doc.getString("financialcategoryeffectivedate"));
        fec.setFinancialcategoryenddate(doc.getString("financialcategoryenddate"));
        fec.setFinancialeventcategorycode(doc.getString("financialeventcategorycode"));
        fec.setFinancialeventcategorytype(doc.getString("financialeventcategorytype"));
        fec.setFinancialeventdescription(doc.getString("financialeventdescription"));
        fec.setFinancialeventdescription(doc.getString("financialeventdescription"));
        fec.setFinancialeventdetails(doc.getString("financialeventdetails"));
        fec.setFinancialeventdetails(doc.getString("financialeventdetails"));
        fec.setFinancialeventeffectivedate(doc.getString("financialeventeffectivedate"));
        fec.setFinancialeventenddate(doc.getString("financialeventenddate"));
        fec.setFinancialeventnormalsign(doc.getString("financialeventnormalsign"));
        fec.setFinancialeventnumber(doc.getInteger("financialeventnumber"));
        fec.setFinancialeventprocessingcode(doc.getString("financialeventprocessingcode"));
        fec.setFinancialgroupcode(doc.getString("financialgroupcode"));
        fec.setFinancialmappingeffectivedate(doc.getString("financialmappingeffectivedate"));
        fec.setFinancialmappingenddate(doc.getString("financialmappingenddate"));
        fec.setFinancialmarketsourcecode(doc.getString("financialmarketsourcecode"));
        fec.setForeignservedindicator(doc.getString("foreignservedindicator"));
        fec.setGlaccountdescription(doc.getString("glaccountdescription"));
        fec.setGlaccountnumber(doc.getString("glaccountnumber"));
        fec.setGlcostcenternumber(doc.getString("glcostcenternumber"));
        fec.setGleffectivedate(doc.getString("gleffectivedate"));
        fec.setGlenddate(doc.getString("glenddate"));
        fec.setGlfinancialaccounttypecode(doc.getString("glfinancialaccounttypecode"));
        fec.setGllocationid(doc.getString("gllocationid"));
        fec.setGlproductid(doc.getString("glproductid"));
        fec.setLegalentityeqindicator(doc.getString("legalentityeqindicator"));
        fec.setMiscfinancialtransactionnumber(doc.getInteger("miscfinancialtransactionnumber"));
        fec.setPaymentmediacode(doc.getString("paymentmediacode"));
        fec.setPaymentsourcecode(doc.getString("paymentsourcecode"));
        fec.setPricebandtypecode(doc.getString("pricebandtypecode"));
        fec.setProcessinggroupnumber(doc.getString("processinggroupnumber"));
        fec.setStatecode(doc.getString("statecode"));
        fec.setTaxjurisdictionlevel(doc.getString("taxjurisdictionlevel"));
        fec.setTaxtypecode(doc.getString("taxtypecode"));
        fec.setTransactionitemtypecode(doc.getString("transactionitemtypecode"));
        fec.setDummy(doc.getString("dummy"));        
        return fec;
    }
}
