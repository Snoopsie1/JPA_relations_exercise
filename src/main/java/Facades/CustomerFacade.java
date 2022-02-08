package Facades;

import Entities.Address;
import Entities.Customer;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerFacade
{
    EntityManagerFactory emf;
    EntityManager entityManager;

    public CustomerFacade(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public EntityManagerFactory getEmf()
    {
        return emf;
    }

    public Customer getCustomer(long id)
    {
        EntityManager em = emf.createEntityManager();
        Customer foundCustomer;
        try
        {
            foundCustomer = em.find(Customer.class, id);
        } finally
        {
            em.close();
        }
        return foundCustomer;
    }

    public List<Customer> getAllCustomers()
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> typedQuery = em.createQuery("SELECT customer FROM Customer customer", Customer.class);
        List<Customer> retrievedCustomerList = typedQuery.getResultList();
        em.close();
        return retrievedCustomerList;
    }

    public void addCustomer(Customer customer)
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCustomer(long id)
    {
        EntityManager em = emf.createEntityManager();
        Customer toBeDeleted = em.find(Customer.class, id);
        em.getTransaction().begin();
        em.remove(toBeDeleted);
        em.getTransaction().commit();
        em.close();
    }

    public void editCustomer(Customer customer, String newFName)
    {
        EntityManager em = emf.createEntityManager();
        Customer toBeEdited = em.find(Customer.class, customer.getId());
        em.getTransaction().begin();
        toBeEdited.setFirstName(newFName);
        em.getTransaction().commit();
        em.close();
    }

}
