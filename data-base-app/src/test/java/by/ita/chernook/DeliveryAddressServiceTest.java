package by.ita.chernook;

import by.ita.chernook.model.DeliveryAddress;
import by.ita.chernook.repository.DeliveryAddressRepository;
import by.ita.chernook.service.DeliveryAddressService;
import by.ita.chernook.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryAddressServiceTest extends TestUtils {

    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Test
    void insertDeliveryAddress_then_return() {
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(UUID.randomUUID(), "Test city", "Test street", "20а", 15);

        when(deliveryAddressRepository.save(testDeliveryAddress)).thenReturn(testDeliveryAddress);

        DeliveryAddress actualDeliveryAddress= deliveryAddressService.insertDeliveryAddress(testDeliveryAddress);

        assertEquals(testDeliveryAddress, actualDeliveryAddress);

        verify(deliveryAddressRepository, times(1)).save(testDeliveryAddress);
    }

    @Test
    void updateDeliveryAddress_then_return() {
        UUID deliveryAddressUuid = UUID.randomUUID();
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(deliveryAddressUuid, "Test city", "Test street", "20а", 15);

        when(deliveryAddressRepository.existsById(deliveryAddressUuid)).thenReturn(true);
        when(deliveryAddressRepository.save(testDeliveryAddress)).thenReturn(testDeliveryAddress);

        DeliveryAddress actualDeliveryAddress = deliveryAddressService.updateDeliveryAddress(testDeliveryAddress);

        assertEquals(testDeliveryAddress, actualDeliveryAddress);

        verify(deliveryAddressRepository, times(1)).existsById(deliveryAddressUuid);
        verify(deliveryAddressRepository, times(1)).save(testDeliveryAddress);
    }

    @Test
    void updateDeliveryAddress_then_throws_NoSuchElementException() {
        UUID deliveryAddressUuid = UUID.randomUUID();
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(deliveryAddressUuid, "Test city", "Test street", "20а", 15);

        when(deliveryAddressRepository.existsById(deliveryAddressUuid)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> deliveryAddressService.updateDeliveryAddress(testDeliveryAddress));

        verify(deliveryAddressRepository, times(1)).existsById(deliveryAddressUuid);
        verify(deliveryAddressRepository, times(0)).save(testDeliveryAddress);
    }

    @Test
    void deleteDeliveryAddress_then_return() {
        UUID deliveryAddressUuid = UUID.randomUUID();
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(deliveryAddressUuid, "Test city", "Test street", "20а", 15);

        when(deliveryAddressRepository.findById(deliveryAddressUuid)).thenReturn(Optional.of(testDeliveryAddress));
        doNothing().when(deliveryAddressRepository).deleteById(deliveryAddressUuid);

        DeliveryAddress actualDeliveryAddress = deliveryAddressService.deleteDeliveryAddress(deliveryAddressUuid);

        assertEquals(testDeliveryAddress, actualDeliveryAddress);

        verify(deliveryAddressRepository, times(1)).findById(deliveryAddressUuid);
        verify(deliveryAddressRepository, times(1)).deleteById(deliveryAddressUuid);
    }

    @Test
    void deleteDeliveryAddress_then_throws_NoSuchElementException() {
        UUID deliveryAddressUuid = UUID.randomUUID();

        when(deliveryAddressRepository.findById(deliveryAddressUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> deliveryAddressService.deleteDeliveryAddress(deliveryAddressUuid));

        verify(deliveryAddressRepository, times(1)).findById(deliveryAddressUuid);
        verify(deliveryAddressRepository, times(0)).deleteById(deliveryAddressUuid);
    }

    @Test
    void findDeliveryAddressById_then_return() {
        UUID deliveryAddressUuid = UUID.randomUUID();
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(deliveryAddressUuid, "Test city", "Test street", "20а", 15);

        when(deliveryAddressRepository.findById(deliveryAddressUuid)).thenReturn(Optional.of(testDeliveryAddress));

        DeliveryAddress actualDeliveryAddress = deliveryAddressService.findDeliveryAddressById(deliveryAddressUuid);

        assertEquals(testDeliveryAddress, actualDeliveryAddress);

        verify(deliveryAddressRepository, times(1)).findById(deliveryAddressUuid);
    }

    @Test
    void findDeliveryAddressById_then_throws_NoSuchElementException() {
        UUID deliveryAddressUuid = UUID.randomUUID();

        when(deliveryAddressRepository.findById(deliveryAddressUuid)).thenThrow(new NoSuchElementException(""));

        assertThrows(NoSuchElementException.class, () -> deliveryAddressService.findDeliveryAddressById(deliveryAddressUuid));

        verify(deliveryAddressRepository, times(1)).findById(deliveryAddressUuid);
    }

    @Test
    void findAll_then_return() {
        DeliveryAddress testDeliveryAddress = buildDeliveryAddress(UUID.randomUUID(), "Test city", "Test street", "20а", 15);
        DeliveryAddress testDeliveryAddress2 = buildDeliveryAddress(UUID.randomUUID(), "Test city 2", "Test street 2", "30/1", 18);
        List<DeliveryAddress> testList = new ArrayList<>(Arrays.asList(testDeliveryAddress, testDeliveryAddress2));

        when(deliveryAddressService.findAll()).thenReturn(testList);

        List<DeliveryAddress> actualList = deliveryAddressService.findAll();

        assertIterableEquals(testList, actualList);

        verify(deliveryAddressRepository, times(1)).findAll();
    }
}
