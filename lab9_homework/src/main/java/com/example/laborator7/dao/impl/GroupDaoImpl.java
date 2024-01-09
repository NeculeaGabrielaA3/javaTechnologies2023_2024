package com.example.laborator7.dao.impl;

import com.example.laborator7.entity.Group;

public class GroupDaoImpl extends GenericDaoImpl<Group>{
    public GroupDaoImpl() {
        super(Group.class);
    }

    public Group findByGroupId(String groupId) {
        return super.getByProperty("Group.findByGroupId", groupId);
    }

}
