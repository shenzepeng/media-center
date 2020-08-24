package kxg.sso.mediacenter.provider.pojo;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "t_file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件的url
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 文件摘要
     */
    private String md5;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * Remark
     */
    private String remark;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文件的url
     *
     * @return file_url - 文件的url
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 设置文件的url
     *
     * @param fileUrl 文件的url
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    /**
     * 获取文件摘要
     *
     * @return md5 - 文件摘要
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 设置文件摘要
     *
     * @param md5 文件摘要
     */
    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取Remark
     *
     * @return remark - Remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置Remark
     *
     * @param remark Remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}