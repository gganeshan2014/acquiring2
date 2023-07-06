package com.cg.sp.service;

import com.cg.sp.constants.Subscriptions;
import com.cg.sp.exceptionHandler.InvalidSubscription;
import com.cg.sp.model.converter.AcquiringPremiumDTO;
import com.cg.sp.model.converter.AcquiringStandardDTO;
import com.cg.sp.model.request.AcquiringNonPosRequest;
import com.cg.sp.model.request.UserProfile;
import com.cg.sp.model.response.AcquiringPremium;
import com.cg.sp.model.response.AcquiringStandard;
import com.cg.sp.repository.AcquiringPremiumRepository;
import com.cg.sp.repository.AcquiringStandardRepository;
import com.cg.sp.util.CheckDateRanges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AcquiringServiceImpl implements AcquiringService {

    @Autowired
    AcquiringStandardRepository acquiringStandardRepository;
    @Autowired
    AcquiringPremiumRepository acquiringPremiumRepository;

    @Autowired
    CheckDateRanges checkDateRanges;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private static final Logger LOG = LoggerFactory.getLogger(AcquiringServiceImpl.class);

    private List<AcquiringStandard> createStandardResponse(AcquiringNonPosRequest acquiringNonPosRequest, int pageNo, int size) {

        validateDateRange(acquiringNonPosRequest.getStartDate(), acquiringNonPosRequest.getEndDate());
        List<AcquiringStandard> acquiringStandardList = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo, size);
        List<AcquiringStandardDTO> acquiringStandardDTOList = null;
        try {
            acquiringStandardDTOList = acquiringStandardRepository.fetchStandardDetailsWithCrNumber(acquiringNonPosRequest.getCrNumber(),
                    sdf.parse(acquiringNonPosRequest.getStartDate()), sdf.parse(acquiringNonPosRequest.getEndDate()), pageable);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LOG.info("acquiringStandardDTO : {}", acquiringStandardDTOList);
        for (AcquiringStandardDTO acqStandard : acquiringStandardDTOList) {
            AcquiringStandard acquiringStandard = new AcquiringStandard();
            acquiringStandard.setTransactionVolume(acqStandard.getTotalVolume());
            acquiringStandard.setTransactionValue(acqStandard.getTotalTransactionValue());
            acquiringStandard.setChannel("PoS");
            acquiringStandard.setTerminalId(acqStandard.getTerminalId());
            acquiringStandard.setStartDate(acquiringNonPosRequest.getStartDate());
            acquiringStandard.setEndDate(acquiringNonPosRequest.getEndDate());
            acquiringStandardList.add(acquiringStandard);
        }
        return acquiringStandardList;
    }


    private List<AcquiringPremium> createPremiumResponse(AcquiringNonPosRequest acquiringNonPosRequest, int pageNo, int size) {

       validateDateRange(acquiringNonPosRequest.getStartDate(), acquiringNonPosRequest.getEndDate());

        List<AcquiringPremium> acquiringPremiumList = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNo, size);
        List<AcquiringPremiumDTO> acquiringPremiumDTOList = null;
        try {
            acquiringPremiumDTOList = acquiringPremiumRepository.fetchPremiumDetailsWithCrNumber(acquiringNonPosRequest.getCrNumber(),
                    sdf.parse(acquiringNonPosRequest.getStartDate()), sdf.parse(acquiringNonPosRequest.getEndDate()), pageable);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (AcquiringPremiumDTO acqPremium : acquiringPremiumDTOList) {
            AcquiringPremium acquiringPremium = new AcquiringPremium();
            acquiringPremium.setChannel("PoS");
            acquiringPremium.setTransactionValue(acqPremium.getTotalTransactionValue());
            acquiringPremium.setTransactionVolume(acqPremium.getTotalVolume());
            acquiringPremium.setRegion(acqPremium.getRegion());
            acquiringPremium.setCity(acqPremium.getCity());
            acquiringPremium.setLocation(acqPremium.getLocation());
            acquiringPremium.setMcc(acqPremium.getMcc());

            acquiringPremium.setMccDescription(acqPremium.getMccDescription());
            acquiringPremium.setTerminalId(acqPremium.getTerminalId());
            acquiringPremium.setTransDate(acqPremium.getTransactionDate());
            acquiringPremium.setStartDate(acquiringNonPosRequest.getStartDate());
            acquiringPremium.setEndDate(acquiringNonPosRequest.getEndDate());
            acquiringPremiumList.add(acquiringPremium);
        }
        return acquiringPremiumList;
    }


    @Override
    public Object createNonPosResponse(AcquiringNonPosRequest acquiringNonPosRequest, int pageNo, int size) {
        Subscriptions subscriptions = this.retrieveSubscription(acquiringNonPosRequest.getUserProfile());
        if (subscriptions.equals(Subscriptions.STANDARD))
            return createStandardResponse(acquiringNonPosRequest, pageNo, size);
        else if (subscriptions.equals(Subscriptions.PREMIUM))
            return createPremiumResponse(acquiringNonPosRequest, pageNo, size);
        else
            throw new InvalidSubscription("Invalid Subscription Type");
    }

    private void validateDateRange(String startDate, String endDate) {
        checkDateRanges.validateRegEx(startDate);
        checkDateRanges.validateRegEx(endDate);
        try {
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            checkDateRanges.validateYearsDateRange(sDate);
            checkDateRanges.validateMonthsDateRange(sDate, eDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private Subscriptions retrieveSubscription(UserProfile userProfile) {
        Subscriptions subscription = Subscriptions.getSubscriptionByValue(userProfile.getSubscription());
        if (subscription != null)
            return subscription;
        else
            throw new InvalidSubscription("Invalid Subscription Type");
    }
}
