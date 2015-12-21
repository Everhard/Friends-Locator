package ua.com.friendslocator.dao;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.friendslocator.entity.Group;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by alena on 21.12.15.
 */
public class GroupDaoTest {
    @Test
    public void testSave() throws Exception {
        String stringGroupId = "FFF";
        Date date = mock(Date.class);
        Group group = new Group(stringGroupId, date);

        EbeanServer ebeanServerMock = mock(EbeanServer.class);
        Query queryMock = mock(Query.class);
        when(ebeanServerMock.find(Group.class)).thenReturn(queryMock);
        ExpressionList expressionList = mock(ExpressionList.class);
        when(queryMock.where()).thenReturn(expressionList);
        when(expressionList.eq(anyString(), anyString())).thenReturn(expressionList);
        when(expressionList.findUnique()).thenReturn(group);

        GroupDao groupDao = new GroupDao(ebeanServerMock);
        assertEquals(group, groupDao.save(group));
    }

    @Test
    public void testSelectByStringGroupId() throws Exception {
        String stringGroupId = "FFF";
        Date date = mock(Date.class);
        Group group = new Group(stringGroupId, date);

        EbeanServer ebeanServerMock = mock(EbeanServer.class);
        Query queryMock = mock(Query.class);
        when(ebeanServerMock.find(Group.class)).thenReturn(queryMock);
        ExpressionList expressionList = mock(ExpressionList.class);
        when(queryMock.where()).thenReturn(expressionList);
        when(expressionList.eq(anyString(), anyString())).thenReturn(expressionList);
        when(expressionList.findUnique()).thenReturn(group);

        GroupDao groupDao = new GroupDao(ebeanServerMock);
        assertEquals(group, groupDao.selectByStringGroupId(stringGroupId));
    }
}