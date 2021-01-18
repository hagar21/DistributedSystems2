package Rest.host.controllers;

import Rest.entities.Ride;
import Rest.entities.CustomerRequest;
import ZkService.ZkService;
import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import ch.qos.logback.classic.Level;
import client.LbClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
//import Rest.repositories.CustomerRepository;
import Rest.utils.RideAlreadyExistsException;
import Rest.utils.CustomerRequestAlreadyExistsException;
import server.LbServer;
//import Rest.utils.RideNotFoundException;
//import Rest.utils.CustomerRequestNotFoundException;


import java.util.List;

import static server.utils.global.zkHostName;

class LBServerRun implements Runnable{
    private final LbServer server;

    public void run(){

        try {
            server.start();
            server.blockUntilShutdown();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("City Server service failed to start");
        }


    }

    public LBServerRun(LbServer lbServer) {
        server = lbServer;
    }

}


@RestController
public class CustomerController {
    private final LbServer redirectionService = new LbServer(zkHostName);
    public static ZkServiceImpl zkService; /* shai private? */

    public CustomerController() {
        Runnable r = new LBServerRun(redirectionService);
        new Thread(r).start();
    }

    @GetMapping("/rides")
    List<Ride> allRides() {
        return redirectionService.FindAllRides();
    }

    @GetMapping("/customerRequests")
    List<CustomerRequest> allCustomerRequests() {
        return redirectionService.FindAllCustomerRequests();
    }

    @PostMapping("/rides")
    void newRide(@RequestBody Ride newRide) throws RideAlreadyExistsException {
        redirectionService.PostRide(newRide);
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
        return redirectionService.PostPathPlanningRequest(newCustomerRequest);
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
