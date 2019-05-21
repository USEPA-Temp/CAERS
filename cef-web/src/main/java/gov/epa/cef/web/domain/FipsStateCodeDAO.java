package gov.epa.cef.web.domain;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * FipsStateCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.FipsStateCode
 * @author MyEclipse Persistence Tools
 */
public class FipsStateCodeDAO {
    // property constants
    public static final String USPS_CODE = "uspsCode";
    public static final String NAME = "name";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved FipsStateCode entity. All
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
     * FipsStateCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            FipsStateCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(FipsStateCode entity) {
        EntityManagerHelper.log("saving FipsStateCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent FipsStateCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * FipsStateCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            FipsStateCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(FipsStateCode entity) {
        EntityManagerHelper.log("deleting FipsStateCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(FipsStateCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved FipsStateCode entity and return it or a copy
     * of it to the sender. A copy of the FipsStateCode entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = FipsStateCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            FipsStateCode entity to update
     * @return FipsStateCode the persisted FipsStateCode entity instance, may
     *         not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public FipsStateCode update(FipsStateCode entity) {
        EntityManagerHelper.log("updating FipsStateCode instance", Level.INFO, null);
        try {
            FipsStateCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public FipsStateCode findById(String id) {
        EntityManagerHelper.log("finding FipsStateCode instance with id: " + id, Level.INFO, null);
        try {
            FipsStateCode instance = getEntityManager().find(FipsStateCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all FipsStateCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the FipsStateCode property to query
     * @param value
     *            the property value to match
     * @return List<FipsStateCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<FipsStateCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding FipsStateCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from FipsStateCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<FipsStateCode> findByUspsCode(Object uspsCode) {
        return findByProperty(USPS_CODE, uspsCode);
    }

    public List<FipsStateCode> findByName(Object name) {
        return findByProperty(NAME, name);
    }

    /**
     * Find all FipsStateCode entities.
     * 
     * @return List<FipsStateCode> all FipsStateCode entities
     */
    @SuppressWarnings("unchecked")
    public List<FipsStateCode> findAll() {
        EntityManagerHelper.log("finding all FipsStateCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from FipsStateCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}