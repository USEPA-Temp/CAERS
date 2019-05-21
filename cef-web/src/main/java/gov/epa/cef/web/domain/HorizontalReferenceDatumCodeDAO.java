package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * HorizontalReferenceDatumCode entities. Transaction control of the save(),
 * update() and delete() operations must be handled externally by senders of
 * these methods or must be manually added to each of these methods for data to
 * be persisted to the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.HorizontalReferenceDatumCode
 * @author MyEclipse Persistence Tools
 */
public class HorizontalReferenceDatumCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved
     * HorizontalReferenceDatumCode entity. All subsequent persist actions of
     * this entity should use the #update() method. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * HorizontalReferenceDatumCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            HorizontalReferenceDatumCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(HorizontalReferenceDatumCode entity) {
        EntityManagerHelper.log("saving HorizontalReferenceDatumCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent HorizontalReferenceDatumCode entity. This operation
     * must be performed within the a database transaction context for the
     * entity's data to be permanently deleted from the persistence store, i.e.,
     * database. This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * HorizontalReferenceDatumCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            HorizontalReferenceDatumCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(HorizontalReferenceDatumCode entity) {
        EntityManagerHelper.log("deleting HorizontalReferenceDatumCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(HorizontalReferenceDatumCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved HorizontalReferenceDatumCode entity and return
     * it or a copy of it to the sender. A copy of the
     * HorizontalReferenceDatumCode entity parameter is returned when the JPA
     * persistence mechanism has not previously been tracking the updated
     * entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = HorizontalReferenceDatumCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            HorizontalReferenceDatumCode entity to update
     * @return HorizontalReferenceDatumCode the persisted
     *         HorizontalReferenceDatumCode entity instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public HorizontalReferenceDatumCode update(HorizontalReferenceDatumCode entity) {
        EntityManagerHelper.log("updating HorizontalReferenceDatumCode instance", Level.INFO, null);
        try {
            HorizontalReferenceDatumCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public HorizontalReferenceDatumCode findById(String id) {
        EntityManagerHelper.log("finding HorizontalReferenceDatumCode instance with id: " + id, Level.INFO, null);
        try {
            HorizontalReferenceDatumCode instance = getEntityManager().find(HorizontalReferenceDatumCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all HorizontalReferenceDatumCode entities with a specific property
     * value.
     * 
     * @param propertyName
     *            the name of the HorizontalReferenceDatumCode property to query
     * @param value
     *            the property value to match
     * @return List<HorizontalReferenceDatumCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<HorizontalReferenceDatumCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding HorizontalReferenceDatumCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from HorizontalReferenceDatumCode model where model."
                    + propertyName + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<HorizontalReferenceDatumCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all HorizontalReferenceDatumCode entities.
     * 
     * @return List<HorizontalReferenceDatumCode> all
     *         HorizontalReferenceDatumCode entities
     */
    @SuppressWarnings("unchecked")
    public List<HorizontalReferenceDatumCode> findAll() {
        EntityManagerHelper.log("finding all HorizontalReferenceDatumCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from HorizontalReferenceDatumCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}