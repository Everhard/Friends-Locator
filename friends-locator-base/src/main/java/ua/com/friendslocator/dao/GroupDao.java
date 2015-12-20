package ua.com.friendslocator.dao;

import com.avaje.ebean.EbeanServer;
import ua.com.friendslocator.entity.Group;

public class GroupDao {
    private EbeanServer ebeanServer;

    public GroupDao(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    public Group save(Group group) {
        ebeanServer.beginTransaction();
        ebeanServer.save(group);
        ebeanServer.commitTransaction();
        return selectByStringGroupId(group.getStringGroupId());
    }

    public Group selectByStringGroupId(String stringGroupId) {
        return ebeanServer.find(Group.class).where().eq("stringGroupId", stringGroupId).findUnique();
    }
}
