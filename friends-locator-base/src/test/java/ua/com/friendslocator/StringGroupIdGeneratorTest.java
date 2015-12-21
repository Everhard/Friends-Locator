package ua.com.friendslocator;

import org.junit.Test;
import ua.com.friendslocator.dao.GroupDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alena on 21.12.15.
 */
public class StringGroupIdGeneratorTest {

    @Test
    public void testGetNewIdNotNull() throws Exception {
        GroupDao groupDao = mock(GroupDao.class);
        when(groupDao.selectByStringGroupId(anyString())).thenReturn(null);

        StringGroupIdGenerator generator = new StringGroupIdGenerator(groupDao);
        assertNotNull(generator.getNewId());
    }

    @Test
    public void testGetNewIdNoRepeatsIn1Mln() throws Exception {
        GroupDao groupDao = mock(GroupDao.class);
        when(groupDao.selectByStringGroupId(anyString())).thenReturn(null);

        long testSampleSize = 1000000;
        StringGroupIdGenerator generator = new StringGroupIdGenerator(groupDao);
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < testSampleSize; i++) {
            ids.add(generator.getNewId());
        }
        assertEquals(testSampleSize, ids.size());
    }
}