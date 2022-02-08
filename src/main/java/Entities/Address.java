package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    @ManyToMany
    @JoinColumn(name = "CUSTOMER_ID")
    private List<Customer> customers;


    //JPA Constructor
    public Address()
    {
    }

    public Address(String street, String city)
    {
        this.street = street;
        this.city = city;
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }

    @Override
    public String toString()
    {
        return "Address: " + "\n"
                +"Street: " + street + "\n"
                +"City: " + city + "\n";
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }


    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
