package com.my.spring.service;

import com.my.spring.model.Building;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface BuildingService {
    DataWrapper<Void> addBuilding(Building building, String token);
    DataWrapper<Void> deleteBuilding(Long id,String token);
    DataWrapper<Void> updateBuilding(Building building,String token);
    DataWrapper<List<Building>> getBuildingList();
	DataWrapper<Building> getBuildingByProjectId(Long projectId,String token);
}
