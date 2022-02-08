package Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "customers")
    private List<Address> addresses = new ArrayList<>();

    //JPA constructor
    public Customer()
    {
    }

    public Customer(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addAddress (Address address)
    {
        this.addresses.add(address);
    }

    public List<Address> getAddress()
    {
        return addresses;
    }


    @Override
    public String toString()
    {
        return "Customer Info: " + "\n"
                + "ID: " + id +"\n"
                + "First name: " + firstName +"\n"
                + "Last name: "+ lastName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    public Long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
