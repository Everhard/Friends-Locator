package ua.com.friendslocator.dao;

import com.avaje.ebean.EbeanServer;
import ua.com.friendslocator.entity.GroupMember;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GroupMemberDao {
    private EbeanServer ebeanServer;

    public GroupMemberDao(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    public GroupMember selectByMemberId(long groupMemberId) {
        return ebeanServer.find(GroupMember.class).where().eq("groupMemberId", groupMemberId).findUnique();
    }

    public GroupMember selectByGroupIdAndMemberName(long groupId, String groupMemberName) {
        return ebeanServer.find(GroupMember.class).where().eq("groupId", groupId)
                .eq("memberName", groupMemberName).findUnique();
    }

    public Collection<GroupMember> selectByGroupId(long groupId) {
        List<GroupMember> groupMembers = ebeanServer.find(GroupMember.class).where().eq("groupId", groupId).findList();
        if (groupMembers == null) {
            return Collections.emptyList();
        }
        return groupMembers;
    }

    public GroupMember save(GroupMember groupMember) {
        ebeanServer.beginTransaction();
        ebeanServer.save(groupMember);
        ebeanServer.commitTransaction();
        return selectByGroupIdAndMemberName(groupMember.getGroupId(), groupMember.getMemberName());
    }
}
