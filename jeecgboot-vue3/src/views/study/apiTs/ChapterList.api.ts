import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/chapterList/chapterList/list',
  save='/chapterList/chapterList/add',
  edit='/chapterList/chapterList/edit',
  deleteOne = '/chapterList/chapterList/delete',
  deleteBatch = '/chapterList/chapterList/deleteBatch',
  importExcel = '/chapterList/chapterList/importExcel',
  exportXls = '/chapterList/chapterList/exportXls',
  objectDataList = '/chapterList/chapterList/listObjectDataByMainId',
  objectDataSave='/chapterList/chapterList/addObjectData',
  objectDataEdit='/chapterList/chapterList/editObjectData',
  objectDataDelete = '/chapterList/chapterList/deleteObjectData',
  objectDataDeleteBatch = '/chapterList/chapterList/deleteBatchObjectData',
  selectDataByObjectCode = '/chapterList/chapterList/selectDataByObjectCode',
  updateWatch = '/chapterList/chapterList/updateWatch',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
/**
 * 列表接口
 * @param params
 */
export const objectDataList = (params) => {
  if(params['chapterCode']){
    return defHttp.get({url: Api.objectDataList, params});
  }
  return Promise.resolve({});
}


/**
 * 删除单个
 */
export const objectDataDelete = (params,handleSuccess) => {
  return defHttp.delete({url: Api.objectDataDelete, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const objectDataDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.objectDataDeleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const  objectDataSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.objectDataEdit : Api.objectDataSave;
  return defHttp.post({url: url, params});
}
/**
 * 导入
 */
export const objectDataImportUrl = '/chapterList/chapterList/importObjectData'

/**
 * 导出
 */
export const objectDataExportXlsUrl = '/chapterList/chapterList/exportObjectData'
/**
 * 列表接口, 根据课程编码查询数据
 * @param params
 */
export const selectDataByObjectCode = (params) =>
  defHttp.post({url: Api.selectDataByObjectCode, params});

/**
 * 更新watch字段
 * @param params
 */
export const updateWatch = (params) => {
  defHttp.post({url: Api.updateWatch, params});
}
