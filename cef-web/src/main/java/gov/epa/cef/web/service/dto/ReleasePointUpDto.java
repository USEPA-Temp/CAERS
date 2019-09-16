package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class ReleasePointUpDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String releasePointIdentifier;
    private String description;
    private String comments;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReleasePointIdentifier() {
        return releasePointIdentifier;
    }
    public void setReleasePointIdentifier(String releasePointIdentifier) {
        this.releasePointIdentifier = releasePointIdentifier;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
