package gov.epa.cef.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * ReportingPeriod entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.ReportingPeriod
 * @author MyEclipse Persistence Tools
 */
public class ReportingPeriodDAO {
    // property constants
    public static final String REPORTING_PERIOD_TYPE_CODE = "reportingPeriodTypeCode";
    public static final String EMISSIONS_OPERATING_TYPE_CODE = "emissionsOperatingTypeCode";
    public static final String CALCULATION_PARAMETER_TYPE_CODE = "calculationParameterTypeCode";
    public static final String CALCULATION_PARAMETER_UOM = "calculationParameterUom";
    public static final String CALCULATION_MATERIAL_CODE = "calculationMaterialCode";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved ReportingPeriod entity.
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
     * ReportingPeriodDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReportingPeriod entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(ReportingPeriod entity) {
        EntityManagerHelper.log("saving ReportingPeriod instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent ReportingPeriod entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * ReportingPeriodDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            ReportingPeriod entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(ReportingPeriod entity) {
        EntityManagerHelper.log("deleting ReportingPeriod instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(ReportingPeriod.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved ReportingPeriod entity and return it or a copy
     * of it to the sender. A copy of the ReportingPeriod entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = ReportingPeriodDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            ReportingPeriod entity to update
     * @return ReportingPeriod the persisted ReportingPeriod entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public ReportingPeriod update(ReportingPeriod entity) {
        EntityManagerHelper.log("updating ReportingPeriod instance", Level.INFO, null);
        try {
            ReportingPeriod result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public ReportingPeriod findById(Long id) {
        EntityManagerHelper.log("finding ReportingPeriod instance with id: " + id, Level.INFO, null);
        try {
            ReportingPeriod instance = getEntityManager().find(ReportingPeriod.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all ReportingPeriod entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the ReportingPeriod property to query
     * @param value
     *            the property value to match
     * @return List<ReportingPeriod> found by query
     */
    @SuppressWarnings("unchecked")
    public List<ReportingPeriod> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding ReportingPeriod instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from ReportingPeriod model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<ReportingPeriod> findByReportingPeriodTypeCode(Object reportingPeriodTypeCode) {
        return findByProperty(REPORTING_PERIOD_TYPE_CODE, reportingPeriodTypeCode);
    }

    public List<ReportingPeriod> findByEmissionsOperatingTypeCode(Object emissionsOperatingTypeCode) {
        return findByProperty(EMISSIONS_OPERATING_TYPE_CODE, emissionsOperatingTypeCode);
    }

    public List<ReportingPeriod> findByCalculationParameterTypeCode(Object calculationParameterTypeCode) {
        return findByProperty(CALCULATION_PARAMETER_TYPE_CODE, calculationParameterTypeCode);
    }

    public List<ReportingPeriod> findByCalculationParameterUom(Object calculationParameterUom) {
        return findByProperty(CALCULATION_PARAMETER_UOM, calculationParameterUom);
    }

    public List<ReportingPeriod> findByCalculationMaterialCode(Object calculationMaterialCode) {
        return findByProperty(CALCULATION_MATERIAL_CODE, calculationMaterialCode);
    }

    public List<ReportingPeriod> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<ReportingPeriod> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all ReportingPeriod entities.
     * 
     * @return List<ReportingPeriod> all ReportingPeriod entities
     */
    @SuppressWarnings("unchecked")
    public List<ReportingPeriod> findAll() {
        EntityManagerHelper.log("finding all ReportingPeriod instances", Level.INFO, null);
        try {
            final String queryString = "select model from ReportingPeriod model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}