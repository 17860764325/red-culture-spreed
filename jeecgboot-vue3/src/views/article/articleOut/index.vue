<template>
  <div v-if="!modalvisable">
    <!--  背景板-->
    <a-row>
      <a-col :span="24">
        <img class="imageBack" src="@/assets/images/shqudingbu.png"/>
      </a-col>
    </a-row>
    <!--  文章-->
    <a-row style="display: flex;justify-content: center;align-items: center;margin-top: 10px;">
      <a-card style="width: 100%">
        <template #extra><a-button type="primary" @click="addPage">发布帖子</a-button></template>
      </a-card>
      <a-col v-for="item in allArticleList" :span="24">
        <a-card class="red-theme-card" :title="item.text" style="width: 99%;height:500px;margin: 10px;">
          <template #extra><a @click="indexDetailMore(item)">more</a></template>
          <template #cover>
            <img  alt="example" src="@/assets/images/shqudingbus.png" />
          </template>
          <!--        专栏文章列表-->
          <div style="width:100%; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" v-for="no in pages(item.value)">
            <a @click="pageDetailOpen(no)" style="color: black">{{ no.title }}</a>
            <br/>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
  <indexDetail v-if="modalvisable" :item="index" @cancel="indexDetailCencel" @view="pageDetailOpen"></indexDetail>
  <pageDetail v-if="openModalVisable" @register="registerModal" @cancel="modalClose"></pageDetail>
  <PageInfoModal @register="addPageregisterModal"></PageInfoModal>
</template>
<script lang="ts" setup>
import {ajaxGetDictItems} from '/@/utils/dict';
import {ref} from "vue";
import indexDetail from "./comenpents/indexDetail.vue"
import {getAllPageQuery, list} from "../page/PageInfo.api"
import {useModal} from "@/components/Modal";
import  pageDetail from "./comenpents/pageDetail.vue"
import PageInfoModal  from "@/views/article/page/components/PageInfoModal.vue";

// 所有栏目分类
const allArticleList = ref([])
// 所有文章
const pagesAllList = ref([])

// 是否打开弹窗
const openModalVisable = ref(false)

// 文章详情的弹窗
const [registerModal, {openModal,setModalProps,closeModal}] = useModal();
const [addPageregisterModal, {openModal:addPageOpenModal,closeModal:closePageModal}] = useModal();

// 获取所有栏目分类
async function getAll() {
  await ajaxGetDictItems("article_classify", null).then((res) => {
    allArticleList.value = res
  })
  await list({}).then(res => {
    pagesAllList.value = res.records
  })
}

getAll()

function pages(type) {
  console.log(pagesAllList.value,"all")
  const result = pagesAllList.value.filter(item => item.pageType === type)
  const resultNew = [];
  if (result.length > 5) {
    for (let i = 0; i < 5; i++) {
        resultNew.push(result[i])
    }
    return resultNew
  } else {
    console.log(result,type)
    return result
  }
}

// 详情分类列表分类组件是否渲染的控制变量
const modalvisable = ref(false)
const index = ref()

// 打开弹窗方法
function indexDetailMore(data) {
  index.value = data
  modalvisable.value = true
}

// 关闭弹窗的方法
function indexDetailCencel() {
  modalvisable.value = false
}

// 文章详情打开
function pageDetailOpen(data){
  openModalVisable.value = true
  setTimeout(()=>{
    setModalProps({defaultFullscreen:true})
    openModal(true ,data)
  },800)

}
// 关闭弹窗
function modalClose(){
  openModalVisable.value = false
  closeModal(true)
}

function addPage(){
  // 新增帖子
  addPageOpenModal(true,{  isUpdate: false,
    showFooter: true,})
}

</script>
<style>
.red-theme-card.ant-card {
  /* 红色背景 */
  background-color: white; /* 可根据需求调整红色色调 */
  /* 边框半径 */
  border-radius: 8px;
  /* 金色边框 */
  border: 4px solid gold; /* 麦穗效果 */
  /* 外部阴影 */
  box-shadow: 0 0 10px rgba(255, 255, 0, 0.5);
}

/* 覆盖 Ant Design 组件的默认样式 */
.red-theme-card.ant-card > .ant-card-head {
  /* 卡片头部背景保持红色 */
  color: black; /* 文字颜色 */
}

.red-theme-card.ant-card > .ant-card-body {
  /* 卡片内容区背景保持红色 */
  background-color: white;
  color: white; /* 文字颜色 */
}


</style>
