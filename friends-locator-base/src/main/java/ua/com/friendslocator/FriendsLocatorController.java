package ua.com.friendslocator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.friendslocator.dao.GroupDao;
import ua.com.friendslocator.dao.GroupMemberDao;
import ua.com.friendslocator.dao.LocationDao;
import ua.com.friendslocator.entity.Group;
import ua.com.friendslocator.entity.GroupMember;
import ua.com.friendslocator.entity.Location;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RestController
@Controller
public class FriendsLocatorController {
    @Autowired
    private StringGroupIdGenerator stringGroupIdGenerator;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private DateTimeProvider dateTimeProvider;

    @RequestMapping(method = RequestMethod.GET, path = "/ajax/group/new-id")
    public Group createGroup() {
        String stringGroupId = stringGroupIdGenerator.getNewId();
        return groupDao.save(new Group(stringGroupId, dateTimeProvider.now()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/ajax/location")
    public ResponseEntity saveLocation(@RequestParam(value="groupId") String stringGroupId,
                                       @RequestParam(value="memberName") String groupMemberName,
                                       @RequestParam(value="long") double longitude,
                                       @RequestParam(value="lat") double latitude) {

        Group group = groupDao.selectByStringGroupId(stringGroupId);
        if (group == null) {
            return new ResponseEntity<String>("There is no group with id=" + stringGroupId,  HttpStatus.BAD_REQUEST);
        }

        GroupMember groupMember = groupMemberDao.selectByGroupIdAndMemberName(group.getGroupId(), groupMemberName);
        if (groupMember == null) {
            groupMember = groupMemberDao.save(new GroupMember(group.getGroupId(), groupMemberName, dateTimeProvider.now()));
        }

        locationDao.save(new Location(groupMember.getGroupMemberId(), dateTimeProvider.now(), longitude, latitude));
        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/ajax/group/friends-coordinates")
    public Collection<Location> getGroupMembersLocations(@RequestParam(value="groupId") String stringGroupId) {
        Collection<Location> locations = new LinkedList<Location>();

        Group group = groupDao.selectByStringGroupId(stringGroupId);
        if (group == null) {
            return null;
        }

        Collection<GroupMember> groupMembers = groupMemberDao.selectByGroupId(group.getGroupId());
        for (GroupMember groupMember: groupMembers) {
            List<Location> locationsForGroupMember = locationDao.selectByGroupMemberId(groupMember.getGroupMemberId());
            if (locationsForGroupMember != null) {
                locations.addAll(locationsForGroupMember);
            }
        }

        return locations;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/ajax/group/friends-info")
    public Collection<GroupMember> getGroupMembers(@RequestParam(value="groupId") String stringGroupId) {
        Group group = groupDao.selectByStringGroupId(stringGroupId);
        if (group == null) {
            return null;
        }
        return groupMemberDao.selectByGroupId(group.getGroupId());
    }
}
