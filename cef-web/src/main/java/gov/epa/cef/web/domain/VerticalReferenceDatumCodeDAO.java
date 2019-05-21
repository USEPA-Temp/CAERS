package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * VerticalReferenceDatumCode entities. Transaction control of the save(),
 * update() and delete() operations must be handled externally by senders of
 * these methods or must be manually added to each of these methods for data to
 * be persisted to the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.VerticalReferenceDatumCode
 * @author MyEclipse Persistence Tools
 */
public class VerticalReferenceDatumCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved
     * VerticalReferenceDatumCode entity. All subsequent persist actions of this
     * entity should use the #update() method. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently saved to the persistence store, i.e., database. This method
     * uses the {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * VerticalReferenceDatumCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            VerticalReferenceDatumCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(VerticalReferenceDatumCode entity) {
        EntityManagerHelper.log("saving VerticalReferenceDatumCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent VerticalReferenceDatumCode entity. This operation
     * must be performed within the a database transaction context for the
     * entity's data to be permanently deleted from the persistence store, i.e.,
     * database. This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * VerticalReferenceDatumCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            VerticalReferenceDatumCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(VerticalReferenceDatumCode entity) {
        EntityManagerHelper.log("deleting VerticalReferenceDatumCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(VerticalReferenceDatumCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved VerticalReferenceDatumCode entity and return
     * it or a copy of it to the sender. A copy of the
     * VerticalReferenceDatumCode entity parameter is returned when the JPA
     * persistence mechanism has not previously been tracking the updated
     * entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = VerticalReferenceDatumCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            VerticalReferenceDatumCode entity to update
     * @return VerticalReferenceDatumCode the persisted
     *         VerticalReferenceDatumCode entity instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public VerticalReferenceDatumCode update(VerticalReferenceDatumCode entity) {
        EntityManagerHelper.log("updating VerticalReferenceDatumCode instance", Level.INFO, null);
        try {
            VerticalReferenceDatumCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public VerticalReferenceDatumCode findById(String id) {
        EntityManagerHelper.log("finding VerticalReferenceDatumCode instance with id: " + id, Level.INFO, null);
        try {
            VerticalReferenceDatumCode instance = getEntityManager().find(VerticalReferenceDatumCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all VerticalReferenceDatumCode entities with a specific property
     * value.
     * 
     * @param propertyName
     *            the name of the VerticalReferenceDatumCode property to query
     * @param value
     *            the property value to match
     * @return List<VerticalReferenceDatumCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<VerticalReferenceDatumCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding VerticalReferenceDatumCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from VerticalReferenceDatumCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<VerticalReferenceDatumCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all VerticalReferenceDatumCode entities.
     * 
     * @return List<VerticalReferenceDatumCode> all VerticalReferenceDatumCode
     *         entities
     */
    @SuppressWarnings("unchecked")
    public List<VerticalReferenceDatumCode> findAll() {
        EntityManagerHelper.log("finding all VerticalReferenceDatumCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from VerticalReferenceDatumCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}