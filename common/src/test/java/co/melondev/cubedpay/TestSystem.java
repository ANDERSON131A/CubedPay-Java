package co.melondev.cubedpay;

import co.melondev.cubedpay.data.Item;
import co.melondev.cubedpay.event.PaymentHandler;
import co.melondev.cubedpay.event.PurchasedEvent;

import java.util.ArrayList;

public class TestSystem {

    public static class EventTester {

        @PaymentHandler
        public void onPurchased(PurchasedEvent event) {
            System.out.println("Purchased: " + event.getId());
        }
    }

    public static void main(String[] args) {
        String appID = "";
        String accessToken = "";
        String shopID = "";

        CubedPayAPI api = CubedPayAPI.create(appID, accessToken);
        api.registerListener(new EventTester());
        api.startEvents(shopID);

        api.requestPayment(shopID, new ArrayList<Item>() {{
            new Item("Diamond", 1.0, 1);
            new Item("Diamond Sword", 2.0, 1);
        }}, "sale").thenAccept(payment -> System.out.println("Payment url: " + payment.getAuthorize().getRedirectTo()));
    }
}