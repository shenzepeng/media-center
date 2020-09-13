package kxg.sso.mediacenter.provider.dao;

import kxg.sso.mediacenter.provider.mapper.FileMapper;
import kxg.sso.mediacenter.provider.pojo.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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

    public List<File> findFileByMd5(String md5){
        if (StringUtils.isEmpty(md5)){
            return new ArrayList<>();
        }
        Example example=new Example(File.class);
        example.createCriteria()
                .andEqualTo("md5",md5);
        return fileMapper.selectByExample(example);
    }
}
