package com.cg.sp.model.converter;

import java.util.Date;

public interface AcquiringPremiumDTO {

    Date getTransactionDate();
    String getTerminalId();
    String getRegion();
    String getCity();
    String getLocation();
    String getMcc();
    String getMccDescription();
    Integer getTotalVolume();
    Double getTotalTransactionValue();

}
