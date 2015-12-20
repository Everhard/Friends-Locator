package ua.com.friendslocator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Groups")
public class Group {
    @Column(name = "groupId")
    private long groupId;
    @Column(name = "stringGroupId")
    private String stringGroupId;
    @Column(name = "createdAt")
    private Date createdAt;

    public Group() {
    }

    public Group(String stringGroupId, Date createdAt) {
        this.stringGroupId = stringGroupId;
        this.createdAt = createdAt;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getStringGroupId() {
        return stringGroupId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public void setStringGroupId(String stringGroupId) {
        this.stringGroupId = stringGroupId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                Objects.equals(stringGroupId, group.stringGroupId) &&
                Objects.equals(createdAt, group.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, stringGroupId, createdAt);
    }
}
