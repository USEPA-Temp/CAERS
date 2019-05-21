package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * RegulatoryCode entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.RegulatoryCode
 * @author MyEclipse Persistence Tools
 */
public class RegulatoryCodeDAO {
    // property constants
    public static final String CODE_TYPE = "codeType";
    public static final String DESCRIPTION = "description";
    public static final String SUBPART_DESCRIPTION = "subpartDescription";
    public static final String PART_DESCRIPTION = "partDescription";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved RegulatoryCode entity.
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
     * RegulatoryCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            RegulatoryCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(RegulatoryCode entity) {
        EntityManagerHelper.log("saving RegulatoryCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent RegulatoryCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * RegulatoryCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            RegulatoryCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(RegulatoryCode entity) {
        EntityManagerHelper.log("deleting RegulatoryCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(RegulatoryCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved RegulatoryCode entity and return it or a copy
     * of it to the sender. A copy of the RegulatoryCode entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = RegulatoryCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            RegulatoryCode entity to update
     * @return RegulatoryCode the persisted RegulatoryCode entity instance, may
     *         not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public RegulatoryCode update(RegulatoryCode entity) {
        EntityManagerHelper.log("updating RegulatoryCode instance", Level.INFO, null);
        try {
            RegulatoryCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public RegulatoryCode findById(String id) {
        EntityManagerHelper.log("finding RegulatoryCode instance with id: " + id, Level.INFO, null);
        try {
            RegulatoryCode instance = getEntityManager().find(RegulatoryCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all RegulatoryCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the RegulatoryCode property to query
     * @param value
     *            the property value to match
     * @return List<RegulatoryCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<RegulatoryCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding RegulatoryCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from RegulatoryCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<RegulatoryCode> findByCodeType(Object codeType) {
        return findByProperty(CODE_TYPE, codeType);
    }

    public List<RegulatoryCode> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List<RegulatoryCode> findBySubpartDescription(Object subpartDescription) {
        return findByProperty(SUBPART_DESCRIPTION, subpartDescription);
    }

    public List<RegulatoryCode> findByPartDescription(Object partDescription) {
        return findByProperty(PART_DESCRIPTION, partDescription);
    }

    /**
     * Find all RegulatoryCode entities.
     * 
     * @return List<RegulatoryCode> all RegulatoryCode entities
     */
    @SuppressWarnings("unchecked")
    public List<RegulatoryCode> findAll() {
        EntityManagerHelper.log("finding all RegulatoryCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from RegulatoryCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}