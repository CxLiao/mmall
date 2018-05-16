package com.mmall.pojo;

import lombok.*;
import java.util.Date;

/**
 * Category domain
 * @author liaocx on 2017/10/13.
 * @author liaocx 重写于2018/5/15 新增lombok注解
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}