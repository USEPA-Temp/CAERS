package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * ReleasePoint entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.ReleasePoint
 * @author MyEclipse Persistence Tools
 */
public class ReleasePointDAO {
    // property constants
    public static final String RELEASE_POINT_IDENTIFIER = "releasePointIdentifier";
    public static final String TYPE_CODE = "typeCode";
    public static final String DESCRIPTION = "description";
    public static final String STACK_HEIGHT = "stackHeight";
    public static final String STACK_HEIGHT_UOM_CODE = "stackHeightUomCode";
    public static final String STACK_DIAMETER = "stackDiameter";
    public static final String STACK_DIAMETER_UOM_CODE = "stackDiameterUomCode";
    public static final String EXIT_GAS_VELOCITY = "exitGasVelocity";
    public static final String EXIT_GAS_VELICITY_UOM_CODE = "exitGasVelicityUomCode";
    public static final String EXIT_GAS_TEMPERATURE = "exitGasTemperature";
    public static final String EXIT_GAS_FLOW_RATE = "exitGasFlowRate";
    public static final String EXIT_GAS_FLOW_UOM_CODE = "exitGasFlowUomCode";
    public static final String STATUS_YEAR = "statusYear";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved ReleasePoint entity. All
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
     * ReleasePointDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReleasePoint entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(ReleasePoint entity) {
        EntityManagerHelper.log("saving ReleasePoint instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent ReleasePoint entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * ReleasePointDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            ReleasePoint entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(ReleasePoint entity) {
        EntityManagerHelper.log("deleting ReleasePoint instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(ReleasePoint.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved ReleasePoint entity and return it or a copy of
     * it to the sender. A copy of the ReleasePoint entity parameter is returned
     * when the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = ReleasePointDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReleasePoint entity to update
     * @return ReleasePoint the persisted ReleasePoint entity instance, may not
     *         be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public ReleasePoint update(ReleasePoint entity) {
        EntityManagerHelper.log("updating ReleasePoint instance", Level.INFO, null);
        try {
            ReleasePoint result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public ReleasePoint findById(Long id) {
        EntityManagerHelper.log("finding ReleasePoint instance with id: " + id, Level.INFO, null);
        try {
            ReleasePoint instance = getEntityManager().find(ReleasePoint.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all ReleasePoint entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the ReleasePoint property to query
     * @param value
     *            the property value to match
     * @return List<ReleasePoint> found by query
     */
    @SuppressWarnings("unchecked")
    public List<ReleasePoint> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding ReleasePoint instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from ReleasePoint model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<ReleasePoint> findByReleasePointIdentifier(Object releasePointIdentifier) {
        return findByProperty(RELEASE_POINT_IDENTIFIER, releasePointIdentifier);
    }

    public List<ReleasePoint> findByTypeCode(Object typeCode) {
        return findByProperty(TYPE_CODE, typeCode);
    }

    public List<ReleasePoint> findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List<ReleasePoint> findByStackHeight(Object stackHeight) {
        return findByProperty(STACK_HEIGHT, stackHeight);
    }

    public List<ReleasePoint> findByStackHeightUomCode(Object stackHeightUomCode) {
        return findByProperty(STACK_HEIGHT_UOM_CODE, stackHeightUomCode);
    }

    public List<ReleasePoint> findByStackDiameter(Object stackDiameter) {
        return findByProperty(STACK_DIAMETER, stackDiameter);
    }

    public List<ReleasePoint> findByStackDiameterUomCode(Object stackDiameterUomCode) {
        return findByProperty(STACK_DIAMETER_UOM_CODE, stackDiameterUomCode);
    }

    public List<ReleasePoint> findByExitGasVelocity(Object exitGasVelocity) {
        return findByProperty(EXIT_GAS_VELOCITY, exitGasVelocity);
    }

    public List<ReleasePoint> findByExitGasVelicityUomCode(Object exitGasVelicityUomCode) {
        return findByProperty(EXIT_GAS_VELICITY_UOM_CODE, exitGasVelicityUomCode);
    }

    public List<ReleasePoint> findByExitGasTemperature(Object exitGasTemperature) {
        return findByProperty(EXIT_GAS_TEMPERATURE, exitGasTemperature);
    }

    public List<ReleasePoint> findByExitGasFlowRate(Object exitGasFlowRate) {
        return findByProperty(EXIT_GAS_FLOW_RATE, exitGasFlowRate);
    }

    public List<ReleasePoint> findByExitGasFlowUomCode(Object exitGasFlowUomCode) {
        return findByProperty(EXIT_GAS_FLOW_UOM_CODE, exitGasFlowUomCode);
    }

    public List<ReleasePoint> findByStatusYear(Object statusYear) {
        return findByProperty(STATUS_YEAR, statusYear);
    }

    public List<ReleasePoint> findByLatitude(Object latitude) {
        return findByProperty(LATITUDE, latitude);
    }

    public List<ReleasePoint> findByLongitude(Object longitude) {
        return findByProperty(LONGITUDE, longitude);
    }

    public List<ReleasePoint> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<ReleasePoint> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all ReleasePoint entities.
     * 
     * @return List<ReleasePoint> all ReleasePoint entities
     */
    @SuppressWarnings("unchecked")
    public List<ReleasePoint> findAll() {
        EntityManagerHelper.log("finding all ReleasePoint instances", Level.INFO, null);
        try {
            final String queryString = "select model from ReleasePoint model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}