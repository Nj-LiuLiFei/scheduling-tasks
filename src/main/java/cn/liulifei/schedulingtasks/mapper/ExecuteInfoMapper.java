package cn.liulifei.schedulingtasks.mapper;

import cn.liulifei.schedulingtasks.entity.ExecuteInfoEntity;
import org.springframework.stereotype.Repository;

@Repository("executeInfoMapper")
public interface ExecuteInfoMapper {
    int insert(ExecuteInfoEntity executeInfoEntity);
    int update(ExecuteInfoEntity executeInfoEntity);
}
