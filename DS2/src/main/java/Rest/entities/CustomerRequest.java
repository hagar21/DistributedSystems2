package Rest.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
public class CustomerRequest {
    private Long id;
    private List<String> path;
    private String departureDate;
    private String name;
    private List<String> satisfiedBy;

    public CustomerRequest() {}

    public CustomerRequest(String name, List<String> path,
                           String departureDate) {
        this.path = path;
        this.departureDate = departureDate;
        this.name = name;
        this.satisfiedBy = new ArrayList<>();
    }

    public CustomerRequest(String name, List<String> path,
                           String departureDate, List<String> satisfiedBy) {
        this.path = path;
        this.departureDate = departureDate;
        this.name = name;
        this.satisfiedBy = satisfiedBy;
    }

    @Id
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPath() {
        return this.path;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }


    public void setPath(List<String> path) {
        this.path = path;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public List<String> getSatisfiedBy() {
        return this.satisfiedBy;
    }
    public void setSatisfiedBy(List<String> satisfiedBy) {
        this.satisfiedBy = satisfiedBy;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof CustomerRequest))
            return false;
        CustomerRequest request = (CustomerRequest) o;
        return Objects.equals(this.id, request.id)
                && Objects.equals(this.path, request.path)
                && Objects.equals(this.departureDate, request.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.path,
                this.departureDate);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id +
//                ", startingPosition='" + this.startingPosition + '\'' +
//                ", endingPosition='" + this.endingPosition + '\'' +
                ", departureDate='" + this.departureDate + '\'' + '}';
    }

}
