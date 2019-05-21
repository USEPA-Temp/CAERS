package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * VerificationCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.VerificationCode
 * @author MyEclipse Persistence Tools
 */
public class VerificationCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved VerificationCode entity.
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
     * VerificationCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            VerificationCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(VerificationCode entity) {
        EntityManagerHelper.log("saving VerificationCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent VerificationCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * VerificationCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            VerificationCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(VerificationCode entity) {
        EntityManagerHelper.log("deleting VerificationCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(VerificationCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved VerificationCode entity and return it or a
     * copy of it to the sender. A copy of the VerificationCode entity parameter
     * is returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = VerificationCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            VerificationCode entity to update
     * @return VerificationCode the persisted VerificationCode entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public VerificationCode update(VerificationCode entity) {
        EntityManagerHelper.log("updating VerificationCode instance", Level.INFO, null);
        try {
            VerificationCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public VerificationCode findById(String id) {
        EntityManagerHelper.log("finding VerificationCode instance with id: " + id, Level.INFO, null);
        try {
            VerificationCode instance = getEntityManager().find(VerificationCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all VerificationCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the VerificationCode property to query
     * @param value
     *            the property value to match
     * @return List<VerificationCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<VerificationCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding VerificationCode instance with property: " + propertyName + ", value: " + value, Level.INFO,
                null);
        try {
            final String queryString = "select model from VerificationCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<VerificationCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all VerificationCode entities.
     * 
     * @return List<VerificationCode> all VerificationCode entities
     */
    @SuppressWarnings("unchecked")
    public List<VerificationCode> findAll() {
        EntityManagerHelper.log("finding all VerificationCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from VerificationCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}