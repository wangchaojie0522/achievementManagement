package com.achievement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 班级
 *
 * @author weiQiang
 * @date 2018/10/2
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ClassInfo implements Serializable {
  private static final long serialVersionUID = -6880616721079567143L;

  /**
   * 班级主键
   */

  private String classId;
  /**
   * 班级名称
   */
  @Length(max = 500, message = "班级名称不能多于500个字符")
  private String className;
  /**
   * 班级类型
   * 文科班
   * 理科班
   * 艺术班
   * ……
   */
  private String classType;
  /**
   * 年级ID
   */
  private String gradeId;
  /**
   * 年级ID集合
   */
  @JsonIgnore
  private List<String> gradeIds;
  /**
   * 年级信息
   */
  private GradeInfo gradeInfo;
  /**
   * 创建时间
   */
  private Date insertTime;
  /**
   * 备注
   */
  @Length(max = 4000, message = "备注不能多于4000个字符")
  private String remark;
  /**
   * 状态
   * 0:不可用;1:可用
   */
  private Integer status;
  /**
   * 更新时间
   */
  private Date updateTime;
}
