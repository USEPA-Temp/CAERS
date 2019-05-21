package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmissionsReport entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.EmissionsReport
 * @author MyEclipse Persistence Tools
 */
public class EmissionsReportDAO {
    // property constants
    public static final String FRS_FACILITY_ID = "frsFacilityId";
    public static final String AGENCY_CODE = "agencyCode";
    public static final String YEAR = "year";
    public static final String STATUS = "status";
    public static final String VALIDATION_STATUS = "validationStatus";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved EmissionsReport entity.
     * All subsequent persist actions of this entity should use the #update()
     * method. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * EmissionsReportDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsReport entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(EmissionsReport entity) {
        EntityManagerHelper.log("saving EmissionsReport instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent EmissionsReport entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * EmissionsReportDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            EmissionsReport entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(EmissionsReport entity) {
        EntityManagerHelper.log("deleting EmissionsReport instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(EmissionsReport.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved EmissionsReport entity and return it or a copy
     * of it to the sender. A copy of the EmissionsReport entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = EmissionsReportDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsReport entity to update
     * @return EmissionsReport the persisted EmissionsReport entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public EmissionsReport update(EmissionsReport entity) {
        EntityManagerHelper.log("updating EmissionsReport instance", Level.INFO, null);
        try {
            EmissionsReport result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public EmissionsReport findById(Long id) {
        EntityManagerHelper.log("finding EmissionsReport instance with id: " + id, Level.INFO, null);
        try {
            EmissionsReport instance = getEntityManager().find(EmissionsReport.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all EmissionsReport entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the EmissionsReport property to query
     * @param value
     *            the property value to match
     * @return List<EmissionsReport> found by query
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsReport> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding EmissionsReport instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from EmissionsReport model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<EmissionsReport> findByFrsFacilityId(Object frsFacilityId) {
        return findByProperty(FRS_FACILITY_ID, frsFacilityId);
    }

    public List<EmissionsReport> findByAgencyCode(Object agencyCode) {
        return findByProperty(AGENCY_CODE, agencyCode);
    }

    public List<EmissionsReport> findByYear(Object year) {
        return findByProperty(YEAR, year);
    }

    public List<EmissionsReport> findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List<EmissionsReport> findByValidationStatus(Object validationStatus) {
        return findByProperty(VALIDATION_STATUS, validationStatus);
    }

    public List<EmissionsReport> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<EmissionsReport> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all EmissionsReport entities.
     * 
     * @return List<EmissionsReport> all EmissionsReport entities
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsReport> findAll() {
        EntityManagerHelper.log("finding all EmissionsReport instances", Level.INFO, null);
        try {
            final String queryString = "select model from EmissionsReport model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}