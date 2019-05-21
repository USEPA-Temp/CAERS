package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * Facility entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see gov.epa.cef.web.domain.Facility
 * @author MyEclipse Persistence Tools
 */
public class FacilityDAO {
    // property constants
    public static final String FRS_FACILITY_ID = "frsFacilityId";
    public static final String ALT_SITE_IDENTIFIER = "altSiteIdentifier";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String STATUS_YEAR = "statusYear";
    public static final String STREET_ADDRESS = "streetAddress";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    public static final String STATE_CODE = "stateCode";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String POSTAL_CODE = "postalCode";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved Facility entity. All
     * subsequent persist actions of this entity should use the #update()
     * method. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * FacilityDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            Facility entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(Facility entity) {
        EntityManagerHelper.log("saving Facility instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent Facility entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * FacilityDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            Facility entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(Facility entity) {
        EntityManagerHelper.log("deleting Facility instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(Facility.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved Facility entity and return it or a copy of it
     * to the sender. A copy of the Facility entity parameter is returned when
     * the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = FacilityDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            Facility entity to update
     * @return Facility the persisted Facility entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public Facility update(Facility entity) {
        EntityManagerHelper.log("updating Facility instance", Level.INFO, null);
        try {
            Facility result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public Facility findById(Long id) {
        EntityManagerHelper.log("finding Facility instance with id: " + id, Level.INFO, null);
        try {
            Facility instance = getEntityManager().find(Facility.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all Facility entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the Facility property to query
     * @param value
     *            the property value to match
     * @return List<Facility> found by query
     */
    @SuppressWarnings("unchecked")
    public List<Facility> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding Facility instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from Facility model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<Facility> findByFrsFacilityId(Object frsFacilityId) {
        return findByProperty(FRS_FACILITY_ID, frsFacilityId);
    }

    public List<Facility> findByAltSiteIdentifier(Object altSiteIdentifier) {
        return findByProperty(ALT_SITE_IDENTIFIER, altSiteIdentifier);
    }

    public List<Facility> findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List<Facility> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List<Facility> findByStatusYear(Object statusYear) {
        return findByProperty(STATUS_YEAR, statusYear);
    }

    public List<Facility> findByStreetAddress(Object streetAddress) {
        return findByProperty(STREET_ADDRESS, streetAddress);
    }

    public List<Facility> findByCity(Object city) {
        return findByProperty(CITY, city);
    }

    public List<Facility> findByCounty(Object county) {
        return findByProperty(COUNTY, county);
    }

    public List<Facility> findByStateCode(Object stateCode) {
        return findByProperty(STATE_CODE, stateCode);
    }

    public List<Facility> findByCountryCode(Object countryCode) {
        return findByProperty(COUNTRY_CODE, countryCode);
    }

    public List<Facility> findByPostalCode(Object postalCode) {
        return findByProperty(POSTAL_CODE, postalCode);
    }

    public List<Facility> findByLatitude(Object latitude) {
        return findByProperty(LATITUDE, latitude);
    }

    public List<Facility> findByLongitude(Object longitude) {
        return findByProperty(LONGITUDE, longitude);
    }

    public List<Facility> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<Facility> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all Facility entities.
     * 
     * @return List<Facility> all Facility entities
     */
    @SuppressWarnings("unchecked")
    public List<Facility> findAll() {
        EntityManagerHelper.log("finding all Facility instances", Level.INFO, null);
        try {
            final String queryString = "select model from Facility model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}