package kxg.sso.mediacenter.provider.dao;

import kxg.sso.mediacenter.provider.mapper.FileMapper;
import kxg.sso.mediacenter.provider.pojo.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 要写注释呀
 */
@Repository
public class FileDao {
    @Autowired
    private FileMapper fileMapper;

    public Integer addFile(File file){
        return fileMapper.insertSelective(file);
    }
}
