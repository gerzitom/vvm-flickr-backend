package cz.cvut.fit.vmm.backend.distance;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateDistance {
    private static final double DAYS_IN_YEAR = 365;
    private static final double MAX_DAY_DIFFERENCE = DAYS_IN_YEAR * 3;

    public Double computeNormalized(Date date1, Date date2){
        if(date1 == null || date2 == null) return 0D;
        double dayDifference = differenceInMiliseconds(date1, date2);
        if(dayDifference > MAX_DAY_DIFFERENCE) return 0D;
        return 1 - dayDifference / MAX_DAY_DIFFERENCE;
    }

    private double differenceInMiliseconds(Date date1, Date date2){
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
