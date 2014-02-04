package appLogic.utils;

import java.net.InetAddress;


public class Order {
    private InetAddress address;
    private Long clockValue;

    public Order(InetAddress address, Long clockValue) {
        this.address = address;
        this.clockValue = clockValue;
    }

    public InetAddress getAddress() {
        return address;
    }

    public Long getClockValue() {
        return clockValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (clockValue != null ? !clockValue.equals(order.clockValue) : order.clockValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (clockValue != null ? clockValue.hashCode() : 0);
        return result;
    }
}