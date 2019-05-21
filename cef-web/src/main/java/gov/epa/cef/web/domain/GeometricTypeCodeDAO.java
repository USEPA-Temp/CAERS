package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * GeometricTypeCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.GeometricTypeCode
 * @author MyEclipse Persistence Tools
 */
public class GeometricTypeCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved GeometricTypeCode entity.
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
     * GeometricTypeCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            GeometricTypeCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(GeometricTypeCode entity) {
        EntityManagerHelper.log("saving GeometricTypeCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent GeometricTypeCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * GeometricTypeCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            GeometricTypeCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(GeometricTypeCode entity) {
        EntityManagerHelper.log("deleting GeometricTypeCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(GeometricTypeCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved GeometricTypeCode entity and return it or a
     * copy of it to the sender. A copy of the GeometricTypeCode entity
     * parameter is returned when the JPA persistence mechanism has not
     * previously been tracking the updated entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#merge(Object)
     * EntityManager#merge} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = GeometricTypeCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            GeometricTypeCode entity to update
     * @return GeometricTypeCode the persisted GeometricTypeCode entity
     *         instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public GeometricTypeCode update(GeometricTypeCode entity) {
        EntityManagerHelper.log("updating GeometricTypeCode instance", Level.INFO, null);
        try {
            GeometricTypeCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public GeometricTypeCode findById(String id) {
        EntityManagerHelper.log("finding GeometricTypeCode instance with id: " + id, Level.INFO, null);
        try {
            GeometricTypeCode instance = getEntityManager().find(GeometricTypeCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all GeometricTypeCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the GeometricTypeCode property to query
     * @param value
     *            the property value to match
     * @return List<GeometricTypeCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<GeometricTypeCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding GeometricTypeCode instance with property: " + propertyName + ", value: " + value, Level.INFO,
                null);
        try {
            final String queryString = "select model from GeometricTypeCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<GeometricTypeCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all GeometricTypeCode entities.
     * 
     * @return List<GeometricTypeCode> all GeometricTypeCode entities
     */
    @SuppressWarnings("unchecked")
    public List<GeometricTypeCode> findAll() {
        EntityManagerHelper.log("finding all GeometricTypeCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from GeometricTypeCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}