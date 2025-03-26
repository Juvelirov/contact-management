package ru.denisov.NauJava.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.denisov.NauJava.entity.Contact;
import ru.denisov.NauJava.entity.Organization;

@Repository
public class ContactRepositoryImpl implements ContactRepositoryCriteria {

    private final EntityManager entityManager;

    @Autowired
    public ContactRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Contact> findByFirstnameAndLastname(String firstname, String lastname) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> contactRoot = criteriaQuery.from(Contact.class);

        Predicate firstnamePredicate = criteriaBuilder.equal(contactRoot.get("firstname"), firstname);
        Predicate lastnamePredicate = criteriaBuilder.equal(contactRoot.get("surname"), lastname);

        criteriaQuery.select(contactRoot).where(criteriaBuilder.and(firstnamePredicate, lastnamePredicate));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Contact> findContactsByOrganizationName(String organizationName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> contactRoot = criteriaQuery.from(Contact.class);

        Join<Contact, Organization> organizationJoin = contactRoot.join("organizationId", JoinType.INNER);

        Predicate organizationNamePredicate = criteriaBuilder.equal(organizationJoin.get("name"), organizationName);

        criteriaQuery.select(contactRoot).where(organizationNamePredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
