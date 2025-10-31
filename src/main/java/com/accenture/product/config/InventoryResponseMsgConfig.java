package com.accenture.product.config;

import com.accenture.product.config.factory.YamlPropSourceFactory;
import com.accenture.product.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:response_message.yaml", factory = YamlPropSourceFactory.class)
@ConfigurationProperties(prefix = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseMsgConfig {
    private String deletedMessage;
    private String stockNotAvailable;
    private String notFoundForProduct;
    private String notFound;
    private String productServiceDown;

    public String inventoryDeleted(Inventory inventory) {
        return String.format(deletedMessage, inventory);
    }

    public String noStockAvailable(Integer availableQty, Integer expectedQty) {
        return String.format(stockNotAvailable, availableQty, expectedQty);
    }

    public String inventoryNotFound(String productCode) {
        return String.format(notFoundForProduct, productCode);
    }
}
