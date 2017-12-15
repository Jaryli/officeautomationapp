package com.app.officeautomationapp.common;

/**
 * Created by CS-711701-00027 on 2017/4/28.
 */

public class Constants {
    public static final String ip="http://101.132.162.181";
//    public static final String port="89";//正式环境
    public static final String port="8083";//测试环境

    public final static String address=ip+":"+port;

    public final static String login=address+"/api/V1/Login/Login";//登录

    public final static String GetBanner=address+"/api/V1/Sys/GetBanner";//获取banner信息

    public final static String UpdatePhoto=address+"/api/V1/User/UpdatePhoto";//更新头像

    public final static String  GetUserInfo=address+"/api/V1/User/GetUserInfo";//获取我的资料

    public final static String getMessage=address+"/api/V1/Sys/GetPageList";//获取消息接口//?pageIndex={pageIndex}&pageSize={pageSize}

    public final static String getReadRecords=address+"/api/V1/Sys/GetReadRecords";//获取消息已读人员列表?noticeId=115

    public final static String getGetMessageDetail="/api/V1/Sys/GetNoticeDetails";//获取消息详情，目前只需调用一下?noticeId={noticeId}

    public final static String getMyProject=address+"/api/V1/Project/GetMyProjectList";//获取我参与的工程

    public final static String getSaveHiddenProject=address+"/api/V1/HideProject/SaveHideProject";//保存隐藏工程

    public final static String getHiddenProject=address+"/api/V1/HideProject/GetHideProjectPageList";//?projectName= &pageIndex=1&pageSize=20

    public final static String deleteHiddenProject=address+"/api/V1/HideProject/DeleteHideProject";//删除我的隐藏工程{id}

    public final static String addArchMachine=address+"/api/V1/Flow/AddArchMachine";//台班申请



    public final static String addArchJob=address+"/api/V1/Flow/AddArchJob";//点工申请

    public final static String addArchSign=address+"/api/V1/Flow/AddArchSign";//用章申请

    public final static String addArchLeave=address+"/api/V1/Flow/AddArchLeave";//请假申请

    public final static String getToUser=address+"/api/V1/WorkFlow/SelectUsersByRealName";//获取获取审批人?realName=

    public final static String IMG_PATH=address+"/AppFileServer/";//图片地址

    public final static String getThings=address+"/api/V1/Res/GetList";//根据商品名称或规格获取物品列表?resNameOrSpecial={resNameOrSpecial}

    public final static String addReceiveThing=address+"/api/V1/Res/ApplyResTransaction";//物品领用申请提交?resId={resId}&num={num}&projectId={projectId}&toUser={toUser}&remark={remark}


    public final static String FlowGuidArchMachine="d1693633-e78a-4f7d-97fc-a3742639eaa9"; //机械台班结算单审批 api/V1/WorkFlow/GetFlowList

    public final static String FlowGuidaddArchJob="9fac41f7-fd37-4959-8bc0-7c35922fd204";//现场用工签工单审批

    public final static String FlowGuidaddArchSign="f58f8da6-a7f2-45d9-8fb1-70ec0bdc83f2";//用章管理流程


    public final static String getMyMenu=address+"/api/V1/Menu/GetPageList";//获取我的菜单列表

    public final static String updateMenuIndex=address+"/api/V1/Menu/UpdateMenuIndex";//?indexIds={indexIds}&notIndexIds={notIndexIds}//添加菜单到应用首页

    public final static String GetPersonList=address+"/api/V1/User/GetAddressList";//?pageIndex={pageIndex}&pageSize={pageSize}//获取通信录


    public final static String GetMyTaskList=address+"/api/V1/Task/GetMyTaskList";//?pageIndex={pageIndex}&pageSize={pageSize}"获取我参与的任务 我的任务


    public final static String GetTaskDetail=address+"/api/V1/Task/GetTaskDetail";//?taskId={taskId} 获取任务详情

