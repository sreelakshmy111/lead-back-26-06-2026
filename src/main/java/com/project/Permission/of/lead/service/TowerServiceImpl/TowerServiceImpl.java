package com.project.Permission.of.lead.service.TowerServiceImpl;

import com.project.Permission.of.lead.dto.TowerDto;
import com.project.Permission.of.lead.entity.Tower;
import com.project.Permission.of.lead.mapper.TowerMapper;
import com.project.Permission.of.lead.repository.TowerRepository;
import com.project.Permission.of.lead.service.TowerService;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.TypeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TowerServiceImpl implements TowerService {

    @Autowired
    private TowerRepository towerRepository;

    @Override
    public TowerDto createTower(TowerDto towerDto) {

        Tower t= TowerMapper.maptoTower(towerDto);
        Tower t1=towerRepository.save(t);
        return TowerMapper.mapToTowerDto(t1);


    }
}
