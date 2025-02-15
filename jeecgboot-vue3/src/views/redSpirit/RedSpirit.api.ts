import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/redSpirit/redSpirit/list',
  save='/redSpirit/redSpirit/add',
  edit='/redSpirit/redSpirit/edit',
  deleteOne = '/redSpirit/redSpirit/delete',
  deleteBatch = '/redSpirit/redSpirit/deleteBatch',
  importExcel = '/redSpirit/redSpirit/importExcel',
  exportXls = '/redSpirit/redSpirit/exportXls',
  pageInfoList = '/redSpirit/redSpirit/listPageInfoByMainId',
  pageInfoSave='/redSpirit/redSpirit/addPageInfo',
  pageInfoEdit='/redSpirit/redSpirit/editPageInfo',
  pageInfoDelete = '/redSpirit/redSpirit/deletePageInfo',
  pageInfoDeleteBatch = '/redSpirit/redSpirit/deleteBatchPageInfo',
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
export const pageInfoList = (params) => {
  if(params['pageNumber']){
    return defHttp.get({url: Api.pageInfoList, params});
  }
  return Promise.resolve({});
}


/**
 * 删除单个
 */
export const pageInfoDelete = (params,handleSuccess) => {
  return defHttp.delete({url: Api.pageInfoDelete, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const pageInfoDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.pageInfoDeleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const  pageInfoSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.pageInfoEdit : Api.pageInfoSave;
  return defHttp.post({url: url, params});
}
/**
 * 导入
 */
export const pageInfoImportUrl = '/redSpirit/redSpirit/importPageInfo'

/**
 * 导出
 */
export const pageInfoExportXlsUrl = '/redSpirit/redSpirit/exportPageInfo'