    public final static String AddTodoDetail=address+"/api/V1/Task/AddTodoDetail";//我的任务办理

    public final static String GetMyDoingWork=address+"/api/V1/WorkFlow/GetMyDoingWork";//获取待我审批的工作//?workName={workName}&workGuid={workGuid}&pageIndex={pageIndex}&pageSize={pageSize}

    public final static String GetMyPostWork=address+"/api/V1/WorkFlow/GetMyPostWork";//获取我发起的工作//?workName={workName}&workGuid={workGuid}&pageIndex={pageIndex}&pageSize={pageSize}

    public final static String GetMyWorkFlows=address+"/api/V1/WorkFlow/GetMyWorkFlows";//获取我的工作?workName={workName}&workGuid={workGuid}&pageIndex={pageIndex}&pageSize={pageSize}

    public final static String GetIndexTip=address+"/api/V1/WorkFlow/GetIndexTip";//获取首页待审批角标数量和我的工作角标数量
    public final static String GetTodoTip=address+"/api/V1/WorkFlow/GetTodoTip";//获取待办角标数量（含各子分类角标数量）

    public final static String GetFlowList=address+"/api/V1/WorkFlow/GetFlowList";//获取工作流分类列表


    public final static String GetUnCheckApplyResList=address+"/api/V1/Res/GetUnCheckApplyResList";//获取待我审批的物品列表


    public final static String HandleResApply =address+"/api/V1/Res/HandleResApply";//领用审批post id：记录主键编号，resultCode：审批结果，2-同意，9-不同意。msg：审批意见。

    public final static String GetWorkView =address+"/api/V1/WorkFlow/GetWorkView";//根据步骤Id获取工作详情?hid={hid}


    public final static String GetWorkInfo =address+"/api/V1/WorkFlow/GetWorkInfo";//根据工作Id获取工作详情?workId={workId}

    public final static String HandleWork =address+"/api/V1/WorkFlow/HandleWork";//工作办理

    public final static String PublishTask =address+"/api/V1/Task/PublishTask";//任务发起


    public final static String GetTreeMainList=address+"/api/V1/Flow/GetTreeMainList";//获取苗木采购列表?orderCode={orderCode}&projectName={projectName}&pageIndex={pageIndex}&pageSize={pageSize}
    public final static String GetCivilMainList=address+"/api/V1/Flow/GetCivilMainList";//获取土建采购列表?orderCode={orderCode}&projectName={projectName}&pageIndex={pageIndex}&pageSize={pageSize}

    public final static String AddTreeApply=address+"/api/V1/Flow/AddTreeApply";//添加苗木验收表头
    public final static String AddCivilApply=address+"/api/V1/Flow/AddCivilApply";//添加土建验收表头

    public final static String AddTreeDetails=address+"/api/V1/Flow/AddTreeDetails";//添加苗木验收表头
    public final static String AddCivilDetails=address+"/api/V1/Flow/AddCivilDetails";//添加土建验收明细

    public final static String GetTreeDetailsList=address+"/api/V1/Flow/GetTreeDetailsList";//苗木采购或验收明细?orderCode={}
    public final static String GetCivilDetailsList=address+"/api/V1/Flow/GetCivilDetailsList";//获取土建采购或验收明细

    public final static String GetSupplyPageList=address+"/api/V1/WorkFlow/GetSupplyPageList";//获取供应商?pageIndex=1&pageSize=9999&supplyName=
    //点工查询url：
    public final static String DIANGONG_URL=address+"/Home/JobList?userId=";

    //台班查询url：
    public final static String TAIBAN_URL=address+"/Home/MachineList?userId=";

    //苗木明细url：
    public final static String MIAOMU_URL=address+"/Home/TreeCheckList?userId=";

    //土建明细url：
    public final static String TUJIAN_URL=address+"/Home/CivilCheckList?userId=";



}
