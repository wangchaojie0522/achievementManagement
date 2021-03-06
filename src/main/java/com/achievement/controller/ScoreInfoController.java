package com.achievement.controller;

import com.achievement.entity.ScoreInfo;
import com.achievement.entity.TeacherInfo;
import com.achievement.service.ScoreInfoService;
import com.achievement.utils.ResultUtil;
import com.achievement.vo.ObjectInfo;
import com.achievement.vo.ParentStudentScore;
import com.achievement.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;


/**
 * 成绩(ScoreInfo)表控制层
 *
 * @author weiQiang
 * @since 2018-10-02 17:52:27
 */
@RestController
@RequestMapping("scoreInfo")
@CrossOrigin
public class ScoreInfoController {
  /**
   * Service
   */
  @Autowired
  private ScoreInfoService scoreInfoService;

  /**
   * 根据主键集合删除成绩(ScoreInfo)
   *
   * @param objectInfo 主键集合
   * @return ResultEntity
   */
  @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
  public ResultEntity delete(@Valid ObjectInfo objectInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
    }
    return scoreInfoService.delete(objectInfo.getIds());
  }

  /**
   * 删除成绩(ScoreInfo)
   *
   * @param scoreId 主键
   * @return ResultEntity
   */
  @RequestMapping(path = "{scoreId}", method = RequestMethod.DELETE)
  public ResultEntity delete(@PathVariable String scoreId) {
    return scoreInfoService.delete(new ArrayList<String>() {{
      add(scoreId);
    }});
  }

  /**
   * 导出当前教师所带班级科目的学生成绩模板
   *
   * @param teacherInfo 教师信息
   * @param response    响应
   */
  @RequestMapping(value = "exportScoreTemplate", method = RequestMethod.GET)
  public void exportScoreTemplate(TeacherInfo teacherInfo, HttpServletResponse response) {
    scoreInfoService.exportScoreTemplate(teacherInfo, response);
  }

  /**
   * 上传学生成绩
   *
   * @param scoreFile 成绩文件
   * @param scoreInfo 成绩信息
   * @return ResultEntity
   */
  @RequestMapping(value = "importScore", method = RequestMethod.POST)
  public ResultEntity importScoreByFile(MultipartFile scoreFile, ScoreInfo scoreInfo) {
    return scoreInfoService.importScoreByFile(scoreFile, scoreInfo);
  }

  /**
   * 增加成绩(ScoreInfo)
   *
   * @param scoreInfo     插入参数
   * @param bindingResult 参数绑定校验
   * @return ResultEntity
   */
  @RequestMapping(value = "insert", method = RequestMethod.POST)
  public ResultEntity insert(@Valid ScoreInfo scoreInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
    }
    return scoreInfoService.insert(new ArrayList<ScoreInfo>() {{
      add(scoreInfo);
    }});
  }

  /**
   * 根据条件查询
   *
   * @param scoreInfo 查询参数
   * @return ResultEntity
   */
  @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
  public ResultEntity list(ScoreInfo scoreInfo) {
    return scoreInfoService.list(scoreInfo);
  }

  /**
   * 根据条件分页查询
   *
   * @param scoreInfo 查询参数
   * @param pageNum   开始页数
   * @param pageSize  每页显示的数据条数
   * @return ResultEntity
   */
  @RequestMapping(value = "/listByPage", method = RequestMethod.GET)
  public ResultEntity list(ScoreInfo scoreInfo, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "30") Integer pageSize) {
    return scoreInfoService.list(scoreInfo, pageNum, pageSize);
  }

  /**
   * 家长查询学生成绩
   *
   * @param parentStudentScore 查询成绩信息
   * @return ResultEntity
   */
  @RequestMapping(value = "listByParent", method = RequestMethod.GET)
  public ResultEntity listByParent(ParentStudentScore parentStudentScore) {
    return scoreInfoService.listByParent(parentStudentScore);
  }

  /**
   * 更新成绩(ScoreInfo)
   *
   * @param scoreInfo     更新参数
   * @param bindingResult 参数绑定校验
   * @return ResultEntity
   */
  @RequestMapping(value = "update", method = RequestMethod.POST)
  public ResultEntity update(@Valid ScoreInfo scoreInfo, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
    }
    return scoreInfoService.update(new ArrayList<ScoreInfo>() {{
      add(scoreInfo);
    }});
  }
}