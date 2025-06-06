package com.loja.suplementos.shipping;

import com.loja.suplementos.address.repository.DeliveryAddressRepository;
import com.loja.suplementos.shipping.domain.Shipping;
import com.loja.suplementos.shipping.domain.ShippingStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingRestController {

     private final ShippingService shippingService;
     private final DeliveryAddressRepository deliveryAddressRepository;

     @PostMapping()
     public ResponseEntity<?> saveShipping(@RequestBody Map<String, String> requestBody) {
         Long deliveryAddressId = Long.valueOf(requestBody.get("deliveryAddress"));

         var deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
             .orElseThrow(() -> new IllegalArgumentException("Invalid delivery address ID: " + deliveryAddressId));

         Shipping shipping = new Shipping();
         shipping.setDeliveryAddress(deliveryAddress);
         shipping.setTrackingNumber(requestBody.get("trackingNumber"));
         shipping.setStatus(ShippingStatus.valueOf(requestBody.get("status")));
         shipping.setStatusDescription(requestBody.get("statusDescription"));

         shippingService.save(shipping);

         return ResponseEntity.ok().body(shipping);
     }

     @PutMapping("/edit/{id}")
     public ResponseEntity<?> updateShipping(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
         Long deliveryAddressId = Long.valueOf(requestBody.get("deliveryAddress"));

         var deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
             .orElseThrow(() -> new IllegalArgumentException("Invalid delivery address ID: " + deliveryAddressId));

         Shipping shipping = shippingService.findById(id);
         shipping.setDeliveryAddress(deliveryAddress);
         shipping.setTrackingNumber(requestBody.get("trackingNumber"));
         shipping.setStatus(ShippingStatus.valueOf(requestBody.get("status")));
         shipping.setStatusDescription(requestBody.get("statusDescription"));

         shippingService.update(shipping);

         return ResponseEntity.ok().build();
     }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteShipping(@PathVariable Long id) {
         shippingService.delete(id);

         return ResponseEntity.ok().build();
     }
}
