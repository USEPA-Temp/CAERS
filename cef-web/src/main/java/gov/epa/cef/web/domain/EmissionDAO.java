package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * Emission entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see gov.epa.cef.web.domain.Emission
 * @author MyEclipse Persistence Tools
 */
public class EmissionDAO {
    // property constants
    public static final String POLLUTANT_CODE = "pollutantCode";
    public static final String POLLUTANT_NAME = "pollutantName";
    public static final String TOTAL_EMISSIONS = "totalEmissions";
    public static final String EMISSIONS_UOM_CODE = "emissionsUomCode";
    public static final String EMISSIONS_FACTOR_TEXT = "emissionsFactorText";
    public static final String EMISSIONS_CALC_METHOD_CODE = "emissionsCalcMethodCode";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved Emission entity. All
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
     * EmissionDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            Emission entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(Emission entity) {
        EntityManagerHelper.log("saving Emission instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent Emission entity. This operation must be performed
     * within the a database transaction context for the entity's data to be
     * permanently deleted from the persistence store, i.e., database. This
     * method uses the {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * EmissionDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            Emission entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(Emission entity) {
        EntityManagerHelper.log("deleting Emission instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(Emission.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved Emission entity and return it or a copy of it
     * to the sender. A copy of the Emission entity parameter is returned when
     * the JPA persistence mechanism has not previously been tracking the
     * updated entity. This operation must be performed within the a database
     * transaction context for the entity's data to be permanently saved to the
     * persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = EmissionDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            Emission entity to update
     * @return Emission the persisted Emission entity instance, may not be the
     *         same
     * @throws RuntimeException
     *             if the operation fails
     */
    public Emission update(Emission entity) {
        EntityManagerHelper.log("updating Emission instance", Level.INFO, null);
        try {
            Emission result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public Emission findById(Long id) {
        EntityManagerHelper.log("finding Emission instance with id: " + id, Level.INFO, null);
        try {
            Emission instance = getEntityManager().find(Emission.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all Emission entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the Emission property to query
     * @param value
     *            the property value to match
     * @return List<Emission> found by query
     */
    @SuppressWarnings("unchecked")
    public List<Emission> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding Emission instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from Emission model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<Emission> findByPollutantCode(Object pollutantCode) {
        return findByProperty(POLLUTANT_CODE, pollutantCode);
    }

    public List<Emission> findByPollutantName(Object pollutantName) {
        return findByProperty(POLLUTANT_NAME, pollutantName);
    }

    public List<Emission> findByTotalEmissions(Object totalEmissions) {
        return findByProperty(TOTAL_EMISSIONS, totalEmissions);
    }

    public List<Emission> findByEmissionsUomCode(Object emissionsUomCode) {
        return findByProperty(EMISSIONS_UOM_CODE, emissionsUomCode);
    }

    public List<Emission> findByEmissionsFactorText(Object emissionsFactorText) {
        return findByProperty(EMISSIONS_FACTOR_TEXT, emissionsFactorText);
    }

    public List<Emission> findByEmissionsCalcMethodCode(Object emissionsCalcMethodCode) {
        return findByProperty(EMISSIONS_CALC_METHOD_CODE, emissionsCalcMethodCode);
    }

    public List<Emission> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<Emission> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all Emission entities.
     * 
     * @return List<Emission> all Emission entities
     */
    @SuppressWarnings("unchecked")
    public List<Emission> findAll() {
        EntityManagerHelper.log("finding all Emission instances", Level.INFO, null);
        try {
            final String queryString = "select model from Emission model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}