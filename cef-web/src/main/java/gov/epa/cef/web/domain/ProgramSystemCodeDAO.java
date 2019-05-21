package gov.epa.cef.web.domain;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * ProgramSystemCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.ProgramSystemCode
 * @author MyEclipse Persistence Tools
 */
public class ProgramSystemCodeDAO {
    // property constants
    public static final String DESCRIPTION = "description";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved ProgramSystemCode entity.
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
     * ProgramSystemCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ProgramSystemCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(ProgramSystemCode entity) {
        EntityManagerHelper.log("saving ProgramSystemCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent ProgramSystemCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * ProgramSystemCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            ProgramSystemCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(ProgramSystemCode entity) {
        EntityManagerHelper.log("deleting ProgramSystemCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(ProgramSystemCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved ProgramSystemCode entity and return it or a
     * copy of it to the sender. A copy of the ProgramSystemCode entity
     * parameter is returned when the JPA persistence mechanism has not
     * previously been tracking the updated entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#merge(Object)
     * EntityManager#merge} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = ProgramSystemCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ProgramSystemCode entity to update
     * @return ProgramSystemCode the persisted ProgramSystemCode entity
     *         instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public ProgramSystemCode update(ProgramSystemCode entity) {
        EntityManagerHelper.log("updating ProgramSystemCode instance", Level.INFO, null);
        try {
            ProgramSystemCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public ProgramSystemCode findById(String id) {
        EntityManagerHelper.log("finding ProgramSystemCode instance with id: " + id, Level.INFO, null);
        try {
            ProgramSystemCode instance = getEntityManager().find(ProgramSystemCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all ProgramSystemCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the ProgramSystemCode property to query
     * @param value
     *            the property value to match
     * @return List<ProgramSystemCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<ProgramSystemCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding ProgramSystemCode instance with property: " + propertyName + ", value: " + value, Level.INFO,
                null);
        try {
            final String queryString = "select model from ProgramSystemCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<ProgramSystemCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    /**
     * Find all ProgramSystemCode entities.
     * 
     * @return List<ProgramSystemCode> all ProgramSystemCode entities
     */
    @SuppressWarnings("unchecked")
    public List<ProgramSystemCode> findAll() {
        EntityManagerHelper.log("finding all ProgramSystemCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from ProgramSystemCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}