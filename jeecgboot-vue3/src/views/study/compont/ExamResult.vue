<template>
  <BasicModal v-bind="$attrs" @register="registerBasicModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']" @visible-change="getStart">
    <!--引用表格-->
    <BasicTable @register="registerTable" :expandedRowKeys="expandedRowKeys"  @expand="handleExpand">
      <!-- 内嵌table区域 begin -->
      <template #expandedRowRender="{record}">
        <a-tabs tabPosition="top">
          <a-tab-pane tab="答题详情" key="answerQuestionLogDetail" forceRender>
            <answerQuestionLogDetailSubTable :id="expandedRowKeys[0]"/>
          </a-tab-pane>
        </a-tabs>
      </template>
      <!-- 内嵌table区域 end -->
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
<!--        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }">
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <AnswerQuestionLogModal @register="registerModal" @success="handleSuccess"></AnswerQuestionLogModal>
    <!--  查看成绩分析页面  -->
    <achievement @register="registeAchievementModal" ></achievement>
  </BasicModal>
</template>

<script lang="ts" name="test-answerQuestionLog" setup>
  import {ref, computed, unref} from 'vue';
  //引入依赖
  import { FormSchema } from '/@/components/Form';
  import {useUserStore} from "../../../store/modules/user";
  // 当前登录人信息
  const userInfo = useUserStore().getUserInfo;
  const queryParams = ref({});
  import {BasicModal, useModal, useModalInner} from "@/components/Modal";
  const [registerBasicModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
    setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
    // 课程编码
    queryParams.value.objectCode = data.courseData.objectCode;
  });
  import AnswerQuestionLogModal from '../../test/log/components/AnswerQuestionLogModal.vue'
  import AnswerQuestionLogDetailSubTable from '../../test/log/subTables/AnswerQuestionLogDetailSubTable.vue'
  import achievement from "../../test/log/components/achievement.vue"

  import {columns} from '../../test/log/AnswerQuestionLog.data';
  import {list, list2, deleteOne, batchDelete, getImportUrl,getExportUrl} from '../../test/log/AnswerQuestionLog.api';
  import {useListPage} from "../../../hooks/system/useListPage";

  import {BasicTable, useTable, TableAction } from '/@/components/Table';
  import {getList} from "@/views/system/notice/notice.api";
  //注册model
  const [registerModal, {openModal}] = useModal();
  // 查看成绩弹窗
  const [registeAchievementModal, {openModal:openAchievementModal,setModalProps:setAchievementModalProps}] = useModal();

  //自定义表单字段
  const formSchemas: FormSchema[] = [
    {
      label: "创建人",
      field: 'createBy',
      component: 'Input',
      defaultValue: userInfo.username,
      dynamicDisabled: true
      //colProps: {span: 6},
    },
    {
      label: "创建时间",
      field: "createTime",
      component: 'RangePicker',
      componentProps: {
        valueType: 'Date',
        showTime:true
      },
      //colProps: {span: 6},
    },
    {
      label: "试卷名称",
      field: 'paperName',
      component: 'Input',
      //colProps: {span: 6},
    }
  ];

  //注册table数据
  const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
    tableProps:{
      title: 'answer_question_log',
      api: (params) => list2({...params,...queryParams.value }),
      // dataSource: getList;
      columns,
      canResize:false,
      formConfig: {
        //labelWidth: 120,
        schemas: formSchemas,
        autoSubmitOnEnter:true,
        showAdvancedButton:true,
        fieldMapToNumber: [
        ],
        fieldMapToTime: [
          ['createTime', ['createTime_begin', 'createTime_end'], 'YYYY-MM-DD HH:mm:ss'],
        ],
      },
      actionColumn: {
        width: 120,
        fixed:'right'
      },
    },
    exportConfig: {
      name:"answer_question_log",
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess
    },
  })

  const [registerTable, {reload},{ rowSelection, selectedRowKeys }] = tableContext

  // 展开key
  const expandedRowKeys = ref<any[]>([]);

  /**
   * 成绩查看
   */
  function achievementDetail(record: Recordable){
    console.log(record)
    // 设置为全屏展示
    setAchievementModalProps({defaultFullscreen:true})
    // 打开弹窗弹窗展示成绩信息,并将 log 信息传输过去
    openAchievementModal(true,record)

  }
  /**
   * 展开事件
   * */
  function handleExpand(expanded, record){
    expandedRowKeys.value=[];
    if (expanded === true) {
      expandedRowKeys.value.push(record.id)
    }
  }
  /**
   * 新增事件
   */
  function handleAdd() {
    openModal(true, {
      isUpdate: false,
      showFooter: true,
    });
  }
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: false,
    });
  }
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({id: record.id}, handleSuccess);
  }
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ids: selectedRowKeys.value},handleSuccess);
  }
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
  /**
   * 操作栏
   */
  function getTableAction(record){
    return [
      {
        label: '查看成绩',
        onClick: achievementDetail.bind(null, record),
      },
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }
    ]
  }


  /**
   * 下拉操作栏
   */
  function getDropDownAction(record){
    return [

    ]
  }

  function getStart(visible) {
    // 这样设置 是每次打开 页面的时候都进行一次数据请求
    // 如果不这样的话只会在第一次打开页面的时候才会进行数据请求，之后都会用缓存中的数据
    if (visible) {
      reload();
    }
  }
</script>

<style scoped>

</style>
