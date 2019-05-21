package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * HorizontalCollectionMethodCode entities. Transaction control of the save(),
 * update() and delete() operations must be handled externally by senders of
 * these methods or must be manually added to each of these methods for data to
 * be persisted to the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.HorizontalCollectionMethodCode
 * @author MyEclipse Persistence Tools
 */
public class HorizontalCollectionMethodCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved
     * HorizontalCollectionMethodCode entity. All subsequent persist actions of
     * this entity should use the #update() method. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * HorizontalCollectionMethodCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            HorizontalCollectionMethodCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(HorizontalCollectionMethodCode entity) {
        EntityManagerHelper.log("saving HorizontalCollectionMethodCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent HorizontalCollectionMethodCode entity. This operation
     * must be performed within the a database transaction context for the
     * entity's data to be permanently deleted from the persistence store, i.e.,
     * database. This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * HorizontalCollectionMethodCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            HorizontalCollectionMethodCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(HorizontalCollectionMethodCode entity) {
        EntityManagerHelper.log("deleting HorizontalCollectionMethodCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(HorizontalCollectionMethodCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved HorizontalCollectionMethodCode entity and
     * return it or a copy of it to the sender. A copy of the
     * HorizontalCollectionMethodCode entity parameter is returned when the JPA
     * persistence mechanism has not previously been tracking the updated
     * entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = HorizontalCollectionMethodCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            HorizontalCollectionMethodCode entity to update
     * @return HorizontalCollectionMethodCode the persisted
     *         HorizontalCollectionMethodCode entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public HorizontalCollectionMethodCode update(HorizontalCollectionMethodCode entity) {
        EntityManagerHelper.log("updating HorizontalCollectionMethodCode instance", Level.INFO, null);
        try {
            HorizontalCollectionMethodCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public HorizontalCollectionMethodCode findById(String id) {
        EntityManagerHelper.log("finding HorizontalCollectionMethodCode instance with id: " + id, Level.INFO, null);
        try {
            HorizontalCollectionMethodCode instance = getEntityManager().find(HorizontalCollectionMethodCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all HorizontalCollectionMethodCode entities with a specific property
     * value.
     * 
     * @param propertyName
     *            the name of the HorizontalCollectionMethodCode property to
     *            query
     * @param value
     *            the property value to match
     * @return List<HorizontalCollectionMethodCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<HorizontalCollectionMethodCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding HorizontalCollectionMethodCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from HorizontalCollectionMethodCode model where model."
                    + propertyName + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<HorizontalCollectionMethodCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all HorizontalCollectionMethodCode entities.
     * 
     * @return List<HorizontalCollectionMethodCode> all
     *         HorizontalCollectionMethodCode entities
     */
    @SuppressWarnings("unchecked")
    public List<HorizontalCollectionMethodCode> findAll() {
        EntityManagerHelper.log("finding all HorizontalCollectionMethodCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from HorizontalCollectionMethodCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}