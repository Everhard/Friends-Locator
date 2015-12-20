package ua.com.friendslocator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "GroupMembers")
public class GroupMember {
    @Column(name = "groupMemberId")
    private long groupMemberId;
    @Column(name = "groupId")
    private long groupId;
    @Column(name="memberName")
    private String memberName;
    @Column(name="createdAt")
    private Date createdAt;

    public GroupMember() {
    }

    public GroupMember(long groupId, String memberName, Date createdAt) {
        this.groupId = groupId;
        this.memberName = memberName;
        this.createdAt = createdAt;
    }

    public long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMember that = (GroupMember) o;
        return groupMemberId == that.groupMemberId &&
                groupId == that.groupId &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupMemberId, groupId, memberName, createdAt);
    }
}
