package by.ita.chernook;

import by.ita.chernook.service.DeliveryAddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeliveryAddressServiceTest extends TestUtils {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Test
    public void testDeleteDeliveryAddress() {
        String uuid = UUID.randomUUID().toString();

        deliveryAddressService.deleteDeliveryAddress(uuid);

        verify(restTemplate, times(1)).delete(String.format(REQUEST_DELIVERY_ADDRESS_DELETE, uuid));
    }
}
