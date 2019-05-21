package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmissionsUnit entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.EmissionsUnit
 * @author MyEclipse Persistence Tools
 */
public class EmissionsUnitDAO {
    // property constants
    public static final String UNIT_IDENTIFIER = "unitIdentifier";
    public static final String PROGRAM_SYSTEM_CODE = "programSystemCode";
    public static final String DESCRIPTION = "description";
    public static final String TYPE_CODE_DESCRIPTION = "typeCodeDescription";
    public static final String STATUS_YEAR = "statusYear";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved EmissionsUnit entity. All
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
     * EmissionsUnitDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsUnit entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(EmissionsUnit entity) {
        EntityManagerHelper.log("saving EmissionsUnit instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent EmissionsUnit entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * EmissionsUnitDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            EmissionsUnit entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(EmissionsUnit entity) {
        EntityManagerHelper.log("deleting EmissionsUnit instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(EmissionsUnit.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved EmissionsUnit entity and return it or a copy
     * of it to the sender. A copy of the EmissionsUnit entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = EmissionsUnitDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            EmissionsUnit entity to update
     * @return EmissionsUnit the persisted EmissionsUnit entity instance, may
     *         not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public EmissionsUnit update(EmissionsUnit entity) {
        EntityManagerHelper.log("updating EmissionsUnit instance", Level.INFO, null);
        try {
            EmissionsUnit result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public EmissionsUnit findById(Long id) {
        EntityManagerHelper.log("finding EmissionsUnit instance with id: " + id, Level.INFO, null);
        try {
            EmissionsUnit instance = getEntityManager().find(EmissionsUnit.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all EmissionsUnit entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the EmissionsUnit property to query
     * @param value
     *            the property value to match
     * @return List<EmissionsUnit> found by query
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsUnit> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding EmissionsUnit instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from EmissionsUnit model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<EmissionsUnit> findByUnitIdentifier(Object unitIdentifier) {
        return findByProperty(UNIT_IDENTIFIER, unitIdentifier);
    }

    public List<EmissionsUnit> findByProgramSystemCode(Object programSystemCode) {
        return findByProperty(PROGRAM_SYSTEM_CODE, programSystemCode);
    }

    public List<EmissionsUnit> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List<EmissionsUnit> findByTypeCodeDescription(Object typeCodeDescription) {
        return findByProperty(TYPE_CODE_DESCRIPTION, typeCodeDescription);
    }

    public List<EmissionsUnit> findByStatusYear(Object statusYear) {
        return findByProperty(STATUS_YEAR, statusYear);
    }

    public List<EmissionsUnit> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<EmissionsUnit> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all EmissionsUnit entities.
     * 
     * @return List<EmissionsUnit> all EmissionsUnit entities
     */
    @SuppressWarnings("unchecked")
    public List<EmissionsUnit> findAll() {
        EntityManagerHelper.log("finding all EmissionsUnit instances", Level.INFO, null);
        try {
            final String queryString = "select model from EmissionsUnit model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}