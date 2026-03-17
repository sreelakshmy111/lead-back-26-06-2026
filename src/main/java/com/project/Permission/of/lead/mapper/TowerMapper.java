package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.TowerDto;
import com.project.Permission.of.lead.entity.Tower;

public class TowerMapper {

    public static Tower maptoTower(TowerDto towerDto){
        return new Tower(
                towerDto.getId(),
                towerDto.getName()
        );
    }

    public static TowerDto mapToTowerDto(Tower tower){
        return new TowerDto(
                tower.getId(),
                tower.getName()
        );

    }
}
