package gov.epa.cef.web.domain;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * AircraftEngineTypeCode entities. Transaction control of the save(), update()
 * and delete() operations must be handled externally by senders of these
 * methods or must be manually added to each of these methods for data to be
 * persisted to the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.AircraftEngineTypeCode
 * @author MyEclipse Persistence Tools
 */
public class AircraftEngineTypeCodeDAO {
    // property constants
    public static final String FAA_AIRCRAFT_TYPE = "faaAircraftType";
    public static final String EDMS_ACCODE = "edmsAccode";
    public static final String ENGINE_MANUFACTURER = "engineManufacturer";
    public static final String ENGINE = "engine";
    public static final String EDMS_UID = "edmsUid";
    public static final String SCC = "scc";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved AircraftEngineTypeCode
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
     * AircraftEngineTypeCodeDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            AircraftEngineTypeCode entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(AircraftEngineTypeCode entity) {
        EntityManagerHelper.log("saving AircraftEngineTypeCode instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent AircraftEngineTypeCode entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * AircraftEngineTypeCodeDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            AircraftEngineTypeCode entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(AircraftEngineTypeCode entity) {
        EntityManagerHelper.log("deleting AircraftEngineTypeCode instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(AircraftEngineTypeCode.class, entity.getCode());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved AircraftEngineTypeCode entity and return it or
     * a copy of it to the sender. A copy of the AircraftEngineTypeCode entity
     * parameter is returned when the JPA persistence mechanism has not
     * previously been tracking the updated entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently saved to the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#merge(Object)
     * EntityManager#merge} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = AircraftEngineTypeCodeDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            AircraftEngineTypeCode entity to update
     * @return AircraftEngineTypeCode the persisted AircraftEngineTypeCode
     *         entity instance, may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public AircraftEngineTypeCode update(AircraftEngineTypeCode entity) {
        EntityManagerHelper.log("updating AircraftEngineTypeCode instance", Level.INFO, null);
        try {
            AircraftEngineTypeCode result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public AircraftEngineTypeCode findById(String id) {
        EntityManagerHelper.log("finding AircraftEngineTypeCode instance with id: " + id, Level.INFO, null);
        try {
            AircraftEngineTypeCode instance = getEntityManager().find(AircraftEngineTypeCode.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all AircraftEngineTypeCode entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the AircraftEngineTypeCode property to query
     * @param value
     *            the property value to match
     * @return List<AircraftEngineTypeCode> found by query
     */
    @SuppressWarnings("unchecked")
    public List<AircraftEngineTypeCode> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log(
                "finding AircraftEngineTypeCode instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from AircraftEngineTypeCode model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<AircraftEngineTypeCode> findByFaaAircraftType(Object faaAircraftType) {
        return findByProperty(FAA_AIRCRAFT_TYPE, faaAircraftType);
    }

    public List<AircraftEngineTypeCode> findByEdmsAccode(Object edmsAccode) {
        return findByProperty(EDMS_ACCODE, edmsAccode);
    }

    public List<AircraftEngineTypeCode> findByEngineManufacturer(Object engineManufacturer) {
        return findByProperty(ENGINE_MANUFACTURER, engineManufacturer);
    }

    public List<AircraftEngineTypeCode> findByEngine(Object engine) {
        return findByProperty(ENGINE, engine);
    }

    public List<AircraftEngineTypeCode> findByEdmsUid(Object edmsUid) {
        return findByProperty(EDMS_UID, edmsUid);
    }

    public List<AircraftEngineTypeCode> findByScc(Object scc) {
        return findByProperty(SCC, scc);
    }

    /**
     * Find all AircraftEngineTypeCode entities.
     * 
     * @return List<AircraftEngineTypeCode> all AircraftEngineTypeCode entities
     */
    @SuppressWarnings("unchecked")
    public List<AircraftEngineTypeCode> findAll() {
        EntityManagerHelper.log("finding all AircraftEngineTypeCode instances", Level.INFO, null);
        try {
            final String queryString = "select model from AircraftEngineTypeCode model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}