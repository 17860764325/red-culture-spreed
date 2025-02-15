<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']">
    <!--    页面头部以及标题展示-->
    <a-row style="display: flex;flex-wrap: nowrap">
      <a-row style="display: flex;flex-direction: column;">
        <h1 style="color: red;">红色文化展示</h1>
        <h5 style="color: black;padding-left: 30px">红色文化宣传平台</h5>
      </a-row>
      <img src="../../../../static/topOther.jpg" style="width: 92%">
    </a-row>
    <a-divider style="height: 2px; background-color: #b70b0b"/>
    <img src="../../../../static/top.jpg" style="width: 100%;"/>
    <!--面包屑-->
    <a-breadcrumb style="padding: 20px;border: black 1px solid;margin-top: 10px">
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item>精神谱系大家谈</a-breadcrumb-item>
      <a-breadcrumb-item> {{ flagName }}</a-breadcrumb-item>
      <a-breadcrumb-item> 正文</a-breadcrumb-item>
    </a-breadcrumb>
    <!--    正文内容展示-->
    <!--    标题模块-->
    <div style="text-align: center">
      <p style="font-size: 50px">{{ pageInfo.pageTitle }}</p>
      <p style="font-size: 30px">{{ pageInfo.pageLittleTitle }}</p>
      <p style="font-size: 16px">{{ pageInfo.pageDetaWriter }}</p>
    </div>
    <!--    分割线-->
    <a-divider/>
    <idv style="">
      <p v-for="item in pageContextList"
         style="text-indent: 2em;font-size: 20px;line-height: 3;width: 70%;margin-left: 15%">
        {{
          item
        }}</p>

    </idv>

  </BasicModal>
</template>
<script lang="ts" setup>
import {defineComponent, ref, computed, unref, reactive} from 'vue';
import {BasicModal, useModalInner} from '/@/components/Modal';
import {pageInfoList} from '../../redSpirit/RedSpirit.api'

const flagName = ref()
const pageInfo = ref<Object>({})
const pageContextList = ref<Array>([])
//表单赋值，打开弹窗页面中传输的患者信目前就存储在data变量中
const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
  // 面包屑数据渲染
  flagName.value = data.flagName
  // 打印传输过来的文章编码
  console.log(data.pageNumber)
  pageInfoList({pageNumber: data.pageNumber}).then(res => {
    if (res.records.length > 0) {
      // 获取数据
      console.log(res.records[0])
      // 进行赋值
      pageInfo.value = res.records[0]
      console.log(res.records[0].pageContext)
      console.log(res.records[0].pageContext.split('|'))
      // 将正文进行分割
      pageContextList.value = res.records[0].pageContext.split('|')
    }
  })
});


</script>
<style scoped lang="less">

</style>
