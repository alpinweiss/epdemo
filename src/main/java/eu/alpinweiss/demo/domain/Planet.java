package eu.alpinweiss.demo.domain;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class Planet implements Serializable {

    @Id
    @GeneratedValue
    private Long pk;

    @Column(nullable = false)
    @NotEmpty(message = "{validation.not-empty.name}")
    private String name;

    @Column(name = "distancefromearth")
    private Double distanceFromEarth;

    @Column(name = "discoveredby")
    private String discoveredBy;

	private Double diameter;

    private Boolean atmosphere;

    private String description;

    public Planet() {
    }

    public Planet(String name, Double distanceFromEarth, String discoveredBy, Double diameter, Boolean atmosphere, String description) {
        this.name = name;
        this.distanceFromEarth = distanceFromEarth;
        this.discoveredBy = discoveredBy;
        this.diameter = diameter;
        this.atmosphere = atmosphere;
        this.description = description;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Double getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public void setDistanceFromEarth(Double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
    }

    public String getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(String discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Boolean isAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Boolean atmosphere) {
        this.atmosphere = atmosphere;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equal(pk, planet.pk) &&
                Objects.equal(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pk, name);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "pk=" + pk +
                ", name='" + name + '\'' +
                ", distanceFromEarth=" + distanceFromEarth +
                ", discoveredBy='" + discoveredBy + '\'' +
                ", diameter=" + diameter +
                ", atmosphere=" + atmosphere +
                ", description='" + description + '\'' +
                '}';
    }
}
