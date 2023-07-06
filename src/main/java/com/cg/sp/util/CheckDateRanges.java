package com.cg.sp.util;

import com.cg.sp.exceptionHandler.InvalidDateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CheckDateRanges {
    @Value("${timeGapInDays}")
    private int timeGapInDays;

    @Value(("${dateRangeInYears}"))
    private int dateRangeInYears;

    private Date endDate;
    private long checkTimeGap;

    private static final Logger LOG = LoggerFactory.getLogger(CheckDateRanges.class);

    public boolean validateMonthsDateRange(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date today = cal.getTime();
        LOG.info("today's date : {}", sdf.format(today));

        LOG.info("end date from request : {}", sdf.format(endDate));
        if (endDate.after(today)) {

            throw new InvalidDateRange("EndDate : " + sdf.format(endDate) + " can not be exceeded than yesterday : " + sdf.format(today));
        } else {
            if (startDate.before(endDate)) {
                long differenceInTime = endDate.getTime() - startDate.getTime();
                checkTimeGap = TimeUnit.MILLISECONDS.toDays(differenceInTime);
                if (checkTimeGap <= timeGapInDays && checkTimeGap >= 1) {
                    return true;
                } else {
                    throw new InvalidDateRange("Time Gap must have to be " + timeGapInDays + " days or LESS");
                }
            } else {
                throw new InvalidDateRange("Start Date can not be later than or equal to End Date");
            }
        }

    }

    public boolean validateYearsDateRange(Date startDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        LOG.info("{}", cal.get(Calendar.YEAR));
        cal.add(Calendar.YEAR, -dateRangeInYears);
        LOG.info("now year is : {}", cal.get(Calendar.YEAR));
        endDate = cal.getTime();
        if (startDate.before(endDate)) {
            throw new InvalidDateRange(sdf.format(startDate) + " date is before " + sdf.format(endDate) +
                    ".You can only fetch data for past " + dateRangeInYears + " years from today's date.");
        } else {
            return true;
        }
    }
    public boolean validateRegEx(String date) {
        if(GenericValidator.isDate(date,"dd-MM-yyyy",true))
            return true;
        else
            throw new InvalidDateRange("Kindly Check The Date Value And Format. Date Format should be dd-mm-yyyy");

    }
}
