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
}