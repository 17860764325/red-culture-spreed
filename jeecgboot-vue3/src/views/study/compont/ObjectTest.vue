

<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="title" :width="896"
              @ok="closeModal" :helpMessage="['提示1', '提示2']">
    <h1>kechn</h1>
  </BasicModal>
</template>

<script setup lang="ts">
import {BasicModal, useModalInner} from "@/components/Modal";
const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  setModalProps({defaultFullscreen: true, showCancelBtn: true, showOkBtn: true});
});
</script>

<style scoped lang="less">

</style>
