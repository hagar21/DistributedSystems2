package Rest.repositories;

import Rest.entities.CustomerRequest;
import Rest.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import Rest.utils.RideAlreadyExistsException;
import Rest.utils.CustomerRequestAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private final ArrayList<Ride> rides;
    private final ArrayList<CustomerRequest> customerRequests;
    private long rideIdIndex = 1;
    private long customerRequestsIdIndex = 1;

    public CustomerRepository() {
        rides = new ArrayList<>();
        customerRequests = new ArrayList<>();
    }

    public List<Ride> findAllRides() {
        return rides;
    }
    public List<CustomerRequest> findAllCustomerRequests() {
        return customerRequests;
    }

    public Ride save(Ride newRide) throws RideAlreadyExistsException {
        if (rides.contains(newRide)) {
            throw new RideAlreadyExistsException(newRide.getId());
        }
        newRide.setId(rideIdIndex++);
        saveRide(newRide);
        return newRide;
    }

    public CustomerRequest save(CustomerRequest newCustomerRequest) throws CustomerRequestAlreadyExistsException {
        if (customerRequests.contains(newCustomerRequest)) {
            throw new CustomerRequestAlreadyExistsException(newCustomerRequest.getId());
        }
        newCustomerRequest.setId(customerRequestsIdIndex++);
        saveCustomerRequest(newCustomerRequest);
        return newCustomerRequest;
    }

    public Optional<Ride> findByRideId(Long id) {
        return rides.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Optional<CustomerRequest> findByCustomerRequestId(Long id) {
        return customerRequests.stream().filter(cr -> cr.getId().equals(id)).findFirst();
    }

    public void delete(Ride ride) {
        rides.remove(ride);
    }

    public void delete(CustomerRequest customerRequest) {
        customerRequests.remove(customerRequest);
    }

    public Ride saveOrSwitch(Ride newRide, Long id) {
        rides.removeIf(r -> r.getId().equals(id));
        newRide.setId(id);
        saveRide(newRide);
        return newRide;
    }

    public CustomerRequest saveOrSwitch(CustomerRequest newCustomerRequest, Long id) {
        customerRequests.removeIf(cr -> cr.getId().equals(id));
        newCustomerRequest.setId(id);
        saveCustomerRequest(newCustomerRequest);
        return newCustomerRequest;
    }

    private void saveRide(Ride ride){
        rides.add(ride);
    }

    private void saveCustomerRequest(CustomerRequest customerRequest) {
        customerRequests.add(customerRequest);
    }
}

