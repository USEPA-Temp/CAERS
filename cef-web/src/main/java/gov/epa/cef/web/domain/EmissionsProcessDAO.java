package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmissionsProcess entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.EmissionsProcess
 * @author MyEclipse Persistence Tools
 */
public class EmissionsProcessDAO {
    // property constants
    public static final String EMISSIONS_PROCESS_IDENTIFIER = "emissionsProcessIdentifier";
    public static final String STATUS_YEAR = "statusYear";
    public static final String SCC_CODE = "sccCode";
    public static final String SCC_SHORT_NAME = "sccShortName";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved EmissionsProcess entity.
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
     * EmissionsProcessDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsProcess entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(EmissionsProcess entity) {
        EntityManagerHelper.log("saving EmissionsProcess instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent EmissionsProcess entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * EmissionsProcessDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            EmissionsProcess entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(EmissionsProcess entity) {
        EntityManagerHelper.log("deleting EmissionsProcess instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(EmissionsProcess.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved EmissionsProcess entity and return it or a
     * copy of it to the sender. A copy of the EmissionsProcess entity parameter
     * is returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = EmissionsProcessDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsProcess entity to update
     * @return EmissionsProcess the persisted EmissionsProcess entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public EmissionsProcess update(EmissionsProcess entity) {
        EntityManagerHelper.log("updating EmissionsProcess instance", Level.INFO, null);
        try {
            EmissionsProcess result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public EmissionsProcess findById(Long id) {
        EntityManagerHelper.log("finding EmissionsProcess instance with id: " + id, Level.INFO, null);
        try {
            EmissionsProcess instance = getEntityManager().find(EmissionsProcess.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all EmissionsProcess entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the EmissionsProcess property to query
     * @param value
     *            the property value to match
     * @return List<EmissionsProcess> found by query
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsProcess> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding EmissionsProcess instance with property: " + propertyName + ", value: " + value, Level.INFO,
                null);
        try {
            final String queryString = "select model from EmissionsProcess model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<EmissionsProcess> findByEmissionsProcessIdentifier(Object emissionsProcessIdentifier) {
        return findByProperty(EMISSIONS_PROCESS_IDENTIFIER, emissionsProcessIdentifier);
    }

    public List<EmissionsProcess> findByStatusYear(Object statusYear) {
        return findByProperty(STATUS_YEAR, statusYear);
    }

    public List<EmissionsProcess> findBySccCode(Object sccCode) {
        return findByProperty(SCC_CODE, sccCode);
    }

    public List<EmissionsProcess> findBySccShortName(Object sccShortName) {
        return findByProperty(SCC_SHORT_NAME, sccShortName);
    }

    public List<EmissionsProcess> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List<EmissionsProcess> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<EmissionsProcess> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all EmissionsProcess entities.
     * 
     * @return List<EmissionsProcess> all EmissionsProcess entities
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsProcess> findAll() {
        EntityManagerHelper.log("finding all EmissionsProcess instances", Level.INFO, null);
        try {
            final String queryString = "select model from EmissionsProcess model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}