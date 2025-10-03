package dev.sukriti.productservice.Controllers.Webhooks;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhooksController {

        @PostMapping("/")
        public void handleWebhookRequest() {
            // Handle Stripe webhook events here

        }
    }

