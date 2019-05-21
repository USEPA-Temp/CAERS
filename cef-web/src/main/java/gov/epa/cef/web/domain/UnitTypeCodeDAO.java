package gov.epa.cef.web.domain;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * UnitTypeCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.UnitTypeCode
 * @author MyEclipse Persistence Tools
 */
public class UnitTypeCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved UnitTypeCode entity. All
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
     * UnitTypeCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            UnitTypeCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(UnitTypeCode entity) {
        EntityManagerHelper.log("saving UnitTypeCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent UnitTypeCode entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * UnitTypeCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            UnitTypeCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(UnitTypeCode entity) {
        EntityManagerHelper.log("deleting UnitTypeCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(UnitTypeCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved UnitTypeCode entity and return it or a copy of
     * it to the sender. A copy of the UnitTypeCode entity parameter is returned
     * when the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = UnitTypeCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            UnitTypeCode entity to update
     * @return UnitTypeCode the persisted UnitTypeCode entity instance, may not
     *         be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public UnitTypeCode update(UnitTypeCode entity) {
        EntityManagerHelper.log("updating UnitTypeCode instance", Level.INFO, null);
        try {
            UnitTypeCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public UnitTypeCode findById(String id) {
        EntityManagerHelper.log("finding UnitTypeCode instance with id: " + id, Level.INFO, null);
        try {
            UnitTypeCode instance = getEntityManager().find(UnitTypeCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all UnitTypeCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the UnitTypeCode property to query
     * @param value
     *            the property value to match
     * @return List<UnitTypeCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<UnitTypeCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding UnitTypeCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from UnitTypeCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<UnitTypeCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all UnitTypeCode entities.
     * 
     * @return List<UnitTypeCode> all UnitTypeCode entities
     */
    @SuppressWarnings("unchecked")
    public List<UnitTypeCode> findAll() {
        EntityManagerHelper.log("finding all UnitTypeCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from UnitTypeCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}