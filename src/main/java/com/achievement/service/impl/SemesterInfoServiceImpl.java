package com.achievement.service.impl;

import com.achievement.entity.SemesterInfo;
import com.achievement.enums.GlobalEnum;
import com.achievement.mapper.SemesterInfoMapper;
import com.achievement.service.SemesterInfoService;
import com.achievement.utils.GloabalUtils;
import com.achievement.utils.ResultUtil;
import com.achievement.vo.ResultEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 学期(SemesterInfo)ServiceImpl
 *
 * @author weiQiang
 * @since 2018-10-02 17:52:27
 */
@Service("semesterInfoService")
public class SemesterInfoServiceImpl implements SemesterInfoService {
  @Resource
  private SemesterInfoMapper semesterInfoMapper;

  /**
   * 学期(SemesterInfo)信息Map
   *
   * @param semesterInfo 查询参数
   * @return ResultEntity
   */
  @Override
  public Map<String, SemesterInfo> convertRecordToMap(SemesterInfo semesterInfo) {
    List<SemesterInfo> scoreInfos = semesterInfoMapper.list(semesterInfo);
    Map<String, SemesterInfo> semesterInfoMap = scoreInfos.stream().filter(info -> StringUtils.isNotBlank(info.getSemesterId()))
        .collect(Collectors.toMap(SemesterInfo::getSemesterId, Function.identity(), (oldValue, newValue) -> newValue));
    return semesterInfoMap;
  }

  /**
   * 删除学期(SemesterInfo)
   *
   * @param semesterIds 主键集合
   * @return ResultEntity
   */
  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public ResultEntity delete(List<String> semesterIds) {
    if (null == semesterIds || semesterIds.size() < 1) {
      return ResultUtil.error(GlobalEnum.DataEmpty);
    }
    List<SemesterInfo> semesterInfoList = new ArrayList<SemesterInfo>() {{
      semesterIds.forEach(semesterId -> add(SemesterInfo.builder().semesterId(semesterId).build()));
    }};
    semesterInfoMapper.delete(semesterInfoList);
    return ResultUtil.success(GlobalEnum.DeleteSuccess, semesterIds);
  }

  /**
   * 增加学期(SemesterInfo)
   *
   * @param semesterInfoList 参数
   * @return ResultEntity
   */
  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public ResultEntity insert(List<SemesterInfo> semesterInfoList) {
    if (null == semesterInfoList || semesterInfoList.size() < 1) {
      return ResultUtil.error(GlobalEnum.DataEmpty);
    }
    semesterInfoList.stream().forEach(semesterInfo -> semesterInfo.setSemesterId("semester_" + GloabalUtils.ordinaryId()));
    Integer insertCount = semesterInfoMapper.insert(semesterInfoList);
    if (insertCount > 0) {
      return ResultUtil.success(GlobalEnum.InsertSuccess, semesterInfoList);
    }
    return ResultUtil.error(GlobalEnum.InsertError);
  }

  /**
   * 根据条件分页查询
   *
   * @param semesterInfo 查询参数
   * @param pageNum      开始页数
   * @param pageSize     每页显示的数据条数
   * @return ResultEntity
   */
  @Override
  public ResultEntity list(SemesterInfo semesterInfo, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<SemesterInfo> semesterInfoPage = semesterInfoMapper.list(semesterInfo);
    PageInfo pageInfo = new PageInfo(semesterInfoPage);
    return ResultUtil.success(GlobalEnum.QuerySuccess, pageInfo);
  }

  /**
   * 根据条件查询
   *
   * @param semesterInfo 查询参数
   * @return ResultEntity
   */
  @Override
  public ResultEntity list(SemesterInfo semesterInfo) {
    List<SemesterInfo> semesterInfos = semesterInfoMapper.list(semesterInfo);
    return ResultUtil.success(GlobalEnum.QuerySuccess, semesterInfos);
  }

  /**
   * 更新学期(SemesterInfo)
   *
   * @param semesterInfoList 更新参数
   * @return ResultEntity
   */
  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public ResultEntity update(List<SemesterInfo> semesterInfoList) {
    if (null == semesterInfoList || semesterInfoList.size() < 1) {
      return ResultUtil.error(GlobalEnum.DataEmpty);
    }
    Integer updateCount = semesterInfoMapper.update(semesterInfoList);
    if (updateCount > 0) {
      return ResultUtil.success(GlobalEnum.UpdateSuccess, semesterInfoList);
    }
    return ResultUtil.error(GlobalEnum.UpdateError);
  }
}