package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * ReleasePointAppt entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.ReleasePointAppt
 * @author MyEclipse Persistence Tools
 */
public class ReleasePointApptDAO {
    // property constants
    public static final String PERCENT = "percent";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved ReleasePointAppt entity.
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
     * ReleasePointApptDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReleasePointAppt entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(ReleasePointAppt entity) {
        EntityManagerHelper.log("saving ReleasePointAppt instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent ReleasePointAppt entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * ReleasePointApptDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            ReleasePointAppt entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(ReleasePointAppt entity) {
        EntityManagerHelper.log("deleting ReleasePointAppt instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(ReleasePointAppt.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved ReleasePointAppt entity and return it or a
     * copy of it to the sender. A copy of the ReleasePointAppt entity parameter
     * is returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = ReleasePointApptDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReleasePointAppt entity to update
     * @return ReleasePointAppt the persisted ReleasePointAppt entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public ReleasePointAppt update(ReleasePointAppt entity) {
        EntityManagerHelper.log("updating ReleasePointAppt instance", Level.INFO, null);
        try {
            ReleasePointAppt result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public ReleasePointAppt findById(Long id) {
        EntityManagerHelper.log("finding ReleasePointAppt instance with id: " + id, Level.INFO, null);
        try {
            ReleasePointAppt instance = getEntityManager().find(ReleasePointAppt.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all ReleasePointAppt entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the ReleasePointAppt property to query
     * @param value
     *            the property value to match
     * @return List<ReleasePointAppt> found by query
     */
    @SuppressWarnings("unchecked")
    public List<ReleasePointAppt> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding ReleasePointAppt instance with property: " + propertyName + ", value: " + value, Level.INFO,
                null);
        try {
            final String queryString = "select model from ReleasePointAppt model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<ReleasePointAppt> findByPercent(Object percent) {
        return findByProperty(PERCENT, percent);
    }

    public List<ReleasePointAppt> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<ReleasePointAppt> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all ReleasePointAppt entities.
     * 
     * @return List<ReleasePointAppt> all ReleasePointAppt entities
     */
    @SuppressWarnings("unchecked")
    public List<ReleasePointAppt> findAll() {
        EntityManagerHelper.log("finding all ReleasePointAppt instances", Level.INFO, null);
        try {
            final String queryString = "select model from ReleasePointAppt model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}