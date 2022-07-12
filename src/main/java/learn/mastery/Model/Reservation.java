package learn.mastery.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Reservation {

    private String id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Guest guest_id;
    private Host host;
    private double total;


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

    public Guest getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Guest guest_id) {
        this.guest_id = guest_id;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }



    public void setTotal(double total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        /*if ((start_date == null || end_date == null) ||
                (host.getWeekend_rate() > 0 || host.getStandard_rate() > 0)) {
            return BigDecimal.ZERO;
        }
        BigDecimal kilos = new BigDecimal(kilograms).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);*/
        return null;
    }




}
