package Rest.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ride {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String startingPosition;
    private String endingPosition;
    private String departureDate;
    private int vacancies;
    private int pd; // permitted deviation

    Ride() {}

    public Ride(String firstName,
                String lastName,
                String phoneNumber,
                String startingPosition,
                String endingPosition,
                String departureDate,
                int vacancies,
                int pd) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.departureDate = departureDate;
        this.vacancies = vacancies;
        this.pd = pd;
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

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getStartingPosition() {
        return this.startingPosition;
    }

    public String getEndingPosition() {
        return this.endingPosition;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public int getVacancies() {
        return this.vacancies;
    }

    public int getPd() {
        return this.pd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Ride))
            return false;
        Ride ride = (Ride) o;
        return Objects.equals(this.id, ride.id)
                && Objects.equals(this.firstName, ride.firstName)
                && Objects.equals(this.lastName, ride.lastName)
                && Objects.equals(this.phoneNumber, ride.phoneNumber)
                && Objects.equals(this.startingPosition, ride.startingPosition)
                && Objects.equals(this.endingPosition, ride.endingPosition)
                && Objects.equals(this.departureDate, ride.departureDate)
                && Objects.equals(this.vacancies, ride.vacancies)
                && Objects.equals(this.pd, ride.pd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.firstName,
                this.lastName,
                this.phoneNumber,
                this.startingPosition,
                this.endingPosition,
                this.departureDate,
                this.vacancies,
                this.pd);
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + this.id +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                ", startingPosition='" + this.startingPosition + '\'' +
                ", endingPosition='" + this.endingPosition + '\'' +
                ", departureDate='" + this.departureDate + '\'' +
                ", vacancies='" + this.vacancies + '\'' +
                ", pd='" + this.pd + '\'' + '}';
    }
}
