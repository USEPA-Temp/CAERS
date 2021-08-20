package gov.epa.cef.web.service.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EisDataStatsDto {

    private final Set<Integer> availableYears = new LinkedHashSet<>();

    private final List<EisDataStatusStat> statuses = new ArrayList<>();

    public Collection<Integer> getAvailableYears() {

        return availableYears;
    }

    public void setAvailableYears(Collection<Integer> availableYears) {

        this.availableYears.clear();
        if (availableYears != null) {

            this.availableYears.addAll(availableYears);
        }
    }

    public Collection<EisDataStatusStat> getStatuses() {

        return statuses;
    }

    public void setStatuses(Collection<EisDataStatusStat> statuses) {

        this.statuses.clear();
        if (statuses != null) {
            this.statuses.addAll(statuses);
        }
    }

    public EisDataStatsDto withAvailableYears(final Collection<Integer> availableYears) {

        setAvailableYears(availableYears);
        return this;
    }

    public EisDataStatsDto withStatuses(final Collection<EisDataStatusStat> statuses) {

        setStatuses(statuses);
        return this;
    }

    public interface EisDataStatusStat {

        int getCount();

        EisSubmissionStatus getStatus();
    }
}
