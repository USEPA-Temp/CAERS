package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * RegionCode entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see gov.epa.cef.web.domain.RegionCode
 * @author MyEclipse Persistence Tools
 */
public class RegionCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved RegionCode entity. All
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
     * RegionCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            RegionCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(RegionCode entity) {
        EntityManagerHelper.log("saving RegionCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent RegionCode entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * RegionCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            RegionCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(RegionCode entity) {
        EntityManagerHelper.log("deleting RegionCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(RegionCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved RegionCode entity and return it or a copy of
     * it to the sender. A copy of the RegionCode entity parameter is returned
     * when the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = RegionCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            RegionCode entity to update
     * @return RegionCode the persisted RegionCode entity instance, may not be
     *         the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public RegionCode update(RegionCode entity) {
        EntityManagerHelper.log("updating RegionCode instance", Level.INFO, null);
        try {
            RegionCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public RegionCode findById(String id) {
        EntityManagerHelper.log("finding RegionCode instance with id: " + id, Level.INFO, null);
        try {
            RegionCode instance = getEntityManager().find(RegionCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all RegionCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the RegionCode property to query
     * @param value
     *            the property value to match
     * @return List<RegionCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<RegionCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding RegionCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from RegionCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<RegionCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all RegionCode entities.
     * 
     * @return List<RegionCode> all RegionCode entities
     */
    @SuppressWarnings("unchecked")
    public List<RegionCode> findAll() {
        EntityManagerHelper.log("finding all RegionCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from RegionCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}