package learn.mastery.Model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class Reservation {

    private String id;
    private LocalDate start_date;
    private LocalDate end_date;
    private BigDecimal total;

    private Guest guest;
    private Host host;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }



    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /*public BigDecimal getTotal() {
        BigDecimal standard = host.getStandard_rate();
        BigDecimal weekend = host.getWeekend_rate();

        *//*if ((start_date == null || end_date == null) ||
                (host.getWeekend_rate() <= 0 || host.getStandard_rate() <= 0)) {
            return BigDecimal.ZERO;
        }*//*
        *//*BigDecimal kilos = new BigDecimal(kilograms).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);
        return null;*//*

        Set<DayOfWeek> weekends = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
        final long weekDaysBetween = start_date.datesUntil(end_date)
                .filter(d -> !weekends.contains(d.getDayOfWeek()))
                .count();
        //BigDecimal weekDayCost = BigDecimal.valueOf(weekDaysBetween * (host.getStandard_rate()) );

        Set<DayOfWeek> weekday = EnumSet.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY);
        final long weekEndsBetween = start_date.datesUntil(end_date)
                .filter(d -> !weekday.contains(d.getDayOfWeek()))
                .count();
        //BigDecimal weekEndCost = BigDecimal.valueOf(weekEndsBetween * (host.getWeekend_rate()) );
        return standard.multiply(new BigDecimal(weekDaysBetween)).add(weekend.multiply(new BigDecimal(weekEndsBetween)));
        *//*total = weekEndCost.add(weekDayCost);
        return total;*//*
    }*/




}
