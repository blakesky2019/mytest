package com.itbsky.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itbsky.constant.MessageConstant;
import com.itbsky.entity.Result;
import com.itbsky.service.MemberService;
import com.itbsky.service.ReportService;
import com.itbsky.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itbsky.controller
 * 作者:龙在江湖
 * 日期:2019/10/9 12:27
 */
@RestController
@RequestMapping("/report")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Reference
    private ReportService reportService;

    /**
     * 获取每月的会员总量
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String, Object> map=memberService.getMemberReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     * 获取套餐的占比
     * @return
     */
    @GetMapping("/getPackageReport")
    public Result getPackageReport(){
        Map<String, Object> map=memberService.getPackageReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }


    /**
     * 获取运营统计数据
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String, Object> result=memberService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,result);
    }

    /**
     * 用户下载运营统计数据,输出excel表格
     */
    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        // 1.获取模板, getRealPath 获取运行在电脑上的路径 webapp目录
        String templatePath = request.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        // 2.获取运营数据
        Map<String, Object> reportData = memberService.getBusinessReportData();
        // 3.操作模板，把运营数据填入模板中
        //  操作excel
        // 设置http头信息
        String filename = "运营统计数据.xlsx";
        try {
            // filename.getBytes() UTF-8字符打散，字节数组，ISO-8859-1  latin-1就能支持
            filename = new String(filename.getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition","attachment;filename=" + filename);
        // 设置http的内容体的格式
        response.setContentType("application/vnd.ms-excel");

        try(
                XSSFWorkbook wk = new XSSFWorkbook(templatePath);
                OutputStream os = response.getOutputStream();
        ){
            // 工作表
            XSSFSheet sht = wk.getSheetAt(0);
            //日期
            sht.getRow(2).getCell(5).setCellValue((String)reportData.get("reportDate"));
            //新增会员数
            sht.getRow(4).getCell(5).setCellValue(reportData.get("todayNewMember").toString());
            //总会员数
            sht.getRow(4).getCell(7).setCellValue(reportData.get("totalMember").toString());

            //本周新增会员数
            sht.getRow(5).getCell(5).setCellValue(reportData.get("thisWeekNewMember").toString());
            //本月新增会员数
            sht.getRow(5).getCell(7).setCellValue(reportData.get("thisMonthNewMember").toString());

            //今日预约数
            sht.getRow(7).getCell(5).setCellValue(reportData.get("todayOrderNumber").toString());
            //今日到诊数
            sht.getRow(7).getCell(7).setCellValue(reportData.get("todayVisitsNumber").toString());

            //本周预约数
            sht.getRow(8).getCell(5).setCellValue(reportData.get("thisWeekOrderNumber").toString());
            //本周到诊数
            sht.getRow(8).getCell(7).setCellValue(reportData.get("thisWeekVisitsNumber").toString());

            //本月预约数
            sht.getRow(9).getCell(5).setCellValue(reportData.get("thisMonthOrderNumber").toString());
            //本月到诊数
            sht.getRow(9).getCell(7).setCellValue(reportData.get("thisMonthVisitsNumber").toString());

            int rowCount = 12;// 热门套餐的开始行下标
            List<Map<String,Object>> hotPackage = (List<Map<String,Object>>)reportData.get("hotPackage");
            if(null != hotPackage && hotPackage.size() > 0){
                XSSFRow row = null;
                for (Map<String, Object> pkg : hotPackage) {
                    row = sht.getRow(rowCount);
                    row.getCell(4).setCellValue((String)pkg.get("name"));
                    row.getCell(5).setCellValue(pkg.get("count").toString());
                    row.getCell(6).setCellValue(((BigDecimal)pkg.get("proportion")).doubleValue());
                    row.getCell(7).setCellValue((String)pkg.get("remark"));
                    rowCount++;
                }
            }
            // 4.输出给输出流实现下载
            wk.write(os);
            os.flush();



            os.close();
            wk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/findMemberCountBy2Month")
    public Result getfindMemberCountBy2Month(String startMonth,String endMonth){
        startMonth+="-01";
        endMonth+="-31";
        List<String> DateBetween=null;
        List<Integer> memberCount=null;
        try {
            DateBetween  = DateUtils.getMonthBetween(startMonth, endMonth, "yyyy-MM");

            memberCount = memberService.findMemberCount(DateBetween);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("months",DateBetween);
        result.put("memberCount",memberCount);
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,result);
    }

    /**
     * 性别分析
     *
     * @return
     */
    @GetMapping("/getGender")
    public Result getGender() {
        List<Map<String, Object>> genderCount = reportService.getGender();
        List<String> genderNames = new ArrayList<>();
        for (Map<String, Object> map : genderCount) {
            genderNames.add((String) map.get("name"));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("genderNames", genderNames);
        map.put("genderCount", genderCount);
        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS, map);
    }

    /**
     * 年龄分析
     *
     * @return
     */
    @GetMapping("/getAge")
    public Result getAge() {
        List<Map<String, Object>> ageData = reportService.getAge();
        List<String> ageName = new ArrayList<>();
        for (Map<String, Object> map : ageData) {
            ageName.add((String) map.get("name"));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ageName", ageName);
        map.put("ageData", ageData);
        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS, map);
    }


}
