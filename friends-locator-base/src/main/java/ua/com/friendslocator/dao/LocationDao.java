package ua.com.friendslocator.dao;

import com.avaje.ebean.EbeanServer;
import ua.com.friendslocator.entity.Location;

import java.util.Date;
import java.util.List;

public class LocationDao {
    private EbeanServer ebeanServer;

    public LocationDao(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    public Location save(Location location) {
        ebeanServer.beginTransaction();
        ebeanServer.save(location);
        ebeanServer.commitTransaction();
        return  selectByGroupMemberIdAndCreationTime(location.getGroupMemberId(), location.getCreatedAt());
    }

    public Location selectByGroupMemberIdAndCreationTime(long groupMemberId, Date creationTime) {
        return ebeanServer.find(Location.class).where().eq("groupMemberId", groupMemberId)
                .eq("createdAt", creationTime).findUnique();
    }

    public List<Location> selectByGroupMemberId(long groupMemberId) {
        return  ebeanServer.find(Location.class).where().eq("groupMemberId", groupMemberId).order("createdAt").findList();
    }
}
