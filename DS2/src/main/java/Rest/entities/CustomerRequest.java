package Rest.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> path;
    private String departureDate;
    private int passengers;

    CustomerRequest() {}

    CustomerRequest(String firstName,
                    String lastName,
                    List<String> path,
                    String departureDate,
                    int passengers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.path = path;
        this.departureDate = departureDate;
        this.passengers = passengers;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public List<String> getPath() {
        return this.path;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public int getPassengers() {
        return this.passengers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof CustomerRequest))
            return false;
        CustomerRequest request = (CustomerRequest) o;
        return Objects.equals(this.id, request.id)
                && Objects.equals(this.firstName, request.firstName)
                && Objects.equals(this.lastName, request.lastName)
                && Objects.equals(this.path, request.path)
                && Objects.equals(this.departureDate, request.departureDate)
                && Objects.equals(this.passengers, request.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.firstName,
                this.lastName,
                this.path,
                this.departureDate,
                this.passengers);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
//                ", startingPosition='" + this.startingPosition + '\'' +
//                ", endingPosition='" + this.endingPosition + '\'' +
                ", departureDate='" + this.departureDate + '\'' +
                ", passengers='" + this.passengers + '\'' + '}';
    }

}
