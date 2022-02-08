import Entities.Address;
import Entities.Customer;
import Facades.CustomerFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificationTest
{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    CustomerFacade cf = new CustomerFacade(emf);

    public void create(Customer customer)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.persist(customer);
            customer.getAddress().forEach(address -> {
                em.persist(address);
            });
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }

    @BeforeEach
    void setup()
    {
        Persistence.generateSchema("pu", new HashMap());
    }

    @AfterEach
    void teardown()
    {

    }

    @Test
    public void test()
    {
        Customer customer = new Customer("Ole", "Svendsen");
        Customer customer1 = new Customer("Jakob", "Olsen");
        Customer customer2 = new Customer("Birgitte", "Tot");

        Address address = new Address("Valnøddevej", "Hornbæk");
        Address address1 = new Address("Roskildevej", "Brøndby");
        Address address2 = new Address("Kongevej", "Kongeby");
        customer.addAddress(address2);
        customer.addAddress(address);
        customer2.addAddress(address1);
        create(customer);
        create(customer2);
    }

    @Test
    void getCustomerTest()
    {
        Customer customer = new Customer("Margit", "Jensen");
        Address address = new Address("Jakobsvej", "Lyngby");
        customer.addAddress(address);
        create(customer);
        Customer foundCustomer = cf.getCustomer(1);

        assertEquals(customer.equals(foundCustomer), foundCustomer.equals(customer));
    }

    @Test
    void getAllCustomersTest()
    {
        EntityManager em = emf.createEntityManager();
        List<Customer> expectedCustomers = new ArrayList<>();
        Customer customer1 = new Customer("Lone", "Bakker");
        Customer customer2 = new Customer("Jakob", "Jensen");
        Customer customer3 = new Customer("Viktor", "Makker");
        expectedCustomers.add(customer1);
        expectedCustomers.add(customer2);
        expectedCustomers.add(customer3);
        em.getTransaction().begin();
        em.persist(customer1);
        em.persist(customer2);
        em.persist(customer3);
        em.getTransaction().commit();
        em.close();

        List<Customer> actualCustomers = cf.getAllCustomers();

        assertEquals(expectedCustomers.equals(actualCustomers), actualCustomers.equals(expectedCustomers));
    }

    @Test
    void addCustomerTest()
    {
        EntityManager em = emf.createEntityManager();
        Customer customer1 = new Customer("Lone", "Bakker");
        Customer customer2 = new Customer("Jakob", "Jensen");
        Customer customer3 = new Customer("Viktor", "Makker");
        em.getTransaction().begin();
        em.persist(customer1);
        em.persist(customer2);
        em.persist(customer3);
        em.getTransaction().commit();
        em.close();

        cf.addCustomer(new Customer("Mogens", "Borison"));
        String expectedCustomerDetails = cf.getCustomer(4).toString();

        assertEquals(expectedCustomerDetails, "Customer Info: \n" +
                "ID: 4\n" +
                "First name: Mogens\n" +
                "Last name: Borison");

        //Kan også testes ved at lave 2 lister af customers. En med den der skal slettes, og en uden.
        //Og så sammenligne og de er ens med hinanden via assertEquals(list.equals(list2),list2.equals(list));
    }

    @Test
    void deleteCustomerTest()
    {
        EntityManager em = emf.createEntityManager();
        List<Customer> oldCustomerList = new ArrayList<>();
        Customer customer1 = new Customer("Lone", "Bakker");
        Customer customer2 = new Customer("Jakob", "Jensen");
        Customer customer3 = new Customer("Viktor", "Makker");
        Customer customer4 = new Customer("Boris", "Johnson");
        Customer customer5 = new Customer("Hansen", "Jensen");
        Customer customer6 = new Customer("Viggo", "Buster");
        em.getTransaction().begin();
        em.persist(customer1);
        em.persist(customer2);
        em.persist(customer3);
        em.persist(customer4);
        em.persist(customer5);
        em.persist(customer6);
        em.getTransaction().commit();
        oldCustomerList = cf.getAllCustomers();
        cf.deleteCustomer(oldCustomerList.get(1).getId());
        //Hvorfor fik Viggo Buster id: 2???

        List<Customer> newCustomerList = new ArrayList<>();
        Customer nCustomer1 = new Customer("Lone", "Bakker");
        Customer nCustomer2 = new Customer("Jakob", "Jensen");
        Customer nCustomer3 = new Customer("Viktor", "Makker");
        Customer nCustomer4 = new Customer("Boris", "Johnson");
        Customer nCustomer5 = new Customer("Hansen", "Jensen");
        newCustomerList.add(nCustomer1);
        newCustomerList.add(nCustomer2);
        newCustomerList.add(nCustomer3);
        newCustomerList.add(nCustomer4);
        newCustomerList.add(nCustomer5);

        assertEquals(oldCustomerList.equals(newCustomerList), newCustomerList.equals(oldCustomerList));
    }

    @Test
    void editCustomer()
    {
        EntityManager em = emf.createEntityManager();
        Customer expectedCustomer = new Customer("Ole", "Fransen");
        Customer actualCustomer = new Customer("Jens", "Fransen");
        cf.addCustomer(actualCustomer);
        cf.editCustomer(actualCustomer, "Ole");

        assertEquals(expectedCustomer.equals(actualCustomer), actualCustomer.equals(expectedCustomer));
    }

}
