package gov.epa.cef.web.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * A data access object (DAO) providing persistence and search support for
 * OperatingDetail entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see gov.epa.cef.web.domain.OperatingDetail
 * @author MyEclipse Persistence Tools
 */
public class OperatingDetailDAO {
    // property constants
    public static final String AVG_HOURS_PER_PERIOD = "avgHoursPerPeriod";
    public static final String AVG_HOURS_PER_DAY = "avgHoursPerDay";
    public static final String AVG_DAYS_PER_WEEK = "avgDaysPerWeek";
    public static final String AVG_WEEKS_PER_PERIOD = "avgWeeksPerPeriod";
    public static final String PERCENT_WINTER = "percentWinter";
    public static final String PERCENT_SPRING = "percentSpring";
    public static final String PERCENT_SUMMER = "percentSummer";
    public static final String PERCENT_FALL = "percentFall";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    private EntityManager getEntityManager() {
        return EntityManagerHelper.getEntityManager();
    }

    /**
     * Perform an initial save of a previously unsaved OperatingDetail entity.
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
     * OperatingDetailDAO.save(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            OperatingDetail entity to persist
     * @throws RuntimeException
     *             when the operation fails
     */
    public void save(OperatingDetail entity) {
        EntityManagerHelper.log("saving OperatingDetail instance", Level.INFO, null);
        try {
            getEntityManager().persist(entity);
            EntityManagerHelper.log("save successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("save failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Delete a persistent OperatingDetail entity. This operation must be
     * performed within the a database transaction context for the entity's data
     * to be permanently deleted from the persistence store, i.e., database.
     * This method uses the
     * {@link javax.persistence.EntityManager#remove(Object)
     * EntityManager#delete} operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * OperatingDetailDAO.delete(entity);
     * EntityManagerHelper.commit();
     * entity = null;
     * </pre>
     * 
     * @param entity
     *            OperatingDetail entity to delete
     * @throws RuntimeException
     *             when the operation fails
     */
    public void delete(OperatingDetail entity) {
        EntityManagerHelper.log("deleting OperatingDetail instance", Level.INFO, null);
        try {
            entity = getEntityManager().getReference(OperatingDetail.class, entity.getId());
            getEntityManager().remove(entity);
            EntityManagerHelper.log("delete successful", Level.INFO, null);
        } catch (RuntimeException re) {
            EntityManagerHelper.log("delete failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Persist a previously saved OperatingDetail entity and return it or a copy
     * of it to the sender. A copy of the OperatingDetail entity parameter is
     * returned when the JPA persistence mechanism has not previously been
     * tracking the updated entity. This operation must be performed within the
     * a database transaction context for the entity's data to be permanently
     * saved to the persistence store, i.e., database. This method uses the
     * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
     * operation.
     * 
     * <pre>
     * EntityManagerHelper.beginTransaction();
     * entity = OperatingDetailDAO.update(entity);
     * EntityManagerHelper.commit();
     * </pre>
     * 
     * @param entity
     *            OperatingDetail entity to update
     * @return OperatingDetail the persisted OperatingDetail entity instance,
     *         may not be the same
     * @throws RuntimeException
     *             if the operation fails
     */
    public OperatingDetail update(OperatingDetail entity) {
        EntityManagerHelper.log("updating OperatingDetail instance", Level.INFO, null);
        try {
            OperatingDetail result = getEntityManager().merge(entity);
            EntityManagerHelper.log("update successful", Level.INFO, null);
            return result;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("update failed", Level.SEVERE, re);
            throw re;
        }
    }

    public OperatingDetail findById(Long id) {
        EntityManagerHelper.log("finding OperatingDetail instance with id: " + id, Level.INFO, null);
        try {
            OperatingDetail instance = getEntityManager().find(OperatingDetail.class, id);
            return instance;
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find failed", Level.SEVERE, re);
            throw re;
        }
    }

    /**
     * Find all OperatingDetail entities with a specific property value.
     * 
     * @param propertyName
     *            the name of the OperatingDetail property to query
     * @param value
     *            the property value to match
     * @return List<OperatingDetail> found by query
     */
    @SuppressWarnings("unchecked")
    public List<OperatingDetail> findByProperty(String propertyName, final Object value) {
        EntityManagerHelper.log("finding OperatingDetail instance with property: " + propertyName + ", value: " + value,
                Level.INFO, null);
        try {
            final String queryString = "select model from OperatingDetail model where model." + propertyName
                    + "= :propertyValue";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("propertyValue", value);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find by property name failed", Level.SEVERE, re);
            throw re;
        }
    }

    public List<OperatingDetail> findByAvgHoursPerPeriod(Object avgHoursPerPeriod) {
        return findByProperty(AVG_HOURS_PER_PERIOD, avgHoursPerPeriod);
    }

    public List<OperatingDetail> findByAvgHoursPerDay(Object avgHoursPerDay) {
        return findByProperty(AVG_HOURS_PER_DAY, avgHoursPerDay);
    }

    public List<OperatingDetail> findByAvgDaysPerWeek(Object avgDaysPerWeek) {
        return findByProperty(AVG_DAYS_PER_WEEK, avgDaysPerWeek);
    }

    public List<OperatingDetail> findByAvgWeeksPerPeriod(Object avgWeeksPerPeriod) {
        return findByProperty(AVG_WEEKS_PER_PERIOD, avgWeeksPerPeriod);
    }

    public List<OperatingDetail> findByPercentWinter(Object percentWinter) {
        return findByProperty(PERCENT_WINTER, percentWinter);
    }

    public List<OperatingDetail> findByPercentSpring(Object percentSpring) {
        return findByProperty(PERCENT_SPRING, percentSpring);
    }

    public List<OperatingDetail> findByPercentSummer(Object percentSummer) {
        return findByProperty(PERCENT_SUMMER, percentSummer);
    }

    public List<OperatingDetail> findByPercentFall(Object percentFall) {
        return findByProperty(PERCENT_FALL, percentFall);
    }

    public List<OperatingDetail> findByCreatedBy(Object createdBy) {
        return findByProperty(CREATED_BY, createdBy);
    }

    public List<OperatingDetail> findByLastModifiedBy(Object lastModifiedBy) {
        return findByProperty(LAST_MODIFIED_BY, lastModifiedBy);
    }

    /**
     * Find all OperatingDetail entities.
     * 
     * @return List<OperatingDetail> all OperatingDetail entities
     */
    @SuppressWarnings("unchecked")
    public List<OperatingDetail> findAll() {
        EntityManagerHelper.log("finding all OperatingDetail instances", Level.INFO, null);
        try {
            final String queryString = "select model from OperatingDetail model";
            Query query = getEntityManager().createQuery(queryString);
            return query.getResultList();
        } catch (RuntimeException re) {
            EntityManagerHelper.log("find all failed", Level.SEVERE, re);
            throw re;
        }
    }

}