package com.sinosoft.cms.base.module.sys.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sinosoft.cms.base.annotation.Excel;
import com.sinosoft.cms.base.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author sinosoft
 */
@Getter
@Setter
@ApiModel("用户对象")
@NoArgsConstructor
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    @ApiModelProperty("")
    private Long userId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    @ApiModelProperty("登录名称")
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    @ApiModelProperty("用户昵称")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /** 用户类型 */
    @Excel(name = "用户类型：0-平台注册用户,1-AD域用户,2-单点登录用户")
    @ApiModelProperty("用户类型：0-平台注册用户,1-AD域用户,2-单点登录用户")
    private Integer type;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    @ApiModelProperty("用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phone;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty("用户性别 0=男,1=女,2=未知")
    private Integer sex;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /** 背景图 */
    @ApiModelProperty("背景图")
    private String background;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", hidden = true)
    @JsonProperty
    private String password;

    /**
     * 盐加密
     */
    @ApiModelProperty(value = "盐加密", hidden = true)
    private String salt;

    /**
     * 改变状态
     */
    @ApiModelProperty("改变状态")
    private Boolean changeStatus;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private Integer status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在 1代表删除）")
    private Integer delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT)
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    /**
     * 角色组
     */
    @ApiModelProperty("角色组")
    private Long[] roleIds;

    /**
     * 角色对象
     */
    @ApiModelProperty("角色对象")
    private List<SysRole> roles;

    @ApiModelProperty("是否是超级管理员")
    private Boolean isAdmin;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
