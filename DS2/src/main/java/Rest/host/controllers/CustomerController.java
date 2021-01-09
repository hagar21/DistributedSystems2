package Rest.host.controllers;

import Rest.entities.Ride;
import Rest.entities.CustomerRequest;
import client.CityClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.*;
//import Rest.repositories.CustomerRepository;
import Rest.utils.RideAlreadyExistsException;
import Rest.utils.CustomerRequestAlreadyExistsException;
//import Rest.utils.RideNotFoundException;
//import Rest.utils.CustomerRequestNotFoundException;


import java.util.List;

@RestController
public class CustomerController {
    private final CityClient redirectionService;

    public CustomerController() {
        String target = "localhost:8990";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        this.redirectionService = new CityClient(channel);
    }
    /*
        @GetMapping("/rides")
        List<Ride> allRides() {
            return repository.findAllRides();
        }

        @GetMapping("/customerRequests")
        List<CustomerRequest> allCustomerRequests() {
            return repository.findAllCustomerRequests();
        }
    */
    @PostMapping("/rides")
    void newRide(@RequestBody Ride newRide) throws RideAlreadyExistsException {
        redirectionService.postRide(newRide);
    }

    /*
    @PostMapping("/customerRequests")
    CustomerRequest newCustomerRequest(@RequestBody CustomerRequest newCustomerRequest)
            throws CustomerRequestAlreadyExistsException {
        return repository.save(newCustomerRequest);
    }
    */

    @PostMapping("/customerRequests")
    List<Ride> newCustomerRequest(@RequestBody CustomerRequest newCustomerRequest)
            throws CustomerRequestAlreadyExistsException {
        return redirectionService.postPathPlanningRequest(newCustomerRequest);
    }

    /*
    // Single item
    @ResponseBody
    @GetMapping("/rides/{id}")
    Ride oneRide(@PathVariable Long id) throws RideNotFoundException {
        return repository.findByRideId(id)
                .orElseThrow(() -> new RideNotFoundException(id));
    }

    // Single item
    @ResponseBody
    @GetMapping("/customerRequests/{id}")
    CustomerRequest oneCustomerRequest(@PathVariable Long id) throws CustomerRequestNotFoundException {
        return repository.findByCustomerRequestId(id)
                .orElseThrow(() -> new CustomerRequestNotFoundException(id));
    }

    @ResponseBody
    @PutMapping("/rides/{id}")
    Ride replaceRide(@RequestBody Ride newRide, @PathVariable Long id){
        return repository.saveOrSwitch(newRide, id);
    }

    @ResponseBody
    @PutMapping("/customerRequests/{id}")
    CustomerRequest replaceCustomerRequest(@RequestBody CustomerRequest newCustomerRequest, @PathVariable Long id){
        return repository.saveOrSwitch(newCustomerRequest, id);
    }

    @ResponseBody
    @DeleteMapping("/rides/{id}")
    Ride deleteRide(@PathVariable Long id) {
        Ride ride = oneRide(id);
        repository.delete(ride);
        return ride;
    }

    @ResponseBody
    @DeleteMapping("/customerRequests/{id}")
    CustomerRequest deleteCustomerRequest(@PathVariable Long id) throws CustomerRequestNotFoundException {
        CustomerRequest customerRequest = oneCustomerRequest(id);
        repository.delete(customerRequest);
        return customerRequest;
    }
*/
}
