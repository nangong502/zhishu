package com.itheima.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表（菜单）
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 菜单权限编码(前端按钮权限标识)
     */
    private String code;

    /**
     * 菜单权限名称
     */
    private String title;

    /**
     * 菜单图标(侧边导航栏图标)
     */
    private String icon;

    /**
     * SpringSecurity授权标识(如：sys:user:add)
     */
    private String perms;

    /**
     * 访问地址URL
     */
    private String url;

    /**
     * 资源请求类型
     */
    private String method;

    /**
     * name与前端vue路由name约定一致
     */
    private String name;

    /**
     * 父级菜单权限id，pid等于0 为顶层权限
     */
    private Long pid;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 菜单权限类型(1:目录;2:菜单;3:按钮)
     */
    private Integer type;

    /**
     * 状态1:正常 0：禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;


}
