
![CubedPay Logo](https://app.cubedpay.com/static/media/CubedPayRoundSquare.2bab53a3.png)

# CubedPay Java
Java client for the CubedPay API.

The client is separated into 2 modules, the main java library and a bukkit plugin.

## Getting Your Keys
It's easy to get the keys you need:

1. Sign into http://app.cubedpay.com with your CubedPay account.
2. Visit the store you want to make purchases through
3. On the store navigation, select "Settings" then "Integrations".
4. Copy your Client ID and Secret. These will be your authentication methods.

## Using a Bot User
If you'd like to limit what access the client has to your store, use a Bot User instead.

1. Sign into http://app.cubedpay.com with your CubedPay account.
2. Visit the store you want to make purchases through
3. On the store navigation, select "Settings" then "Team".
4. Create a New Bot User with the permissions you'd like to allow.
5. Copy your bot's Bot ID and Secret. Use these in place of the regular App ID and Secret.

Please remember that using Bot Users requires your store to be on the **Unlimited** plan.

## API
### Standalone API
You can find the main standalone java library code in the `common` folder.

To get started with the API you need to create an instance of `CubedPayAPI` by calling 
`CubedPayAPI.create(String appID, String token)`.

To listen to store events you can call `CubedPayAPI#startEvents(String shopId)` and register listeners using
`CubedPayAPI#registerListener(Object listener)`. Events will called with methods that conform to the following format:
```java
public class Test {
    @PaymentHandler
    public void handle(PurchasedEvent event) {
        //Your code here...
    }
}
```

#### Requesting a payment from a player.
```java
package test;

import co.melondev.cubedpay.CubedPayAPI;
import co.melondev.cubedpay.data.Item;

public class PaymentRequest {
    
    public static void main(String[] args){
      CubedPayAPI cubedpay = CubedPayAPI.create("app_XXXXXXXXXXXXX", "oauth_XXXXXXXXXXXX");
      cubedpay.requestPayment("shop_XXXXXXXXXXX", "sale", 
        new Item("Item 1", 10.00, 1), //Add item on the fly
        new Item("package_XXXXXXXXXX", 2) //Get already defined item from the store panel
      ).thenAccept(paymentRequest -> System.out.println("Send user to "+paymentRequest.getAuthorize().getRedirectTo()));
      
      cubedpay.shutdown();
    }
    
}
```

### Bukkit Plugin
You can find the bukkit plugin code inside the `bukkit` folder.

The bukkit plugin will create a config on start that you need to fill in with your credentials from the panel. Every 
store that you add to the config will auto start the event system so all your need to do in your other plugins is 
register your event listeners.

You can get the API instance used by the plugin by calling `CubedPayPlugin.getAPI()`.

## Support
For any issues relating to the code, please create an issue. For any other help with the website/dashboard/purchases 
please [contact our support](https://cubedpay.com/support)
