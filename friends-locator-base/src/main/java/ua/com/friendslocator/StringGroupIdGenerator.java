package ua.com.friendslocator;

import ua.com.friendslocator.dao.GroupDao;
import ua.com.friendslocator.entity.Group;

import java.util.UUID;

public class StringGroupIdGenerator {
    private GroupDao groupDao;

    public StringGroupIdGenerator(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public String getNewId() {
        String stringGroupId;
        Group group;
        do {
            stringGroupId = getRandomUUID();
            group = groupDao.selectByStringGroupId(stringGroupId);
        } while (group != null);
        return  stringGroupId;
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
