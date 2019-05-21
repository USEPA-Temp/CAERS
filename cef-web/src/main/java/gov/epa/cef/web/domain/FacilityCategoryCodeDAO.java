package gov.epa.cef.web.domain;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * FacilityCategoryCode entities. Transaction control of the save(), update()
 * and delete() operations must be handled externally by senders of these
 * methods or must be manually added to each of these methods for data to be
 * persisted to the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.FacilityCategoryCode
 * @author MyEclipse Persistence Tools
 */
public class FacilityCategoryCodeDAO {
    // property constants
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved FacilityCategoryCode
     * entity. All subsequent persist actions of this entity should use the
     * #update() method. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#persist(Object)
     * EntityManager#persist} operation.
     * 
     * <pre>
     * 
     * EntityManagerHelper.beginTransaction();
     * FacilityCategoryCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            FacilityCategoryCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(FacilityCategoryCode entity) {
        EntityManagerHelper.log("saving FacilityCategoryCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent FacilityCategoryCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * FacilityCategoryCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            FacilityCategoryCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(FacilityCategoryCode entity) {
        EntityManagerHelper.log("deleting FacilityCategoryCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(FacilityCategoryCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved FacilityCategoryCode entity and return it or a
     * copy of it to the sender. A copy of the FacilityCategoryCode entity
     * parameter is returned when the JPA persistence mechanism has not
     * previously been tracking the updated entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#merge(Object)
     * EntityManager#merge} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = FacilityCategoryCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            FacilityCategoryCode entity to update
     * @return FacilityCategoryCode the persisted FacilityCategoryCode entity
     *         instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public FacilityCategoryCode update(FacilityCategoryCode entity) {
        EntityManagerHelper.log("updating FacilityCategoryCode instance", Level.INFO, null);
        try {
            FacilityCategoryCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public FacilityCategoryCode findById(String id) {
        EntityManagerHelper.log("finding FacilityCategoryCode instance with id: " + id, Level.INFO, null);
        try {
            FacilityCategoryCode instance = getEntityManager().find(FacilityCategoryCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all FacilityCategoryCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the FacilityCategoryCode property to query
     * @param value
     *            the property value to match
     * @return List<FacilityCategoryCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<FacilityCategoryCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding FacilityCategoryCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from FacilityCategoryCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<FacilityCategoryCode> findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List<FacilityCategoryCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all FacilityCategoryCode entities.
     * 
     * @return List<FacilityCategoryCode> all FacilityCategoryCode entities
     */
    @SuppressWarnings("unchecked")
    public List<FacilityCategoryCode> findAll() {
        EntityManagerHelper.log("finding all FacilityCategoryCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from FacilityCategoryCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}