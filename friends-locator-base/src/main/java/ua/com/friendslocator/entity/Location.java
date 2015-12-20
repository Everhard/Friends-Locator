package ua.com.friendslocator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Locations")
public class Location {
    @Column(name="groupMemberId")
    private long groupMemberId;
    @Column(name="createdAt")
    private Date createdAt;
    @Column(name = "longitude")
    private double longitude;
    @Column(name="latitude")
    private double latitude;

    public Location() {
    }

    public Location(long groupMemberId, Date createdAt, double longitude, double latitude) {
        this.groupMemberId = groupMemberId;
        this.createdAt = createdAt;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
