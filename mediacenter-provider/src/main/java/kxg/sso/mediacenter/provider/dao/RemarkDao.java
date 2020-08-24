package kxg.sso.mediacenter.provider.dao;

import kxg.sso.mediacenter.provider.mapper.RemarkMapper;
import kxg.sso.mediacenter.provider.pojo.Remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 要写注释呀
 */
@Repository
public class RemarkDao {
    @Autowired
    private RemarkMapper remarkMapper;

    public  Integer addRemark(Remark remark){
        return remarkMapper.insertSelective(remark);
    }

    public List<Remark> findRemark(String remark){
        Example example=new Example(Remark.class);
        example.createCriteria()
                .andEqualTo("remark",remark);
        return remarkMapper.selectByExample(example);
    }
}
